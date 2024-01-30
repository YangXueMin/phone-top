package com.ruoyi.shop.service.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.github.binarywang.wxpay.bean.notify.WxPayNotifyResponse;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.bean.order.WxPayMpOrderResult;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.ruoyi.common.annotation.ShopScope;
import com.ruoyi.common.config.WechatConfiguration;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.entity.Member;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.SnowflakeGenerator;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.uuid.IdUtils;
import com.ruoyi.framework.websocket.WebSocketServerMessage;
import com.ruoyi.shop.domain.BalanceInfo;
import com.ruoyi.shop.domain.OfflineOrder;
import com.ruoyi.shop.mapper.BalanceInfoMapper;
import com.ruoyi.shop.mapper.OfflineOrderMapper;
import com.ruoyi.shop.service.IOfflineOrderService;
import com.ruoyi.system.mapper.MemberMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * 线下订单Service业务层处理
 *
 * @author ruoyi
 * @date 2024-01-14
 */
@Service
@Slf4j
public class OfflineOrderServiceImpl implements IOfflineOrderService {
    @Autowired
    private OfflineOrderMapper offlineOrderMapper;
    @Autowired
    private BalanceInfoMapper balanceInfoMapper;
    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private WechatConfiguration wechatConfiguration;

    /**
     * 查询线下订单
     *
     * @param id 线下订单主键
     * @return 线下订单
     */
    @Override
    public OfflineOrder selectOfflineOrderById(Long id) {
        return offlineOrderMapper.selectOfflineOrderById(id);
    }

    /**
     * 查询线下订单列表
     *
     * @param offlineOrder 线下订单
     * @return 线下订单
     */
    @Override
    @ShopScope(shopAlias = "a")
    public List<OfflineOrder> selectOfflineOrderList(OfflineOrder offlineOrder) {
        return offlineOrderMapper.selectOfflineOrderList(offlineOrder);
    }

    /**
     * 查询线下订单列表
     *
     * @param offlineOrder 线下订单
     * @return 线下订单
     */
    @Override
    public List<OfflineOrder> selectOfflineOrderListApi(OfflineOrder offlineOrder) {
        return offlineOrderMapper.selectOfflineOrderList(offlineOrder);
    }

    /**
     * 新增线下订单
     *
     * @param offlineOrder 线下订单
     * @return 结果
     */
    @Override
    public OfflineOrder insertOfflineOrder(OfflineOrder offlineOrder) {
        String orderNumber = SnowflakeGenerator.generateOrderNumber();
        offlineOrder.setOrderNumber(orderNumber);
        offlineOrder.setCreateTime(DateUtils.getNowDate());
        offlineOrder.setOrderStatus("1");
        final int i = offlineOrderMapper.insertOfflineOrder(offlineOrder);
        if (i > 0) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", "offlineOrder");
            jsonObject.put("data", offlineOrder);
            WebSocketServerMessage.sendInfo(jsonObject.toString(), offlineOrder.getMemberId());
        }
        return offlineOrder;
    }


    /**
     * 修改线下订单
     *
     * @param offlineOrder 线下订单
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateOfflineOrder(OfflineOrder offlineOrder) {
        offlineOrder.setUpdateTime(DateUtils.getNowDate());
        final int i = offlineOrderMapper.updateOfflineOrder(offlineOrder);
        if (i > 0) {
            if (StringUtils.equals("2", offlineOrder.getOrderStatus())) {
                Member member = memberMapper.selectMemberById(offlineOrder.getMemberId());
                BigDecimal beforeBalance = member.getBalance();
                member.setBalance(member.getBalance().subtract(offlineOrder.getMoney()));
                member.setUpdateTime(DateUtils.getNowDate());
                memberMapper.updateMember(member);
                //添加余额消费记录
                BalanceInfo balanceInfo = new BalanceInfo(offlineOrder.getMemberId(), "2", offlineOrder.getId(), beforeBalance, member.getBalance(), offlineOrder.getMoney());
                balanceInfo.setCreateTime(DateUtils.getNowDate());
                balanceInfoMapper.insertBalanceInfo(balanceInfo);
            }
        }
        return i;
    }

    @Override
    public WxPayMpOrderResult pay(OfflineOrder offlineOrder) {
        Member member = SecurityUtils.getLoginUser().getMember();
        WxPayUnifiedOrderRequest request = new WxPayUnifiedOrderRequest();
        //随机字符串
        request.setNonceStr(IdUtils.generateNonceStr());
        //加密方式
        request.setSignType("MD5");
        //订单号
        request.setOutTradeNo(offlineOrder.getOrderNumber());
        //金额，以分为单位
        request.setTotalFee(offlineOrder.getMoney().multiply(BigDecimal.valueOf(100L)).intValue());
        // 用户ip
        request.setSpbillCreateIp("127.0.0.1");
        //回调通知地址（必须外网能访问的地址）
        request.setNotifyUrl(Constants.URL + "api/shop/offlineOrder/payNotify");
        //小程序支付
        request.setTradeType("JSAPI");
        //小程序用户openid
        request.setOpenid(member.getOpenId());
        request.setBody(offlineOrder.getName());
        try {
            return wechatConfiguration.wxPayService().createOrder(request);
        } catch (WxPayException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String payNotify(String xmlData) {
        try {
            WxPayOrderNotifyResult notifyResult = wechatConfiguration.wxPayService().parseOrderNotifyResult(xmlData);
            if (StringUtils.equals("SUCCESS", notifyResult.getReturnCode())) {
                List<OfflineOrder> orderList = offlineOrderMapper.selectOfflineOrderByOrderNumber(notifyResult.getOutTradeNo());
                if (orderList != null && orderList.size() > 0) {
                    OfflineOrder offlineOrder = orderList.get(0);
                    if (StringUtils.equals("1", offlineOrder.getOrderStatus()) && offlineOrder.getMoney().multiply(BigDecimal.valueOf(100L)).intValue() == notifyResult.getTotalFee()) {
                        offlineOrder.setOrderStatus("2");
                        offlineOrder.setPayType("2");
                        offlineOrder.setPayTime(notifyResult.getTimeEnd());
                        offlineOrder.setPayResult(JSON.toJSONString(notifyResult));
                        offlineOrder.setUpdateTime(DateUtils.getNowDate());
                        offlineOrderMapper.updateOfflineOrder(offlineOrder);
                        return WxPayNotifyResponse.success("成功");
                    }
                }
                log.error("支付回调失败，回调信息：{}", JSON.toJSONString(notifyResult));
            }
        } catch (WxPayException e) {
            e.printStackTrace();
        }
        return WxPayNotifyResponse.fail("失败");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int balanceRefund(OfflineOrder offlineOrder) {
        offlineOrder.setOrderStatus("3");
        offlineOrder.setUpdateTime(DateUtils.getNowDate());
        final int i = offlineOrderMapper.updateOfflineOrder(offlineOrder);
        if (i > 0) {
            Member member = memberMapper.selectMemberById(offlineOrder.getMemberId());
            BigDecimal beforeBalance = member.getBalance();
            member.setBalance(member.getBalance().add(offlineOrder.getMoney()));
            member.setUpdateTime(DateUtils.getNowDate());
            memberMapper.updateMember(member);
            //删除余额消费记录
            balanceInfoMapper.deleteBalanceInfoByOrderIdAndOrderType(offlineOrder.getId(), "2");
        }
        return i;
    }

    /**
     * 批量删除线下订单
     *
     * @param ids 需要删除的线下订单主键
     * @return 结果
     */
    @Override
    public int deleteOfflineOrderByIds(Long[] ids) {
        return offlineOrderMapper.deleteOfflineOrderByIds(ids);
    }

    /**
     * 删除线下订单信息
     *
     * @param id 线下订单主键
     * @return 结果
     */
    @Override
    public int deleteOfflineOrderById(Long id) {
        return offlineOrderMapper.deleteOfflineOrderById(id);
    }
}

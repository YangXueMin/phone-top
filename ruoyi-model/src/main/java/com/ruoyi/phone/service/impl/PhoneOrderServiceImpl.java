package com.ruoyi.phone.service.impl;

import java.math.BigDecimal;
import java.util.List;

import com.alibaba.fastjson2.JSON;
import com.github.binarywang.wxpay.bean.notify.WxPayNotifyResponse;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.bean.order.WxPayMpOrderResult;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.ruoyi.common.config.WechatConfiguration;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.entity.Member;
import com.ruoyi.common.core.domain.entity.WechatConfig;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.SnowflakeGenerator;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.uuid.IdUtils;
import com.ruoyi.phone.domain.PhoneBalanceLog;
import com.ruoyi.phone.domain.PhoneMemberCardLog;
import com.ruoyi.phone.mapper.PhoneBalanceLogMapper;
import com.ruoyi.system.mapper.MemberMapper;
import com.ruoyi.system.service.IWechatConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.phone.mapper.PhoneOrderMapper;
import com.ruoyi.phone.domain.PhoneOrder;
import com.ruoyi.phone.service.IPhoneOrderService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 订单记录Service业务层处理
 *
 * @author ruoyi
 * @date 2024-03-05
 */
@Service
public class PhoneOrderServiceImpl implements IPhoneOrderService {
    @Autowired
    private PhoneOrderMapper phoneOrderMapper;
    @Autowired
    private WechatConfiguration wechatConfiguration;
    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private PhoneBalanceLogMapper phoneBalanceLogMapper;
    @Autowired
    private IWechatConfigService wechatConfigService;

    /**
     * 查询订单记录
     *
     * @param id 订单记录主键
     * @return 订单记录
     */
    @Override
    public PhoneOrder selectPhoneOrderById(Long id) {
        return phoneOrderMapper.selectPhoneOrderById(id);
    }

    /**
     * 查询订单记录列表
     *
     * @param phoneOrder 订单记录
     * @return 订单记录
     */
    @Override
    public List<PhoneOrder> selectPhoneOrderList(PhoneOrder phoneOrder) {
        return phoneOrderMapper.selectPhoneOrderList(phoneOrder);
    }

    /**
     * 新增订单记录
     *
     * @param phoneOrder 订单记录
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public PhoneOrder insertPhoneOrder(PhoneOrder phoneOrder) {
        phoneOrder.setOrderNo(SnowflakeGenerator.generateOrderNumber());
        phoneOrder.setCreateTime(DateUtils.getNowDate());

        Member member = memberMapper.selectMemberById(phoneOrder.getMemberId());
        BigDecimal balance = member.getBalance();
        phoneOrder.setStatus("1");
        if(StringUtils.equals("1",phoneOrder.getPayType())){
            phoneOrder.setPayMoney(phoneOrder.getMoney());
            phoneOrder.setPayBalance(BigDecimal.ZERO);
            phoneOrder.setPayStatus("1");
        }else{
            BigDecimal money;
            if (phoneOrder.getMoney().compareTo(member.getBalance()) > 0) {
                member.setBalance(BigDecimal.ZERO);
                phoneOrder.setPayStatus("1");
                phoneOrder.setPayMoney(phoneOrder.getMoney().subtract(balance));
                money = balance;
                phoneOrder.setPayBalance(balance);
            } else {
                member.setBalance(balance.subtract(phoneOrder.getMoney()));
                phoneOrder.setPayBalance(phoneOrder.getMoney());
                money = phoneOrder.getMoney();
                phoneOrder.setPayStatus("2");
                phoneOrder.setPayMoney(BigDecimal.ZERO);
            }
            memberMapper.updateMember(member);
            //添加余额变更记录
            PhoneBalanceLog phoneBalanceLog = new PhoneBalanceLog();
            phoneBalanceLog.setCompanyId(phoneOrder.getCompanyId());
            phoneBalanceLog.setAppId(phoneOrder.getAppId());
            phoneBalanceLog.setMemberId(member.getMemberId());
            phoneBalanceLog.setType("2");
            phoneBalanceLog.setBalanceAfter(balance);
            phoneBalanceLog.setMoney(money);
            phoneBalanceLog.setBalanceBefore(member.getBalance());
            phoneBalanceLog.setCreateTime(DateUtils.getNowDate());
            phoneBalanceLogMapper.insertPhoneBalanceLog(phoneBalanceLog);
        }
        phoneOrderMapper.insertPhoneOrder(phoneOrder);
        return phoneOrder;
    }

    /**
     * 修改订单记录
     *
     * @param phoneOrder 订单记录
     * @return 结果
     */
    @Override
    public int updatePhoneOrder(PhoneOrder phoneOrder) {
        phoneOrder.setUpdateTime(DateUtils.getNowDate());
        return phoneOrderMapper.updatePhoneOrder(phoneOrder);
    }

    /**
     * 批量删除订单记录
     *
     * @param ids 需要删除的订单记录主键
     * @return 结果
     */
    @Override
    public int deletePhoneOrderByIds(Long[] ids) {
        return phoneOrderMapper.deletePhoneOrderByIds(ids);
    }

    /**
     * 删除订单记录信息
     *
     * @param id 订单记录主键
     * @return 结果
     */
    @Override
    public int deletePhoneOrderById(Long id) {
        return phoneOrderMapper.deletePhoneOrderById(id);
    }

    @Override
    public WxPayMpOrderResult pay(PhoneOrder phoneOrder) {
        Member member = SecurityUtils.getLoginUser().getMember();
        WxPayUnifiedOrderRequest request = new WxPayUnifiedOrderRequest();
        //随机字符串
        request.setNonceStr(IdUtils.generateNonceStr());
        //加密方式
        request.setSignType("MD5");
        //订单号
        request.setOutTradeNo(phoneOrder.getOrderNo());
        //金额，以分为单位
        request.setTotalFee(phoneOrder.getPayMoney().multiply(BigDecimal.valueOf(100L)).intValue());
        // 用户ip
        request.setSpbillCreateIp("127.0.0.1");
        //回调通知地址（必须外网能访问的地址）
        request.setNotifyUrl(Constants.URL + "/api/phone/memberCard/payNotify?appid=" + phoneOrder.getAppId());
        //公众号支付
        request.setTradeType("JSAPI");
        //小程序用户openid
        request.setOpenid(member.getOpenId());
        StringBuilder sb = new StringBuilder();
        if(StringUtils.equals("1",phoneOrder.getType())){
            sb.append("手机充值");
        }else{
            sb.append("电费充值");
        }
        request.setBody(sb.toString());
        try {
            final WechatConfig wechatConfig = wechatConfigService.selectWechatConfigByAppId(phoneOrder.getAppId());
            return wechatConfiguration.wxPayService(wechatConfig).createOrder(request);
        } catch (WxPayException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String payNotify(String appid, String xmlData) {
        try {
            final WechatConfig wechatConfig = wechatConfigService.selectWechatConfigByAppId(appid);
            WxPayOrderNotifyResult notifyResult = wechatConfiguration.wxPayService(wechatConfig).parseOrderNotifyResult(xmlData);
            if (StringUtils.equals("SUCCESS", notifyResult.getReturnCode())) {
                List<PhoneOrder> phoneOrderList = phoneOrderMapper.selectPhoneOrderListByOrderNo(notifyResult.getOutTradeNo());
                if (phoneOrderList != null && phoneOrderList.size() > 0) {
                    PhoneOrder phoneOrder = phoneOrderList.get(0);
                    if (!StringUtils.equals("2", phoneOrder.getPayStatus())) {
                        phoneOrder.setPayStatus("2");
                        phoneOrder.setPayTime(notifyResult.getTimeEnd());
                        phoneOrder.setPayResult(JSON.toJSONString(notifyResult));
                        phoneOrder.setUpdateTime(DateUtils.getNowDate());
                        phoneOrderMapper.updatePhoneOrder(phoneOrder);
                    }
                }
                return WxPayNotifyResponse.success("成功");
            }
        } catch (WxPayException e) {
            e.printStackTrace();
        }
        return WxPayNotifyResponse.fail("失败");
    }
}

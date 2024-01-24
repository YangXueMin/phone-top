package com.ruoyi.shop.service.impl;

import com.alibaba.fastjson2.JSON;
import com.github.binarywang.wxpay.bean.notify.WxPayNotifyResponse;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.bean.notify.WxPayRefundNotifyResult;
import com.github.binarywang.wxpay.bean.order.WxPayMpOrderResult;
import com.github.binarywang.wxpay.bean.request.WxPayRefundRequest;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.bean.result.WxPayRefundResult;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.ruoyi.common.config.WechatConfiguration;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.entity.Member;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.SnowflakeGenerator;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.uuid.IdUtils;
import com.ruoyi.shop.domain.RechargeOrder;
import com.ruoyi.shop.domain.RechargeOrderCoupon;
import com.ruoyi.shop.domain.ShopCard;
import com.ruoyi.shop.mapper.MemberMapper;
import com.ruoyi.shop.mapper.RechargeOrderCouponMapper;
import com.ruoyi.shop.mapper.RechargeOrderMapper;
import com.ruoyi.shop.mapper.ShopCardMapper;
import com.ruoyi.shop.service.IRechargeOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * 充值记录Service业务层处理
 *
 * @author ruoyi
 * @date 2024-01-11
 */
@Service
public class RechargeOrderServiceImpl implements IRechargeOrderService {
    @Autowired
    private RechargeOrderMapper rechargeOrderMapper;
    @Autowired
    private RechargeOrderCouponMapper rechargeOrderCouponMapper;
    @Autowired
    private WechatConfiguration wechatConfiguration;
    @Autowired
    private ShopCardMapper shopCardMapper;
    @Autowired
    private MemberMapper memberMapper;

    /**
     * 查询充值记录
     *
     * @param id 充值记录主键
     * @return 充值记录
     */
    @Override
    public RechargeOrder selectRechargeOrderById(Long id) {
        RechargeOrder rechargeOrder = rechargeOrderMapper.selectRechargeOrderById(id);
        if(rechargeOrder != null){
            RechargeOrderCoupon rechargeOrderCoupon = new RechargeOrderCoupon();
            rechargeOrderCoupon.setRechargeId(id);
            rechargeOrder.setCouponList(rechargeOrderCouponMapper.selectRechargeOrderCouponList(rechargeOrderCoupon));
        }
        return rechargeOrder;
    }

    /**
     * 查询充值记录列表
     *
     * @param rechargeOrder 充值记录
     * @return 充值记录
     */
    @Override
    public List<RechargeOrder> selectRechargeOrderList(RechargeOrder rechargeOrder) {
        List<RechargeOrder> rechargeOrderList = rechargeOrderMapper.selectRechargeOrderList(rechargeOrder);
        if (rechargeOrderList.size() > 0) {
            for (RechargeOrder order : rechargeOrderList) {
                RechargeOrderCoupon rechargeOrderCoupon = new RechargeOrderCoupon();
                rechargeOrderCoupon.setRechargeId(order.getId());
                order.setCouponList(rechargeOrderCouponMapper.selectRechargeOrderCouponList(rechargeOrderCoupon));
            }
        }
        return rechargeOrderList;
    }

    @Override
    public BigDecimal getTotalMoney(RechargeOrder rechargeOrder) {
        return rechargeOrderMapper.getTotalMoney(rechargeOrder);
    }

    /**
     * 新增充值记录
     *
     * @param rechargeOrder 充值记录
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public RechargeOrder insertRechargeOrder(RechargeOrder rechargeOrder) {
        rechargeOrder.setCreateTime(DateUtils.getNowDate());
        rechargeOrder.setOrderNo(SnowflakeGenerator.generateOrderNumber());
        rechargeOrder.setOrderStatus("1");
        int i = rechargeOrderMapper.insertRechargeOrder(rechargeOrder);
        if (i > 0 && rechargeOrder.getCouponList().size() > 0) {
            for (RechargeOrderCoupon rechargeOrderCoupon : rechargeOrder.getCouponList()) {
                rechargeOrderCoupon.setRechargeId(rechargeOrder.getId());
                rechargeOrderCoupon.setPayStatus("1");
                rechargeOrderCoupon.setStatus("1");
                rechargeOrderCoupon.setCreateTime(DateUtils.getNowDate());
                rechargeOrderCouponMapper.insertRechargeOrderCoupon(rechargeOrderCoupon);
            }
        }
        return rechargeOrder;
    }

    @Override
    public WxPayMpOrderResult pay(RechargeOrder rechargeOrder) {
        rechargeOrder = rechargeOrderMapper.selectRechargeOrderById(rechargeOrder.getId());
        Member member = SecurityUtils.getLoginUser().getMember();
        WxPayUnifiedOrderRequest request = new WxPayUnifiedOrderRequest();
        //随机字符串
        request.setNonceStr(IdUtils.generateNonceStr());
        //加密方式
        request.setSignType("MD5");
        //订单号
        request.setOutTradeNo(rechargeOrder.getOrderNo());
        //金额，以分为单位
        request.setTotalFee(rechargeOrder.getMoney().multiply(BigDecimal.valueOf(100L)).intValue());
        // 用户ip
        request.setSpbillCreateIp("127.0.0.1");
        //回调通知地址（必须外网能访问的地址）
        request.setNotifyUrl(Constants.URL + "api/shop/rechargeOrder/payOrderNotify");
        //小程序支付
        request.setTradeType("JSAPI");
        //小程序用户openid
        request.setOpenid(member.getOpenId());
        //商品描述
        final ShopCard shopCard = shopCardMapper.selectShopCardById(rechargeOrder.getCardId());
        StringBuilder body = new StringBuilder("祁大脑袋熏鸡");
        if (shopCard != null) {
            body.append("-");
            body.append(shopCard.getTitle());
        }
        request.setBody(body.toString());
        try {
            return wechatConfiguration.wxPayService().createOrder(request);
        } catch (WxPayException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String payOrderNotify(String xmlData) {
        try {
            WxPayOrderNotifyResult notifyResult = wechatConfiguration.wxPayService().parseOrderNotifyResult(xmlData);
            if (StringUtils.equals("SUCCESS", notifyResult.getReturnCode())) {
                List<RechargeOrder> orderList = rechargeOrderMapper.selectOrderByOrderNo(notifyResult.getOutTradeNo());
                if (orderList != null && orderList.size() > 0) {
                    RechargeOrder rechargeOrder = orderList.get(0);
                    rechargeOrder.setOrderStatus("2");
                    rechargeOrder.setPayTime(notifyResult.getTimeEnd());
                    rechargeOrder.setPayResult(JSON.toJSONString(notifyResult));
                    rechargeOrder.setUpdateTime(DateUtils.getNowDate());
                    rechargeOrderMapper.updateRechargeOrder(rechargeOrder);
                    //更新优惠券支付状态
                    RechargeOrderCoupon rechargeOrderCoupon = new RechargeOrderCoupon();
                    rechargeOrderCoupon.setRechargeId(rechargeOrder.getId());
                    rechargeOrderCoupon.setPayStatus("2");
                    rechargeOrderCouponMapper.updateRechargeOrderCouponPayStatusByRechargeId(rechargeOrderCoupon);
                    //更新用户余额
                    Member member = memberMapper.selectMemberById(rechargeOrder.getMemberId());
                    BigDecimal balance = member.getBalance() != null ? member.getBalance() : BigDecimal.ZERO;
                    member.setBalance(balance.add(rechargeOrder.getMoney()));
                    member.setIsMember("1");
                    memberMapper.updateMember(member);
                }
                return WxPayNotifyResponse.success("成功");
            }
        } catch (WxPayException e) {
            e.printStackTrace();
        }
        return WxPayNotifyResponse.fail("失败");
    }

    @Override
    public WxPayRefundResult refund(RechargeOrder rechargeOrder) {
        WxPayRefundRequest request = new WxPayRefundRequest();
        try {
            //订单号
            request.setOutTradeNo(rechargeOrder.getOrderNo());
            //退款单号
            request.setOutRefundNo(rechargeOrder.getOrderNo());
            //订单金额
            request.setTotalFee(rechargeOrder.getMoney().multiply(BigDecimal.valueOf(100L)).intValue());
            //退款金额
            request.setRefundFee(rechargeOrder.getMoney().multiply(BigDecimal.valueOf(100L)).intValue());
            //加密方式
            request.setSignType("MD5");
            //回调通知地址（必须外网能访问的地址）
            request.setNotifyUrl(Constants.URL + "api/shop/rechargeOrder/notify/refund");
            return wechatConfiguration.wxPayService().refund(request);
        } catch (WxPayException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String refundNotify(String xmlData) {
        try {
            final WxPayRefundNotifyResult result = wechatConfiguration.wxPayService().parseRefundNotifyResult(xmlData);
            if (StringUtils.equals("SUCCESS", result.getReturnCode())) {
                List<RechargeOrder> orderList = rechargeOrderMapper.selectOrderByOrderNo(result.getReqInfo().getOutTradeNo());
                if (orderList != null && orderList.size() > 0) {
                    RechargeOrder rechargeOrder = orderList.get(0);
                    rechargeOrder.setOrderStatus("3");
                    rechargeOrder.setPayResult(JSON.toJSONString(result));
                    rechargeOrder.setUpdateTime(DateUtils.getNowDate());
                    rechargeOrderMapper.updateRechargeOrder(rechargeOrder);
                    //更新优惠券支付状态
                    RechargeOrderCoupon rechargeOrderCoupon = new RechargeOrderCoupon();
                    rechargeOrderCoupon.setRechargeId(rechargeOrder.getId());
                    rechargeOrderCoupon.setPayStatus("3");
                    rechargeOrderCouponMapper.updateRechargeOrderCouponPayStatusByRechargeId(rechargeOrderCoupon);
                    //更新用户余额
                    Member member = memberMapper.selectMemberById(rechargeOrder.getMemberId());
                    BigDecimal balance = member.getBalance() != null ? member.getBalance() : BigDecimal.ZERO;
                    member.setBalance(balance.subtract(rechargeOrder.getMoney()));
                    memberMapper.updateMember(member);
                }
                return WxPayNotifyResponse.success("成功");
            }
        } catch (WxPayException e) {
            e.printStackTrace();
        }
        return WxPayNotifyResponse.fail("失败");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int cancel(RechargeOrder rechargeOrder) {
        rechargeOrder.setCreateTime(DateUtils.getNowDate());
        rechargeOrder.setOrderStatus("4");
        int i = rechargeOrderMapper.updateRechargeOrder(rechargeOrder);
        rechargeOrderCouponMapper.deleteRechargeOrderCouponByRechargeId(rechargeOrder.getId());
        return i;
    }

    /**
     * 修改充值记录
     *
     * @param rechargeOrder 充值记录
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateRechargeOrder(RechargeOrder rechargeOrder) {
        rechargeOrder.setUpdateTime(DateUtils.getNowDate());
        final int i = rechargeOrderMapper.updateRechargeOrder(rechargeOrder);
        rechargeOrderCouponMapper.deleteRechargeOrderCouponByRechargeId(rechargeOrder.getId());
        if (i > 0 && rechargeOrder.getCouponList().size() > 0) {
            for (RechargeOrderCoupon rechargeOrderCoupon : rechargeOrder.getCouponList()) {
                rechargeOrderCoupon.setRechargeId(rechargeOrder.getId());
                rechargeOrderCouponMapper.insertRechargeOrderCoupon(rechargeOrderCoupon);
            }
        }
        return i;
    }

    /**
     * 批量删除充值记录
     *
     * @param ids 需要删除的充值记录主键
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteRechargeOrderByIds(Long[] ids) {
        int i = rechargeOrderMapper.deleteRechargeOrderByIds(ids);
        if (i > 0) {
            rechargeOrderCouponMapper.deleteRechargeOrderCouponByRechargeIds(ids);
        }
        return i;
    }

    /**
     * 删除充值记录信息
     *
     * @param id 充值记录主键
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteRechargeOrderById(Long id) {
        int i = rechargeOrderMapper.deleteRechargeOrderById(id);
        if (i > 0) {
            rechargeOrderCouponMapper.deleteRechargeOrderCouponByRechargeId(id);
        }
        return i;
    }
}

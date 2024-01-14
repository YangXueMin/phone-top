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
import com.ruoyi.common.annotation.ShopScope;
import com.ruoyi.common.config.WechatConfiguration;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.entity.Member;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.SnowflakeGenerator;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.uuid.IdUtils;
import com.ruoyi.shop.domain.*;
import com.ruoyi.shop.mapper.*;
import com.ruoyi.shop.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * 订单记录Service业务层处理
 *
 * @author ruoyi
 * @date 2024-01-10
 */
@Service
public class OrderServiceImpl implements IOrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderDetailsMapper orderDetailsMapper;
    @Autowired
    private RechargeOrderCouponMapper rechargeOrderCouponMapper;
    @Autowired
    private WechatConfiguration wechatConfiguration;
    @Autowired
    private ShopInfoMapper shopInfoMapper;
    @Autowired
    private BalanceInfoMapper balanceInfoMapper;
    @Autowired
    private MemberMapper memberMapper;

    /**
     * 查询订单记录
     *
     * @param id 订单记录主键
     * @return 订单记录
     */
    @Override
    public Order selectOrderById(Long id) {
        Order order = orderMapper.selectOrderById(id);
        if (order != null) {
            order.setDetailsList(orderDetailsMapper.selectOrderDetailsByOrderId(id));
        }
        return order;
    }

    /**
     * 查询订单记录列表
     *
     * @param order 订单记录
     * @return 订单记录
     */
    @Override
    @ShopScope()
    public List<Order> selectOrderList(Order order) {
        List<Order> orderList = orderMapper.selectOrderList(order);
        if (orderList.size() > 0) {
            for (Order orderData : orderList) {
                orderData.setDetailsList(orderDetailsMapper.selectOrderDetailsByOrderId(orderData.getId()));
            }
        }
        return orderList;
    }

    @Override
    public List<Order> selectOrderListApi(Order order) {
        List<Order> orderList = orderMapper.selectOrderList(order);
        if (orderList.size() > 0) {
            for (Order orderData : orderList) {
                orderData.setDetailsList(orderDetailsMapper.selectOrderDetailsByOrderId(orderData.getId()));
            }
        }
        return orderList;
    }

    /**
     * 新增订单记录
     *
     * @param order 订单记录
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertOrder(Order order) {
        if (StringUtils.equals("1", order.getPayType())) {
            order.setOrderStatus("2");
            order.setCancelStatus("1");
            order.setPayTime(DateUtils.dateTimeNow());
        } else {
            order.setOrderStatus("1");
            order.setCancelStatus("1");
        }
        order.setCreateTime(DateUtils.getNowDate());
        order.setOrderNumber(SnowflakeGenerator.generateOrderNumber());
        int i = orderMapper.insertOrder(order);
        if (i > 0) {
            if (order.getDetailsList().size() > 0) {
                for (OrderDetails orderDetails : order.getDetailsList()) {
                    orderDetails.setOrderId(order.getId());
                    orderDetails.setCreateTime(DateUtils.getNowDate());
                    orderDetailsMapper.insertOrderDetails(orderDetails);
                }
            }
            //修改优惠券状态
            if (StringUtils.isNotBlank(order.getCouponList())) {
                String[] couponList = order.getCouponList().split(",");
                for (String couponId : couponList) {
                    RechargeOrderCoupon rechargeOrderCoupon = rechargeOrderCouponMapper.selectRechargeOrderCouponById(Long.parseLong(couponId));
                    if (rechargeOrderCoupon.getId() != null) {
                        rechargeOrderCoupon.setStatus("2");
                        rechargeOrderCoupon.setUpdateTime(DateUtils.getNowDate());
                        rechargeOrderCouponMapper.updateRechargeOrderCoupon(rechargeOrderCoupon);
                    }
                }
            }
            if (StringUtils.equals("1", order.getPayType())) {
                Member member = memberMapper.selectMemberById(order.getMemberId());
                BigDecimal beforeBalance = member.getBalance();
                member.setBalance(member.getBalance().subtract(order.getMoney()));
                member.setUpdateTime(DateUtils.getNowDate());
                memberMapper.updateMember(member);
                //添加余额消费记录
                BalanceInfo balanceInfo = new BalanceInfo(order.getMemberId(), "1", order.getId(), beforeBalance, member.getBalance(), order.getMoney());
                balanceInfo.setCreateTime(DateUtils.getNowDate());
                balanceInfoMapper.insertBalanceInfo(balanceInfo);
            }
        }
        return i;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public WxPayMpOrderResult pay(Order order) {
        order = orderMapper.selectOrderById(order.getId());
        Member member = SecurityUtils.getLoginUser().getMember();
        WxPayUnifiedOrderRequest request = new WxPayUnifiedOrderRequest();
        //随机字符串
        request.setNonceStr(IdUtils.generateNonceStr());
        //加密方式
        request.setSignType("MD5");
        //订单号
        request.setOutTradeNo(order.getOrderNumber());
        //金额，以分为单位
        request.setTotalFee(order.getMoney().multiply(BigDecimal.valueOf(100L)).intValue());
        // 用户ip
        request.setSpbillCreateIp("127.0.0.1");
        //回调通知地址（必须外网能访问的地址）
        request.setNotifyUrl(Constants.URL + "api/shop/order/payOrderNotify");
        //小程序支付
        request.setTradeType("JSAPI");
        //小程序用户openid
        request.setOpenid(member.getOpenId());
        //商品描述
        final ShopInfo shopInfo = shopInfoMapper.selectShopInfoById(order.getShopId());
        StringBuilder body = new StringBuilder("祁大脑袋熏鸡");
        if (shopInfo != null) {
            body.append("-");
            body.append(shopInfo.getName());
        }
        request.setBody(body.toString());
        //商品详情
        //request.setDetail("");

        try {
            return wechatConfiguration.wxPayService().createOrder(request);
        } catch (WxPayException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String payOrderNotify(String xmlData) {
        try {
            WxPayOrderNotifyResult notifyResult = wechatConfiguration.wxPayService().parseOrderNotifyResult(xmlData);
            if (StringUtils.equals("SUCCESS", notifyResult.getReturnCode())) {
                List<Order> orderList = orderMapper.selectOrderByOrderNumber(notifyResult.getOutTradeNo());
                if (orderList != null && orderList.size() > 0) {
                    Order order = orderList.get(0);
                    order.setOrderStatus("2");
                    order.setCancelStatus("1");
                    order.setPayType("2");
                    order.setPayTime(notifyResult.getTimeEnd());
                    order.setPayResult(JSON.toJSONString(notifyResult));
                    order.setUpdateTime(DateUtils.getNowDate());
                    orderMapper.updateOrder(order);
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
    public WxPayRefundResult refund(Order order) {
        WxPayRefundRequest request = new WxPayRefundRequest();
        try {
            //订单号
            request.setOutTradeNo(order.getOrderNumber());
            //退款单号
            request.setOutRefundNo(order.getOrderNumber());
            //订单金额
            request.setTotalFee(order.getMoney().multiply(BigDecimal.valueOf(100L)).intValue());
            //退款金额
            request.setRefundFee(order.getMoney().multiply(BigDecimal.valueOf(100L)).intValue());
            //加密方式
            request.setSignType("MD5");
            //回调通知地址（必须外网能访问的地址）
            request.setNotifyUrl(Constants.URL + "api/shop/order/notify/refund");
            return wechatConfiguration.wxPayService().refund(request);
        } catch (WxPayException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int balanceRefund(Order order) {
        order.setOrderStatus("3");
        order.setUpdateTime(DateUtils.getNowDate());
        final int i = orderMapper.updateOrder(order);
        if(i > 0){
            //还原用户优惠券
            if (StringUtils.isNotBlank(order.getCouponList())) {
                String[] couponList = order.getCouponList().split(",");
                for (String couponId : couponList) {
                    RechargeOrderCoupon rechargeOrderCoupon = rechargeOrderCouponMapper.selectRechargeOrderCouponById(Long.parseLong(couponId));
                    if (rechargeOrderCoupon.getId() != null) {
                        rechargeOrderCoupon.setStatus("1");
                        rechargeOrderCoupon.setUpdateTime(DateUtils.getNowDate());
                        rechargeOrderCouponMapper.updateRechargeOrderCoupon(rechargeOrderCoupon);
                    }
                }
            }
            //余额还原到用户
            Member member = memberMapper.selectMemberById(order.getMemberId());
            member.setBalance(member.getBalance().add(order.getMoney()));
            memberMapper.updateMember(member);
            //删除余额消费记录
            balanceInfoMapper.deleteBalanceInfoByOrderIdAndOrderType(order.getId(), "1");
        }
        return i;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String refundNotify(String xmlData) {
        try {
            final WxPayRefundNotifyResult result = wechatConfiguration.wxPayService().parseRefundNotifyResult(xmlData);
            if(StringUtils.equals("SUCCESS", result.getReturnCode())){
                List<Order> orderList = orderMapper.selectOrderByOrderNumber(result.getReqInfo().getOutTradeNo());
                if (orderList != null && orderList.size() > 0) {
                    Order order = orderList.get(0);
                    order.setOrderStatus("3");
                    order.setPayResult(JSON.toJSONString(result));
                    order.setUpdateTime(DateUtils.getNowDate());
                    final int i = orderMapper.updateOrder(order);
                    //还原用户优惠券
                    if(i > 0){
                        if (StringUtils.isNotBlank(order.getCouponList())) {
                            String[] couponList = order.getCouponList().split(",");
                            for (String couponId : couponList) {
                                RechargeOrderCoupon rechargeOrderCoupon = rechargeOrderCouponMapper.selectRechargeOrderCouponById(Long.parseLong(couponId));
                                if (rechargeOrderCoupon.getId() != null) {
                                    rechargeOrderCoupon.setStatus("1");
                                    rechargeOrderCoupon.setUpdateTime(DateUtils.getNowDate());
                                    rechargeOrderCouponMapper.updateRechargeOrderCoupon(rechargeOrderCoupon);
                                }
                            }
                        }
                    }
                }
                return WxPayNotifyResponse.success("成功");
            }
        } catch (WxPayException e) {
            e.printStackTrace();
        }
        return WxPayNotifyResponse.fail("失败");
    }

    /**
     * 修改订单记录
     *
     * @param order 订单记录
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateOrder(Order order) {
        order.setUpdateTime(DateUtils.getNowDate());
        final int i = orderMapper.updateOrder(order);
        orderDetailsMapper.deleteOrderDetailsByOrderId(order.getId());
        if (i > 0) {
            if (order.getDetailsList().size() > 0) {
                for (OrderDetails orderDetails : order.getDetailsList()) {
                    orderDetails.setOrderId(order.getId());
                    orderDetails.setCreateTime(DateUtils.getNowDate());
                    orderDetailsMapper.insertOrderDetails(orderDetails);
                }
            }
            //修改优惠券状态
            if (StringUtils.equals("4", order.getOrderStatus())
                    && StringUtils.isNotBlank(order.getCouponList())) {
                String[] couponList = order.getCouponList().split(",");
                for (String couponId : couponList) {
                    RechargeOrderCoupon rechargeOrderCoupon = rechargeOrderCouponMapper.selectRechargeOrderCouponById(Long.parseLong(couponId));
                    if (rechargeOrderCoupon.getId() != null) {
                        rechargeOrderCoupon.setStatus("1");
                        rechargeOrderCoupon.setUpdateTime(DateUtils.getNowDate());
                        rechargeOrderCouponMapper.updateRechargeOrderCoupon(rechargeOrderCoupon);
                    }
                }
            }
        }
        return i;
    }

    /**
     * 批量删除订单记录
     *
     * @param ids 需要删除的订单记录主键
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteOrderByIds(Long[] ids) {
        final int i = orderMapper.deleteOrderByIds(ids);
        if (i > 0) {
            orderDetailsMapper.deleteOrderDetailsByOrderIds(ids);
        }
        return i;
    }

    /**
     * 删除订单记录信息
     *
     * @param id 订单记录主键
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteOrderById(Long id) {
        final int i = orderMapper.deleteOrderById(id);
        if (i > 0) {
            orderDetailsMapper.deleteOrderDetailsByOrderId(id);
        }
        return i;
    }
}

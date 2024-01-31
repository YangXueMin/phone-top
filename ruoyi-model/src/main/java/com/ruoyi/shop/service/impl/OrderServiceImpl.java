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
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.SnowflakeGenerator;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.uuid.IdUtils;
import com.ruoyi.shop.domain.*;
import com.ruoyi.shop.mapper.*;
import com.ruoyi.shop.service.IOrderService;
import com.ruoyi.system.mapper.MemberMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 订单记录Service业务层处理
 *
 * @author ruoyi
 * @date 2024-01-10
 */
@Service
@Slf4j
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
    @Autowired
    private RechargeOrderMapper rechargeOrderMapper;
    @Autowired
    private ShopCardMapper shopCardMapper;
    @Autowired
    private CardCouponMapper cardCouponMapper;
    @Autowired
    private ShopActivityMapper shopActivityMapper;

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
            if (order.getActivityId() != null) {
                order.setShopActivity(shopActivityMapper.selectShopActivityById(order.getActivityId()));
            }
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
    @ShopScope(shopAlias = "a")
    public List<Order> selectOrderList(Order order) {
        return selectOrderListApi(order);
    }

    @Override
    @ShopScope(shopAlias = "a")
    public BigDecimal getCancelSum(Order order) {
        return orderMapper.getCancelSum(order);
    }

    @Override
    public List<Order> selectOrderListApi(Order order) {
        List<Order> orderList = orderMapper.selectOrderList(order);
        if (orderList.size() > 0) {
            for (Order orderData : orderList) {
                orderData.setDetailsList(orderDetailsMapper.selectOrderDetailsByOrderId(orderData.getId()));
                if (orderData.getActivityId() != null) {
                    orderData.setShopActivity(shopActivityMapper.selectShopActivityById(orderData.getActivityId()));
                }
                if (StringUtils.isNotBlank(order.getCouponList())) {
                    List<RechargeOrderCoupon> rechargeOrderCouponList = new ArrayList<>();
                    String[] couponList = order.getCouponList().split(",");
                    for (String couponId : couponList) {
                        RechargeOrderCoupon rechargeOrderCoupon = rechargeOrderCouponMapper.selectRechargeOrderCouponById(Long.parseLong(couponId));
                        rechargeOrderCouponList.add(rechargeOrderCoupon);
                    }
                    orderData.setRechargeOrderCouponList(rechargeOrderCouponList);
                }
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
    public Order insertOrder(Order order) {
        String orderNumber = SnowflakeGenerator.generateOrderNumber();
        Member member = memberMapper.selectMemberById(order.getMemberId());
        BigDecimal beforeBalance = member.getBalance();

        if ((StringUtils.equals("1", order.getPayType()) || order.getMoney().compareTo(BigDecimal.ZERO) == 0)
                && order.getMoney().compareTo(member.getBalance()) < 0) {
            order.setOrderStatus("2");
            order.setCancelStatus("1");
            order.setPayTime(DateUtils.dateTimeNow());
        } else {
            order.setOrderStatus("1");
            order.setCancelStatus("1");
        }
        order.setCreateTime(DateUtils.getNowDate());
        order.setOrderNumber(orderNumber);
        final int i = orderMapper.insertOrder(order);
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
            //判断是否是充卡
            if (order.getCardId() != null) {
                ShopCard shopCard = shopCardMapper.selectShopCardById(order.getCardId());
                RechargeOrder rechargeOrder = new RechargeOrder();
                rechargeOrder.setCardId(order.getCardId());
                rechargeOrder.setMemberId(order.getMemberId());
                rechargeOrder.setMoney(shopCard.getMoney());
                rechargeOrder.setCreateTime(DateUtils.getNowDate());
                rechargeOrder.setOrderNo(orderNumber);
                rechargeOrder.setOrderStatus("1");
                int ri = rechargeOrderMapper.insertRechargeOrder(rechargeOrder);
                if (ri > 0 && order.getCardId() != null) {
                    List<CardCoupon> cardCouponList = cardCouponMapper.selectCardCouponByCardId(order.getCardId());
                    for (CardCoupon cardCoupon : cardCouponList) {
                        if(cardCoupon.getNumber() > 0){
                            for (int i1 = 0; i1 < cardCoupon.getNumber().intValue(); i1++) {
                                RechargeOrderCoupon rechargeOrderCoupon = new RechargeOrderCoupon();
                                rechargeOrderCoupon.setCouponId(cardCoupon.getCouponId());
                                rechargeOrderCoupon.setMemberId(order.getMemberId());
                                rechargeOrderCoupon.setRechargeId(rechargeOrder.getId());
                                rechargeOrderCoupon.setPayStatus("1");
                                rechargeOrderCoupon.setStatus("1");
                                rechargeOrderCoupon.setCreateTime(DateUtils.getNowDate());
                                rechargeOrderCouponMapper.insertRechargeOrderCoupon(rechargeOrderCoupon);
                            }
                        }
                    }
                }
            }
            if (StringUtils.equals("1", order.getPayType()) && order.getMoney().compareTo(member.getBalance()) < 0) {
                member.setBalance(member.getBalance().subtract(order.getMoney()));
                member.setUpdateTime(DateUtils.getNowDate());
                memberMapper.updateMember(member);
                //添加余额消费记录
                BalanceInfo balanceInfo = new BalanceInfo(order.getMemberId(), "1", order.getId(), beforeBalance, member.getBalance(), order.getMoney());
                balanceInfo.setCreateTime(DateUtils.getNowDate());
                balanceInfoMapper.insertBalanceInfo(balanceInfo);
            }
        }
        return order;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public WxPayMpOrderResult pay(Order order) {
        order = orderMapper.selectOrderById(order.getId());
        List<RechargeOrder> rechargeOrderList = rechargeOrderMapper.selectOrderByOrderNo(order.getOrderNumber());
        BigDecimal money = BigDecimal.ZERO;
        if (StringUtils.equals("1", order.getOrderStatus())) {
            money = money.add(order.getMoney());
        }
        if (rechargeOrderList.size() > 0) {
            money = rechargeOrderList.get(0).getMoney();
        }
        Member member = SecurityUtils.getLoginUser().getMember();
        WxPayUnifiedOrderRequest request = new WxPayUnifiedOrderRequest();
        //随机字符串
        request.setNonceStr(IdUtils.generateNonceStr());
        //加密方式
        request.setSignType("MD5");
        //订单号
        request.setOutTradeNo(order.getOrderNumber());
        //金额，以分为单位
        request.setTotalFee(money.multiply(BigDecimal.valueOf(100L)).intValue());
        // 用户ip
        request.setSpbillCreateIp("127.0.0.1");
        //回调通知地址（必须外网能访问的地址）
        request.setNotifyUrl(Constants.URL + "api/shop/order/payNotify");
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
        try {
            return wechatConfiguration.wxPayService().createOrder(request);
        } catch (WxPayException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public WxPayMpOrderResult payOrder(Order order) {
        order = orderMapper.selectOrderById(order.getId());
        //需要重新修改订单号
        String orderNumber = SnowflakeGenerator.generateOrderNumber();
        order.setOrderNumber(orderNumber);
        orderMapper.updateOrder(order);
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
        try {
            return wechatConfiguration.wxPayService().createOrder(request);
        } catch (WxPayException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String payNotify(String xmlData) {
        try {
            WxPayOrderNotifyResult notifyResult = wechatConfiguration.wxPayService().parseOrderNotifyResult(xmlData);
            if (StringUtils.equals("SUCCESS", notifyResult.getReturnCode())) {
                List<Order> orderList = orderMapper.selectOrderByOrderNumber(notifyResult.getOutTradeNo());
                List<RechargeOrder> rechargeOrderList = rechargeOrderMapper.selectOrderByOrderNo(notifyResult.getOutTradeNo());
                RechargeOrder rechargeOrder = null;
                if (rechargeOrderList != null && rechargeOrderList.size() > 0) {
                    rechargeOrder = rechargeOrderList.get(0);
                }
                if (orderList != null && orderList.size() > 0) {
                    Order order = orderList.get(0);
                    if (StringUtils.equals("1", order.getOrderStatus())) {
                        if (rechargeOrder != null
                                && rechargeOrder.getMoney().multiply(BigDecimal.valueOf(100L)).intValue() == notifyResult.getTotalFee()) {
                            order.setPayType("1");
                        } else {
                            order.setPayType("2");
                        }
                        order.setOrderStatus("2");
                        order.setCancelStatus("1");
                        order.setPayTime(notifyResult.getTimeEnd());
                        order.setPayResult(JSON.toJSONString(notifyResult));
                        order.setUpdateTime(DateUtils.getNowDate());
                        orderMapper.updateOrder(order);
                    }
                    if (rechargeOrder != null && rechargeOrder.getMoney().multiply(BigDecimal.valueOf(100L)).intValue() == notifyResult.getTotalFee()) {
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
                        member.setBalance(balance.add(rechargeOrder.getMoney()).subtract(order.getMoney()).setScale(2, RoundingMode.HALF_UP));
                        member.setIsMember("1");
                        memberMapper.updateMember(member);
                        //添加余额消费记录
                        BalanceInfo balanceInfo = new BalanceInfo(order.getMemberId(), "1", order.getId(), balance.add(rechargeOrder.getMoney()), member.getBalance(), order.getMoney());
                        balanceInfo.setCreateTime(DateUtils.getNowDate());
                        balanceInfoMapper.insertBalanceInfo(balanceInfo);
                    }else {
                        log.error("支付回调失败，回调信息：{}",JSON.toJSONString(notifyResult));
                    }
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
    public String payOrderNotify(String xmlData) {
        try {
            WxPayOrderNotifyResult notifyResult = wechatConfiguration.wxPayService().parseOrderNotifyResult(xmlData);
            if (StringUtils.equals("SUCCESS", notifyResult.getReturnCode())) {
                List<Order> orderList = orderMapper.selectOrderByOrderNumber(notifyResult.getOutTradeNo());
                if (orderList != null && orderList.size() > 0) {
                    Order order = orderList.get(0);
                    if (StringUtils.equals("1", order.getOrderStatus()) && order.getMoney().multiply(BigDecimal.valueOf(100L)).intValue() == notifyResult.getTotalFee()) {
                        order.setOrderStatus("2");
                        order.setCancelStatus("1");
                        order.setPayType("2");
                        order.setPayTime(notifyResult.getTimeEnd());
                        order.setPayResult(JSON.toJSONString(notifyResult));
                        order.setUpdateTime(DateUtils.getNowDate());
                        orderMapper.updateOrder(order);
                        return WxPayNotifyResponse.success("成功");
                    }
                }
                log.error("支付回调失败，回调信息：{}",JSON.toJSONString(notifyResult));
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
        order.setUpdateTime(DateUtils.getNowDate());
        final int i = orderMapper.updateOrder(order);
        if (i > 0) {
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
            //如果是余额订单需要退钱
            if (StringUtils.equals("1", order.getPayType())) {
                //余额还原到用户
                Member member = memberMapper.selectMemberById(order.getMemberId());
                member.setBalance(member.getBalance().add(order.getMoney()));
                memberMapper.updateMember(member);
                //删除余额消费记录
                balanceInfoMapper.deleteBalanceInfoByOrderIdAndOrderType(order.getId(), "1");
            }
        }
        return i;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String refundNotify(String xmlData) {
        try {
            final WxPayRefundNotifyResult result = wechatConfiguration.wxPayService().parseRefundNotifyResult(xmlData);
            if (StringUtils.equals("SUCCESS", result.getReturnCode())) {
                List<Order> orderList = orderMapper.selectOrderByOrderNumber(result.getReqInfo().getOutTradeNo());
                if (orderList != null && orderList.size() > 0) {
                    Order order = orderList.get(0);
                    order.setOrderStatus("3");
                    order.setPayResult(JSON.toJSONString(result));
                    order.setUpdateTime(DateUtils.getNowDate());
                    final int i = orderMapper.updateOrder(order);
                    //还原用户优惠券
                    if (i > 0) {
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
     * 核销订单
     *
     * @param order 订单记录
     * @return
     */
    @Override
    public int cancelOrder(Order order) {
        order.setCancelStatus("2");
        order.setUpdateTime(DateUtils.getNowDate());
        return orderMapper.updateOrder(order);
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

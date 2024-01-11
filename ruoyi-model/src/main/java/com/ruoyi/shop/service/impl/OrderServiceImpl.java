package com.ruoyi.shop.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.shop.domain.Order;
import com.ruoyi.shop.domain.OrderDetails;
import com.ruoyi.shop.domain.RechargeOrderCoupon;
import com.ruoyi.shop.mapper.OrderDetailsMapper;
import com.ruoyi.shop.mapper.OrderMapper;
import com.ruoyi.shop.mapper.RechargeOrderCouponMapper;
import com.ruoyi.shop.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public List<Order> selectOrderList(Order order) {
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
        order.setCreateTime(DateUtils.getNowDate());
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
        }
        return i;
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
            if (StringUtils.equals("4",order.getOrderStatus())
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

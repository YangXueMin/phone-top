package com.ruoyi.shop.service.impl;

import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.shop.domain.OrderDetails;
import com.ruoyi.shop.mapper.OrderDetailsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.shop.mapper.OrderMapper;
import com.ruoyi.shop.domain.Order;
import com.ruoyi.shop.service.IOrderService;
import org.springframework.transaction.annotation.Transactional;

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

    /**
     * 查询订单记录
     *
     * @param id 订单记录主键
     * @return 订单记录
     */
    @Override
    public Order selectOrderById(Long id) {
        return orderMapper.selectOrderById(id);
    }

    /**
     * 查询订单记录列表
     *
     * @param order 订单记录
     * @return 订单记录
     */
    @Override
    public List<Order> selectOrderList(Order order) {
        return orderMapper.selectOrderList(order);
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
        if(i > 0 && order.getDetailsList().size() > 0){
            for (OrderDetails orderDetails : order.getDetailsList()) {
                orderDetails.setOrderId(order.getId());
                orderDetails.setCreateTime(DateUtils.getNowDate());
                orderDetailsMapper.insertOrderDetails(orderDetails);
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
        if(i > 0 && order.getDetailsList().size() > 0){
            for (OrderDetails orderDetails : order.getDetailsList()) {
                orderDetails.setOrderId(order.getId());
                orderDetails.setCreateTime(DateUtils.getNowDate());
                orderDetailsMapper.insertOrderDetails(orderDetails);
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
        if(i > 0){
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
        if(i > 0){
            orderDetailsMapper.deleteOrderDetailsByOrderId(id);
        }
        return i;
    }
}

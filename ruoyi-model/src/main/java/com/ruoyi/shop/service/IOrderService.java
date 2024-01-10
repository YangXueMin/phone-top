package com.ruoyi.shop.service;

import java.util.List;
import com.ruoyi.shop.domain.Order;

/**
 * 订单记录Service接口
 *
 * @author ruoyi
 * @date 2024-01-10
 */
public interface IOrderService
{
    /**
     * 查询订单记录
     *
     * @param id 订单记录主键
     * @return 订单记录
     */
    public Order selectOrderById(Long id);

    /**
     * 查询订单记录列表
     *
     * @param order 订单记录
     * @return 订单记录集合
     */
    public List<Order> selectOrderList(Order order);

    /**
     * 新增订单记录
     *
     * @param order 订单记录
     * @return 结果
     */
    public int insertOrder(Order order);

    /**
     * 修改订单记录
     *
     * @param order 订单记录
     * @return 结果
     */
    public int updateOrder(Order order);

    /**
     * 批量删除订单记录
     *
     * @param ids 需要删除的订单记录主键集合
     * @return 结果
     */
    public int deleteOrderByIds(Long[] ids);

    /**
     * 删除订单记录信息
     *
     * @param id 订单记录主键
     * @return 结果
     */
    public int deleteOrderById(Long id);
}

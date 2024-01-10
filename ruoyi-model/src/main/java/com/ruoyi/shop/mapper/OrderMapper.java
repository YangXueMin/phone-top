package com.ruoyi.shop.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.ruoyi.shop.domain.Order;

/**
 * 订单记录Mapper接口
 *
 * @author ruoyi
 * @date 2024-01-10
 */
@Mapper
public interface OrderMapper
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
     * 删除订单记录
     *
     * @param id 订单记录主键
     * @return 结果
     */
    public int deleteOrderById(Long id);

    /**
     * 批量删除订单记录
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteOrderByIds(Long[] ids);
}

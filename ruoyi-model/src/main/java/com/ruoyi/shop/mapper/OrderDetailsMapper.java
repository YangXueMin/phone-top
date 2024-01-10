package com.ruoyi.shop.mapper;

import com.ruoyi.shop.domain.OrderDetails;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 订单详情Mapper接口
 *
 * @author ruoyi
 * @date 2024-01-10
 */
@Mapper
public interface OrderDetailsMapper {
    /**
     * 查询订单详情
     *
     * @param id 订单详情主键
     * @return 订单详情
     */
    public OrderDetails selectOrderDetailsById(Long id);

    /**
     * 查询订单详情
     *
     * @param orderId 订单详情主键
     * @return 订单详情
     */
    public List<OrderDetails> selectOrderDetailsByOrderId(Long orderId);

    /**
     * 查询订单详情列表
     *
     * @param orderDetails 订单详情
     * @return 订单详情集合
     */
    public List<OrderDetails> selectOrderDetailsList(OrderDetails orderDetails);

    /**
     * 新增订单详情
     *
     * @param orderDetails 订单详情
     * @return 结果
     */
    public int insertOrderDetails(OrderDetails orderDetails);

    /**
     * 修改订单详情
     *
     * @param orderDetails 订单详情
     * @return 结果
     */
    public int updateOrderDetails(OrderDetails orderDetails);

    /**
     * 删除订单详情
     *
     * @param id 订单详情主键
     * @return 结果
     */
    public int deleteOrderDetailsById(Long id);

    /**
     * 批量删除订单详情
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteOrderDetailsByIds(Long[] ids);

    /**
     * 删除订单详情
     *
     * @param orderId 订单详情主键
     * @return 结果
     */
    public int deleteOrderDetailsByOrderId(Long orderId);

    /**
     * 批量删除订单详情
     *
     * @param orderIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteOrderDetailsByOrderIds(Long[] orderIds);
}

package com.ruoyi.shop.service;

import java.util.List;

import com.github.binarywang.wxpay.bean.notify.WxPayRefundNotifyResult;
import com.github.binarywang.wxpay.bean.order.WxPayMpOrderResult;
import com.github.binarywang.wxpay.bean.result.WxPayRefundResult;
import com.ruoyi.shop.domain.Order;
import com.ruoyi.shop.domain.OrderRequest;

/**
 * 订单记录Service接口
 *
 * @author ruoyi
 * @date 2024-01-10
 */
public interface IOrderService {
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
     * 查询订单记录列表
     *
     * @param order 订单记录
     * @return 订单记录集合
     */
    public List<Order> selectOrderListApi(Order order);

    /**
     * 新增订单记录
     *
     * @param order 订单记录
     * @return 结果
     */
    public Order insertOrder(Order order);

    /**
     * 新增订单记录
     *
     * @param orderRequest 订单记录
     * @return 结果
     */
    public Order insertOrderBalance(OrderRequest orderRequest);

    /**
     * 发起支付
     *
     * @param order 发起支付
     * @return 结果
     */
    public WxPayMpOrderResult payBalance(Order order);

    /**
     * 发起支付
     *
     * @param order 发起支付
     * @return 结果
     */
    public WxPayMpOrderResult pay(Order order);

    /**
     * 支付通知
     *
     * @param xmlData 支付通知
     * @return 结果
     */
    public String payOrderBalanceNotify(String xmlData);

    /**
     * 支付通知
     *
     * @param xmlData 支付通知
     * @return 结果
     */
    public String payOrderNotify(String xmlData);

    /**
     * 退款
     *
     * @param order
     * @return
     */
    public WxPayRefundResult refund(Order order);

    /**
     * 余额退款或取消订单
     *
     * @param order
     * @return
     */
    public int balanceRefund(Order order);

    /**
     * 退款通知
     *
     * @param xmlData
     * @return
     */
    public String refundNotify(String xmlData);

    /**
     * 修改订单记录
     *
     * @param order 订单记录
     * @return 结果
     */
    public int updateOrder(Order order);

    /**
     * 核销订单记录
     *
     * @param order 订单记录
     * @return 结果
     */
    public int cancelOrder(Order order);

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

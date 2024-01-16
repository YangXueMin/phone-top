package com.ruoyi.shop.service;

import com.ruoyi.shop.domain.OfflineOrder;

import java.util.List;

/**
 * 线下订单Service接口
 *
 * @author ruoyi
 * @date 2024-01-14
 */
public interface IOfflineOrderService {
    /**
     * 查询线下订单
     *
     * @param id 线下订单主键
     * @return 线下订单
     */
    public OfflineOrder selectOfflineOrderById(Long id);

    /**
     * 查询线下订单列表
     *
     * @param offlineOrder 线下订单
     * @return 线下订单集合
     */
    public List<OfflineOrder> selectOfflineOrderList(OfflineOrder offlineOrder);

    /**
     * 查询线下订单列表
     *
     * @param offlineOrder 线下订单
     * @return 线下订单集合
     */
    public List<OfflineOrder> selectOfflineOrderListApi(OfflineOrder offlineOrder);

    /**
     * 新增线下订单
     *
     * @param offlineOrder 线下订单
     * @return 结果
     */
    public OfflineOrder insertOfflineOrder(OfflineOrder offlineOrder);

    /**
     * 修改线下订单
     *
     * @param offlineOrder 线下订单
     * @return 结果
     */
    public int updateOfflineOrder(OfflineOrder offlineOrder);

    /**
     * 申请退款
     * @param offlineOrder
     * @return
     */
    int balanceRefund(OfflineOrder offlineOrder);

    /**
     * 批量删除线下订单
     *
     * @param ids 需要删除的线下订单主键集合
     * @return 结果
     */
    public int deleteOfflineOrderByIds(Long[] ids);

    /**
     * 删除线下订单信息
     *
     * @param id 线下订单主键
     * @return 结果
     */
    public int deleteOfflineOrderById(Long id);
}

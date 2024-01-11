package com.ruoyi.shop.service;

import java.util.List;

import com.ruoyi.shop.domain.RechargeOrder;

/**
 * 充值记录Service接口
 *
 * @author ruoyi
 * @date 2024-01-11
 */
public interface IRechargeOrderService {
    /**
     * 查询充值记录
     *
     * @param id 充值记录主键
     * @return 充值记录
     */
    public RechargeOrder selectRechargeOrderById(Long id);

    /**
     * 查询充值记录列表
     *
     * @param rechargeOrder 充值记录
     * @return 充值记录集合
     */
    public List<RechargeOrder> selectRechargeOrderList(RechargeOrder rechargeOrder);

    /**
     * 新增充值记录
     *
     * @param rechargeOrder 充值记录
     * @return 结果
     */
    public int insertRechargeOrder(RechargeOrder rechargeOrder);

    /**
     * 修改充值记录
     *
     * @param rechargeOrder 充值记录
     * @return 结果
     */
    public int updateRechargeOrder(RechargeOrder rechargeOrder);

    /**
     * 批量删除充值记录
     *
     * @param ids 需要删除的充值记录主键集合
     * @return 结果
     */
    public int deleteRechargeOrderByIds(Long[] ids);

    /**
     * 删除充值记录信息
     *
     * @param id 充值记录主键
     * @return 结果
     */
    public int deleteRechargeOrderById(Long id);
}

package com.ruoyi.shop.service;

import java.util.List;

import com.ruoyi.shop.domain.RechargeOrderCoupon;

/**
 * 充值订单与优惠券关联Service接口
 *
 * @author ruoyi
 * @date 2024-01-11
 */
public interface IRechargeOrderCouponService {
    /**
     * 查询充值订单与优惠券关联
     *
     * @param id 充值订单与优惠券关联主键
     * @return 充值订单与优惠券关联
     */
    public RechargeOrderCoupon selectRechargeOrderCouponById(Long id);

    /**
     * 查询充值订单与优惠券关联列表
     *
     * @param rechargeOrderCoupon 充值订单与优惠券关联
     * @return 充值订单与优惠券关联集合
     */
    public List<RechargeOrderCoupon> selectRechargeOrderCouponList(RechargeOrderCoupon rechargeOrderCoupon);

    /**
     * 新增充值订单与优惠券关联
     *
     * @param rechargeOrderCoupon 充值订单与优惠券关联
     * @return 结果
     */
    public int insertRechargeOrderCoupon(RechargeOrderCoupon rechargeOrderCoupon);

    /**
     * 修改充值订单与优惠券关联
     *
     * @param rechargeOrderCoupon 充值订单与优惠券关联
     * @return 结果
     */
    public int updateRechargeOrderCoupon(RechargeOrderCoupon rechargeOrderCoupon);

    /**
     * 批量删除充值订单与优惠券关联
     *
     * @param ids 需要删除的充值订单与优惠券关联主键集合
     * @return 结果
     */
    public int deleteRechargeOrderCouponByIds(Long[] ids);

    /**
     * 删除充值订单与优惠券关联信息
     *
     * @param id 充值订单与优惠券关联主键
     * @return 结果
     */
    public int deleteRechargeOrderCouponById(Long id);
}

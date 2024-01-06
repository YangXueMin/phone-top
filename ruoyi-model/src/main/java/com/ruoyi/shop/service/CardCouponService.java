package com.ruoyi.shop.service;

/**
 * @author yangxuemin
 * @ClassName CardCouponService
 * @Description
 * @date 2024/1/6 11:49 AM
 */
public interface CardCouponService {
    /**
     * 查询储值卡使用数量
     *
     * @param couponId 优惠券ID
     * @return 结果
     */
    public int selectCountCardCouponByCouponId(Long couponId);

}

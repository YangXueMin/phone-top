package com.ruoyi.shop.service;

import java.util.List;
import com.ruoyi.shop.domain.Coupon;

/**
 * 优惠券配置Service接口
 *
 * @author ruoyi
 * @date 2024-01-06
 */
public interface ICouponService
{
    /**
     * 查询优惠券配置
     *
     * @param id 优惠券配置主键
     * @return 优惠券配置
     */
    public Coupon selectCouponById(Long id);

    /**
     * 查询优惠券配置列表
     *
     * @param coupon 优惠券配置
     * @return 优惠券配置集合
     */
    public List<Coupon> selectCouponList(Coupon coupon);

    /**
     * 新增优惠券配置
     *
     * @param coupon 优惠券配置
     * @return 结果
     */
    public int insertCoupon(Coupon coupon);

    /**
     * 修改优惠券配置
     *
     * @param coupon 优惠券配置
     * @return 结果
     */
    public int updateCoupon(Coupon coupon);

    /**
     * 批量删除优惠券配置
     *
     * @param ids 需要删除的优惠券配置主键集合
     * @return 结果
     */
    public int deleteCouponByIds(Long[] ids);

    /**
     * 删除优惠券配置信息
     *
     * @param id 优惠券配置主键
     * @return 结果
     */
    public int deleteCouponById(Long id);
}

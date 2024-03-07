package com.ruoyi.phone.service;

import java.util.List;
import com.ruoyi.phone.domain.PhoneCoupon;

/**
 * 优惠券管理Service接口
 *
 * @author ruoyi
 * @date 2024-03-07
 */
public interface IPhoneCouponService
{
    /**
     * 查询优惠券管理
     *
     * @param id 优惠券管理主键
     * @return 优惠券管理
     */
    public PhoneCoupon selectPhoneCouponById(Long id);

    /**
     * 查询优惠券管理列表
     *
     * @param phoneCoupon 优惠券管理
     * @return 优惠券管理集合
     */
    public List<PhoneCoupon> selectPhoneCouponList(PhoneCoupon phoneCoupon);

    /**
     * 新增优惠券管理
     *
     * @param phoneCoupon 优惠券管理
     * @return 结果
     */
    public int insertPhoneCoupon(PhoneCoupon phoneCoupon);

    /**
     * 修改优惠券管理
     *
     * @param phoneCoupon 优惠券管理
     * @return 结果
     */
    public int updatePhoneCoupon(PhoneCoupon phoneCoupon);

    /**
     * 批量删除优惠券管理
     *
     * @param ids 需要删除的优惠券管理主键集合
     * @return 结果
     */
    public int deletePhoneCouponByIds(Long[] ids);

    /**
     * 删除优惠券管理信息
     *
     * @param id 优惠券管理主键
     * @return 结果
     */
    public int deletePhoneCouponById(Long id);
}

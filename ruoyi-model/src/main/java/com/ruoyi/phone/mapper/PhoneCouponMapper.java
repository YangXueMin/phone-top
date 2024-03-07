package com.ruoyi.phone.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.ruoyi.phone.domain.PhoneCoupon;

/**
 * 优惠券管理Mapper接口
 *
 * @author ruoyi
 * @date 2024-03-07
 */
@Mapper
public interface PhoneCouponMapper
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
     * 删除优惠券管理
     *
     * @param id 优惠券管理主键
     * @return 结果
     */
    public int deletePhoneCouponById(Long id);

    /**
     * 批量删除优惠券管理
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePhoneCouponByIds(Long[] ids);
}

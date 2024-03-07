package com.ruoyi.phone.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.phone.mapper.PhoneCouponMapper;
import com.ruoyi.phone.domain.PhoneCoupon;
import com.ruoyi.phone.service.IPhoneCouponService;

/**
 * 优惠券管理Service业务层处理
 *
 * @author ruoyi
 * @date 2024-03-07
 */
@Service
public class PhoneCouponServiceImpl implements IPhoneCouponService
{
    @Autowired
    private PhoneCouponMapper phoneCouponMapper;

    /**
     * 查询优惠券管理
     *
     * @param id 优惠券管理主键
     * @return 优惠券管理
     */
    @Override
    public PhoneCoupon selectPhoneCouponById(Long id)
    {
        return phoneCouponMapper.selectPhoneCouponById(id);
    }

    /**
     * 查询优惠券管理列表
     *
     * @param phoneCoupon 优惠券管理
     * @return 优惠券管理
     */
    @Override
    public List<PhoneCoupon> selectPhoneCouponList(PhoneCoupon phoneCoupon)
    {
        return phoneCouponMapper.selectPhoneCouponList(phoneCoupon);
    }

    /**
     * 新增优惠券管理
     *
     * @param phoneCoupon 优惠券管理
     * @return 结果
     */
    @Override
    public int insertPhoneCoupon(PhoneCoupon phoneCoupon)
    {
        phoneCoupon.setCreateTime(DateUtils.getNowDate());
        return phoneCouponMapper.insertPhoneCoupon(phoneCoupon);
    }

    /**
     * 修改优惠券管理
     *
     * @param phoneCoupon 优惠券管理
     * @return 结果
     */
    @Override
    public int updatePhoneCoupon(PhoneCoupon phoneCoupon)
    {
        phoneCoupon.setUpdateTime(DateUtils.getNowDate());
        return phoneCouponMapper.updatePhoneCoupon(phoneCoupon);
    }

    /**
     * 批量删除优惠券管理
     *
     * @param ids 需要删除的优惠券管理主键
     * @return 结果
     */
    @Override
    public int deletePhoneCouponByIds(Long[] ids)
    {
        return phoneCouponMapper.deletePhoneCouponByIds(ids);
    }

    /**
     * 删除优惠券管理信息
     *
     * @param id 优惠券管理主键
     * @return 结果
     */
    @Override
    public int deletePhoneCouponById(Long id)
    {
        return phoneCouponMapper.deletePhoneCouponById(id);
    }
}

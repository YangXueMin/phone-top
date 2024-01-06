package com.ruoyi.shop.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.shop.domain.Coupon;
import com.ruoyi.shop.mapper.CouponMapper;
import com.ruoyi.shop.service.ICouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 优惠券配置Service业务层处理
 *
 * @author ruoyi
 * @date 2024-01-06
 */
@Service
public class CouponServiceImpl implements ICouponService {
    @Autowired
    private CouponMapper couponMapper;

    /**
     * 查询优惠券配置
     *
     * @param id 优惠券配置主键
     * @return 优惠券配置
     */
    @Override
    public Coupon selectCouponById(Long id) {
        return couponMapper.selectCouponById(id);
    }

    /**
     * 查询优惠券配置列表
     *
     * @param coupon 优惠券配置
     * @return 优惠券配置
     */
    @Override
    public List<Coupon> selectCouponList(Coupon coupon) {
        return couponMapper.selectCouponList(coupon);
    }

    /**
     * 新增优惠券配置
     *
     * @param coupon 优惠券配置
     * @return 结果
     */
    @Override
    public int insertCoupon(Coupon coupon) {
        coupon.setCreateTime(DateUtils.getNowDate());
        return couponMapper.insertCoupon(coupon);
    }

    /**
     * 修改优惠券配置
     *
     * @param coupon 优惠券配置
     * @return 结果
     */
    @Override
    public int updateCoupon(Coupon coupon) {
        coupon.setUpdateTime(DateUtils.getNowDate());
        return couponMapper.updateCoupon(coupon);
    }

    /**
     * 批量删除优惠券配置
     *
     * @param ids 需要删除的优惠券配置主键
     * @return 结果
     */
    @Override
    public int deleteCouponByIds(Long[] ids) {
        return couponMapper.deleteCouponByIds(ids);
    }

    /**
     * 删除优惠券配置信息
     *
     * @param id 优惠券配置主键
     * @return 结果
     */
    @Override
    public int deleteCouponById(Long id) {
        return couponMapper.deleteCouponById(id);
    }
}

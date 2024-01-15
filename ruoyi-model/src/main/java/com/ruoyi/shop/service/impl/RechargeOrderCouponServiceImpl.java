package com.ruoyi.shop.service.impl;

import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.shop.mapper.RechargeOrderCouponMapper;
import com.ruoyi.shop.domain.RechargeOrderCoupon;
import com.ruoyi.shop.service.IRechargeOrderCouponService;

/**
 * 充值订单与优惠券关联Service业务层处理
 *
 * @author ruoyi
 * @date 2024-01-11
 */
@Service
public class RechargeOrderCouponServiceImpl implements IRechargeOrderCouponService {
    @Autowired
    private RechargeOrderCouponMapper rechargeOrderCouponMapper;

    /**
     * 查询充值订单与优惠券关联
     *
     * @param id 充值订单与优惠券关联主键
     * @return 充值订单与优惠券关联
     */
    @Override
    public RechargeOrderCoupon selectRechargeOrderCouponById(Long id) {
        RechargeOrderCoupon rechargeOrderCoupon = rechargeOrderCouponMapper.selectRechargeOrderCouponById(id);
        if (rechargeOrderCoupon.getCoupon() != null) {
            rechargeOrderCoupon.setEndDate(DateUtils.addDays(rechargeOrderCoupon.getCreateTime(), rechargeOrderCoupon.getCoupon().getTermValidity()));
        }
        return rechargeOrderCoupon;
    }

    /**
     * 查询充值订单与优惠券关联列表
     *
     * @param rechargeOrderCoupon 充值订单与优惠券关联
     * @return 充值订单与优惠券关联
     */
    @Override
    public List<RechargeOrderCoupon> selectRechargeOrderCouponList(RechargeOrderCoupon rechargeOrderCoupon) {
        List<RechargeOrderCoupon> rechargeOrderCouponList = rechargeOrderCouponMapper.selectRechargeOrderCouponList(rechargeOrderCoupon);
        if (rechargeOrderCouponList != null && rechargeOrderCouponList.size() > 0) {
            for (RechargeOrderCoupon orderCoupon : rechargeOrderCouponList) {
                if (orderCoupon.getCoupon() != null) {
                    orderCoupon.setEndDate(DateUtils.addDays(orderCoupon.getCreateTime(), orderCoupon.getCoupon().getTermValidity()));
                }
            }
        }
        return rechargeOrderCouponList;
    }

    /**
     * 查询充值订单与优惠券关联列表
     *
     * @param rechargeOrderCoupon 充值订单与优惠券关联
     * @return 充值订单与优惠券关联
     */
    @Override
    public List<RechargeOrderCoupon> selectRechargeOrderCouponMemberList(RechargeOrderCoupon rechargeOrderCoupon) {
        return rechargeOrderCouponMapper.selectRechargeOrderCouponMemberList(rechargeOrderCoupon);
    }

    @Override
    public Integer getOrderCouponNumber() {
        return rechargeOrderCouponMapper.getOrderCouponNumber(SecurityUtils.getUserId());
    }

    /**
     * 新增充值订单与优惠券关联
     *
     * @param rechargeOrderCoupon 充值订单与优惠券关联
     * @return 结果
     */
    @Override
    public int insertRechargeOrderCoupon(RechargeOrderCoupon rechargeOrderCoupon) {
        rechargeOrderCoupon.setCreateTime(DateUtils.getNowDate());
        return rechargeOrderCouponMapper.insertRechargeOrderCoupon(rechargeOrderCoupon);
    }

    /**
     * 修改充值订单与优惠券关联
     *
     * @param rechargeOrderCoupon 充值订单与优惠券关联
     * @return 结果
     */
    @Override
    public int updateRechargeOrderCoupon(RechargeOrderCoupon rechargeOrderCoupon) {
        rechargeOrderCoupon.setUpdateTime(DateUtils.getNowDate());
        return rechargeOrderCouponMapper.updateRechargeOrderCoupon(rechargeOrderCoupon);
    }

    /**
     * 批量删除充值订单与优惠券关联
     *
     * @param ids 需要删除的充值订单与优惠券关联主键
     * @return 结果
     */
    @Override
    public int deleteRechargeOrderCouponByIds(Long[] ids) {
        return rechargeOrderCouponMapper.deleteRechargeOrderCouponByIds(ids);
    }

    /**
     * 删除充值订单与优惠券关联信息
     *
     * @param id 充值订单与优惠券关联主键
     * @return 结果
     */
    @Override
    public int deleteRechargeOrderCouponById(Long id) {
        return rechargeOrderCouponMapper.deleteRechargeOrderCouponById(id);
    }
}

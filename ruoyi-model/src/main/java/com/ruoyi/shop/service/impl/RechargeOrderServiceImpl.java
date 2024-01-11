package com.ruoyi.shop.service.impl;

import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.shop.domain.RechargeOrderCoupon;
import com.ruoyi.shop.domain.ShopCard;
import com.ruoyi.shop.mapper.RechargeOrderCouponMapper;
import com.ruoyi.shop.mapper.ShopCardMapper;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.shop.mapper.RechargeOrderMapper;
import com.ruoyi.shop.domain.RechargeOrder;
import com.ruoyi.shop.service.IRechargeOrderService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 充值记录Service业务层处理
 *
 * @author ruoyi
 * @date 2024-01-11
 */
@Service
public class RechargeOrderServiceImpl implements IRechargeOrderService {
    @Autowired
    private RechargeOrderMapper rechargeOrderMapper;
    @Autowired
    private RechargeOrderCouponMapper rechargeOrderCouponMapper;

    /**
     * 查询充值记录
     *
     * @param id 充值记录主键
     * @return 充值记录
     */
    @Override
    public RechargeOrder selectRechargeOrderById(Long id) {
        RechargeOrder rechargeOrder = rechargeOrderMapper.selectRechargeOrderById(id);
        RechargeOrderCoupon rechargeOrderCoupon = new RechargeOrderCoupon();
        rechargeOrderCoupon.setRechargeId(id);
        rechargeOrderCouponMapper.selectRechargeOrderCouponList(rechargeOrderCoupon);
        return rechargeOrder;
    }

    /**
     * 查询充值记录列表
     *
     * @param rechargeOrder 充值记录
     * @return 充值记录
     */
    @Override
    public List<RechargeOrder> selectRechargeOrderList(RechargeOrder rechargeOrder) {
        List<RechargeOrder> rechargeOrderList = rechargeOrderMapper.selectRechargeOrderList(rechargeOrder);
        if (rechargeOrderList.size() > 0) {
            for (RechargeOrder order : rechargeOrderList) {
                RechargeOrderCoupon rechargeOrderCoupon = new RechargeOrderCoupon();
                rechargeOrderCoupon.setRechargeId(order.getId());
                rechargeOrderCouponMapper.selectRechargeOrderCouponList(rechargeOrderCoupon);
            }
        }
        return rechargeOrderList;
    }

    /**
     * 新增充值记录
     *
     * @param rechargeOrder 充值记录
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertRechargeOrder(RechargeOrder rechargeOrder) {
        rechargeOrder.setCreateTime(DateUtils.getNowDate());
        int i = rechargeOrderMapper.insertRechargeOrder(rechargeOrder);
        if (i > 0 && rechargeOrder.getCouponList().size() > 0) {
            for (RechargeOrderCoupon rechargeOrderCoupon : rechargeOrder.getCouponList()) {
                rechargeOrderCoupon.setRechargeId(rechargeOrder.getId());
                rechargeOrderCouponMapper.insertRechargeOrderCoupon(rechargeOrderCoupon);
            }
        }
        return i;
    }

    /**
     * 修改充值记录
     *
     * @param rechargeOrder 充值记录
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateRechargeOrder(RechargeOrder rechargeOrder) {
        rechargeOrder.setUpdateTime(DateUtils.getNowDate());
        final int i = rechargeOrderMapper.updateRechargeOrder(rechargeOrder);
        rechargeOrderCouponMapper.deleteRechargeOrderCouponByRechargeId(rechargeOrder.getId());
        if (i > 0 && rechargeOrder.getCouponList().size() > 0) {
            for (RechargeOrderCoupon rechargeOrderCoupon : rechargeOrder.getCouponList()) {
                rechargeOrderCoupon.setRechargeId(rechargeOrder.getId());
                rechargeOrderCouponMapper.insertRechargeOrderCoupon(rechargeOrderCoupon);
            }
        }
        return i;
    }

    /**
     * 批量删除充值记录
     *
     * @param ids 需要删除的充值记录主键
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteRechargeOrderByIds(Long[] ids) {
        int i = rechargeOrderMapper.deleteRechargeOrderByIds(ids);
        if (i > 0) {
            rechargeOrderCouponMapper.deleteRechargeOrderCouponByRechargeIds(ids);
        }
        return i;
    }

    /**
     * 删除充值记录信息
     *
     * @param id 充值记录主键
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteRechargeOrderById(Long id) {
        int i = rechargeOrderMapper.deleteRechargeOrderById(id);
        if (i > 0) {
            rechargeOrderCouponMapper.deleteRechargeOrderCouponByRechargeId(id);
        }
        return i;
    }
}

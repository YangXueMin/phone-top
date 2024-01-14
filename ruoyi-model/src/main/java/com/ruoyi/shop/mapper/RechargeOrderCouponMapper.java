package com.ruoyi.shop.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import com.ruoyi.shop.domain.RechargeOrderCoupon;

/**
 * 充值订单与优惠券关联Mapper接口
 *
 * @author ruoyi
 * @date 2024-01-11
 */
@Mapper
public interface RechargeOrderCouponMapper {
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
     * 查询充值订单与优惠券关联列表
     *
     * @param rechargeOrderCoupon 充值订单与优惠券关联
     * @return 充值订单与优惠券关联集合
     */
    public List<RechargeOrderCoupon> selectRechargeOrderCouponMemberList(RechargeOrderCoupon rechargeOrderCoupon);

    /**
     * 获取会员优惠券数量
     *
     * @param memberId
     * @return
     */
    Integer getOrderCouponNumber(Long memberId);

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
     * 更新优惠券支付状态
     *
     * @param rechargeId 充值订单与优惠券关联主键
     * @param payStatus 支付状态
     * @return 结果
     */
    public int updateRechargeOrderCouponPayStatusByRechargeId(Long rechargeId, String payStatus);

    /**
     * 删除充值订单与优惠券关联
     *
     * @param id 充值订单与优惠券关联主键
     * @return 结果
     */
    public int deleteRechargeOrderCouponById(Long id);

    /**
     * 批量删除充值订单与优惠券关联
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteRechargeOrderCouponByIds(Long[] ids);

    /**
     * 删除充值订单与优惠券关联
     *
     * @param rechargeId 充值订单与优惠券关联主键
     * @return 结果
     */
    public int deleteRechargeOrderCouponByRechargeId(Long rechargeId);

    /**
     * 批量删除充值订单与优惠券关联
     *
     * @param rechargeIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteRechargeOrderCouponByRechargeIds(Long[] rechargeIds);
}

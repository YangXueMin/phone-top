package com.ruoyi.shop.mapper;

import com.ruoyi.shop.domain.CardCoupon;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author yangxuemin
 * @ClassName CardCouponMapper
 * @Description
 * @date 2024/1/6 11:20 AM
 */
@Mapper
public interface CardCouponMapper {
    /**
     * 通过角色ID删除角色和部门关联
     *
     * @param cardId 储值卡ID
     * @return 结果
     */
    public int deleteCardCouponByCardId(Long cardId);

    /**
     * 批量删除角色部门关联信息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteCardCoupon(Long[] ids);

    /**
     * 查询储值卡使用数量
     *
     * @param couponId 优惠券ID
     * @return 结果
     */
    public int selectCountCardCouponByCouponId(Long couponId);

    /**
     * 根据储值卡ID查询数据
     *
     * @param cardId 储值卡ID
     * @return 结果
     */
    public List<CardCoupon> selectCardCouponByCardId(Long cardId);

    /**
     * 批量新增储值卡优惠券信息
     *
     * @param cardCouponList 优惠券列表
     * @return 结果
     */
    public int batchCardCoupon(List<CardCoupon> cardCouponList);
}

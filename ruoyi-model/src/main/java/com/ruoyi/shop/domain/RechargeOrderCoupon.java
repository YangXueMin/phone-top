package com.ruoyi.shop.domain;

import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 充值订单与优惠券关联对象 shop_recharge_order_coupon
 *
 * @author ruoyi
 * @date 2024-01-11
 */
@ApiModel(value = "RechargeOrderCoupon", description = "充值订单与优惠券关联")
@ToString
public class RechargeOrderCoupon extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @ApiModelProperty("主键")
    private Long id;

    /**
     * 充值ID
     */
    @Excel(name = "充值ID")
    @ApiModelProperty("充值ID")
    private Long rechargeId;

    /**
     * 会员ID
     */
    @Excel(name = "会员ID")
    @ApiModelProperty("会员ID")
    private Long memberId;

    /**
     * 优惠券ID
     */
    @Excel(name = "优惠券ID")
    @ApiModelProperty("优惠券ID")
    private Long couponId;

    /**
     * 状态
     */
    @Excel(name = "状态")
    @ApiModelProperty("状态:(待使用：1，已使用：2，已过期：3，已失效：4)")
    private String status;

    /**
     * 支付状态
     */
    @Excel(name = "支付状态")
    @ApiModelProperty("支付状态")
    private String payStatus;

    @ApiModelProperty("优惠券")
    private Coupon coupon;

    @ApiModelProperty("数量")
    private Integer num;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setRechargeId(Long rechargeId) {
        this.rechargeId = rechargeId;
    }

    public Long getRechargeId() {
        return rechargeId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setCouponId(Long couponId) {
        this.couponId = couponId;
    }

    public Long getCouponId() {
        return couponId;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public Coupon getCoupon() {
        return coupon;
    }

    public void setCoupon(Coupon coupon) {
        this.coupon = coupon;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}

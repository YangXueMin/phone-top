package com.ruoyi.shop.domain;

import java.math.BigDecimal;
import java.util.List;

import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 充值记录对象 shop_recharge_order
 *
 * @author ruoyi
 * @date 2024-01-11
 */
@ApiModel(value = "RechargeOrder", description = "充值记录")
@ToString
public class RechargeOrder extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty("主键")
    private Long id;

    /**
     * 订单编号
     */
    @Excel(name = "订单编号")
    @ApiModelProperty("订单编号")
    private String orderNo;

    /**
     * 会员ID
     */
    @Excel(name = "会员ID")
    @ApiModelProperty("会员ID")
    private Long memberId;

    /**
     * 充值卡ID
     */
    @Excel(name = "充值卡ID")
    @ApiModelProperty("充值卡ID")
    private Long cardId;

    /**
     * 充值金额
     */
    @Excel(name = "充值金额")
    @ApiModelProperty("充值金额")
    private BigDecimal money;

    /**
     * 微信订单号
     */
    @Excel(name = "微信订单号")
    @ApiModelProperty("微信订单号")
    private String wechatOrder;

    @ApiModelProperty("优惠券集合")
    private List<RechargeOrderCoupon> couponList;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

    public Long getCardId() {
        return cardId;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setWechatOrder(String wechatOrder) {
        this.wechatOrder = wechatOrder;
    }

    public String getWechatOrder() {
        return wechatOrder;
    }

    public List<RechargeOrderCoupon> getCouponList() {
        return couponList;
    }

    public void setCouponList(List<RechargeOrderCoupon> couponList) {
        this.couponList = couponList;
    }
}

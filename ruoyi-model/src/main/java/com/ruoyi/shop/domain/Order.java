package com.ruoyi.shop.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 订单记录对象 shop_order
 *
 * @author ruoyi
 * @date 2024-01-10
 */
@ApiModel(value = "Order", description = "订单记录")
@ToString
public class Order extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty("主键")
    private Long id;

    /**
     * 商铺ID
     */
    @Excel(name = "商铺ID")
    @ApiModelProperty("商铺ID")
    private Long shopId;

    /**
     * 会员ID
     */
    @Excel(name = "会员ID")
    @ApiModelProperty("会员ID")
    private Long memberId;

    /**
     * 订单编号
     */
    @Excel(name = "订单编号")
    @ApiModelProperty("订单编号")
    private String orderNumber;

    /**
     * 优惠金额
     */
    @Excel(name = "优惠金额")
    @ApiModelProperty("优惠金额")
    private BigDecimal favourableMoney;

    /**
     * 订单总金额
     */
    @Excel(name = "订单总金额")
    @ApiModelProperty("订单总金额")
    private BigDecimal money;

    /**
     * 优惠券集合
     */
    @Excel(name = "优惠券集合")
    @ApiModelProperty("优惠券集合")
    private String couponList;

    /**
     * 订单状态
     */
    @Excel(name = "订单状态")
    @ApiModelProperty("订单状态")
    private String orderStatus;

    /**
     * 支付方式
     */
    @Excel(name = "支付方式")
    @ApiModelProperty("支付方式")
    private String payType;

    /**
     * 用餐方式
     */
    @Excel(name = "用餐方式")
    @ApiModelProperty("用餐方式")
    private String haveType;

    /**
     * 用餐时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "用餐时间", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty("用餐时间")
    private Date haveTime;

    /**
     * 核销人员ID
     */
    @Excel(name = "核销人员ID")
    @ApiModelProperty("核销人员ID")
    private Long userId;

    /**
     * 核销状态
     */
    @Excel(name = "核销状态")
    @ApiModelProperty("核销状态")
    private String cancelStatus;

    /**
     * 订单详情
     */
    private List<OrderDetails> detailsList;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setFavourableMoney(BigDecimal favourableMoney) {
        this.favourableMoney = favourableMoney;
    }

    public BigDecimal getFavourableMoney() {
        return favourableMoney;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setCouponList(String couponList) {
        this.couponList = couponList;
    }

    public String getCouponList() {
        return couponList;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getPayType() {
        return payType;
    }

    public void setHaveType(String haveType) {
        this.haveType = haveType;
    }

    public String getHaveType() {
        return haveType;
    }

    public void setHaveTime(Date haveTime) {
        this.haveTime = haveTime;
    }

    public Date getHaveTime() {
        return haveTime;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setCancelStatus(String cancelStatus) {
        this.cancelStatus = cancelStatus;
    }

    public String getCancelStatus() {
        return cancelStatus;
    }

    public List<OrderDetails> getDetailsList() {
        return detailsList;
    }

    public void setDetailsList(List<OrderDetails> detailsList) {
        this.detailsList = detailsList;
    }
}

package com.ruoyi.shop.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.common.core.domain.entity.Member;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * 线下订单对象 shop_offline_order
 *
 * @author ruoyi
 * @date 2024-01-14
 */
@ApiModel(value = "OfflineOrder", description = "线下订单")
@ToString
public class OfflineOrder extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 店铺ID
     */
    @Excel(name = "店铺ID")
    @ApiModelProperty("店铺ID")
    private Long shopId;

    /**
     * 会员ID
     */
    @Excel(name = "会员ID")
    @ApiModelProperty("会员ID")
    private Long memberId;

    /**
     * 名称
     */
    @Excel(name = "名称")
    @ApiModelProperty("名称")
    private String name;

    /**
     * 订单编号
     */
    @Excel(name = "订单编号")
    @ApiModelProperty("订单编号")
    private String orderNumber;

    /**
     * 序号
     */
    @Excel(name = "序号")
    @ApiModelProperty("序号")
    private Long serialNumber;

    /**
     * 订单总金额
     */
    @Excel(name = "订单总金额")
    @ApiModelProperty("订单总金额")
    private BigDecimal money;

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
     * 支付时间
     */
    @Excel(name = "支付时间")
    @ApiModelProperty("支付时间")
    private String payTime;

    @ApiModelProperty("提货人姓名")
    private String mName;

    @ApiModelProperty("提货人手机号")
    private String mPhone;

    /**
     * 店员ID
     */
    @Excel(name = "店员ID")
    @ApiModelProperty("店员ID")
    private Long userId;

    @ApiModelProperty("店铺信息")
    private ShopInfo shopInfo;

    /**
     * 会员信息
     */
    @ApiModelProperty("会员信息")
    private Member member;

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

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setSerialNumber(Long serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Long getSerialNumber() {
        return serialNumber;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public BigDecimal getMoney() {
        return money;
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

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public ShopInfo getShopInfo() {
        return shopInfo;
    }

    public void setShopInfo(ShopInfo shopInfo) {
        this.shopInfo = shopInfo;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmPhone() {
        return mPhone;
    }

    public void setmPhone(String mPhone) {
        this.mPhone = mPhone;
    }
}

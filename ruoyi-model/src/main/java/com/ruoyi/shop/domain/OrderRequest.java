package com.ruoyi.shop.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;

import java.util.Date;
import java.util.List;

/**
 * @author yangxuemin
 * @ClassName OrderRequest
 * @Description
 * @date 2024/1/20 9:08 AM
 */
@ApiModel(value = "OrderRequest", description = "普通订单请求参数")
@ToString
public class OrderRequest {
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
     * 订单类型
     */
    @Excel(name = "订单类型")
    @ApiModelProperty("订单类型 （普通订单：1，会员日订单：2，活动订单：3）")
    private String orderType;

    /**
     * 优惠券关系集合
     */
    @Excel(name = "优惠券关系集合")
    @ApiModelProperty("优惠券关系集合，多个用英文逗号隔开")
    private String couponList;

    /**
     * 支付方式
     */
    @Excel(name = "支付方式")
    @ApiModelProperty("支付方式 （余额支付：1，微信支付：2）")
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "用餐时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("用餐时间")
    private Date haveTime;

    /**
     * 订单详情
     */
    @ApiModelProperty("订单详情")
    private List<OrderDetailsRequest> detailsList;

    /**
     * 充值卡ID
     */
    @Excel(name = "充值卡ID")
    @ApiModelProperty("充值卡ID")
    private Long cardId;


    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getCouponList() {
        return couponList;
    }

    public void setCouponList(String couponList) {
        this.couponList = couponList;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getHaveType() {
        return haveType;
    }

    public void setHaveType(String haveType) {
        this.haveType = haveType;
    }

    public Date getHaveTime() {
        return haveTime;
    }

    public void setHaveTime(Date haveTime) {
        this.haveTime = haveTime;
    }

    public List<OrderDetailsRequest> getDetailsList() {
        return detailsList;
    }

    public void setDetailsList(List<OrderDetailsRequest> detailsList) {
        this.detailsList = detailsList;
    }

    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }
}

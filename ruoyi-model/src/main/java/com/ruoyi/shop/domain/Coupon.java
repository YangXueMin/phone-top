package com.ruoyi.shop.domain;

import java.math.BigDecimal;
import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 优惠券配置对象 shop_coupon
 *
 * @author ruoyi
 * @date 2024-01-06
 */
@ApiModel(value = "Coupon", description = "优惠券配置")
@ToString
public class Coupon extends BaseEntity{
    private static final long serialVersionUID=1L;

    /** 主键 */
    private Long id;

    /** 名称 */
    @Excel(name = "名称")
    @ApiModelProperty("名称")
    private String couponName;

    /** 图片 */
    @Excel(name = "图片")
    @ApiModelProperty("图片")
    private String photo;

    /** 适用范围 */
    @Excel(name = "适用范围")
    @ApiModelProperty("适用范围")
    private String suitRange;

    /** 商品ID集合 */
    @Excel(name = "商品ID集合")
    @ApiModelProperty("商品ID集合")
    private String goodsList;

    /** 优惠券类型 */
    @Excel(name = "优惠券类型")
    @ApiModelProperty("优惠券类型")
    private String couponType;

    /** 优惠力度满 */
    @Excel(name = "优惠力度满")
    @ApiModelProperty("优惠力度满")
    private BigDecimal couponStrength;

    /** 优惠力度减 */
    @Excel(name = "优惠力度减")
    @ApiModelProperty("优惠力度减")
    private BigDecimal couponStrengthMoney;

    /** 折扣 */
    @Excel(name = "折扣")
    @ApiModelProperty("折扣")
    private BigDecimal discount;

    /** 有效期 */
    @Excel(name = "有效期")
    @ApiModelProperty("有效期")
    private Integer termValidity;

    /** 发放数量 */
    @Excel(name = "发放数量")
    @ApiModelProperty("发放数量")
    private Long number;

    /** 限领数量 */
    @Excel(name = "限领数量")
    @ApiModelProperty("限领数量")
    private Long limitCollar;

    /** 状态 */
    @Excel(name = "状态")
    @ApiModelProperty("状态")
    private String status;

    public void setId(Long id){
        this.id = id;
    }

    public Long getId(){
        return id;
    }
    public void setCouponName(String couponName){
        this.couponName = couponName;
    }

    public String getCouponName(){
        return couponName;
    }
    public void setSuitRange(String suitRange){
        this.suitRange = suitRange;
    }

    public String getSuitRange(){
        return suitRange;
    }
    public void setGoodsList(String goodsList){
        this.goodsList = goodsList;
    }

    public String getGoodsList(){
        return goodsList;
    }
    public void setCouponType(String couponType){
        this.couponType = couponType;
    }

    public String getCouponType(){
        return couponType;
    }
    public void setCouponStrength(BigDecimal couponStrength){
        this.couponStrength = couponStrength;
    }

    public BigDecimal getCouponStrength(){
        return couponStrength;
    }
    public void setCouponStrengthMoney(BigDecimal couponStrengthMoney){
        this.couponStrengthMoney = couponStrengthMoney;
    }

    public BigDecimal getCouponStrengthMoney(){
        return couponStrengthMoney;
    }
    public void setDiscount(BigDecimal discount){
        this.discount = discount;
    }

    public BigDecimal getDiscount(){
        return discount;
    }
    public void setTermValidity(Integer termValidity){
        this.termValidity = termValidity;
    }

    public Integer getTermValidity(){
        return termValidity;
    }
    public void setNumber(Long number){
        this.number = number;
    }

    public Long getNumber(){
        return number;
    }
    public void setLimitCollar(Long limitCollar){
        this.limitCollar = limitCollar;
    }

    public Long getLimitCollar(){
        return limitCollar;
    }
    public void setStatus(String status){
        this.status = status;
    }

    public String getStatus(){
        return status;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}

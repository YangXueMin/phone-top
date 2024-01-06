package com.ruoyi.shop.domain;

import java.math.BigDecimal;
import java.util.List;

import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 储值卡对象 shop_card
 *
 * @author ruoyi
 * @date 2024-01-06
 */
@ApiModel(value = "ShopCard", description = "储值卡")
@ToString
public class ShopCard extends BaseEntity{
    private static final long serialVersionUID=1L;

    /** 主键 */
    private Long id;

    /** 标题 */
    @Excel(name = "标题")
    @ApiModelProperty("标题")
    private String title;

    /** 充值金额 */
    @Excel(name = "充值金额")
    @ApiModelProperty("充值金额")
    private BigDecimal money;

    /** 封面图 */
    @Excel(name = "封面图")
    @ApiModelProperty("封面图")
    private String picture;

    /** 内容 */
    @Excel(name = "内容")
    @ApiModelProperty("内容")
    private String content;

    /** 状态 */
    @Excel(name = "状态")
    @ApiModelProperty("状态")
    private String status;

    @ApiModelProperty("优惠券关联表")
    List<CardCoupon> cardCouponList;

    public void setId(Long id){
        this.id = id;
    }

    public Long getId(){
        return id;
    }
    public void setTitle(String title){
        this.title = title;
    }

    public String getTitle(){
        return title;
    }
    public void setMoney(BigDecimal money){
        this.money = money;
    }

    public BigDecimal getMoney(){
        return money;
    }
    public void setPicture(String picture){
        this.picture = picture;
    }

    public String getPicture(){
        return picture;
    }
    public void setContent(String content){
        this.content = content;
    }

    public String getContent(){
        return content;
    }
    public void setStatus(String status){
        this.status = status;
    }

    public String getStatus(){
        return status;
    }

    public List<CardCoupon> getCardCouponList() {
        return cardCouponList;
    }

    public void setCardCouponList(List<CardCoupon> cardCouponList) {
        this.cardCouponList = cardCouponList;
    }
}

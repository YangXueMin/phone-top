package com.ruoyi.shop.domain;

import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;

/**
 * 储蓄卡优惠券关联对象 shop_card_coupon
 * @author yangxuemin
 * @ClassName CardCoupon
 * @Description
 * @date 2024/1/6 11:19 AM
 */
@ToString
public class CardCoupon {
    /** 充值卡ID */
    @Excel(name = "充值卡ID")
    @ApiModelProperty("充值卡ID")
    private Long cardId;

    /** 优惠券ID */
    @Excel(name = "优惠券ID")
    @ApiModelProperty("优惠券ID")
    private Long couponId;

    /** 数量 */
    @Excel(name = "数量")
    @ApiModelProperty("数量")
    private Long number;

    public void setCardId(Long cardId){
        this.cardId = cardId;
    }

    public Long getCardId(){
        return cardId;
    }
    public void setCouponId(Long couponId){
        this.couponId = couponId;
    }

    public Long getCouponId(){
        return couponId;
    }
    public void setNumber(Long number){
        this.number = number;
    }

    public Long getNumber(){
        return number;
    }


}

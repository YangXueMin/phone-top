package com.ruoyi.shop.domain;

import lombok.ToString;

/**
 * @author yangxuemin
 * @ClassName DayActivityGoods
 * @Description
 * @date 2024/1/13 4:22 PM
 */
@ToString
public class DayActivityGoods {
    /**
     * 会员日ID
     */
    private Long dayActivityId;

    /**
     * 商品ID
     */
    private Long goodsId;

    public Long getDayActivityId() {
        return dayActivityId;
    }

    public void setDayActivityId(Long dayActivityId) {
        this.dayActivityId = dayActivityId;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }
}

package com.ruoyi.shop.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;

/**
 * 订单详情对象 shop_order_details
 *
 * @author ruoyi
 * @date 2024-01-10
 */
@ApiModel(value = "OrderDetailsRequest", description = "订单请求详情")
@ToString
public class OrderDetailsRequest extends BaseEntity {
    private static final long serialVersionUID = 1L;


    /**
     * 商品ID
     */
    @Excel(name = "商品ID")
    @ApiModelProperty("商品ID")
    private Long goodsId;

    /**
     * 规格ID
     */
    @Excel(name = "规格ID")
    @ApiModelProperty("规格ID")
    private Long specsId;

    /**
     * 数量
     */
    @Excel(name = "数量")
    @ApiModelProperty("数量")
    private Long number;

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public Long getSpecsId() {
        return specsId;
    }

    public void setSpecsId(Long specsId) {
        this.specsId = specsId;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }
}

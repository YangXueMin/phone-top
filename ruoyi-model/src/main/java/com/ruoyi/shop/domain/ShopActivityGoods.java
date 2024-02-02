package com.ruoyi.shop.domain;

import java.math.BigDecimal;
import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 活动商品对象 shop_activity_goods
 *
 * @author ruoyi
 * @date 2024-01-11
 */
@ApiModel(value = "ShopActivityGoods", description = "活动商品")
@ToString
public class ShopActivityGoods extends BaseEntity{
    private static final long serialVersionUID=1L;

    /** 主键 */
    private Long id;

    /** 活动ID */
    @Excel(name = "活动ID")
    @ApiModelProperty("活动ID")
    private Long activityId;

    /** 商品ID */
    @Excel(name = "商品ID")
    @ApiModelProperty("商品ID")
    private Long goodsId;

    /** 规格ID */
    @Excel(name = "规格ID")
    @ApiModelProperty("规格ID")
    private Long specsId;

    /** 抢购价 */
    @Excel(name = "抢购价")
    @ApiModelProperty("抢购价")
    private BigDecimal buyingPrice;

    /** 抢购数量 */
    @Excel(name = "抢购数量")
    @ApiModelProperty("抢购数量")
    private Long buyingNumber;

    @ApiModelProperty("商品")
    private Goods goods;

    @ApiModelProperty("商品规格")
    private GoodsSpecs specs;

    @ApiModelProperty("出售数量")
    private Integer sellNumber;

    public void setId(Long id){
        this.id = id;
    }

    public Long getId(){
        return id;
    }
    public void setActivityId(Long activityId){
        this.activityId = activityId;
    }

    public Long getActivityId(){
        return activityId;
    }
    public void setGoodsId(Long goodsId){
        this.goodsId = goodsId;
    }

    public Long getGoodsId(){
        return goodsId;
    }

    public Long getSpecsId() {
        return specsId;
    }

    public void setSpecsId(Long specsId) {
        this.specsId = specsId;
    }

    public void setBuyingPrice(BigDecimal buyingPrice){
        this.buyingPrice = buyingPrice;
    }

    public BigDecimal getBuyingPrice(){
        return buyingPrice;
    }
    public void setBuyingNumber(Long buyingNumber){
        this.buyingNumber = buyingNumber;
    }

    public Long getBuyingNumber(){
        return buyingNumber;
    }

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public GoodsSpecs getSpecs() {
        return specs;
    }

    public void setSpecs(GoodsSpecs specs) {
        this.specs = specs;
    }

    public Integer getSellNumber() {
        return sellNumber;
    }

    public void setSellNumber(Integer sellNumber) {
        this.sellNumber = sellNumber;
    }
}

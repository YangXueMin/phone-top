package com.ruoyi.shop.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * 企业商品规格对象 shop_goods_specs
 *
 * @author ruoyi
 * @date 2024-01-06
 */
@ApiModel(value = "CompanyGoodsSpecs", description = "企业商品规格")
@ToString
public class CompanyGoodsSpecs extends BaseEntity{
    private static final long serialVersionUID=1L;

    /** 主键 */
    private Long id;

    /** 企业商品ID */
    @Excel(name = "企业商品ID")
    @ApiModelProperty("企业商品ID")
    private Long goodsId;

    /** 企业商品规格 */
    @Excel(name = "企业商品规格")
    @ApiModelProperty("企业商品规格")
    private String goodsSpecs;

    /** 企业商品库存 */
    @Excel(name = "企业商品库存")
    @ApiModelProperty("企业商品库存")
    private Long goodsStock;

    /** 企业商品价格 */
    @Excel(name = "企业商品价格")
    @ApiModelProperty("企业商品价格")
    private BigDecimal goodsPrice;

    /** 折扣 */
    @Excel(name = "折扣")
    @ApiModelProperty("折扣")
    private BigDecimal goodsDiscount;

    /** 规格编码 */
    @Excel(name = "规格编码")
    @ApiModelProperty("规格编码")
    private String goodsNumber;

    public void setId(Long id){
        this.id = id;
    }

    public Long getId(){
        return id;
    }
    public void setGoodsId(Long goodsId){
        this.goodsId = goodsId;
    }

    public Long getGoodsId(){
        return goodsId;
    }
    public void setGoodsSpecs(String goodsSpecs){
        this.goodsSpecs = goodsSpecs;
    }

    public String getGoodsSpecs(){
        return goodsSpecs;
    }
    public void setGoodsStock(Long goodsStock){
        this.goodsStock = goodsStock;
    }

    public Long getGoodsStock(){
        return goodsStock;
    }
    public void setGoodsPrice(BigDecimal goodsPrice){
        this.goodsPrice = goodsPrice;
    }

    public BigDecimal getGoodsPrice(){
        return goodsPrice;
    }
    public void setGoodsDiscount(BigDecimal goodsDiscount){
        this.goodsDiscount = goodsDiscount;
    }

    public BigDecimal getGoodsDiscount(){
        return goodsDiscount;
    }
    public void setGoodsNumber(String goodsNumber){
        this.goodsNumber = goodsNumber;
    }

    public String getGoodsNumber(){
        return goodsNumber;
    }

}

package com.ruoyi.shop.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.utils.DictUtils;
import com.ruoyi.common.utils.StringUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;
import com.ruoyi.common.core.domain.BaseEntity;

import java.math.BigDecimal;

/**
 * 订单详情对象 shop_order_details
 *
 * @author ruoyi
 * @date 2024-01-10
 */
@ApiModel(value = "OrderDetails", description = "订单详情")
@ToString
public class OrderDetails extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty("主键")
    private Long id;

    /**
     * 订单ID
     */
    @Excel(name = "订单ID")
    @ApiModelProperty("订单ID")
    private Long orderId;

    /**
     * 商品ID
     */
    @Excel(name = "商品ID")
    @ApiModelProperty("商品ID")
    private Long goodsId;

    /**
     * 名称
     */
    @Excel(name = "名称")
    @ApiModelProperty("名称")
    private String name;

    /**
     * 描述
     */
    @Excel(name = "描述")
    @ApiModelProperty("描述")
    private String describe;

    /**
     * 分类ID
     */
    @Excel(name = "分类ID")
    @ApiModelProperty("分类ID")
    private Long classId;

    /**
     * 商品标签
     */
    @Excel(name = "商品标签")
    @ApiModelProperty("商品标签")
    private String goodsLabel;

    /**
     * 商品标签
     */
    @Excel(name = "商品标签")
    @ApiModelProperty("商品标签")
    private String goodsLabelLabel;

    /**
     * 图片
     */
    @Excel(name = "图片")
    @ApiModelProperty("图片")
    private String pictures;

    /**
     * 规格列表
     */
    @Excel(name = "规格列表")
    @ApiModelProperty("规格列表")
    private String attributeList;

    /**
     * 配送方式
     */
    @Excel(name = "配送方式")
    @ApiModelProperty("配送方式")
    private String deliveryType;

    /**
     * 是否限制
     */
    @Excel(name = "是否限制")
    @ApiModelProperty("是否限制")
    private String isLimit;

    /**
     * 限制方式
     */
    @Excel(name = "限制方式")
    @ApiModelProperty("限制方式")
    private String limitType;

    /**
     * 限制数量
     */
    @Excel(name = "限制数量")
    @ApiModelProperty("限制数量")
    private Integer limitNum;

    /**
     * 规格ID
     */
    @Excel(name = "规格ID")
    @ApiModelProperty("规格ID")
    private Long specsId;

    /** 商品规格 */
    @Excel(name = "商品规格")
    @ApiModelProperty("商品规格")
    private String goodsSpecs;

    /** 商品库存 */
    @Excel(name = "商品库存")
    @ApiModelProperty("商品库存")
    private Long goodsStock;

    /** 商品价格 */
    @Excel(name = "商品价格")
    @ApiModelProperty("商品价格")
    private BigDecimal goodsPrice;

    /** 折扣 */
    @Excel(name = "折扣")
    @ApiModelProperty("折扣")
    private BigDecimal goodsDiscount;

    /** 规格编码 */
    @Excel(name = "规格编码")
    @ApiModelProperty("规格编码")
    private String goodsNumber;

    /**
     * 数量
     */
    @Excel(name = "数量")
    @ApiModelProperty("数量")
    private Long number;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getOrderId() {
        return orderId;
    }

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

    public void setNumber(Long number) {
        this.number = number;
    }

    public Long getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    public String getGoodsLabel() {
        return goodsLabel;
    }

    public void setGoodsLabel(String goodsLabel) {
        this.goodsLabel = goodsLabel;
    }

    public void setGoodsLabelLabel(String goodsLabelLabel) {
        this.goodsLabelLabel = goodsLabelLabel;
    }

    public String getPictures() {
        return pictures;
    }

    public void setPictures(String pictures) {
        this.pictures = pictures;
    }

    public String getAttributeList() {
        return attributeList;
    }

    public void setAttributeList(String attributeList) {
        this.attributeList = attributeList;
    }

    public String getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }

    public String getIsLimit() {
        return isLimit;
    }

    public void setIsLimit(String isLimit) {
        this.isLimit = isLimit;
    }

    public String getLimitType() {
        return limitType;
    }

    public void setLimitType(String limitType) {
        this.limitType = limitType;
    }

    public Integer getLimitNum() {
        return limitNum;
    }

    public void setLimitNum(Integer limitNum) {
        this.limitNum = limitNum;
    }

    public String getGoodsSpecs() {
        return goodsSpecs;
    }

    public void setGoodsSpecs(String goodsSpecs) {
        this.goodsSpecs = goodsSpecs;
    }

    public Long getGoodsStock() {
        return goodsStock;
    }

    public void setGoodsStock(Long goodsStock) {
        this.goodsStock = goodsStock;
    }

    public BigDecimal getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(BigDecimal goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public BigDecimal getGoodsDiscount() {
        return goodsDiscount;
    }

    public void setGoodsDiscount(BigDecimal goodsDiscount) {
        this.goodsDiscount = goodsDiscount;
    }

    public String getGoodsNumber() {
        return goodsNumber;
    }

    public void setGoodsNumber(String goodsNumber) {
        this.goodsNumber = goodsNumber;
    }

    public String getGoodsLabelLabel() {
        if(StringUtils.isNotBlank(goodsLabel)){
            return DictUtils.getDictLabel("shop_goods_label",goodsLabel);
        }
        return goodsLabelLabel;
    }
}

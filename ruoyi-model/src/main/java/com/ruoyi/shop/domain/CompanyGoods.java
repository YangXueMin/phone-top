package com.ruoyi.shop.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;

import java.util.List;

/**
 * 企业商品对象 shop_goods
 *
 * @author ruoyi
 * @date 2024-01-06
 */
@ApiModel(value = "CompanyGoods", description = "企业商品")
@ToString
public class CompanyGoods extends BaseEntity{
    private static final long serialVersionUID=1L;

    /** 主键 */
    private Long id;

    /** 名称 */
    @Excel(name = "名称")
    @ApiModelProperty("名称")
    private String name;

    /** 描述 */
    @Excel(name = "描述")
    @ApiModelProperty("描述")
    private String describe;

    /** 分类ID */
    @Excel(name = "分类ID")
    @ApiModelProperty("分类ID")
    private Long classId;

    /** 商品标签 */
    @Excel(name = "商品标签")
    @ApiModelProperty("商品标签")
    private String goodsLabel;

    /** 图片 */
    @Excel(name = "图片")
    @ApiModelProperty("图片")
    private String pictures;

    /** 规格列表 */
    @Excel(name = "规格列表")
    @ApiModelProperty("规格列表")
    private String attributeList;

    /** 配送方式 */
    @Excel(name = "配送方式")
    @ApiModelProperty("配送方式")
    private String deliveryType;

    /** 是否限制 */
    @Excel(name = "是否限制")
    @ApiModelProperty("是否限制")
    private String isLimit;

    /** 限制方式 */
    @Excel(name = "限制方式")
    @ApiModelProperty("限制方式")
    private String limitType;

    /** 限制数量 */
    @Excel(name = "限制数量")
    @ApiModelProperty("限制数量")
    private Long limitNum;

    @Excel(name = "商品状态")
    @ApiModelProperty("商品状态")
    private String status;

    @ApiModelProperty("规格详情数据")
    private List<CompanyGoodsSpecs> specsList;

    @ApiModelProperty("分类")
    private GoodsClassify goodsClassify;

    public void setId(Long id){
        this.id = id;
    }

    public Long getId(){
        return id;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }
    public void setDescribe(String describe){
        this.describe = describe;
    }

    public String getDescribe(){
        return describe;
    }
    public void setClassId(Long classId){
        this.classId = classId;
    }

    public Long getClassId(){
        return classId;
    }
    public void setGoodsLabel(String goodsLabel){
        this.goodsLabel = goodsLabel;
    }

    public String getGoodsLabel(){
        return goodsLabel;
    }
    public void setPictures(String pictures){
        this.pictures = pictures;
    }

    public String getPictures(){
        return pictures;
    }
    public void setAttributeList(String attributeList){
        this.attributeList = attributeList;
    }

    public String getAttributeList(){
        return attributeList;
    }
    public void setDeliveryType(String deliveryType){
        this.deliveryType = deliveryType;
    }

    public String getDeliveryType(){
        return deliveryType;
    }
    public void setIsLimit(String isLimit){
        this.isLimit = isLimit;
    }

    public String getIsLimit(){
        return isLimit;
    }
    public void setLimitType(String limitType){
        this.limitType = limitType;
    }

    public String getLimitType(){
        return limitType;
    }
    public void setLimitNum(Long limitNum){
        this.limitNum = limitNum;
    }

    public Long getLimitNum(){
        return limitNum;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<CompanyGoodsSpecs> getSpecsList() {
        return specsList;
    }

    public void setSpecsList(List<CompanyGoodsSpecs> specsList) {
        this.specsList = specsList;
    }

    public GoodsClassify getGoodsClassify() {
        return goodsClassify;
    }

    public void setGoodsClassify(GoodsClassify goodsClassify) {
        this.goodsClassify = goodsClassify;
    }
}

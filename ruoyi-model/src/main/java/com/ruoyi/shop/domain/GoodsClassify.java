package com.ruoyi.shop.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品分类对象 shop_goods_classify
 *
 * @author ruoyi
 * @date 2024-01-05
 */
@ApiModel(value = "GoodsClassify", description = "商品分类")
@ToString
public class GoodsClassify extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long classId;

    /**
     * 父ID
     */
    @Excel(name = "父ID")
    @ApiModelProperty("父ID")
    private Long parentId;

    /**
     * 祖级列表
     */
    @Excel(name = "祖级列表")
    @ApiModelProperty("祖级列表")
    private String ancestors;

    /**
     * 名称
     */
    @Excel(name = "名称")
    @ApiModelProperty("名称")
    private String name;

    /**
     * 图片
     */
    @Excel(name = "图片")
    @ApiModelProperty("图片")
    private String picture;

    /**
     * 显示顺序
     */
    @Excel(name = "显示顺序")
    @ApiModelProperty("显示顺序")
    private Integer orderNum;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    private String delFlag;

    /**
     * 子部门
     */
    private List<GoodsClassify> children = new ArrayList<>();

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    public Long getClassId() {
        return classId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setAncestors(String ancestors) {
        this.ancestors = ancestors;
    }

    public String getAncestors() {
        return ancestors;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public List<GoodsClassify> getChildren() {
        return children;
    }

    public void setChildren(List<GoodsClassify> children) {
        this.children = children;
    }
}

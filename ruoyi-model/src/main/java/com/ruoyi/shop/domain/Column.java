package com.ruoyi.shop.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;

import java.util.List;

/**
 * 栏目设置对象 shop_column
 *
 * @author ruoyi
 * @date 2024-01-05
 */
@ApiModel(value = "Column", description = "栏目设置")
@ToString
public class Column extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty("主键")
    private Long id;

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
     * 栏目名称
     */
    @Excel(name = "栏目名称")
    @ApiModelProperty("栏目名称")
    private String columnName;

    /** 状态 */
    @Excel(name = "状态")
    @ApiModelProperty("状态")
    private String status;

    /** 显示顺序 */
    @Excel(name = "显示顺序")
    @ApiModelProperty("显示顺序")
    private Integer orderNum;

    /**
     * 图片
     */
    @Excel(name = "图片")
    @ApiModelProperty("图片")
    private String picture;

    /**
     * 是否跳转
     */
    @Excel(name = "是否跳转")
    @ApiModelProperty("是否跳转")
    private String isSkip;

    /**
     * 链接
     */
    @Excel(name = "链接")
    @ApiModelProperty("链接")
    private String interlinkage;

    /**
     * 子集合
     */
    private List<Column> children;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getAncestors() {
        return ancestors;
    }

    public void setAncestors(String ancestors) {
        this.ancestors = ancestors;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getPicture() {
        return picture;
    }

    public void setIsSkip(String isSkip) {
        this.isSkip = isSkip;
    }

    public String getIsSkip() {
        return isSkip;
    }

    public void setInterlinkage(String interlinkage) {
        this.interlinkage = interlinkage;
    }

    public String getInterlinkage() {
        return interlinkage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public List<Column> getChildren() {
        return children;
    }

    public void setChildren(List<Column> children) {
        this.children = children;
    }
}

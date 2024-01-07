package com.ruoyi.shop.domain;

import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;
import com.ruoyi.common.core.domain.BaseEntity;

import java.util.List;

/**
 * 内容分类对象 shop_content_type
 *
 * @author ruoyi
 * @date 2024-01-07
 */
@ApiModel(value = "ContentType", description = "内容分类")
@ToString
public class ContentType extends BaseEntity{
    private static final long serialVersionUID=1L;

    /** 主键 */
    private Long typeId;

    /** 父ID */
    @Excel(name = "父ID")
    @ApiModelProperty("父ID")
    private Long parentId;

    /** 祖级列表 */
    @Excel(name = "祖级列表")
    @ApiModelProperty("祖级列表")
    private String ancestors;

    /** 名称 */
    @Excel(name = "名称")
    @ApiModelProperty("名称")
    private String name;

    /** 状态 */
    @Excel(name = "状态")
    @ApiModelProperty("状态")
    private String status;

    /** 显示顺序 */
    @Excel(name = "显示顺序")
    @ApiModelProperty("显示顺序")
    private Integer orderNum;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    /**
     * 子集合
     */
    private List<ContentType> children;

    public void setTypeId(Long typeId){
        this.typeId = typeId;
    }

    public Long getTypeId(){
        return typeId;
    }
    public void setParentId(Long parentId){
        this.parentId = parentId;
    }

    public Long getParentId(){
        return parentId;
    }
    public void setAncestors(String ancestors){
        this.ancestors = ancestors;
    }

    public String getAncestors(){
        return ancestors;
    }
    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }
    public void setStatus(String status){
        this.status = status;
    }

    public String getStatus(){
        return status;
    }
    public void setOrderNum(Integer orderNum){
        this.orderNum = orderNum;
    }

    public Integer getOrderNum(){
        return orderNum;
    }
    public void setDelFlag(String delFlag){
        this.delFlag = delFlag;
    }

    public String getDelFlag(){
        return delFlag;
    }

    public List<ContentType> getChildren() {
        return children;
    }

    public void setChildren(List<ContentType> children) {
        this.children = children;
    }
}

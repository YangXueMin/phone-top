package com.ruoyi.shop.domain;

import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 栏目设置对象 shop_column
 *
 * @author ruoyi
 * @date 2024-01-05
 */
@ApiModel(value = "Column", description = "栏目设置")
@ToString
public class Column extends BaseEntity{
    private static final long serialVersionUID=1L;

    /** 主键 */
    @ApiModelProperty("主键")
    private Long id;

    /** 栏目类型 */
    @Excel(name = "栏目类型")
    @ApiModelProperty("栏目类型")
    private String columnType;

    /** 栏目名称 */
    @Excel(name = "栏目名称")
    @ApiModelProperty("栏目名称")
    private String columnName;

    /** 图片 */
    @Excel(name = "图片")
    @ApiModelProperty("图片")
    private String picture;

    /** 是否跳转 */
    @Excel(name = "是否跳转")
    @ApiModelProperty("是否跳转")
    private String isSkip;

    /** 链接 */
    @Excel(name = "链接")
    @ApiModelProperty("链接")
    private String interlinkage;

    public void setId(Long id){
        this.id = id;
    }

    public Long getId(){
        return id;
    }
    public void setColumnType(String columnType){
        this.columnType = columnType;
    }

    public String getColumnType(){
        return columnType;
    }
    public void setColumnName(String columnName){
        this.columnName = columnName;
    }

    public String getColumnName(){
        return columnName;
    }
    public void setPicture(String picture){
        this.picture = picture;
    }

    public String getPicture(){
        return picture;
    }
    public void setIsSkip(String isSkip){
        this.isSkip = isSkip;
    }

    public String getIsSkip(){
        return isSkip;
    }
    public void setInterlinkage(String interlinkage){
        this.interlinkage = interlinkage;
    }

    public String getInterlinkage(){
        return interlinkage;
    }

}

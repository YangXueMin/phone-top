package com.ruoyi.shop.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.common.utils.DictUtils;
import com.ruoyi.common.utils.StringUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * banner配置对象 shop_banner
 *
 * @author ruoyi
 * @date 2024-01-06
 */
@ApiModel(value = "Banner", description = "banner配置")
@ToString
public class Banner extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 名称
     */
    @Excel(name = "名称")
    @ApiModelProperty("名称")
    private String name;

    /**
     * 类型
     */
    @Excel(name = "类型")
    @ApiModelProperty("类型")
    private String type;

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
     * 跳转类型
     */
    @Excel(name = "跳转类型")
    @ApiModelProperty("跳转类型")
    private String skipType;

    /**
     * 栏目类型
     */
    @Excel(name = "栏目类型")
    @ApiModelProperty("栏目类型")
    private String columnType;

    /**
     * 链接地址
     */
    @Excel(name = "链接地址")
    @ApiModelProperty("链接地址")
    private String url;

    /**
     * 上线时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "上线时间", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty("上线时间")
    private Date startDate;

    /**
     * 下线时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "下线时间", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty("下线时间")
    private Date endDate;

    /**
     * 状态
     */
    @Excel(name = "状态")
    @ApiModelProperty("状态")
    private String status;

    @ApiModelProperty("跳转类型字典")
    private SysDictData skipTypeDicData;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
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

    public void setSkipType(String skipType) {
        this.skipType = skipType;
    }

    public String getSkipType() {
        return skipType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    public String getColumnType() {
        return columnType;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public SysDictData getSkipTypeDicData() {
        if (StringUtils.isNotBlank(skipType)) {
            return DictUtils.getDictData("shop_banner_column_type", skipType);
        }
        return skipTypeDicData;
    }
}

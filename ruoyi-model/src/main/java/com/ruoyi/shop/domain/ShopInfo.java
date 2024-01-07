package com.ruoyi.shop.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;

import java.util.Date;

/**
 * 店铺信息对象 shop_info
 *
 * @author ruoyi
 * @date 2024-01-05
 */
@ApiModel(value = "ShopInfo", description = "店铺信息")
@ToString
public class ShopInfo extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty("主键")
    private Long id;

    /**
     * 店铺名称
     */
    @ApiModelProperty("店铺名称")
    @Excel(name = "店铺名称")
    private String name;

    /**
     * 省
     */
    @ApiModelProperty("省")
    @Excel(name = "省")
    private String province;

    /**
     * 市
     */
    @ApiModelProperty("市")
    @Excel(name = "市")
    private String city;

    /**
     * 区县
     */
    @ApiModelProperty("区县")
    @Excel(name = "区县")
    private String county;

    /**
     * 详细地址
     */
    @ApiModelProperty("详细地址")
    @Excel(name = "详细地址")
    private String address;

    /**
     * 经度
     */
    @ApiModelProperty("经度")
    @Excel(name = "经度")
    private String longitude;

    /**
     * 纬度
     */
    @ApiModelProperty("纬度")
    @Excel(name = "纬度")
    private String latitude;

    /**
     * 门店电话
     */
    @ApiModelProperty("门店电话")
    @Excel(name = "门店电话")
    private String mobile;

    /**
     * 营业时间
     */
    @ApiModelProperty("营业时间")
    @Excel(name = "营业时间")
    private String businessHours;

    /**
     * 开始时间
     */
    @ApiModelProperty("开始时间")
    @JsonFormat(pattern = "HH:mm")
    @Excel(name = "开始时间", width = 30, dateFormat = "HH:mm")
    private Date startTime;

    /**
     * 结束时间
     */
    @ApiModelProperty("结束时间")
    @JsonFormat(pattern = "HH:mm")
    @Excel(name = "结束时间", width = 30, dateFormat = "HH:mm")
    private Date endTime;

    /**
     * 配送方式
     */
    @ApiModelProperty("配送方式")
    @Excel(name = "配送方式")
    private String distributionMode;

    /**
     * 是否活动
     */
    @ApiModelProperty("是否活动")
    @Excel(name = "是否活动")
    private String isActivity;

    /**
     * 是否同步商品
     */
    @ApiModelProperty("是否同步商品")
    @Excel(name = "是否同步商品")
    private String isSyncShop;

    /**
     * 是否同步资质
     */
    @ApiModelProperty("是否同步资质")
    @Excel(name = "是否同步资质")
    private String isSyncNatural;

    /**
     * 食品安全档案
     */
    @ApiModelProperty("食品安全档案")
    @Excel(name = "食品安全档案")
    private String securityFile;

    /**
     * 门店服务资质
     */
    @ApiModelProperty("门店服务资质")
    @Excel(name = "门店服务资质")
    private String naturalFile;

    /**
     * 状态
     */
    @ApiModelProperty("状态")
    @Excel(name = "状态")
    private String status;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    private String delFlag;

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

    public void setProvince(String province) {
        this.province = province;
    }

    public String getProvince() {
        return province;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getCounty() {
        return county;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMobile() {
        return mobile;
    }

    public void setBusinessHours(String businessHours) {
        this.businessHours = businessHours;
    }

    public String getBusinessHours() {
        return businessHours;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setDistributionMode(String distributionMode) {
        this.distributionMode = distributionMode;
    }

    public String getDistributionMode() {
        return distributionMode;
    }

    public void setIsActivity(String isActivity) {
        this.isActivity = isActivity;
    }

    public String getIsActivity() {
        return isActivity;
    }

    public void setIsSyncShop(String isSyncShop) {
        this.isSyncShop = isSyncShop;
    }

    public String getIsSyncShop() {
        return isSyncShop;
    }

    public void setIsSyncNatural(String isSyncNatural) {
        this.isSyncNatural = isSyncNatural;
    }

    public String getIsSyncNatural() {
        return isSyncNatural;
    }

    public void setSecurityFile(String securityFile) {
        this.securityFile = securityFile;
    }

    public String getSecurityFile() {
        return securityFile;
    }

    public void setNaturalFile(String naturalFile) {
        this.naturalFile = naturalFile;
    }

    public String getNaturalFile() {
        return naturalFile;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getDelFlag() {
        return delFlag;
    }
}

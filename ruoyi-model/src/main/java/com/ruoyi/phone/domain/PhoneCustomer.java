package com.ruoyi.phone.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.entity.WechatConfig;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 客服配置对象 phone_customer
 *
 * @author ruoyi
 * @date 2024-03-04
 */
@ApiModel(value = "PhoneCustomer", description = "客服配置")
@ToString
public class PhoneCustomer extends BaseEntity{
    private static final long serialVersionUID=1L;

    /** 主键 */
    private Long id;

    /** 公司ID */
    @Excel(name = "公司ID")
    @ApiModelProperty("公司ID")
    private Long companyId;

    /** APPID */
    @Excel(name = "APPID")
    @ApiModelProperty("APPID")
    private String appId;

    /** 客服banner */
    @Excel(name = "客服banner")
    @ApiModelProperty("客服banner")
    private String customerBanner;

    /** 客服二维码 */
    @Excel(name = "客服二维码")
    @ApiModelProperty("客服二维码")
    private String customerQrCode;

    /** 客服电话 */
    @Excel(name = "客服电话")
    @ApiModelProperty("客服电话")
    private String customerPhone;

    /** 客服上班时间 */
    @Excel(name = "客服上班时间")
    @ApiModelProperty("客服上班时间")
    private String customerBusinessHours;

    @ApiModelProperty("微信配置")
    private WechatConfig wechatConfig;

    public void setId(Long id){
        this.id = id;
    }

    public Long getId(){
        return id;
    }
    public void setCompanyId(Long companyId){
        this.companyId = companyId;
    }

    public Long getCompanyId(){
        return companyId;
    }
    public void setAppId(String appId){
        this.appId = appId;
    }

    public String getAppId(){
        return appId;
    }
    public void setCustomerBanner(String customerBanner){
        this.customerBanner = customerBanner;
    }

    public String getCustomerBanner(){
        return customerBanner;
    }
    public void setCustomerQrCode(String customerQrCode){
        this.customerQrCode = customerQrCode;
    }

    public String getCustomerQrCode(){
        return customerQrCode;
    }
    public void setCustomerPhone(String customerPhone){
        this.customerPhone = customerPhone;
    }

    public String getCustomerPhone(){
        return customerPhone;
    }
    public void setCustomerBusinessHours(String customerBusinessHours){
        this.customerBusinessHours = customerBusinessHours;
    }

    public String getCustomerBusinessHours(){
        return customerBusinessHours;
    }

    public WechatConfig getWechatConfig() {
        return wechatConfig;
    }

    public void setWechatConfig(WechatConfig wechatConfig) {
        this.wechatConfig = wechatConfig;
    }
}

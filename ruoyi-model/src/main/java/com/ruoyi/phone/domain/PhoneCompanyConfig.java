package com.ruoyi.phone.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.entity.WechatConfig;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 企业配置对象 phone_company_config
 *
 * @author ruoyi
 * @date 2024-03-07
 */
@ApiModel(value = "PhoneCompanyConfig", description = "企业配置")
@ToString
public class PhoneCompanyConfig extends BaseEntity{
    private static final long serialVersionUID=1L;

    /** 主键 */
    private Long id;

    /** 公司ID */
    @Excel(name = "公司ID")
    @ApiModelProperty("公司ID")
    private Long deptId;

    /** appId */
    @Excel(name = "appId")
    @ApiModelProperty("appId")
    private String appId;

    /** 推广海报 */
    @Excel(name = "推广海报")
    @ApiModelProperty("推广海报")
    private String promotionPoster;

    /** 慢充简介 */
    @Excel(name = "慢充简介")
    @ApiModelProperty("慢充简介")
    private String slowChargingContent;

    /** 快充简介 */
    @Excel(name = "快充简介")
    @ApiModelProperty("快充简介")
    private String quickChargeContent;

    /** 电费简介 */
    @Excel(name = "电费简介")
    @ApiModelProperty("电费简介")
    private String electricityContent;

    /** 分享标题 */
    @Excel(name = "分享标题")
    @ApiModelProperty("分享标题")
    private String shareTitle;

    /** 分享内容 */
    @Excel(name = "分享内容")
    @ApiModelProperty("分享内容")
    private String shareContent;

    /** 分享图片 */
    @Excel(name = "分享图片")
    @ApiModelProperty("分享图片")
    private String sharePhoto;

    @ApiModelProperty("微信配置")
    private WechatConfig wechatConfig;

    public void setId(Long id){
        this.id = id;
    }

    public Long getId(){
        return id;
    }
    public void setDeptId(Long deptId){
        this.deptId = deptId;
    }

    public Long getDeptId(){
        return deptId;
    }
    public void setAppId(String appId){
        this.appId = appId;
    }

    public String getAppId(){
        return appId;
    }
    public void setPromotionPoster(String promotionPoster){
        this.promotionPoster = promotionPoster;
    }

    public String getPromotionPoster(){
        return promotionPoster;
    }
    public void setSlowChargingContent(String slowChargingContent){
        this.slowChargingContent = slowChargingContent;
    }

    public String getSlowChargingContent(){
        return slowChargingContent;
    }
    public void setQuickChargeContent(String quickChargeContent){
        this.quickChargeContent = quickChargeContent;
    }

    public String getQuickChargeContent(){
        return quickChargeContent;
    }
    public void setElectricityContent(String electricityContent){
        this.electricityContent = electricityContent;
    }

    public String getElectricityContent(){
        return electricityContent;
    }
    public void setShareTitle(String shareTitle){
        this.shareTitle = shareTitle;
    }

    public String getShareTitle(){
        return shareTitle;
    }
    public void setShareContent(String shareContent){
        this.shareContent = shareContent;
    }

    public String getShareContent(){
        return shareContent;
    }
    public void setSharePhoto(String sharePhoto){
        this.sharePhoto = sharePhoto;
    }

    public String getSharePhoto(){
        return sharePhoto;
    }

    public WechatConfig getWechatConfig() {
        return wechatConfig;
    }

    public void setWechatConfig(WechatConfig wechatConfig) {
        this.wechatConfig = wechatConfig;
    }
}

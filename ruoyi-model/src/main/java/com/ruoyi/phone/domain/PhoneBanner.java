package com.ruoyi.phone.domain;

import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * banner轮播配置对象 phone_banner
 *
 * @author ruoyi
 * @date 2024-03-04
 */
@ApiModel(value = "PhoneBanner", description = "banner轮播配置")
@ToString
public class PhoneBanner extends BaseEntity{
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

    /** 标题 */
    @Excel(name = "标题")
    @ApiModelProperty("标题")
    private String title;

    /** banner */
    @Excel(name = "banner")
    @ApiModelProperty("banner")
    private String banner;

    /** 状态 */
    @Excel(name = "状态")
    @ApiModelProperty("状态")
    private String status;

    /** 显示顺序 */
    @Excel(name = "显示顺序")
    @ApiModelProperty("显示顺序")
    private Long orderNum;

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
    public void setTitle(String title){
        this.title = title;
    }

    public String getTitle(){
        return title;
    }
    public void setBanner(String banner){
        this.banner = banner;
    }

    public String getBanner(){
        return banner;
    }
    public void setStatus(String status){
        this.status = status;
    }

    public String getStatus(){
        return status;
    }
    public void setOrderNum(Long orderNum){
        this.orderNum = orderNum;
    }

    public Long getOrderNum(){
        return orderNum;
    }

}

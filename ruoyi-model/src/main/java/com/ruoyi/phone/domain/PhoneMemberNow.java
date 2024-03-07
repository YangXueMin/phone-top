package com.ruoyi.phone.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.entity.WechatConfig;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 用户须知配置对象 phone_member_now
 *
 * @author ruoyi
 * @date 2024-03-04
 */
@ApiModel(value = "PhoneMemberNow", description = "用户须知配置")
@ToString
public class PhoneMemberNow extends BaseEntity{
    private static final long serialVersionUID=1L;

    /** 主键 */
    private Long id;

    /** 公司ID */
    @Excel(name = "公司ID")
    @ApiModelProperty("公司ID")
    private Long companyId;

    /** 公众号配置ID */
    @Excel(name = "公众号配置ID")
    @ApiModelProperty("公众号配置ID")
    private String appId;

    /** 标题 */
    @Excel(name = "标题")
    @ApiModelProperty("标题")
    private String title;

    /** 内容 */
    @Excel(name = "内容")
    @ApiModelProperty("内容")
    private String content;

    /** 状态 */
    @Excel(name = "状态")
    @ApiModelProperty("状态")
    private String status;

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
    public void setTitle(String title){
        this.title = title;
    }

    public String getTitle(){
        return title;
    }
    public void setContent(String content){
        this.content = content;
    }

    public String getContent(){
        return content;
    }
    public void setStatus(String status){
        this.status = status;
    }

    public String getStatus(){
        return status;
    }

    public WechatConfig getWechatConfig() {
        return wechatConfig;
    }

    public void setWechatConfig(WechatConfig wechatConfig) {
        this.wechatConfig = wechatConfig;
    }
}

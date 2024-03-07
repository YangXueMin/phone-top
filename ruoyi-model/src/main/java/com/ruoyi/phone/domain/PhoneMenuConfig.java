package com.ruoyi.phone.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.entity.WechatConfig;
import com.ruoyi.common.utils.DictUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.StringUtils;

/**
 * 小程序菜单配置对象 phone_menu_config
 *
 * @author ruoyi
 * @date 2024-03-07
 */
@ApiModel(value = "PhoneMenuConfig", description = "小程序菜单配置")
@ToString
public class PhoneMenuConfig extends BaseEntity{
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

    /** 图标 */
    @Excel(name = "图标")
    @ApiModelProperty("图标")
    private String url;

    /** 状态 */
    @Excel(name = "状态")
    @ApiModelProperty("状态(字典值：phone_status)")
    private String status;

    /** 状态 */
    @Excel(name = "状态")
    @ApiModelProperty("状态")
    private String statusLabel;

    /** 显示顺序 */
    @Excel(name = "显示顺序")
    @ApiModelProperty("显示顺序")
    private Long orderNum;

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
    public void setUrl(String url){
        this.url = url;
    }

    public String getUrl(){
        return url;
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

    public WechatConfig getWechatConfig() {
        return wechatConfig;
    }

    public void setWechatConfig(WechatConfig wechatConfig) {
        this.wechatConfig = wechatConfig;
    }

    public String getStatusLabel() {
        if(StringUtils.isNotBlank(status)){
            return DictUtils.getDictLabel("phone_status",status);
        }
        return statusLabel;
    }

    public void setStatusLabel(String statusLabel) {
        this.statusLabel = statusLabel;
    }
}

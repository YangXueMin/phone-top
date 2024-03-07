package com.ruoyi.phone.domain;

import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 接口地址配置对象 phone_interface_config
 *
 * @author ruoyi
 * @date 2024-03-07
 */
@ApiModel(value = "PhoneInterfaceConfig", description = "接口地址配置")
@ToString
public class PhoneInterfaceConfig extends BaseEntity{
    private static final long serialVersionUID=1L;

    /** 主键 */
    private Long id;

    /** 公司ID */
    @Excel(name = "公司ID")
    @ApiModelProperty("公司ID")
    private Long companyId;

    /** 公众号ID */
    @Excel(name = "公众号ID")
    @ApiModelProperty("公众号ID")
    private String appId;

    /** 类型（电费：1，快充：2，慢充：3） */
    @Excel(name = "类型", readConverterExp = "电=费：1，快充：2，慢充：3")
    @ApiModelProperty("类型")
    private String type;

    /** 开关类型（开：1，关:2） */
    @Excel(name = "开关类型", readConverterExp = "开=：1，关:2")
    @ApiModelProperty("开关类型")
    private String switchType;

    /** 接口类型 */
    @Excel(name = "接口类型")
    @ApiModelProperty("接口类型")
    private String interfaceType;

    /** 接口地址 */
    @Excel(name = "接口地址")
    @ApiModelProperty("接口地址")
    private String interfaceUrl;

    /** 商户ID */
    @Excel(name = "商户ID")
    @ApiModelProperty("商户ID")
    private String mchId;

    /** apiKey */
    @Excel(name = "apiKey")
    @ApiModelProperty("apiKey")
    private String apiKey;

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
    public void setType(String type){
        this.type = type;
    }

    public String getType(){
        return type;
    }
    public void setSwitchType(String switchType){
        this.switchType = switchType;
    }

    public String getSwitchType(){
        return switchType;
    }
    public void setInterfaceType(String interfaceType){
        this.interfaceType = interfaceType;
    }

    public String getInterfaceType(){
        return interfaceType;
    }
    public void setInterfaceUrl(String interfaceUrl){
        this.interfaceUrl = interfaceUrl;
    }

    public String getInterfaceUrl(){
        return interfaceUrl;
    }
    public void setMchId(String mchId){
        this.mchId = mchId;
    }

    public String getMchId(){
        return mchId;
    }
    public void setApiKey(String apiKey){
        this.apiKey = apiKey;
    }

    public String getApiKey(){
        return apiKey;
    }

}

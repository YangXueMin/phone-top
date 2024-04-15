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
    private Long deptId;

    /** 公众号ID */
    @Excel(name = "公众号ID")
    @ApiModelProperty("公众号ID")
    private String appId;

    /** 开关类型（开：1，关:2） */
    @Excel(name = "开关类型", readConverterExp = "开1，关:2")
    @ApiModelProperty("开关类型")
    private String switchType;

    /** 接口类型 */
    @Excel(name = "接口类型")
    @ApiModelProperty("接口类型(字典值：phone_interface_type 系统：1，大猿人：2)")
    private String interfaceType;

    /** 接口类型 */
    @Excel(name = "接口类型")
    @ApiModelProperty("接口类型(系统：1，大猿人：2)")
    private String interfaceTypeLabel;

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

    @Excel(name = "是否同步状态")
    @ApiModelProperty("是否同步状态")
    private String isSync;

    @Excel(name = "是否同步状态")
    @ApiModelProperty("是否同步状态")
    private String isSyncLabel;

    @Excel(name = "是否退款")
    @ApiModelProperty("是否退款")
    private String isRefund;

    @Excel(name = "是否退款")
    @ApiModelProperty("是否退款")
    private String isRefundLabel;

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

    public WechatConfig getWechatConfig() {
        return wechatConfig;
    }

    public void setWechatConfig(WechatConfig wechatConfig) {
        this.wechatConfig = wechatConfig;
    }

    public String getInterfaceTypeLabel() {
        if(StringUtils.isNotBlank(interfaceType)){
            return DictUtils.getDictLabel("phone_interface_type",interfaceType);
        }
        return interfaceTypeLabel;
    }

    public String getIsSync() {
        return isSync;
    }

    public void setIsSync(String isSync) {
        this.isSync = isSync;
    }

    public String getIsRefund() {
        return isRefund;
    }

    public void setIsRefund(String isRefund) {
        this.isRefund = isRefund;
    }

    public String getIsSyncLabel() {
        if(StringUtils.isNotBlank(isSync)){
            return DictUtils.getDictLabel("phone_status",isSync);
        }
        return isSyncLabel;
    }

    public void setIsSyncLabel(String isSyncLabel) {
        this.isSyncLabel = isSyncLabel;
    }

    public String getIsRefundLabel() {
        if(StringUtils.isNotBlank(isRefund)){
            return DictUtils.getDictLabel("phone_status",isRefund);
        }
        return isRefundLabel;
    }

    public void setIsRefundLabel(String isRefundLabel) {
        this.isRefundLabel = isRefundLabel;
    }

    public String getAppId() {
        return appId;
    }

    public void setInterfaceTypeLabel(String interfaceTypeLabel) {
        this.interfaceTypeLabel = interfaceTypeLabel;
    }
}

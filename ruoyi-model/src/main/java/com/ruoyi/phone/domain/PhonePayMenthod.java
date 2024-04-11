package com.ruoyi.phone.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.entity.WechatConfig;
import com.ruoyi.common.utils.DictUtils;
import com.ruoyi.common.utils.StringUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 充值方式配置对象 phone_pay_menthod
 *
 * @author ruoyi
 * @date 2024-04-11
 */
@ApiModel(value = "PhonePayMenthod", description = "充值方式配置")
@ToString
public class PhonePayMenthod extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 公司ID
     */
    @Excel(name = "公司ID")
    @ApiModelProperty("公司ID")
    private Long deptId;

    /**
     * 公众号配置ID
     */
    @Excel(name = "公众号配置ID")
    @ApiModelProperty("公众号配置ID")
    private String appId;

    /**
     * 充值类型
     */
    @Excel(name = "充值类型")
    @ApiModelProperty("充值类型（ 字典值：phone_pay_method）")
    private String value;

    /**
     * 充值类型
     */
    @Excel(name = "充值类型")
    @ApiModelProperty("充值类型")
    private String valueLabel;

    @ApiModelProperty("微信配置")
    private WechatConfig wechatConfig;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppId() {
        return appId;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public String getValueLabel() {
        if (StringUtils.isNotBlank(value)) {
            return DictUtils.getDictLabel("phone_pay_method", value);
        }
        return valueLabel;
    }

    public void setValueLabel(String valueLabel) {
        this.valueLabel = valueLabel;
    }

    public WechatConfig getWechatConfig() {
        return wechatConfig;
    }

    public void setWechatConfig(WechatConfig wechatConfig) {
        this.wechatConfig = wechatConfig;
    }
}

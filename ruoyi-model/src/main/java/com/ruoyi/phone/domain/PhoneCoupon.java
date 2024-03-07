package com.ruoyi.phone.domain;

import java.math.BigDecimal;
import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 优惠券管理对象 phone_coupon
 *
 * @author ruoyi
 * @date 2024-03-07
 */
@ApiModel(value = "PhoneCoupon", description = "优惠券管理")
@ToString
public class PhoneCoupon extends BaseEntity{
    private static final long serialVersionUID=1L;

    /** 主键 */
    private Long id;

    /** 公司ID */
    @Excel(name = "公司ID")
    @ApiModelProperty("公司ID")
    private Long companyId;

    /** 公众号appID */
    @Excel(name = "公众号appID")
    @ApiModelProperty("公众号appID")
    private String appId;

    /** 标题 */
    @Excel(name = "标题")
    @ApiModelProperty("标题")
    private String title;

    /** 有效期 */
    @Excel(name = "有效期")
    @ApiModelProperty("有效期")
    private Long termValidity;

    /** 充值金额 */
    @Excel(name = "充值金额")
    @ApiModelProperty("充值金额")
    private BigDecimal rechargeAmount;

    /** 满多少元 */
    @Excel(name = "满多少元")
    @ApiModelProperty("满多少元")
    private BigDecimal fullMoney;

    /** 减多少元 */
    @Excel(name = "减多少元")
    @ApiModelProperty("减多少元")
    private BigDecimal minusMoney;

    /** 发放方式（邀请：1，充值：2） */
    @Excel(name = "发放方式", readConverterExp = "邀请：1，充值：2,首单：3")
    @ApiModelProperty("发放方式")
    private String distributionMode;

    /** 发放数量 */
    @Excel(name = "发放数量")
    @ApiModelProperty("发放数量")
    private Long number;

    /** 状态（正常：1，停用：2） */
    @Excel(name = "状态", readConverterExp = "正=常：1，停用：2")
    @ApiModelProperty("状态")
    private String status;

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
    public void setTermValidity(Long termValidity){
        this.termValidity = termValidity;
    }

    public Long getTermValidity(){
        return termValidity;
    }
    public void setRechargeAmount(BigDecimal rechargeAmount){
        this.rechargeAmount = rechargeAmount;
    }

    public BigDecimal getRechargeAmount(){
        return rechargeAmount;
    }
    public void setFullMoney(BigDecimal fullMoney){
        this.fullMoney = fullMoney;
    }

    public BigDecimal getFullMoney(){
        return fullMoney;
    }
    public void setMinusMoney(BigDecimal minusMoney){
        this.minusMoney = minusMoney;
    }

    public BigDecimal getMinusMoney(){
        return minusMoney;
    }
    public void setDistributionMode(String distributionMode){
        this.distributionMode = distributionMode;
    }

    public String getDistributionMode(){
        return distributionMode;
    }
    public void setNumber(Long number){
        this.number = number;
    }

    public Long getNumber(){
        return number;
    }
    public void setStatus(String status){
        this.status = status;
    }

    public String getStatus(){
        return status;
    }

}

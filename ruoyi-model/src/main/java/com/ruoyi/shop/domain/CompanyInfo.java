package com.ruoyi.shop.domain;

import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 企业信息配置对象 shop_company_info
 *
 * @author ruoyi
 * @date 2024-01-05
 */
@ApiModel(value = "CompanyInfo", description = "企业信息配置")
@ToString
public class CompanyInfo extends BaseEntity{
    private static final long serialVersionUID=1L;

    /** 主键 */
    @ApiModelProperty("主键")
    private Long id;

    /** 企业ID */
    @Excel(name = "企业ID")
    @ApiModelProperty("企业ID")
    private Long companyId;

    /** logo */
    @Excel(name = "logo")
    @ApiModelProperty("logo")
    private String logo;

    /** 简称 */
    @Excel(name = "简称")
    @ApiModelProperty("简称")
    private String shortName;

    /** 总部电话 */
    @Excel(name = "总部电话")
    @ApiModelProperty("总部电话")
    private String phone;

    /** 企业资质 */
    @Excel(name = "企业资质")
    @ApiModelProperty("企业资质")
    private String natural;

    /** 服务证件 */
    @Excel(name = "服务证件")
    @ApiModelProperty("服务证件")
    private String certificate;

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
    public void setLogo(String logo){
        this.logo = logo;
    }

    public String getLogo(){
        return logo;
    }
    public void setShortName(String shortName){
        this.shortName = shortName;
    }

    public String getShortName(){
        return shortName;
    }
    public void setPhone(String phone){
        this.phone = phone;
    }

    public String getPhone(){
        return phone;
    }
    public void setNatural(String natural){
        this.natural = natural;
    }

    public String getNatural(){
        return natural;
    }
    public void setCertificate(String certificate){
        this.certificate = certificate;
    }

    public String getCertificate(){
        return certificate;
    }

}

package com.ruoyi.phone.domain;

import java.math.BigDecimal;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.entity.WechatConfig;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 价格配置对象 phone_price
 *
 * @author ruoyi
 * @date 2024-03-07
 */
@ApiModel(value = "PhonePrice", description = "价格配置")
@ToString
public class PhonePrice extends BaseEntity{
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
    private Long configId;

    /** 充值方式（电网：0，快充：1，慢充：2） */
    @Excel(name = "充值方式", readConverterExp = "电=网：0，快充：1，慢充：2")
    @ApiModelProperty("充值方式")
    private String method;

    /** 类型(移动、联通、电信、国家电网、南方电网) */
    @Excel(name = "类型(移动、联通、电信、国家电网、南方电网)")
    @ApiModelProperty("类型(移动、联通、电信、国家电网、南方电网)")
    private String type;

    /** 原价 */
    @Excel(name = "原价")
    @ApiModelProperty("原价")
    private BigDecimal originalPrice;

    /** 折扣 */
    @Excel(name = "折扣")
    @ApiModelProperty("折扣")
    private BigDecimal discount;

    /** 售价 */
    @Excel(name = "售价")
    @ApiModelProperty("售价")
    private BigDecimal sellPrice;

    /** 会员价 */
    @Excel(name = "会员价")
    @ApiModelProperty("会员价")
    private BigDecimal memberPrice;

    /** 直推佣金 */
    @Excel(name = "直推佣金")
    @ApiModelProperty("直推佣金")
    private BigDecimal directCommission;

    /** 间推佣金 */
    @Excel(name = "间推佣金")
    @ApiModelProperty("间推佣金")
    private BigDecimal indirectCommission;

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
    public void setConfigId(Long configId){
        this.configId = configId;
    }

    public Long getConfigId(){
        return configId;
    }
    public void setMethod(String method){
        this.method = method;
    }

    public String getMethod(){
        return method;
    }
    public void setType(String type){
        this.type = type;
    }

    public String getType(){
        return type;
    }
    public void setOriginalPrice(BigDecimal originalPrice){
        this.originalPrice = originalPrice;
    }

    public BigDecimal getOriginalPrice(){
        return originalPrice;
    }
    public void setDiscount(BigDecimal discount){
        this.discount = discount;
    }

    public BigDecimal getDiscount(){
        return discount;
    }
    public void setSellPrice(BigDecimal sellPrice){
        this.sellPrice = sellPrice;
    }

    public BigDecimal getSellPrice(){
        return sellPrice;
    }
    public void setMemberPrice(BigDecimal memberPrice){
        this.memberPrice = memberPrice;
    }

    public BigDecimal getMemberPrice(){
        return memberPrice;
    }
    public void setDirectCommission(BigDecimal directCommission){
        this.directCommission = directCommission;
    }

    public BigDecimal getDirectCommission(){
        return directCommission;
    }
    public void setIndirectCommission(BigDecimal indirectCommission){
        this.indirectCommission = indirectCommission;
    }

    public BigDecimal getIndirectCommission(){
        return indirectCommission;
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
}

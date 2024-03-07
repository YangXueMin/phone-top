package com.ruoyi.phone.domain;

import java.math.BigDecimal;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.entity.Member;
import com.ruoyi.common.core.domain.entity.WechatConfig;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 佣金提现记录对象 phone_commission_log
 *
 * @author ruoyi
 * @date 2024-03-05
 */
@ApiModel(value = "PhoneCommissionLog", description = "佣金提现记录")
@ToString
public class PhoneCommissionLog extends BaseEntity{
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

    /** 会员ID */
    @Excel(name = "会员ID")
    @ApiModelProperty("会员ID")
    private Long memberId;

    /** 提现前佣金 */
    @Excel(name = "提现前佣金")
    @ApiModelProperty("提现前佣金")
    private BigDecimal commissionBefore;

    /** 提现金额 */
    @Excel(name = "提现金额")
    @ApiModelProperty("提现金额")
    private BigDecimal money;

    /** 提现后佣金 */
    @Excel(name = "提现后佣金")
    @ApiModelProperty("提现后佣金")
    private BigDecimal commissionAfter;

    @ApiModelProperty("微信配置")
    private WechatConfig wechatConfig;

    @ApiModelProperty("会员信息")
    private Member member;

    public WechatConfig getWechatConfig() {
        return wechatConfig;
    }

    public void setWechatConfig(WechatConfig wechatConfig) {
        this.wechatConfig = wechatConfig;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

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
    public void setMemberId(Long memberId){
        this.memberId = memberId;
    }

    public Long getMemberId(){
        return memberId;
    }
    public void setCommissionBefore(BigDecimal commissionBefore){
        this.commissionBefore = commissionBefore;
    }

    public BigDecimal getCommissionBefore(){
        return commissionBefore;
    }
    public void setMoney(BigDecimal money){
        this.money = money;
    }

    public BigDecimal getMoney(){
        return money;
    }
    public void setCommissionAfter(BigDecimal commissionAfter){
        this.commissionAfter = commissionAfter;
    }

    public BigDecimal getCommissionAfter(){
        return commissionAfter;
    }

}

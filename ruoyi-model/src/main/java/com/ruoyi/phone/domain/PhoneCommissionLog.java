package com.ruoyi.phone.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
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
 * @date 2024-03-10
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
    private Long appId;

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

    /** 审批状态 */
    @Excel(name = "审批状态")
    @ApiModelProperty("审批状态")
    private String auditStatus;

    /** 审批人 */
    @Excel(name = "审批人")
    @ApiModelProperty("审批人")
    private Long auditId;

    /** 审批时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "审批时间", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty("审批时间")
    private Date auditTime;

    @ApiModelProperty("微信配置")
    private WechatConfig wechatConfig;

    @ApiModelProperty("会员信息")
    private Member member;

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
    public void setAppId(Long appId){
        this.appId = appId;
    }

    public Long getAppId(){
        return appId;
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
    public void setAuditStatus(String auditStatus){
        this.auditStatus = auditStatus;
    }

    public String getAuditStatus(){
        return auditStatus;
    }
    public void setAuditId(Long auditId){
        this.auditId = auditId;
    }

    public Long getAuditId(){
        return auditId;
    }
    public void setAuditTime(Date auditTime){
        this.auditTime = auditTime;
    }

    public Date getAuditTime(){
        return auditTime;
    }

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
}

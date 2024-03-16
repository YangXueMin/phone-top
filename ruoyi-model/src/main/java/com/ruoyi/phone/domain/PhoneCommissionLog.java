package com.ruoyi.phone.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.entity.Member;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.entity.WechatConfig;
import com.ruoyi.common.utils.DictUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.StringUtils;

/**
 * 佣金提现记录对象 phone_commission_log
 *
 * @author ruoyi
 * @date 2024-03-10
 */
@ApiModel(value = "PhoneCommissionLog", description = "佣金提现记录")
@ToString
public class PhoneCommissionLog extends BaseEntity {
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
     * 会员ID
     */
    @Excel(name = "会员ID")
    @ApiModelProperty("会员ID")
    private Long memberId;

    /**
     * 提现类型
     */
    @Excel(name = "提现类型")
    @ApiModelProperty("提现类型（字典：phone_commission_type  1:提现到余额，2：提现到支付宝）")
    private String type;


    /**
     * 提现类型
     */
    @Excel(name = "提现类型")
    @ApiModelProperty("提现类型（字典：phone_commission_type  1:提现到余额，2：提现到支付宝）")
    private String typeLabel;

    /**
     * 提现前佣金
     */
    @Excel(name = "提现前佣金")
    @ApiModelProperty("提现前佣金")
    private BigDecimal commissionBefore;

    /**
     * 提现金额
     */
    @Excel(name = "提现金额")
    @ApiModelProperty("提现金额")
    private BigDecimal money;

    /**
     * 手续费
     */
    @Excel(name = "手续费")
    @ApiModelProperty("手续费")
    private BigDecimal chargeMoney;

    /**
     * 提现后佣金
     */
    @Excel(name = "提现后佣金")
    @ApiModelProperty("提现后佣金")
    private BigDecimal commissionAfter;

    /**
     * 支付宝真实姓名
     */
    @Excel(name = "支付宝真实姓名")
    @ApiModelProperty("支付宝真实姓名")
    private String alipayName;

    /**
     * 支付宝手机号
     */
    @Excel(name = "支付宝手机号")
    @ApiModelProperty("支付宝手机号")
    private String alipayMobile;

    /**
     * 审批状态
     */
    @Excel(name = "审批状态")
    @ApiModelProperty("审批状态(字典：phone_audit_status   1：待审批，2：已审批，3：已拒绝)")
    private String auditStatus;

    /**
     * 审批状态
     */
    @Excel(name = "审批状态")
    @ApiModelProperty("审批状态(字典：phone_audit_status   1：待审批，2：已审批，3：已拒绝)")
    private String auditStatusLabel;

    /**
     * 审批人
     */
    @Excel(name = "审批人")
    @ApiModelProperty("审批人")
    private Long auditId;

    /**
     * 审批时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "审批时间", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty("审批时间")
    private Date auditTime;

    @ApiModelProperty("微信配置")
    private WechatConfig wechatConfig;

    @ApiModelProperty("会员信息")
    private Member member;

    @ApiModelProperty("审批信息")
    private SysUser user;

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

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setCommissionBefore(BigDecimal commissionBefore) {
        this.commissionBefore = commissionBefore;
    }

    public BigDecimal getCommissionBefore() {
        return commissionBefore;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setCommissionAfter(BigDecimal commissionAfter) {
        this.commissionAfter = commissionAfter;
    }

    public BigDecimal getCommissionAfter() {
        return commissionAfter;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditId(Long auditId) {
        this.auditId = auditId;
    }

    public Long getAuditId() {
        return auditId;
    }

    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    public Date getAuditTime() {
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

    public String getAlipayName() {
        return alipayName;
    }

    public void setAlipayName(String alipayName) {
        this.alipayName = alipayName;
    }

    public String getAlipayMobile() {
        return alipayMobile;
    }

    public void setAlipayMobile(String alipayMobile) {
        this.alipayMobile = alipayMobile;
    }

    public String getAuditStatusLabel() {
        if (StringUtils.isNotBlank(auditStatus)) {
            return DictUtils.getDictLabel("phone_audit_status", auditStatus);
        }
        return auditStatusLabel;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeLabel() {
        if (StringUtils.isNotBlank(type)) {
            return DictUtils.getDictLabel("phone_commission_type", type);
        }
        return typeLabel;
    }

    public void setTypeLabel(String typeLabel) {
        this.typeLabel = typeLabel;
    }

    public void setAuditStatusLabel(String auditStatusLabel) {
        this.auditStatusLabel = auditStatusLabel;
    }

    public SysUser getUser() {
        return user;
    }

    public void setUser(SysUser user) {
        this.user = user;
    }

    public BigDecimal getChargeMoney() {
        return chargeMoney;
    }

    public void setChargeMoney(BigDecimal chargeMoney) {
        this.chargeMoney = chargeMoney;
    }
}

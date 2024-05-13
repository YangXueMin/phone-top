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
 * 佣金生成记录对象 phone_commission_config
 *
 * @author ruoyi
 * @date 2024-03-05
 */
@ApiModel(value = "PhoneCommissionConfig", description = "佣金生成记录")
@ToString
public class PhoneCommissionConfig extends BaseEntity {
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
     * 订单ID
     */
    @Excel(name = "订单ID")
    @ApiModelProperty("订单ID")
    private Long orderId;

    /**
     * 推送会员ID
     */
    @Excel(name = "推送会员ID")
    @ApiModelProperty("推送会员ID")
    private Long commissionMemberId;

    /**
     * 佣金前金额
     */
    @Excel(name = "佣金前金额")
    @ApiModelProperty("佣金前金额")
    private BigDecimal commissionBefore;

    /**
     * 金额
     */
    @Excel(name = "金额")
    @ApiModelProperty("金额")
    private BigDecimal money;

    /**
     * 佣金后金额
     */
    @Excel(name = "佣金后金额")
    @ApiModelProperty("佣金后金额")
    private BigDecimal commissionAfter;

    @ApiModelProperty("微信配置")
    private WechatConfig wechatConfig;

    @ApiModelProperty("会员信息")
    private Member member;

    @ApiModelProperty("提成会员信息")
    private Member commissionMember;

    @ApiModelProperty("订单信息")
    private PhoneOrder order;

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

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
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

    public Long getCommissionMemberId() {
        return commissionMemberId;
    }

    public void setCommissionMemberId(Long commissionMemberId) {
        this.commissionMemberId = commissionMemberId;
    }

    public Member getCommissionMember() {
        return commissionMember;
    }

    public void setCommissionMember(Member commissionMember) {
        this.commissionMember = commissionMember;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public PhoneOrder getOrder() {
        return order;
    }

    public void setOrder(PhoneOrder order) {
        this.order = order;
    }
}

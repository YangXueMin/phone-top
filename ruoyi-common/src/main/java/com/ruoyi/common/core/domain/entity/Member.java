package com.ruoyi.common.core.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 会员管理对象 phone_member
 *
 * @author ruoyi
 * @date 2024-01-04
 */
@ToString
public class Member extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /** 公司ID */
    @Excel(name = "公司ID")
    private Long deptId;

    /** 公众号配置ID */
    @Excel(name = "公众号配置ID")
    private String appId;

    /**
     * 名称
     */
    @Excel(name = "名称")
    private String name;

    /**
     * 头像
     */
    @Excel(name = "头像")
    private String avatar;

    /**
     * 会员编号
     */
    @Excel(name = "会员编号")
    private String number;

    /**
     * 密码
     */
    @Excel(name = "密码")
    private String password;

    /**
     * 手机号
     */
    @Excel(name = "手机号")
    private String mobile;

    /**
     * 性别
     */
    @Excel(name = "性别")
    private String sex;

    /**
     * 生日
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "生日", width = 30, dateFormat = "yyyy-MM-dd")
    private Date birthday;

    /**
     * 余额
     */
    @Excel(name = "余额")
    private BigDecimal balance;

    /**
     * 佣金余额
     */
    @Excel(name = "佣金余额")
    private BigDecimal commissionBalance;

    /**
     * 提现金额
     */
    @Excel(name = "提现金额")
    private BigDecimal withdrawalAmount;

    /**
     * openId
     */
    @Excel(name = "openId")
    private String openId;

    /**
     * 是否会员
     */
    @Excel(name = "是否会员")
    private String isMember;

    /** 到期时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "到期时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date expirationTime;

    /**
     * 是否会员
     */
    @Excel(name = "是否超级会员")
    private String isSuperMember;

    /** 超级会员到期时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "超级会员到期时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date superExpirationTime;

    /**
     * 是否拉黑
     */
    @Excel(name = "是否拉黑")
    private String isBlacklist;

    /**
     * 邀请人ID
     */
    @Excel(name = "邀请人ID")
    private Long memberId;

    /**
     * 邀请人ID
     */
    @Excel(name = "邀请人ID")
    private Member inviterMember;

    /**
     * 邀请人ID
     */
    @Excel(name = "邀请人ID")
    private Member secondaryMember;

    /**
     * 所有邀请人ID
     */
    @Excel(name = "所有邀请人ID")
    private String ancestors;

    /**
     * 订单数量
     */
    @Excel(name = "订单数量")
    private Integer orderNum;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    private String delFlag;

    /**
     * 最后登录IP
     */
    @Excel(name = "最后登录IP")
    private String loginIp;

    /**
     * 最后登录时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "最后登录时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date loginDate;

    private WechatConfig wechatConfig;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getIsMember() {
        return isMember;
    }

    public void setIsMember(String isMember) {
        this.isMember = isMember;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public Date getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAncestors() {
        return ancestors;
    }

    public void setAncestors(String ancestors) {
        this.ancestors = ancestors;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getIsBlacklist() {
        return isBlacklist;
    }

    public void setIsBlacklist(String isBlacklist) {
        this.isBlacklist = isBlacklist;
    }

    public BigDecimal getCommissionBalance() {
        return commissionBalance;
    }

    public void setCommissionBalance(BigDecimal commissionBalance) {
        this.commissionBalance = commissionBalance;
    }

    public Date getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(Date expirationTime) {
        this.expirationTime = expirationTime;
    }

    public WechatConfig getWechatConfig() {
        return wechatConfig;
    }

    public void setWechatConfig(WechatConfig wechatConfig) {
        this.wechatConfig = wechatConfig;
    }

    public String getIsSuperMember() {
        return isSuperMember;
    }

    public void setIsSuperMember(String isSuperMember) {
        this.isSuperMember = isSuperMember;
    }

    public Date getSuperExpirationTime() {
        return superExpirationTime;
    }

    public void setSuperExpirationTime(Date superExpirationTime) {
        this.superExpirationTime = superExpirationTime;
    }

    public BigDecimal getWithdrawalAmount() {
        return withdrawalAmount;
    }

    public void setWithdrawalAmount(BigDecimal withdrawalAmount) {
        this.withdrawalAmount = withdrawalAmount;
    }

    public Member getInviterMember() {
        return inviterMember;
    }

    public void setInviterMember(Member inviterMember) {
        this.inviterMember = inviterMember;
    }

    public Member getSecondaryMember() {
        return secondaryMember;
    }

    public void setSecondaryMember(Member secondaryMember) {
        this.secondaryMember = secondaryMember;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }
}

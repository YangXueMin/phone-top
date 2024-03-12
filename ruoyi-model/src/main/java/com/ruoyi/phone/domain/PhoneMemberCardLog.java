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
 * 会员卡充值记录对象 phone_member_card_log
 *
 * @author ruoyi
 * @date 2024-03-06
 */
@ApiModel(value = "PhoneMemberCardLog", description = "会员卡充值记录")
@ToString
public class PhoneMemberCardLog extends BaseEntity{
    private static final long serialVersionUID=1L;

    /** 主键 */
    private Long id;

    /** 公司ID */
    @Excel(name = "公司ID")
    @ApiModelProperty("公司ID")
    private Long deptId;

    /** 公众号配置ID */
    @Excel(name = "公众号配置ID")
    @ApiModelProperty("公众号配置ID")
    private String appId;

    /** 会员ID */
    @Excel(name = "会员ID")
    @ApiModelProperty("会员ID")
    private Long memberId;

    /** 支付状态 */
    @Excel(name = "支付状态")
    @ApiModelProperty("支付状态")
    private String payStatus;

    /** 支付方式 */
    @Excel(name = "支付方式")
    @ApiModelProperty("支付方式")
    private String payType;

    /** 订单编号 */
    @Excel(name = "订单编号")
    @ApiModelProperty("订单编号")
    private String orderNo;

    /** 剩余金额 */
    @Excel(name = "剩余金额")
    @ApiModelProperty("剩余金额")
    private BigDecimal money;

    /** 余额支付金额 */
    @Excel(name = "余额支付金额")
    @ApiModelProperty("余额支付金额")
    private BigDecimal balanceMoney;

    /** 总金额 */
    @Excel(name = "总金额")
    @ApiModelProperty("总金额")
    private BigDecimal totalMoney;

    /** 会员卡ID */
    @Excel(name = "会员卡ID")
    @ApiModelProperty("会员卡ID")
    private Long cardId;

    /** 购买天数 */
    @Excel(name = "购买天数")
    @ApiModelProperty("购买天数")
    private Long buyDay;

    /** 卡名称 */
    @Excel(name = "卡名称")
    @ApiModelProperty("卡名称")
    private String cardName;

    /** 支付时间 */
    @Excel(name = "支付时间")
    @ApiModelProperty("支付时间")
    private String payTime;

    /** 支付回调记录 */
    @Excel(name = "支付回调记录")
    @ApiModelProperty("支付回调记录")
    private String payResult;

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
    public void setDeptId(Long deptId){
        this.deptId = deptId;
    }

    public Long getDeptId(){
        return deptId;
    }
    public void setAppId(String appId){
        this.appId = appId;
    }

    public String getAppId(){
        return appId;
    }
    public void setMemberId(Long memberId){
        this.memberId = memberId;
    }

    public Long getMemberId(){
        return memberId;
    }
    public void setPayStatus(String payStatus){
        this.payStatus = payStatus;
    }

    public String getPayStatus(){
        return payStatus;
    }
    public void setPayType(String payType){
        this.payType = payType;
    }

    public String getPayType(){
        return payType;
    }
    public void setOrderNo(String orderNo){
        this.orderNo = orderNo;
    }

    public String getOrderNo(){
        return orderNo;
    }
    public void setMoney(BigDecimal money){
        this.money = money;
    }

    public BigDecimal getMoney(){
        return money;
    }
    public void setBalanceMoney(BigDecimal balanceMoney){
        this.balanceMoney = balanceMoney;
    }

    public BigDecimal getBalanceMoney(){
        return balanceMoney;
    }
    public void setTotalMoney(BigDecimal totalMoney){
        this.totalMoney = totalMoney;
    }

    public BigDecimal getTotalMoney(){
        return totalMoney;
    }
    public void setCardId(Long cardId){
        this.cardId = cardId;
    }

    public Long getCardId(){
        return cardId;
    }
    public void setBuyDay(Long buyDay){
        this.buyDay = buyDay;
    }

    public Long getBuyDay(){
        return buyDay;
    }
    public void setCardName(String cardName){
        this.cardName = cardName;
    }

    public String getCardName(){
        return cardName;
    }
    public void setPayTime(String payTime){
        this.payTime = payTime;
    }

    public String getPayTime(){
        return payTime;
    }
    public void setPayResult(String payResult){
        this.payResult = payResult;
    }

    public String getPayResult(){
        return payResult;
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

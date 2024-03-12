package com.ruoyi.phone.domain;

import java.math.BigDecimal;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.entity.Member;
import com.ruoyi.common.core.domain.entity.WechatConfig;
import com.ruoyi.common.utils.DictUtils;
import com.ruoyi.common.utils.StringUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 余额充值记录对象 phone_balance_log
 *
 * @author ruoyi
 * @date 2024-03-07
 */
@ApiModel(value = "PhoneBalanceLog", description = "余额充值记录")
@ToString
public class PhoneBalanceLog extends BaseEntity{
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

    /** 订单编号 */
    @Excel(name = "订单编号")
    @ApiModelProperty("订单编号")
    private String orderNo;

    /** 变更类型（充值，消费） */
    @Excel(name = "变更类型", readConverterExp = "充值，消费")
    @ApiModelProperty("变更类型（字典：phone_balance_type）")
    private String type;

    /** 变更类型（充值，消费） */
    @Excel(name = "变更类型", readConverterExp = "充值，消费")
    @ApiModelProperty("变更类型")
    private String typeLabel;

    /** 变更前金额 */
    @Excel(name = "变更前金额")
    @ApiModelProperty("变更前金额")
    private BigDecimal balanceBefore;

    /** 金额 */
    @Excel(name = "金额")
    @ApiModelProperty("金额")
    private BigDecimal money;

    /** 变更后金额 */
    @Excel(name = "变更后金额")
    @ApiModelProperty("变更后金额")
    private BigDecimal balanceAfter;

    /** 支付状态（待支付：1，已支付：2，已退款：3，已取消:4） */
    @Excel(name = "支付状态", readConverterExp = "待=支付：1，已支付：2，已退款：3，已取消:4")
    @ApiModelProperty("支付状态(字典值：phone_pay_status)")
    private String payStatus;

    /** 支付状态（待支付：1，已支付：2，已退款：3，已取消:4） */
    @Excel(name = "支付状态", readConverterExp = "待支付：1，已支付：2，已退款：3，已取消:4")
    @ApiModelProperty("支付状态")
    private String payStatusLabel;

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
    public void setOrderNo(String orderNo){
        this.orderNo = orderNo;
    }

    public String getOrderNo(){
        return orderNo;
    }
    public void setType(String type){
        this.type = type;
    }

    public String getType(){
        return type;
    }
    public void setBalanceBefore(BigDecimal balanceBefore){
        this.balanceBefore = balanceBefore;
    }

    public BigDecimal getBalanceBefore(){
        return balanceBefore;
    }
    public void setMoney(BigDecimal money){
        this.money = money;
    }

    public BigDecimal getMoney(){
        return money;
    }
    public void setBalanceAfter(BigDecimal balanceAfter){
        this.balanceAfter = balanceAfter;
    }

    public BigDecimal getBalanceAfter(){
        return balanceAfter;
    }
    public void setPayStatus(String payStatus){
        this.payStatus = payStatus;
    }

    public String getPayStatus(){
        return payStatus;
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

    public String getTypeLabel() {
        if(StringUtils.isNotBlank(type)){
            return DictUtils.getDictLabel("phone_pay_status",type);
        }
        return typeLabel;
    }

    public void setTypeLabel(String typeLabel) {
        this.typeLabel = typeLabel;
    }

    public String getPayStatusLabel() {
        if(StringUtils.isNotBlank(payStatus)){
            return DictUtils.getDictLabel("phone_pay_status",payStatus);
        }
        return payStatusLabel;
    }

    public void setPayStatusLabel(String payStatusLabel) {
        this.payStatusLabel = payStatusLabel;
    }
}

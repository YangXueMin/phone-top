package com.ruoyi.phone.domain;

import java.math.BigDecimal;
import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 会员卡充值记录对象 phone_member_card_log
 *
 * @author ruoyi
 * @date 2024-03-05
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
    private Long companyId;

    /** 公众号配置ID */
    @Excel(name = "公众号配置ID")
    @ApiModelProperty("公众号配置ID")
    private Long configId;

    /** 会员ID */
    @Excel(name = "会员ID")
    @ApiModelProperty("会员ID")
    private Long memberId;

    /** 订单ID */
    @Excel(name = "订单ID")
    @ApiModelProperty("订单ID")
    private String orderNo;

    /** 金额 */
    @Excel(name = "金额")
    @ApiModelProperty("金额")
    private BigDecimal money;

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

}

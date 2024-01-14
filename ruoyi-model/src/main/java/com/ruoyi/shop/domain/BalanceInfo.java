package com.ruoyi.shop.domain;

import java.math.BigDecimal;
import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 余额消费记录对象 shop_balance_info
 *
 * @author ruoyi
 * @date 2024-01-14
 */
@ApiModel(value = "BalanceInfo", description = "余额消费记录")
@ToString
public class BalanceInfo extends BaseEntity{
    private static final long serialVersionUID=1L;

    /** 主键 */
    private Long id;

    /** 会员ID */
    @Excel(name = "会员ID")
    @ApiModelProperty("会员ID")
    private Long memberId;

    /** 订单类型 */
    @Excel(name = "订单类型")
    @ApiModelProperty("订单类型")
    private String orderType;

    /** 订单ID */
    @Excel(name = "订单ID")
    @ApiModelProperty("订单ID")
    private Long orderId;

    /** 使用前余额 */
    @Excel(name = "使用前余额")
    @ApiModelProperty("使用前余额")
    private BigDecimal beforeBalance;

    /** 余额 */
    @Excel(name = "余额")
    @ApiModelProperty("余额")
    private BigDecimal balance;

    /** 订单金额 */
    @Excel(name = "订单金额")
    @ApiModelProperty("订单金额")
    private BigDecimal money;

    public BalanceInfo() {
    }

    public BalanceInfo(Long memberId, String orderType, Long orderId, BigDecimal beforeBalance, BigDecimal balance, BigDecimal money) {
        this.memberId = memberId;
        this.orderType = orderType;
        this.orderId = orderId;
        this.beforeBalance = beforeBalance;
        this.balance = balance;
        this.money = money;
    }

    public void setId(Long id){
        this.id = id;
    }

    public Long getId(){
        return id;
    }
    public void setMemberId(Long memberId){
        this.memberId = memberId;
    }

    public Long getMemberId(){
        return memberId;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public void setOrderId(Long orderId){
        this.orderId = orderId;
    }

    public Long getOrderId(){
        return orderId;
    }
    public void setBeforeBalance(BigDecimal beforeBalance){
        this.beforeBalance = beforeBalance;
    }

    public BigDecimal getBeforeBalance(){
        return beforeBalance;
    }
    public void setBalance(BigDecimal balance){
        this.balance = balance;
    }

    public BigDecimal getBalance(){
        return balance;
    }
    public void setMoney(BigDecimal money){
        this.money = money;
    }

    public BigDecimal getMoney(){
        return money;
    }

}

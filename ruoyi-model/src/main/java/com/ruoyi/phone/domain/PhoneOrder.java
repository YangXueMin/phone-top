package com.ruoyi.phone.domain;

import java.math.BigDecimal;

import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 订单记录对象 phone_order
 *
 * @author ruoyi
 * @date 2024-03-05
 */
@ApiModel(value = "PhoneOrder", description = "订单记录")
@ToString
public class PhoneOrder extends BaseEntity {
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
    private Long companyId;

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
     * 订单号
     */
    @Excel(name = "订单号")
    @ApiModelProperty("订单号")
    private String orderNo;

    /**
     * 充值方式（电费：0，快充：1，慢充：2）
     */
    @Excel(name = "充值方式", readConverterExp = "电费：0，快充：1，慢充：2")
    @ApiModelProperty("充值方式")
    private String method;

    /**
     * 充值类型
     */
    @Excel(name = "充值类型")
    @ApiModelProperty("充值类型（话费：移动：1，联通：2：电信：3，电费：国家电网：4，南方电网：5）")
    private String type;

    /**
     * 充值状态
     */
    @Excel(name = "充值状态")
    @ApiModelProperty("充值状态(1:充值中，2：充值成功，3：充值失败)")
    private String status;

    /**
     * 手机号
     */
    @Excel(name = "手机号")
    @ApiModelProperty("手机号")
    private String mobile;

    /**
     * 充值账号
     */
    @Excel(name = "充值账号")
    @ApiModelProperty("充值账号")
    private String accountNumber;

    /**
     * 住宅类型
     */
    @Excel(name = "住宅类型")
    @ApiModelProperty("住宅类型（住宅：1，店铺：2，企事业：3，默认：0）")
    private String residenceType;

    /**
     * 区域
     */
    @Excel(name = "区域")
    @ApiModelProperty("区域")
    private String area;

    /**
     * 身份证后6位
     */
    @Excel(name = "身份证后6位")
    @ApiModelProperty("身份证后6位")
    private String cardNo;

    /**
     * 订单金额
     */
    @Excel(name = "订单金额")
    @ApiModelProperty("订单金额")
    private BigDecimal money;

    /**
     * 支付金额
     */
    @Excel(name = "支付金额")
    @ApiModelProperty("支付金额")
    private BigDecimal payMoney;

    /**
     * 余额支付金额
     */
    @Excel(name = "余额支付金额")
    @ApiModelProperty("余额支付金额")
    private BigDecimal payBalance;

    /**
     * 支付方式
     */
    @Excel(name = "支付方式")
    @ApiModelProperty("支付方式（1：线上支付，2：余额支付，3：组合支付）")
    private String payType;

    /**
     * 支付状态
     */
    @Excel(name = "支付状态")
    @ApiModelProperty("支付状态(1:待支付，2：支付完成，3：已退款，4：已取消)")
    private String payStatus;

    /**
     * 到账状态
     */
    @Excel(name = "到账状态")
    @ApiModelProperty("到账状态")
    private String arrivalStatus;

    /**
     * 支付时间
     */
    @Excel(name = "支付时间")
    @ApiModelProperty("支付时间")
    private String payTime;

    /**
     * 支付回调记录
     */
    @Excel(name = "支付回调记录")
    @ApiModelProperty("支付回调记录")
    private String payResult;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getCompanyId() {
        return companyId;
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

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getMethod() {
        return method;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMobile() {
        return mobile;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setResidenceType(String residenceType) {
        this.residenceType = residenceType;
    }

    public String getResidenceType() {
        return residenceType;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getArea() {
        return area;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setPayMoney(BigDecimal payMoney) {
        this.payMoney = payMoney;
    }

    public BigDecimal getPayMoney() {
        return payMoney;
    }

    public void setPayBalance(BigDecimal payBalance) {
        this.payBalance = payBalance;
    }

    public BigDecimal getPayBalance() {
        return payBalance;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setArrivalStatus(String arrivalStatus) {
        this.arrivalStatus = arrivalStatus;
    }

    public String getArrivalStatus() {
        return arrivalStatus;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayResult(String payResult) {
        this.payResult = payResult;
    }

    public String getPayResult() {
        return payResult;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

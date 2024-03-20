package com.ruoyi.phone.domain;

/**
 * @author ruoyi
 * @ClassName TopNotifyRequest
 * @Description
 * @date 2024/3/12 9:57 PM
 */
public class TopNotifyRequest {
    /**
     * 商户ID
     */
    private Integer userid;
    /**
     * 商户订单号
     */
    private String order_number;
    /**
     * 三方订单号
     */
    private String out_trade_num;
    /**
     * 成功/失败时间，10位时间戳
     */
    private Integer otime;
    /**
     * 充值状态；-1取消， 0充值中， 1充值成功 ，2充值失败，3部分成功（-1,2做失败处理；1做成功处理；3做部分成功处理）
     */
    private Integer state;

    /**
     * 充值手机号
     */
    private String mobile;

    /**
     * 备注信息
     */
    private String remark;

    /**
     * 充值成功面额
     */
    private float charge_amount;

    /**
     * 凭证
     */
    private String voucher;

    /**
     * 卡密/流水号
     */
    private String charge_kami;

    /**
     * 签名字符串，用于验签,以保证回调可靠性。
     */
    private String sign;

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getOrder_number() {
        return order_number;
    }

    public void setOrder_number(String order_number) {
        this.order_number = order_number;
    }

    public String getOut_trade_num() {
        return out_trade_num;
    }

    public void setOut_trade_num(String out_trade_num) {
        this.out_trade_num = out_trade_num;
    }

    public Integer getOtime() {
        return otime;
    }

    public void setOtime(Integer otime) {
        this.otime = otime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public float getCharge_amount() {
        return charge_amount;
    }

    public void setCharge_amount(float charge_amount) {
        this.charge_amount = charge_amount;
    }

    public String getVoucher() {
        return voucher;
    }

    public void setVoucher(String voucher) {
        this.voucher = voucher;
    }

    public String getCharge_kami() {
        return charge_kami;
    }

    public void setCharge_kami(String charge_kami) {
        this.charge_kami = charge_kami;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}

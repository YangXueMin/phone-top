package com.ruoyi.phone.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.entity.Member;
import com.ruoyi.common.core.domain.entity.WechatConfig;
import com.ruoyi.common.utils.DictUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.StringUtils;

/**
 * 卡密管理对象 phone_card
 *
 * @author ruoyi
 * @date 2024-03-04
 */
@ApiModel(value = "PhoneCard", description = "卡密管理")
@ToString
public class PhoneCard extends BaseEntity{
    private static final long serialVersionUID=1L;

    /** 主键 */
    private Long id;

    /** 公司ID */
    @Excel(name = "公司ID")
    @ApiModelProperty("公司ID")
    private Long companyId;

    /** appId */
    @Excel(name = "appId")
    @ApiModelProperty("appId")
    private String appId;

    /** 卡密 */
    @Excel(name = "卡密")
    @ApiModelProperty("卡密")
    private String cardNo;

    /** 金额 */
    @Excel(name = "金额")
    @ApiModelProperty("金额")
    private BigDecimal price;

    /** 状态 */
    @Excel(name = "状态")
    @ApiModelProperty("状态")
    private String status;

    /** 状态 */
    @Excel(name = "状态")
    @ApiModelProperty("状态(字典值：phone_status)")
    private String statusLabel;

    /** 核销状态 */
    @Excel(name = "核销状态")
    @ApiModelProperty("核销状态")
    private String cancelStatus;

    /** 核销状态 */
    @Excel(name = "核销状态")
    @ApiModelProperty("核销状态（字典值：phone_cancel_status）")
    private String cancelStatusLabel;

    /** 核销时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "核销时间", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty("核销时间")
    private Date cancelTime;

    /** 核销会员ID */
    @Excel(name = "核销会员ID")
    @ApiModelProperty("核销会员ID")
    private Long memberId;

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
    public void setAppId(String appId){
        this.appId = appId;
    }

    public String getAppId(){
        return appId;
    }
    public void setCardNo(String cardNo){
        this.cardNo = cardNo;
    }

    public String getCardNo(){
        return cardNo;
    }
    public void setPrice(BigDecimal price){
        this.price = price;
    }

    public BigDecimal getPrice(){
        return price;
    }
    public void setStatus(String status){
        this.status = status;
    }

    public String getStatus(){
        return status;
    }
    public void setCancelStatus(String cancelStatus){
        this.cancelStatus = cancelStatus;
    }

    public String getCancelStatus(){
        return cancelStatus;
    }
    public void setCancelTime(Date cancelTime){
        this.cancelTime = cancelTime;
    }

    public Date getCancelTime(){
        return cancelTime;
    }
    public void setMemberId(Long memberId){
        this.memberId = memberId;
    }

    public Long getMemberId(){
        return memberId;
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

    public String getStatusLabel() {
        if(StringUtils.isNotBlank(status)){
            return DictUtils.getDictLabel("phone_status",status);
        }
        return statusLabel;
    }

    public void setStatusLabel(String statusLabel) {
        this.statusLabel = statusLabel;
    }

    public String getCancelStatusLabel() {
        if(StringUtils.isNotBlank(cancelStatus)){
            return DictUtils.getDictLabel("phone_cancel_status",cancelStatus);
        }
        return cancelStatusLabel;
    }

    public void setCancelStatusLabel(String cancelStatusLabel) {
        this.cancelStatusLabel = cancelStatusLabel;
    }
}

package com.ruoyi.phone.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 会员卡券管理关系对象 phone_member_coupon
 *
 * @author ruoyi
 * @date 2024-03-07
 */
@ApiModel(value = "PhoneMemberCoupon", description = "会员卡券管理关系")
@ToString
public class PhoneMemberCoupon extends BaseEntity{
    private static final long serialVersionUID=1L;

    /** 主键 */
    private Long id;

    /** 会员ID */
    @Excel(name = "会员ID")
    @ApiModelProperty("会员ID")
    private Long memberId;

    /** 公司ID */
    @Excel(name = "公司ID")
    @ApiModelProperty("公司ID")
    private Long companyId;

    /** 公众号appID */
    @Excel(name = "公众号appID")
    @ApiModelProperty("公众号appID")
    private String appId;

    /** 优惠券ID */
    @Excel(name = "优惠券ID")
    @ApiModelProperty("优惠券ID")
    private Long couponId;

    /** 到期时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "到期时间", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty("到期时间")
    private Date expirationTime;

    /** 使用状态（待使用：1，已使用：2，已过期：3，已失效：4） */
    @Excel(name = "使用状态", readConverterExp = "待=使用：1，已使用：2，已过期：3，已失效：4")
    @ApiModelProperty("使用状态")
    private String status;

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
    public void setCouponId(Long couponId){
        this.couponId = couponId;
    }

    public Long getCouponId(){
        return couponId;
    }
    public void setExpirationTime(Date expirationTime){
        this.expirationTime = expirationTime;
    }

    public Date getExpirationTime(){
        return expirationTime;
    }
    public void setStatus(String status){
        this.status = status;
    }

    public String getStatus(){
        return status;
    }

}

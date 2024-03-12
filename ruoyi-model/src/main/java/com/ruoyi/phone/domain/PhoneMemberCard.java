package com.ruoyi.phone.domain;

import java.math.BigDecimal;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.entity.WechatConfig;
import com.ruoyi.common.utils.DictUtils;
import com.ruoyi.common.utils.StringUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 会员卡管理对象 phone_member_card
 *
 * @author ruoyi
 * @date 2024-03-07
 */
@ApiModel(value = "PhoneMemberCard", description = "会员卡管理")
@ToString
public class PhoneMemberCard extends BaseEntity{
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

    /** 会员卡类型 */
    @Excel(name = "会员卡类型")
    @ApiModelProperty("会员卡类型")
    private String memberType;

    /** 会员卡类型 */
    @Excel(name = "会员卡类型")
    @ApiModelProperty("会员卡类型")
    private String memberTypeLabel;

    /** 会员卡标题 */
    @Excel(name = "会员卡标题")
    @ApiModelProperty("会员卡标题")
    private String title;

    /** 会员卡内容 */
    @Excel(name = "会员卡内容")
    @ApiModelProperty("会员卡内容")
    private String content;

    /** 会员天数 */
    @Excel(name = "会员天数")
    @ApiModelProperty("会员天数")
    private Long memberDay;

    /** 显示顺序 */
    @Excel(name = "显示顺序")
    @ApiModelProperty("显示顺序")
    private Long orderNum;

    /** 原价 */
    @Excel(name = "原价")
    @ApiModelProperty("原价")
    private BigDecimal originalPrice;

    /** 抢购价 */
    @Excel(name = "抢购价")
    @ApiModelProperty("抢购价")
    private BigDecimal buyingPrice;

    /** 直推佣金 */
    @Excel(name = "直推佣金")
    @ApiModelProperty("直推佣金")
    private BigDecimal directCommission;

    /** 间推佣金 */
    @Excel(name = "间推佣金")
    @ApiModelProperty("间推佣金")
    private BigDecimal indirectCommission;

    /** 会员高级直推佣金 */
    @Excel(name = "会员直推佣金")
    @ApiModelProperty("会员直推佣金")
    private BigDecimal memberDirectCommission;

    /** 会员间推佣金 */
    @Excel(name = "会员间推佣金")
    @ApiModelProperty("会员间推佣金")
    private BigDecimal memberIndirectCommission;

    /** 超级会员直推佣金 */
    @Excel(name = "超级会员直推佣金")
    @ApiModelProperty("超级会员直推佣金")
    private BigDecimal superMemberDirectCommission;

    /** 超级会员间推佣金 */
    @Excel(name = "超级会员间推佣金")
    @ApiModelProperty("超级会员间推佣金")
    private BigDecimal superMemberIndirectCommission;

    /** 充值类型 */
    @Excel(name = "充值类型")
    @ApiModelProperty("充值类型")
    private String type;

    /** 充值方式 */
    @Excel(name = "充值方式")
    @ApiModelProperty("充值方式")
    private String method;

    /** 上架状态 */
    @Excel(name = "上架状态")
    @ApiModelProperty("上架状态")
    private String status;

    @ApiModelProperty("微信配置")
    private WechatConfig wechatConfig;

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
    public void setTitle(String title){
        this.title = title;
    }

    public String getTitle(){
        return title;
    }
    public void setContent(String content){
        this.content = content;
    }

    public String getContent(){
        return content;
    }
    public void setMemberDay(Long memberDay){
        this.memberDay = memberDay;
    }

    public Long getMemberDay(){
        return memberDay;
    }
    public void setOrderNum(Long orderNum){
        this.orderNum = orderNum;
    }

    public Long getOrderNum(){
        return orderNum;
    }
    public void setOriginalPrice(BigDecimal originalPrice){
        this.originalPrice = originalPrice;
    }

    public BigDecimal getOriginalPrice(){
        return originalPrice;
    }
    public void setBuyingPrice(BigDecimal buyingPrice){
        this.buyingPrice = buyingPrice;
    }

    public BigDecimal getBuyingPrice(){
        return buyingPrice;
    }
    public void setDirectCommission(BigDecimal directCommission){
        this.directCommission = directCommission;
    }

    public BigDecimal getDirectCommission(){
        return directCommission;
    }
    public void setIndirectCommission(BigDecimal indirectCommission){
        this.indirectCommission = indirectCommission;
    }

    public BigDecimal getIndirectCommission(){
        return indirectCommission;
    }
    public void setType(String type){
        this.type = type;
    }

    public String getType(){
        return type;
    }
    public void setMethod(String method){
        this.method = method;
    }

    public String getMethod(){
        return method;
    }
    public void setStatus(String status){
        this.status = status;
    }

    public String getStatus(){
        return status;
    }

    public WechatConfig getWechatConfig() {
        return wechatConfig;
    }

    public void setWechatConfig(WechatConfig wechatConfig) {
        this.wechatConfig = wechatConfig;
    }

    public BigDecimal getMemberDirectCommission() {
        return memberDirectCommission;
    }

    public void setMemberDirectCommission(BigDecimal memberDirectCommission) {
        this.memberDirectCommission = memberDirectCommission;
    }

    public BigDecimal getMemberIndirectCommission() {
        return memberIndirectCommission;
    }

    public void setMemberIndirectCommission(BigDecimal memberIndirectCommission) {
        this.memberIndirectCommission = memberIndirectCommission;
    }

    public BigDecimal getSuperMemberDirectCommission() {
        return superMemberDirectCommission;
    }

    public void setSuperMemberDirectCommission(BigDecimal superMemberDirectCommission) {
        this.superMemberDirectCommission = superMemberDirectCommission;
    }

    public BigDecimal getSuperMemberIndirectCommission() {
        return superMemberIndirectCommission;
    }

    public void setSuperMemberIndirectCommission(BigDecimal superMemberIndirectCommission) {
        this.superMemberIndirectCommission = superMemberIndirectCommission;
    }

    public String getMemberType() {
        return memberType;
    }

    public void setMemberType(String memberType) {
        this.memberType = memberType;
    }

    public String getMemberTypeLabel() {
        if(StringUtils.isNotBlank(memberType)){
            return DictUtils.getDictLabel("phone_member_card",memberType);
        }
        return memberTypeLabel;
    }

    public void setMemberTypeLabel(String memberTypeLabel) {
        this.memberTypeLabel = memberTypeLabel;
    }
}

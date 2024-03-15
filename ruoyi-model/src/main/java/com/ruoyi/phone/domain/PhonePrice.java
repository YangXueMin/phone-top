package com.ruoyi.phone.domain;

import java.math.BigDecimal;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.entity.WechatConfig;
import com.ruoyi.common.utils.DictUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.StringUtils;

/**
 * 价格配置对象 phone_price
 *
 * @author ruoyi
 * @date 2024-03-07
 */
@ApiModel(value = "PhonePrice", description = "价格配置")
@ToString
public class PhonePrice extends BaseEntity{
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

    /** 类型ID */
    @Excel(name = "类型ID")
    @ApiModelProperty("类型ID")
    private Long typeId;

    /** 充值到第三方类型 */
    @Excel(name = "充值到第三方类型(字典值：phone_interface_type 系统：1，大猿人：2)")
    @ApiModelProperty("充值到第三方类型(字典值：phone_interface_type 系统：1，大猿人：2)")
    private String rechargeType;

    /** 充值到第三方类型 */
    @Excel(name = "充值到第三方类型(字典值：phone_interface_type 系统：1，大猿人：2)")
    @ApiModelProperty("充值到第三方类型(字典值：phone_interface_type 系统：1，大猿人：2)")
    private String rechargeTypeLabel;

    /** 充值方式（电网：0，快充：1，慢充：2） */
    @Excel(name = "充值方式", readConverterExp = "电费：0，快充：1，慢充：2")
    @ApiModelProperty("充值方式（字典值：phone_pay_method）")
    private String method;

    /** 充值方式（电网：0，快充：1，慢充：2） */
    @Excel(name = "充值方式", readConverterExp = "电费：0，快充：1，慢充：2")
    @ApiModelProperty("充值方式")
    private String methodLabel;

    /** 类型(移动、联通、电信、国家电网、南方电网) */
    @Excel(name = "类型(移动、联通、电信、国家电网、南方电网)")
    @ApiModelProperty("类型(字典值：phone_order_pay_type 移动、联通、电信、国家电网、南方电网)")
    private String type;

    /** 类型(移动、联通、电信、国家电网、南方电网) */
    @Excel(name = "类型(移动、联通、电信、国家电网、南方电网)")
    @ApiModelProperty("类型(字典值：phone_order_pay_type 移动、联通、电信、国家电网、南方电网)")
    private String typeLabel;

    /** 第三方产品ID */
    @Excel(name = "第三方产品ID")
    @ApiModelProperty("第三方产品ID")
    private Integer productId;

    /** 原价 */
    @Excel(name = "原价")
    @ApiModelProperty("原价")
    private BigDecimal originalPrice;

    /** 折扣 */
    @Excel(name = "折扣")
    @ApiModelProperty("折扣")
    private BigDecimal discount;

    /** 售价 */
    @Excel(name = "售价")
    @ApiModelProperty("售价")
    private BigDecimal sellPrice;

    /** 会员价 */
    @Excel(name = "会员价")
    @ApiModelProperty("会员价")
    private BigDecimal memberPrice;

    /** 超级会员价 */
    @Excel(name = "超级会员价")
    @ApiModelProperty("超级会员价")
    private BigDecimal superMemberPrice;

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

    /** 显示顺序 */
    @Excel(name = "显示顺序")
    @ApiModelProperty("显示顺序")
    private Long orderNum;

    @ApiModelProperty("微信配置")
    private WechatConfig wechatConfig;

    @ApiModelProperty("价格类型")
    private PhonePriceType phonePriceType;

    /** 标签 */
    @Excel(name = "标签")
    @ApiModelProperty("标签")
    private String label;

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

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public void setMethod(String method){
        this.method = method;
    }

    public String getMethod(){
        return method;
    }
    public void setType(String type){
        this.type = type;
    }

    public String getType(){
        return type;
    }
    public void setOriginalPrice(BigDecimal originalPrice){
        this.originalPrice = originalPrice;
    }

    public BigDecimal getOriginalPrice(){
        return originalPrice;
    }
    public void setDiscount(BigDecimal discount){
        this.discount = discount;
    }

    public BigDecimal getDiscount(){
        return discount;
    }
    public void setSellPrice(BigDecimal sellPrice){
        this.sellPrice = sellPrice;
    }

    public BigDecimal getSellPrice(){
        return sellPrice;
    }
    public void setMemberPrice(BigDecimal memberPrice){
        this.memberPrice = memberPrice;
    }

    public BigDecimal getMemberPrice(){
        return memberPrice;
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
    public void setOrderNum(Long orderNum){
        this.orderNum = orderNum;
    }

    public Long getOrderNum(){
        return orderNum;
    }

    public WechatConfig getWechatConfig() {
        return wechatConfig;
    }

    public void setWechatConfig(WechatConfig wechatConfig) {
        this.wechatConfig = wechatConfig;
    }

    public String getMethodLabel() {
        if(StringUtils.isNotBlank(method)){
            return DictUtils.getDictLabel("phone_pay_method",method);
        }
        return methodLabel;
    }

    public void setMethodLabel(String methodLabel) {
        this.methodLabel = methodLabel;
    }

    public String getTypeLabel() {
        if(StringUtils.isNotBlank(type)){
            return DictUtils.getDictLabel("phone_order_pay_type",type);
        }
        return typeLabel;
    }

    public void setTypeLabel(String typeLabel) {
        this.typeLabel = typeLabel;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
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

    public PhonePriceType getPhonePriceType() {
        return phonePriceType;
    }

    public void setPhonePriceType(PhonePriceType phonePriceType) {
        this.phonePriceType = phonePriceType;
    }

    public BigDecimal getSuperMemberPrice() {
        return superMemberPrice;
    }

    public void setSuperMemberPrice(BigDecimal superMemberPrice) {
        this.superMemberPrice = superMemberPrice;
    }

    public String getRechargeType() {
        return rechargeType;
    }

    public void setRechargeType(String rechargeType) {
        this.rechargeType = rechargeType;
    }

    public String getRechargeTypeLabel() {
        return rechargeTypeLabel;
    }

    public void setRechargeTypeLabel(String rechargeTypeLabel) {
        this.rechargeTypeLabel = rechargeTypeLabel;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}

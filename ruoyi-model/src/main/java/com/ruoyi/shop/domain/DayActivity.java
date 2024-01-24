package com.ruoyi.shop.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.common.utils.DictUtils;
import com.ruoyi.common.utils.StringUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

/**
 * 会员日活动对象 shop_member_day_activity
 *
 * @author ruoyi
 * @date 2024-01-12
 */
@ApiModel(value = "DayActivity", description = "会员日活动")
@ToString
public class DayActivity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 活动名称
     */
    @Excel(name = "活动名称")
    @ApiModelProperty("活动名称")
    private String activityName;

    /**
     * 店铺名称
     */
    @Excel(name = "店铺名称")
    @ApiModelProperty("店铺名称")
    private Long shopId;

    /**
     * 活动时间
     */
    @Excel(name = "活动时间")
    @ApiModelProperty("活动时间")
    private String activityTime;

    /**
     * 活动标签
     */
    @Excel(name = "活动标签")
    @ApiModelProperty("活动标签")
    private String activityLabel;
    /**
     * 活动标签label
     */
    @Excel(name = "活动标签label")
    @ApiModelProperty("活动标签label")
    private String activityLabelLabel;

    /**
     * 活动商品类型
     */
    @Excel(name = "活动商品类型")
    @ApiModelProperty("活动商品类型")
    private String goodsType;

    /**
     * 活动商品类型
     */
    @Excel(name = "活动商品类型label")
    @ApiModelProperty("活动商品类型label")
    private String goodsTypeLabel;

    /**
     * 折扣
     */
    @Excel(name = "折扣")
    @ApiModelProperty("折扣")
    private BigDecimal activityDiscount;

    @ApiModelProperty("状态")
    private String status;

    @ApiModelProperty("商品ID")
    private Long[] goodsIds;

    @ApiModelProperty("商品数据")
    private List<Goods> goodsList;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setActivityTime(String activityTime) {
        this.activityTime = activityTime;
    }

    public String getActivityTime() {
        return activityTime;
    }

    public void setActivityLabel(String activityLabel) {
        this.activityLabel = activityLabel;
    }

    public String getActivityLabel() {
        return activityLabel;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setActivityDiscount(BigDecimal activityDiscount) {
        this.activityDiscount = activityDiscount;
    }

    public BigDecimal getActivityDiscount() {
        return activityDiscount;
    }

    public Long[] getGoodsIds() {
        return goodsIds;
    }

    public void setGoodsIds(Long[] goodsIds) {
        this.goodsIds = goodsIds;
    }

    public List<Goods> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<Goods> goodsList) {
        this.goodsList = goodsList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getActivityLabelLabel() {
        if(StringUtils.isNotBlank(activityLabel)){
            return DictUtils.getDictLabel("shop_activity_label",activityLabel);
        }
        return activityLabelLabel;
    }

    public void setActivityLabelLabel(String activityLabelLabel) {
        this.activityLabelLabel = activityLabelLabel;
    }

    public String getGoodsTypeLabel() {
        if(StringUtils.isNotBlank(goodsType)){
            return DictUtils.getDictLabel("shop_day_type",goodsType);
        }
        return goodsTypeLabel;
    }

    public void setGoodsTypeLabel(String goodsTypeLabel) {
        this.goodsTypeLabel = goodsTypeLabel;
    }
}

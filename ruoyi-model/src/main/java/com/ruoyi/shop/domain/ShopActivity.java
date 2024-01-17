package com.ruoyi.shop.domain;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.utils.time.DateFormatUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 活动管理对象 shop_activity
 *
 * @author ruoyi
 * @date 2024-01-11
 */
@ApiModel(value = "ShopActivity", description = "活动管理")
@ToString
public class ShopActivity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty("主键")
    private Long id;

    /**
     * 活动名称
     */
    @Excel(name = "活动名称")
    @ApiModelProperty("活动名称")
    private String activityName;

    /**
     * 店铺ID
     */
    @Excel(name = "店铺ID")
    @ApiModelProperty("店铺ID")
    private Long shopId;

    /**
     * 活动开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "活动开始时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("活动开始时间")
    private Date beginDate;

    /**
     * 活动开始时间
     */
    private String beginDateString;

    /**
     * 活动时长
     */
    @Excel(name = "活动时长")
    @ApiModelProperty("活动时长")
    private Long activityTime;

    /**
     * 状态
     */
    @Excel(name = "状态")
    @ApiModelProperty("状态")
    private String status;

    /**
     * 活动商品集合
     */
    @ApiModelProperty("活动商品集合")
    private List<ShopActivityGoods> activityGoodsList;

    @ApiModelProperty("店铺名称")
    private String shopName;

    @ApiModelProperty("店铺信息")
    private ShopInfo shopInfo;

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

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setActivityTime(Long activityTime) {
        this.activityTime = activityTime;
    }

    public Long getActivityTime() {
        return activityTime;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public List<ShopActivityGoods> getActivityGoodsList() {
        return activityGoodsList;
    }

    public void setActivityGoodsList(List<ShopActivityGoods> activityGoodsList) {
        this.activityGoodsList = activityGoodsList;
    }

    public String getBeginDateString() {
        if(beginDate != null){
           return DateFormatUtil.formatDate(DateFormatUtil.PATTERN_ISO_DATE_API, beginDate);
        }
        return beginDateString;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public ShopInfo getShopInfo() {
        return shopInfo;
    }

    public void setShopInfo(ShopInfo shopInfo) {
        this.shopInfo = shopInfo;
    }
}

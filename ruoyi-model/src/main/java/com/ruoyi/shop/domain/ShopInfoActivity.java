package com.ruoyi.shop.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.utils.DictUtils;
import com.ruoyi.common.utils.StringUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 店铺信息活动配置对象 shop_info_activity
 *
 * @author ruoyi
 * @date 2024-01-17
 */
@ApiModel(value = "ShopInfoActivity", description = "店铺信息活动配置")
@ToString
public class ShopInfoActivity extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 店铺ID
     */
    @Excel(name = "店铺ID")
    @ApiModelProperty("店铺ID")
    private Long shopId;

    /**
     * 活动类型
     */
    @Excel(name = "活动类型")
    @ApiModelProperty("活动类型")
    private String activityType;
    /**
     * 活动类型
     */
    @Excel(name = "活动类型")
    @ApiModelProperty("活动类型")
    private String activityTypeLabel;

    /**
     * 标题
     */
    @Excel(name = "标题")
    @ApiModelProperty("标题")
    private String title;

    /**
     * 内容
     */
    @Excel(name = "内容")
    @ApiModelProperty("内容")
    private String content;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public String getActivityTypeLabel() {
        if(StringUtils.isNotBlank(activityType)){
            return DictUtils.getDictLabel("shop_info_activity_type",activityType);
        }
        return activityTypeLabel;
    }
}

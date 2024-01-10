package com.ruoyi.shop.domain;

import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 订单详情对象 shop_order_details
 *
 * @author ruoyi
 * @date 2024-01-10
 */
@ApiModel(value = "OrderDetails", description = "订单详情")
@ToString
public class OrderDetails extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty("主键")
    private Long id;

    /**
     * 订单ID
     */
    @Excel(name = "订单ID")
    @ApiModelProperty("订单ID")
    private Long orderId;

    /**
     * 商品ID
     */
    @Excel(name = "商品ID")
    @ApiModelProperty("商品ID")
    private Long shopId;

    /**
     * 数量
     */
    @Excel(name = "数量")
    @ApiModelProperty("数量")
    private Long number;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public Long getNumber() {
        return number;
    }

}

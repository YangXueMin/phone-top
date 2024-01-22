package com.ruoyi.shop.domain;

import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;

/**
 * IP地址解析对象 shop_ip_json
 *
 * @author ruoyi
 * @date 2024-01-21
 */
@ApiModel(value = "ShopIpJson", description = "IP地址解析")
@ToString
public class ShopIpJson {
    private static final long serialVersionUID = 1L;

    /**
     * IP地址
     */
    private String ip;

    /**
     * 真实地址json
     */
    @Excel(name = "真实地址json")
    @ApiModelProperty("真实地址json")
    private String json;

    public ShopIpJson() {
    }

    public ShopIpJson(String ip, String json) {
        this.ip = ip;
        this.json = json;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getIp() {
        return ip;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public String getJson() {
        return json;
    }

}

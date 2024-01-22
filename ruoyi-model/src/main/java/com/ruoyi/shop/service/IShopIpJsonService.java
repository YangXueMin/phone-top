package com.ruoyi.shop.service;

import com.ruoyi.shop.domain.ShopIpJson;

import java.util.List;

/**
 * IP地址解析Service接口
 *
 * @author ruoyi
 * @date 2024-01-21
 */
public interface IShopIpJsonService {
    /**
     * 查询IP地址解析
     *
     * @param ip IP地址解析主键
     * @return IP地址解析
     */
    public ShopIpJson selectShopIpJsonByIp(String ip);

    /**
     * 查询IP地址解析列表
     *
     * @param shopIpJson IP地址解析
     * @return IP地址解析集合
     */
    public List<ShopIpJson> selectShopIpJsonList(ShopIpJson shopIpJson);

    /**
     * 新增IP地址解析
     *
     * @param shopIpJson IP地址解析
     * @return 结果
     */
    public int insertShopIpJson(ShopIpJson shopIpJson);

    /**
     * 修改IP地址解析
     *
     * @param shopIpJson IP地址解析
     * @return 结果
     */
    public int updateShopIpJson(ShopIpJson shopIpJson);

    /**
     * 批量删除IP地址解析
     *
     * @param ips 需要删除的IP地址解析主键集合
     * @return 结果
     */
    public int deleteShopIpJsonByIps(String[] ips);

    /**
     * 删除IP地址解析信息
     *
     * @param ip IP地址解析主键
     * @return 结果
     */
    public int deleteShopIpJsonByIp(String ip);
}

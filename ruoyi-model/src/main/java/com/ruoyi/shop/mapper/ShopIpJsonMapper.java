package com.ruoyi.shop.mapper;

import com.ruoyi.shop.domain.ShopIpJson;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * IP地址解析Mapper接口
 *
 * @author ruoyi
 * @date 2024-01-21
 */
@Mapper
public interface ShopIpJsonMapper {
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
     * 删除IP地址解析
     *
     * @param ip IP地址解析主键
     * @return 结果
     */
    public int deleteShopIpJsonByIp(String ip);

    /**
     * 批量删除IP地址解析
     *
     * @param ips 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteShopIpJsonByIps(String[] ips);
}

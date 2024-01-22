package com.ruoyi.shop.service.impl;

import cn.hutool.http.HttpUtil;
import com.ruoyi.shop.domain.ShopIpJson;
import com.ruoyi.shop.mapper.ShopIpJsonMapper;
import com.ruoyi.shop.service.IShopIpJsonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * IP地址解析Service业务层处理
 *
 * @author ruoyi
 * @date 2024-01-21
 */
@Service
public class ShopIpJsonServiceImpl implements IShopIpJsonService {
    @Autowired
    private ShopIpJsonMapper shopIpJsonMapper;

    /**
     * 查询IP地址解析
     *
     * @param ip IP地址解析主键
     * @return IP地址解析
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public ShopIpJson selectShopIpJsonByIp(String ip) {
        ShopIpJson shopIpJson = shopIpJsonMapper.selectShopIpJsonByIp(ip);
        if(shopIpJson != null){
            return shopIpJson;
        }
        String url = "https://restapi.amap.com/v3/ip?key=6c2e7bc7a2189168b39c18f5b4b4d00e&ip=" + ip;
        String result = HttpUtil.createGet(url).execute().body();
        shopIpJson = new ShopIpJson(ip,result);
        shopIpJsonMapper.insertShopIpJson(shopIpJson);
        return shopIpJson;
    }

    /**
     * 查询IP地址解析列表
     *
     * @param shopIpJson IP地址解析
     * @return IP地址解析
     */
    @Override
    public List<ShopIpJson> selectShopIpJsonList(ShopIpJson shopIpJson) {
        return shopIpJsonMapper.selectShopIpJsonList(shopIpJson);
    }

    /**
     * 新增IP地址解析
     *
     * @param shopIpJson IP地址解析
     * @return 结果
     */
    @Override
    public int insertShopIpJson(ShopIpJson shopIpJson) {
        return shopIpJsonMapper.insertShopIpJson(shopIpJson);
    }

    /**
     * 修改IP地址解析
     *
     * @param shopIpJson IP地址解析
     * @return 结果
     */
    @Override
    public int updateShopIpJson(ShopIpJson shopIpJson) {
        return shopIpJsonMapper.updateShopIpJson(shopIpJson);
    }

    /**
     * 批量删除IP地址解析
     *
     * @param ips 需要删除的IP地址解析主键
     * @return 结果
     */
    @Override
    public int deleteShopIpJsonByIps(String[] ips) {
        return shopIpJsonMapper.deleteShopIpJsonByIps(ips);
    }

    /**
     * 删除IP地址解析信息
     *
     * @param ip IP地址解析主键
     * @return 结果
     */
    @Override
    public int deleteShopIpJsonByIp(String ip) {
        return shopIpJsonMapper.deleteShopIpJsonByIp(ip);
    }
}

package com.ruoyi.shop.service;

import java.util.List;

import com.ruoyi.shop.domain.ShopInfoActivity;

/**
 * 店铺信息活动配置Service接口
 *
 * @author ruoyi
 * @date 2024-01-17
 */
public interface IShopInfoActivityService {
    /**
     * 查询店铺信息活动配置
     *
     * @param id 店铺信息活动配置主键
     * @return 店铺信息活动配置
     */
    public ShopInfoActivity selectShopInfoActivityById(Long id);

    /**
     * 查询店铺信息活动配置列表
     *
     * @param shopInfoActivity 店铺信息活动配置
     * @return 店铺信息活动配置集合
     */
    public List<ShopInfoActivity> selectShopInfoActivityList(ShopInfoActivity shopInfoActivity);

    /**
     * 新增店铺信息活动配置
     *
     * @param shopInfoActivityList 店铺信息活动配置
     * @return 结果
     */
    public int insertShopInfoActivity(List<ShopInfoActivity> shopInfoActivityList);

    /**
     * 修改店铺信息活动配置
     *
     * @param shopInfoActivity 店铺信息活动配置
     * @return 结果
     */
    public int updateShopInfoActivity(ShopInfoActivity shopInfoActivity);

    /**
     * 批量删除店铺信息活动配置
     *
     * @param ids 需要删除的店铺信息活动配置主键集合
     * @return 结果
     */
    public int deleteShopInfoActivityByIds(Long[] ids);

    /**
     * 删除店铺信息活动配置信息
     *
     * @param id 店铺信息活动配置主键
     * @return 结果
     */
    public int deleteShopInfoActivityById(Long id);
}

package com.ruoyi.shop.service;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.shop.domain.ShopActivity;

/**
 * 活动管理Service接口
 *
 * @author ruoyi
 * @date 2024-01-11
 */
public interface IShopActivityService {
    /**
     * 查询活动管理
     *
     * @param id 活动管理主键
     * @return 活动管理
     */
    public ShopActivity selectShopActivityById(Long id);

    /**
     * 查询活动管理列表
     *
     * @param shopActivity 活动管理
     * @return 活动管理集合
     */
    public List<ShopActivity> selectShopActivityList(ShopActivity shopActivity);

    /**
     * 查询活动管理列表
     *
     * @param shopActivity 活动管理
     * @return 活动管理集合
     */
    public List<ShopActivity> selectShopActivityListApi(ShopActivity shopActivity);

    /**
     * 查询活动管理列表
     *
     * @param shopActivity 活动管理
     * @return 活动管理集合
     */
    public JSONObject selectShopActivityListGroup(ShopActivity shopActivity);

    /**
     * 新增活动管理
     *
     * @param shopActivity 活动管理
     * @return 结果
     */
    public int insertShopActivity(ShopActivity shopActivity);

    /**
     * 修改活动管理
     *
     * @param shopActivity 活动管理
     * @return 结果
     */
    public int updateShopActivity(ShopActivity shopActivity);

    /**
     * 批量删除活动管理
     *
     * @param ids 需要删除的活动管理主键集合
     * @return 结果
     */
    public int deleteShopActivityByIds(Long[] ids);

    /**
     * 删除活动管理信息
     *
     * @param id 活动管理主键
     * @return 结果
     */
    public int deleteShopActivityById(Long id);
}

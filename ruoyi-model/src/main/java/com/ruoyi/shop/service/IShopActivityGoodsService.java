package com.ruoyi.shop.service;

import com.ruoyi.shop.domain.ShopActivityGoods;

import java.util.List;

/**
 * 活动商品Service接口
 *
 * @author ruoyi
 * @date 2024-01-11
 */
public interface IShopActivityGoodsService {
    /**
     * 查询活动商品
     *
     * @param id 活动商品主键
     * @return 活动商品
     */
    public ShopActivityGoods selectShopActivityGoodsById(Long id);

    /**
     * 查询活动商品列表
     *
     * @param shopActivityGoods 活动商品
     * @return 活动商品集合
     */
    public List<ShopActivityGoods> selectShopActivityGoodsList(ShopActivityGoods shopActivityGoods);

    /**
     * 新增活动商品
     *
     * @param shopActivityGoods 活动商品
     * @return 结果
     */
    public int insertShopActivityGoods(ShopActivityGoods shopActivityGoods);

    /**
     * 修改活动商品
     *
     * @param shopActivityGoods 活动商品
     * @return 结果
     */
    public int updateShopActivityGoods(ShopActivityGoods shopActivityGoods);

    /**
     * 批量删除活动商品
     *
     * @param ids 需要删除的活动商品主键集合
     * @return 结果
     */
    public int deleteShopActivityGoodsByIds(Long[] ids);

    /**
     * 删除活动商品信息
     *
     * @param id 活动商品主键
     * @return 结果
     */
    public int deleteShopActivityGoodsById(Long id);
}

package com.ruoyi.shop.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.shop.domain.ShopActivityGoods;
import com.ruoyi.shop.mapper.ShopActivityGoodsMapper;
import com.ruoyi.shop.service.IShopActivityGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 活动商品Service业务层处理
 *
 * @author ruoyi
 * @date 2024-01-11
 */
@Service
public class ShopActivityGoodsServiceImpl implements IShopActivityGoodsService {
    @Autowired
    private ShopActivityGoodsMapper shopActivityGoodsMapper;

    /**
     * 查询活动商品
     *
     * @param id 活动商品主键
     * @return 活动商品
     */
    @Override
    public ShopActivityGoods selectShopActivityGoodsById(Long id) {
        return shopActivityGoodsMapper.selectShopActivityGoodsById(id);
    }

    /**
     * 查询活动商品列表
     *
     * @param shopActivityGoods 活动商品
     * @return 活动商品
     */
    @Override
    public List<ShopActivityGoods> selectShopActivityGoodsList(ShopActivityGoods shopActivityGoods) {
        return shopActivityGoodsMapper.selectShopActivityGoodsList(shopActivityGoods);
    }

    /**
     * 新增活动商品
     *
     * @param shopActivityGoods 活动商品
     * @return 结果
     */
    @Override
    public int insertShopActivityGoods(ShopActivityGoods shopActivityGoods) {
        shopActivityGoods.setCreateTime(DateUtils.getNowDate());
        return shopActivityGoodsMapper.insertShopActivityGoods(shopActivityGoods);
    }

    /**
     * 修改活动商品
     *
     * @param shopActivityGoods 活动商品
     * @return 结果
     */
    @Override
    public int updateShopActivityGoods(ShopActivityGoods shopActivityGoods) {
        shopActivityGoods.setUpdateTime(DateUtils.getNowDate());
        return shopActivityGoodsMapper.updateShopActivityGoods(shopActivityGoods);
    }

    /**
     * 批量删除活动商品
     *
     * @param ids 需要删除的活动商品主键
     * @return 结果
     */
    @Override
    public int deleteShopActivityGoodsByIds(Long[] ids) {
        return shopActivityGoodsMapper.deleteShopActivityGoodsByIds(ids);
    }

    /**
     * 删除活动商品信息
     *
     * @param id 活动商品主键
     * @return 结果
     */
    @Override
    public int deleteShopActivityGoodsById(Long id) {
        return shopActivityGoodsMapper.deleteShopActivityGoodsById(id);
    }
}

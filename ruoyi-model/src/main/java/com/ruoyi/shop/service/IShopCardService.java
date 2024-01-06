package com.ruoyi.shop.service;

import java.util.List;
import com.ruoyi.shop.domain.ShopCard;

/**
 * 储值卡Service接口
 *
 * @author ruoyi
 * @date 2024-01-06
 */
public interface IShopCardService
{
    /**
     * 查询储值卡
     *
     * @param id 储值卡主键
     * @return 储值卡
     */
    public ShopCard selectShopCardById(Long id);

    /**
     * 查询储值卡列表
     *
     * @param shopCard 储值卡
     * @return 储值卡集合
     */
    public List<ShopCard> selectShopCardList(ShopCard shopCard);

    /**
     * 新增储值卡
     *
     * @param shopCard 储值卡
     * @return 结果
     */
    public int insertShopCard(ShopCard shopCard);

    /**
     * 修改储值卡
     *
     * @param shopCard 储值卡
     * @return 结果
     */
    public int updateShopCard(ShopCard shopCard);

    /**
     * 批量删除储值卡
     *
     * @param ids 需要删除的储值卡主键集合
     * @return 结果
     */
    public int deleteShopCardByIds(Long[] ids);

    /**
     * 删除储值卡信息
     *
     * @param id 储值卡主键
     * @return 结果
     */
    public int deleteShopCardById(Long id);
}

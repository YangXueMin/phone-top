package com.ruoyi.shop.service;

import java.util.List;
import com.ruoyi.shop.domain.GoodsSpecs;

/**
 * 商品规格Service接口
 *
 * @author ruoyi
 * @date 2024-01-06
 */
public interface IGoodsSpecsService
{
    /**
     * 查询商品规格
     *
     * @param id 商品规格主键
     * @return 商品规格
     */
    public GoodsSpecs selectGoodsSpecsById(Long id);

    /**
     * 查询商品规格列表
     *
     * @param goodsSpecs 商品规格
     * @return 商品规格集合
     */
    public List<GoodsSpecs> selectGoodsSpecsList(GoodsSpecs goodsSpecs);

    /**
     * 新增商品规格
     *
     * @param goodsSpecs 商品规格
     * @return 结果
     */
    public int insertGoodsSpecs(GoodsSpecs goodsSpecs);

    /**
     * 修改商品规格
     *
     * @param goodsSpecs 商品规格
     * @return 结果
     */
    public int updateGoodsSpecs(GoodsSpecs goodsSpecs);

    /**
     * 批量删除商品规格
     *
     * @param ids 需要删除的商品规格主键集合
     * @return 结果
     */
    public int deleteGoodsSpecsByIds(Long[] ids);

    /**
     * 删除商品规格信息
     *
     * @param id 商品规格主键
     * @return 结果
     */
    public int deleteGoodsSpecsById(Long id);
}

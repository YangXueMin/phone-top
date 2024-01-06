package com.ruoyi.shop.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import com.ruoyi.shop.domain.GoodsSpecs;

/**
 * 商品规格Mapper接口
 *
 * @author ruoyi
 * @date 2024-01-06
 */
@Mapper
public interface GoodsSpecsMapper {
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
     * 删除商品规格
     *
     * @param id 商品规格主键
     * @return 结果
     */
    public int deleteGoodsSpecsById(Long id);

    /**
     * 删除商品规格
     *
     * @param goodId 商品主键
     * @return 结果
     */
    public int deleteGoodsSpecsByGoodId(Long goodId);

    /**
     * 批量删除商品规格
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteGoodsSpecsByIds(Long[] ids);
}

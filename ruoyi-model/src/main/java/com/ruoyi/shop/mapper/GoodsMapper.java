package com.ruoyi.shop.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import com.ruoyi.shop.domain.Goods;

/**
 * 商品Mapper接口
 *
 * @author ruoyi
 * @date 2024-01-06
 */
@Mapper
public interface GoodsMapper {
    /**
     * 查询商品
     *
     * @param id 商品主键
     * @return 商品
     */
    public Goods selectGoodsById(Long id);

    /**
     * 查询商品列表
     *
     * @param goods 商品
     * @return 商品集合
     */
    public List<Goods> selectGoodsList(Goods goods);

    /**
     * 查询商品列表
     *
     * @param ids ID集合
     * @return 商品集合
     */
    public List<Goods> selectGoodsListByIdIn(Long[] ids);

    /**
     * 新增商品
     *
     * @param goods 商品
     * @return 结果
     */
    public int insertGoods(Goods goods);

    /**
     * 修改商品
     *
     * @param goods 商品
     * @return 结果
     */
    public int updateGoods(Goods goods);

    /**
     * 删除商品
     *
     * @param id 商品主键
     * @return 结果
     */
    public int deleteGoodsById(Long id);

    /**
     * 批量删除商品
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteGoodsByIds(Long[] ids);

    /**
     * 删除商品
     *
     * @param companyGoodsId 企业商品主键
     * @return 结果
     */
    public int deleteGoodsByCompanyGoodsId(Long companyGoodsId);

    /**
     * 批量删除商品
     *
     * @param companyGoodsIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteGoodsByCompanyGoodsIds(Long[] companyGoodsIds);
}

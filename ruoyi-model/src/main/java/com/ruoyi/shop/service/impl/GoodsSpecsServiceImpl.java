package com.ruoyi.shop.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.shop.mapper.GoodsSpecsMapper;
import com.ruoyi.shop.domain.GoodsSpecs;
import com.ruoyi.shop.service.IGoodsSpecsService;

/**
 * 商品规格Service业务层处理
 *
 * @author ruoyi
 * @date 2024-01-06
 */
@Service
public class GoodsSpecsServiceImpl implements IGoodsSpecsService
{
    @Autowired
    private GoodsSpecsMapper goodsSpecsMapper;

    /**
     * 查询商品规格
     *
     * @param id 商品规格主键
     * @return 商品规格
     */
    @Override
    public GoodsSpecs selectGoodsSpecsById(Long id)
    {
        return goodsSpecsMapper.selectGoodsSpecsById(id);
    }

    /**
     * 查询商品规格列表
     *
     * @param goodsSpecs 商品规格
     * @return 商品规格
     */
    @Override
    public List<GoodsSpecs> selectGoodsSpecsList(GoodsSpecs goodsSpecs)
    {
        return goodsSpecsMapper.selectGoodsSpecsList(goodsSpecs);
    }

    /**
     * 新增商品规格
     *
     * @param goodsSpecs 商品规格
     * @return 结果
     */
    @Override
    public int insertGoodsSpecs(GoodsSpecs goodsSpecs)
    {
        goodsSpecs.setCreateTime(DateUtils.getNowDate());
        return goodsSpecsMapper.insertGoodsSpecs(goodsSpecs);
    }

    /**
     * 修改商品规格
     *
     * @param goodsSpecs 商品规格
     * @return 结果
     */
    @Override
    public int updateGoodsSpecs(GoodsSpecs goodsSpecs)
    {
        goodsSpecs.setUpdateTime(DateUtils.getNowDate());
        return goodsSpecsMapper.updateGoodsSpecs(goodsSpecs);
    }

    /**
     * 批量删除商品规格
     *
     * @param ids 需要删除的商品规格主键
     * @return 结果
     */
    @Override
    public int deleteGoodsSpecsByIds(Long[] ids)
    {
        return goodsSpecsMapper.deleteGoodsSpecsByIds(ids);
    }

    /**
     * 删除商品规格信息
     *
     * @param id 商品规格主键
     * @return 结果
     */
    @Override
    public int deleteGoodsSpecsById(Long id)
    {
        return goodsSpecsMapper.deleteGoodsSpecsById(id);
    }
}

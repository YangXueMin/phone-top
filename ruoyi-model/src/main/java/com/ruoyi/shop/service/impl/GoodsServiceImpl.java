package com.ruoyi.shop.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.shop.domain.GoodsSpecs;
import com.ruoyi.shop.mapper.GoodsSpecsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.shop.mapper.GoodsMapper;
import com.ruoyi.shop.domain.Goods;
import com.ruoyi.shop.service.IGoodsService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 商品Service业务层处理
 *
 * @author ruoyi
 * @date 2024-01-06
 */
@Service
public class GoodsServiceImpl implements IGoodsService {
    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private GoodsSpecsMapper goodsSpecsMapper;

    /**
     * 查询商品
     *
     * @param id 商品主键
     * @return 商品
     */
    @Override
    public Goods selectGoodsById(Long id) {
        return goodsMapper.selectGoodsById(id);
    }

    /**
     * 查询商品列表
     *
     * @param goods 商品
     * @return 商品
     */
    @Override
    public List<Goods> selectGoodsList(Goods goods) {
        return goodsMapper.selectGoodsList(goods);
    }

    /**
     * 新增商品
     *
     * @param goods 商品
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertGoods(Goods goods) {
        goods.setCreateTime(DateUtils.getNowDate());
        int i = goodsMapper.insertGoods(goods);
        if (goods.getSpecsList().size() > 0) {
            for (GoodsSpecs goodsSpecs : goods.getSpecsList()) {
                goodsSpecs.setGoodsId(goods.getId());
                goodsSpecs.setCreateTime(DateUtils.getNowDate());
                goodsSpecsMapper.insertGoodsSpecs(goodsSpecs);
            }
        }
        return i;
    }

    /**
     * 修改商品
     *
     * @param goods 商品
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateGoods(Goods goods) {
        goods.setUpdateTime(DateUtils.getNowDate());
        int i = goodsMapper.updateGoods(goods);
        goodsSpecsMapper.deleteGoodsSpecsByGoodId(goods.getId());
        if (goods.getSpecsList().size() > 0) {
            for (GoodsSpecs goodsSpecs : goods.getSpecsList()) {
                goodsSpecs.setGoodsId(goods.getId());
                goodsSpecs.setCreateTime(DateUtils.getNowDate());
                goodsSpecsMapper.insertGoodsSpecs(goodsSpecs);
            }
        }
        return i;
    }

    /**
     * 批量删除商品
     *
     * @param ids 需要删除的商品主键
     * @return 结果
     */
    @Override
    public int deleteGoodsByIds(Long[] ids) {
        return goodsMapper.deleteGoodsByIds(ids);
    }

    /**
     * 删除商品信息
     *
     * @param id 商品主键
     * @return 结果
     */
    @Override
    public int deleteGoodsById(Long id) {
        return goodsMapper.deleteGoodsById(id);
    }
}

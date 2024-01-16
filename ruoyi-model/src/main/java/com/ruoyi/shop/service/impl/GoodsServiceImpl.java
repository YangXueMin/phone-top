package com.ruoyi.shop.service.impl;

import com.ruoyi.common.annotation.ShopScope;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.shop.domain.Goods;
import com.ruoyi.shop.domain.GoodsSpecs;
import com.ruoyi.shop.mapper.GoodsMapper;
import com.ruoyi.shop.mapper.GoodsSpecsMapper;
import com.ruoyi.shop.service.IGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        Goods goods = goodsMapper.selectGoodsById(id);
        if(goods != null){
            goods.setSpecsList(goodsSpecsMapper.selectGoodsSpecsByGoodId(id));
        }
        return goods;
    }

    /**
     * 查询商品列表
     *
     * @param goods 商品
     * @return 商品
     */
    @Override
    @ShopScope(shopAlias = "a")
    public List<Goods> selectGoodsList(Goods goods) {
        List<Goods> goodsList = goodsMapper.selectGoodsList(goods);
        if(goodsList.size() > 0){
            for (Goods goodsData : goodsList) {
                goodsData.setSpecsList(goodsSpecsMapper.selectGoodsSpecsByGoodId(goodsData.getId()));
            }
        }
        return goodsList;
    }

    /**
     * 查询商品列表
     *
     * @param goods 商品
     * @return 商品
     */
    @Override
    public List<Goods> selectGoodsListApi(Goods goods) {
        List<Goods> goodsList = goodsMapper.selectGoodsList(goods);
        if(goodsList.size() > 0){
            for (Goods goodsData : goodsList) {
                goodsData.setSpecsList(goodsSpecsMapper.selectGoodsSpecsByGoodId(goodsData.getId()));
            }
        }
        return goodsList;
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
    @Transactional(rollbackFor = Exception.class)
    public int deleteGoodsByIds(Long[] ids) {
        int i = goodsMapper.deleteGoodsByIds(ids);
        if(i > 0){
            goodsSpecsMapper.deleteGoodsSpecsByGoodIds(ids);
        }
        return i;
    }

    /**
     * 删除商品信息
     *
     * @param id 商品主键
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteGoodsById(Long id) {
        int i = goodsMapper.deleteGoodsById(id);
        if(i > 0){
            goodsSpecsMapper.deleteGoodsSpecsByGoodId(id);
        }
        return i;
    }
}

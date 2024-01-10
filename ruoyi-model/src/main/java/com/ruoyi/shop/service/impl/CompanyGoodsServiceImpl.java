package com.ruoyi.shop.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.ToolUtils;
import com.ruoyi.shop.domain.*;
import com.ruoyi.shop.mapper.*;
import com.ruoyi.shop.service.ICompanyGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 企业商品Service业务层处理
 *
 * @author ruoyi
 * @date 2024-01-06
 */
@Service
public class CompanyGoodsServiceImpl implements ICompanyGoodsService {
    @Autowired
    private CompanyGoodsMapper companyGoodsMapper;
    @Autowired
    private CompanyGoodsSpecsMapper companyGoodsSpecsMapper;
    @Autowired
    private ShopInfoMapper shopInfoMapper;
    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private GoodsSpecsMapper goodsSpecsMapper;

    /**
     * 查询企业商品
     *
     * @param id 企业商品主键
     * @return 企业商品
     */
    @Override
    public CompanyGoods selectCompanyGoodsById(Long id) {
        return companyGoodsMapper.selectCompanyGoodsById(id);
    }

    /**
     * 查询企业商品列表
     *
     * @param companyGoods 企业商品
     * @return 企业商品
     */
    @Override
    public List<CompanyGoods> selectCompanyGoodsList(CompanyGoods companyGoods) {
        return companyGoodsMapper.selectCompanyGoodsList(companyGoods);
    }

    /**
     * 新增企业商品
     *
     * @param companyGoods 企业商品
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertCompanyGoods(CompanyGoods companyGoods) {
        CompanyGoods newCompany = new CompanyGoods();
        ToolUtils.copyPropertiesIgnoreNull(companyGoods,newCompany);
        List<CompanyGoodsSpecs> companyGoodsList = companyGoods.getSpecsList();

        companyGoods.setCreateTime(DateUtils.getNowDate());
        int i = companyGoodsMapper.insertCompanyGoods(companyGoods);
        if (companyGoods.getSpecsList().size() > 0) {
            for (CompanyGoodsSpecs companyGoodsSpecs : companyGoods.getSpecsList()) {
                companyGoodsSpecs.setGoodsId(companyGoods.getId());
                companyGoodsSpecs.setCreateTime(DateUtils.getNowDate());
                companyGoodsSpecsMapper.insertCompanyGoodsSpecs(companyGoodsSpecs);
            }
        }
        //查询同步商品的店铺
        ShopInfo shopInfo = new ShopInfo();
        shopInfo.setIsSyncShop("1");
        List<ShopInfo> shopInfoList = shopInfoMapper.selectShopInfoList(shopInfo);
        if(shopInfoList.size() > 0){
            for (ShopInfo info : shopInfoList) {
                Goods goods = new Goods();
                ToolUtils.copyPropertiesIgnoreNull(newCompany,goods);
                goods.setShopId(info.getId());
                goods.setCompanyGoodsId(companyGoods.getId());
                goodsMapper.insertGoods(goods);
                if (companyGoodsList.size() > 0) {
                    for (CompanyGoodsSpecs companyGoodsSpecs : companyGoodsList) {
                        GoodsSpecs goodsSpecs = new GoodsSpecs();
                        ToolUtils.copyPropertiesIgnoreNull(companyGoodsSpecs,goodsSpecs);
                        goodsSpecs.setGoodsId(goods.getId());
                        goodsSpecs.setCreateTime(DateUtils.getNowDate());
                        goodsSpecsMapper.insertGoodsSpecs(goodsSpecs);
                    }
                }
            }
        }
        return i;
    }

    /**
     * 修改企业商品
     *
     * @param companyGoods 企业商品
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateCompanyGoods(CompanyGoods companyGoods) {
        List<CompanyGoodsSpecs> companyGoodsList = companyGoods.getSpecsList();

        companyGoods.setUpdateTime(DateUtils.getNowDate());
        int i = companyGoodsMapper.updateCompanyGoods(companyGoods);
        companyGoodsSpecsMapper.deleteCompanyGoodsSpecsByGoodId(companyGoods.getId());
        if (companyGoods.getSpecsList().size() > 0) {
            for (CompanyGoodsSpecs companyGoodsSpecs : companyGoods.getSpecsList()) {
                companyGoodsSpecs.setGoodsId(companyGoods.getId());
                companyGoodsSpecs.setCreateTime(DateUtils.getNowDate());
                companyGoodsSpecsMapper.insertCompanyGoodsSpecs(companyGoodsSpecs);
            }
        }
        Goods queryGoods = new Goods();
        queryGoods.setCompanyGoodsId(companyGoods.getId());
        List<Goods> goodsList = goodsMapper.selectGoodsList(queryGoods);
        if(goodsList.size() > 0){
            for (Goods goods : goodsList) {
                Long id = goods.getId();
                ToolUtils.copyPropertiesIgnoreNull(companyGoods,goods);
                goods.setId(id);
                goodsMapper.updateGoods(goods);
                goodsSpecsMapper.deleteGoodsSpecsByGoodId(goods.getId());
                if (companyGoodsList.size() > 0) {
                    for (CompanyGoodsSpecs companyGoodsSpecs : companyGoodsList) {
                        GoodsSpecs goodsSpecs = new GoodsSpecs();
                        ToolUtils.copyPropertiesIgnoreNull(companyGoodsSpecs,goodsSpecs);
                        goodsSpecs.setGoodsId(goods.getId());
                        goodsSpecs.setCreateTime(DateUtils.getNowDate());
                        goodsSpecsMapper.insertGoodsSpecs(goodsSpecs);
                    }
                }
            }
        }
        return i;
    }

    /**
     * 批量删除企业商品
     *
     * @param ids 需要删除的企业商品主键
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteCompanyGoodsByIds(Long[] ids) {
        int i = companyGoodsMapper.deleteCompanyGoodsByIds(ids);
        if(i > 0){
            companyGoodsSpecsMapper.deleteCompanyGoodsSpecsByGoodIds(ids);
            for (Long id : ids) {
                Goods queryGoods = new Goods();
                queryGoods.setCompanyGoodsId(id);
                List<Goods> goodsList = goodsMapper.selectGoodsList(queryGoods);
                goodsSpecsMapper.deleteGoodsSpecsByGoodIds(goodsList.stream().map(Goods::getId).toArray(Long[]::new));
            }
            goodsMapper.deleteGoodsByCompanyGoodsIds(ids);
        }
        return i;
    }

    /**
     * 删除企业商品信息
     *
     * @param id 企业商品主键
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteCompanyGoodsById(Long id) {
        final int i = companyGoodsMapper.deleteCompanyGoodsById(id);
        if(i > 0){
            companyGoodsSpecsMapper.deleteCompanyGoodsSpecsByGoodId(id);
            Goods queryGoods = new Goods();
            queryGoods.setCompanyGoodsId(id);
            List<Goods> goodsList = goodsMapper.selectGoodsList(queryGoods);
            goodsSpecsMapper.deleteGoodsSpecsByGoodIds(goodsList.stream().map(Goods::getId).toArray(Long[]::new));
            goodsMapper.deleteGoodsByCompanyGoodsId(id);
        }
        return i;
    }
}

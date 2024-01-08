package com.ruoyi.shop.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.shop.domain.CompanyGoods;
import com.ruoyi.shop.domain.GoodsSpecs;
import com.ruoyi.shop.mapper.CompanyGoodsMapper;
import com.ruoyi.shop.mapper.GoodsSpecsMapper;
import com.ruoyi.shop.service.ICompanyGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        companyGoods.setCreateTime(DateUtils.getNowDate());
        int i = companyGoodsMapper.insertCompanyGoods(companyGoods);
        if (companyGoods.getSpecsList().size() > 0) {
            for (GoodsSpecs goodsSpecs : companyGoods.getSpecsList()) {
                goodsSpecs.setGoodsId(companyGoods.getId());
                goodsSpecs.setCreateTime(DateUtils.getNowDate());
                goodsSpecsMapper.insertGoodsSpecs(goodsSpecs);
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
        companyGoods.setUpdateTime(DateUtils.getNowDate());
        int i = companyGoodsMapper.updateCompanyGoods(companyGoods);
        goodsSpecsMapper.deleteGoodsSpecsByGoodId(companyGoods.getId());
        if (companyGoods.getSpecsList().size() > 0) {
            for (GoodsSpecs goodsSpecs : companyGoods.getSpecsList()) {
                goodsSpecs.setGoodsId(companyGoods.getId());
                goodsSpecs.setCreateTime(DateUtils.getNowDate());
                goodsSpecsMapper.insertGoodsSpecs(goodsSpecs);
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
    public int deleteCompanyGoodsByIds(Long[] ids) {
        return companyGoodsMapper.deleteCompanyGoodsByIds(ids);
    }

    /**
     * 删除企业商品信息
     *
     * @param id 企业商品主键
     * @return 结果
     */
    @Override
    public int deleteCompanyGoodsById(Long id) {
        return companyGoodsMapper.deleteCompanyGoodsById(id);
    }
}

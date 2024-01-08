package com.ruoyi.shop.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.shop.domain.CompanyGoodsSpecs;
import com.ruoyi.shop.mapper.CompanyGoodsSpecsMapper;
import com.ruoyi.shop.service.ICompanyGoodsSpecsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 企业商品规格Service业务层处理
 *
 * @author ruoyi
 * @date 2024-01-06
 */
@Service
public class CompanyGoodsSpecsServiceImpl implements ICompanyGoodsSpecsService {
    @Autowired
    private CompanyGoodsSpecsMapper companyGoodsSpecsMapper;

    /**
     * 查询企业商品规格
     *
     * @param id 企业商品规格主键
     * @return 企业商品规格
     */
    @Override
    public CompanyGoodsSpecs selectCompanyGoodsSpecsById(Long id) {
        return companyGoodsSpecsMapper.selectCompanyGoodsSpecsById(id);
    }

    /**
     * 查询企业商品规格列表
     *
     * @param companyGoodsSpecs 企业商品规格
     * @return 企业商品规格
     */
    @Override
    public List<CompanyGoodsSpecs> selectCompanyGoodsSpecsList(CompanyGoodsSpecs companyGoodsSpecs) {
        return companyGoodsSpecsMapper.selectCompanyGoodsSpecsList(companyGoodsSpecs);
    }

    /**
     * 新增企业商品规格
     *
     * @param companyGoodsSpecs 企业商品规格
     * @return 结果
     */
    @Override
    public int insertCompanyGoodsSpecs(CompanyGoodsSpecs companyGoodsSpecs) {
        companyGoodsSpecs.setCreateTime(DateUtils.getNowDate());
        return companyGoodsSpecsMapper.insertCompanyGoodsSpecs(companyGoodsSpecs);
    }

    /**
     * 修改企业商品规格
     *
     * @param companyGoodsSpecs 企业商品规格
     * @return 结果
     */
    @Override
    public int updateCompanyGoodsSpecs(CompanyGoodsSpecs companyGoodsSpecs) {
        companyGoodsSpecs.setUpdateTime(DateUtils.getNowDate());
        return companyGoodsSpecsMapper.updateCompanyGoodsSpecs(companyGoodsSpecs);
    }

    /**
     * 批量删除企业商品规格
     *
     * @param ids 需要删除的企业商品规格主键
     * @return 结果
     */
    @Override
    public int deleteCompanyGoodsSpecsByIds(Long[] ids) {
        return companyGoodsSpecsMapper.deleteCompanyGoodsSpecsByIds(ids);
    }

    /**
     * 删除企业商品规格信息
     *
     * @param id 企业商品规格主键
     * @return 结果
     */
    @Override
    public int deleteCompanyGoodsSpecsById(Long id) {
        return companyGoodsSpecsMapper.deleteCompanyGoodsSpecsById(id);
    }
}

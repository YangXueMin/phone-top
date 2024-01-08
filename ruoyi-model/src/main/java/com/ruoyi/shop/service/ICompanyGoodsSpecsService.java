package com.ruoyi.shop.service;

import com.ruoyi.shop.domain.CompanyGoodsSpecs;
import com.ruoyi.shop.domain.GoodsSpecs;

import java.util.List;

/**
 * 企业商品规格Service接口
 *
 * @author ruoyi
 * @date 2024-01-06
 */
public interface ICompanyGoodsSpecsService {
    /**
     * 查询企业商品规格
     *
     * @param id 企业商品规格主键
     * @return 企业商品规格
     */
    public CompanyGoodsSpecs selectCompanyGoodsSpecsById(Long id);

    /**
     * 查询企业商品规格列表
     *
     * @param companyGoodsSpecs 企业商品规格
     * @return 企业商品规格集合
     */
    public List<CompanyGoodsSpecs> selectCompanyGoodsSpecsList(CompanyGoodsSpecs companyGoodsSpecs);

    /**
     * 新增企业商品规格
     *
     * @param companyGoodsSpecs 企业商品规格
     * @return 结果
     */
    public int insertCompanyGoodsSpecs(CompanyGoodsSpecs companyGoodsSpecs);

    /**
     * 修改企业商品规格
     *
     * @param companyGoodsSpecs 企业商品规格
     * @return 结果
     */
    public int updateCompanyGoodsSpecs(CompanyGoodsSpecs companyGoodsSpecs);

    /**
     * 批量删除企业商品规格
     *
     * @param ids 需要删除的企业商品规格主键集合
     * @return 结果
     */
    public int deleteCompanyGoodsSpecsByIds(Long[] ids);

    /**
     * 删除企业商品规格信息
     *
     * @param id 企业商品规格主键
     * @return 结果
     */
    public int deleteCompanyGoodsSpecsById(Long id);
}

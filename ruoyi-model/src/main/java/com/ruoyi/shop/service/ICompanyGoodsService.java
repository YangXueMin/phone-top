package com.ruoyi.shop.service;

import com.ruoyi.shop.domain.CompanyGoods;

import java.util.List;

/**
 * 企业商品Service接口
 *
 * @author ruoyi
 * @date 2024-01-06
 */
public interface ICompanyGoodsService {
    /**
     * 查询企业商品
     *
     * @param id 企业商品主键
     * @return 企业商品
     */
    public CompanyGoods selectCompanyGoodsById(Long id);

    /**
     * 查询企业商品列表
     *
     * @param companyGoods 企业商品
     * @return 企业商品集合
     */
    public List<CompanyGoods> selectCompanyGoodsList(CompanyGoods companyGoods);

    /**
     * 新增企业商品
     *
     * @param goods 企业商品
     * @return 结果
     */
    public int insertCompanyGoods(CompanyGoods goods);

    /**
     * 修改企业商品
     *
     * @param goods 企业商品
     * @return 结果
     */
    public int updateCompanyGoods(CompanyGoods goods);

    /**
     * 批量删除企业商品
     *
     * @param ids 需要删除的企业商品主键集合
     * @return 结果
     */
    public int deleteCompanyGoodsByIds(Long[] ids);

    /**
     * 删除企业商品信息
     *
     * @param id 企业商品主键
     * @return 结果
     */
    public int deleteCompanyGoodsById(Long id);
}

package com.ruoyi.shop.service;

import java.util.List;
import com.ruoyi.shop.domain.CompanyInfo;

/**
 * 企业信息配置Service接口
 *
 * @author ruoyi
 * @date 2024-01-05
 */
public interface ICompanyInfoService
{
    /**
     * 查询企业信息配置
     *
     * @param id 企业信息配置主键
     * @return 企业信息配置
     */
    public CompanyInfo selectCompanyInfoById(Long id);

    /**
     * 查询企业信息配置
     *
     * @param companyId 企业ID
     * @return 企业信息配置
     */
    public CompanyInfo selectCompanyInfoByCompanyId(Long companyId);

    /**
     * 查询企业信息配置列表
     *
     * @param companyInfo 企业信息配置
     * @return 企业信息配置集合
     */
    public List<CompanyInfo> selectCompanyInfoList(CompanyInfo companyInfo);

    /**
     * 新增企业信息配置
     *
     * @param companyInfo 企业信息配置
     * @return 结果
     */
    public int insertCompanyInfo(CompanyInfo companyInfo);

    /**
     * 修改企业信息配置
     *
     * @param companyInfo 企业信息配置
     * @return 结果
     */
    public int updateCompanyInfo(CompanyInfo companyInfo);

    /**
     * 批量删除企业信息配置
     *
     * @param ids 需要删除的企业信息配置主键集合
     * @return 结果
     */
    public int deleteCompanyInfoByIds(Long[] ids);

    /**
     * 删除企业信息配置信息
     *
     * @param id 企业信息配置主键
     * @return 结果
     */
    public int deleteCompanyInfoById(Long id);
}

package com.ruoyi.shop.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import com.ruoyi.shop.domain.CompanyInfo;

/**
 * 企业信息配置Mapper接口
 *
 * @author ruoyi
 * @date 2024-01-05
 */
@Mapper
public interface CompanyInfoMapper {
    /**
     * 查询企业信息配置
     *
     * @param id 企业信息配置主键
     * @return 企业信息配置
     */
    public CompanyInfo selectCompanyInfoById(Long id);

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
     * 删除企业信息配置
     *
     * @param id 企业信息配置主键
     * @return 结果
     */
    public int deleteCompanyInfoById(Long id);

    /**
     * 批量删除企业信息配置
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCompanyInfoByIds(Long[] ids);
}

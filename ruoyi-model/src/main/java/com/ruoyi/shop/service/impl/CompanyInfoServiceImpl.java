package com.ruoyi.shop.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.shop.mapper.CompanyInfoMapper;
import com.ruoyi.shop.domain.CompanyInfo;
import com.ruoyi.shop.service.ICompanyInfoService;

/**
 * 企业信息配置Service业务层处理
 *
 * @author ruoyi
 * @date 2024-01-05
 */
@Service
public class CompanyInfoServiceImpl implements ICompanyInfoService
{
    @Autowired
    private CompanyInfoMapper companyInfoMapper;

    /**
     * 查询企业信息配置
     *
     * @param id 企业信息配置主键
     * @return 企业信息配置
     */
    @Override
    public CompanyInfo selectCompanyInfoById(Long id)
    {
        return companyInfoMapper.selectCompanyInfoById(id);
    }

    /**
     * 查询企业信息配置列表
     *
     * @param companyInfo 企业信息配置
     * @return 企业信息配置
     */
    @Override
    public List<CompanyInfo> selectCompanyInfoList(CompanyInfo companyInfo)
    {
        return companyInfoMapper.selectCompanyInfoList(companyInfo);
    }

    /**
     * 新增企业信息配置
     *
     * @param companyInfo 企业信息配置
     * @return 结果
     */
    @Override
    public int insertCompanyInfo(CompanyInfo companyInfo)
    {
        companyInfo.setCreateTime(DateUtils.getNowDate());
        return companyInfoMapper.insertCompanyInfo(companyInfo);
    }

    /**
     * 修改企业信息配置
     *
     * @param companyInfo 企业信息配置
     * @return 结果
     */
    @Override
    public int updateCompanyInfo(CompanyInfo companyInfo)
    {
        companyInfo.setUpdateTime(DateUtils.getNowDate());
        return companyInfoMapper.updateCompanyInfo(companyInfo);
    }

    /**
     * 批量删除企业信息配置
     *
     * @param ids 需要删除的企业信息配置主键
     * @return 结果
     */
    @Override
    public int deleteCompanyInfoByIds(Long[] ids)
    {
        return companyInfoMapper.deleteCompanyInfoByIds(ids);
    }

    /**
     * 删除企业信息配置信息
     *
     * @param id 企业信息配置主键
     * @return 结果
     */
    @Override
    public int deleteCompanyInfoById(Long id)
    {
        return companyInfoMapper.deleteCompanyInfoById(id);
    }
}

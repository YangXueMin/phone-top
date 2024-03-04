package com.ruoyi.phone.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.ruoyi.phone.domain.PhoneCompanyConfig;

/**
 * 企业配置Mapper接口
 *
 * @author ruoyi
 * @date 2024-03-04
 */
@Mapper
public interface PhoneCompanyConfigMapper
{
    /**
     * 查询企业配置
     *
     * @param id 企业配置主键
     * @return 企业配置
     */
    public PhoneCompanyConfig selectPhoneCompanyConfigById(Long id);

    /**
     * 查询企业配置列表
     *
     * @param phoneCompanyConfig 企业配置
     * @return 企业配置集合
     */
    public List<PhoneCompanyConfig> selectPhoneCompanyConfigList(PhoneCompanyConfig phoneCompanyConfig);

    /**
     * 新增企业配置
     *
     * @param phoneCompanyConfig 企业配置
     * @return 结果
     */
    public int insertPhoneCompanyConfig(PhoneCompanyConfig phoneCompanyConfig);

    /**
     * 修改企业配置
     *
     * @param phoneCompanyConfig 企业配置
     * @return 结果
     */
    public int updatePhoneCompanyConfig(PhoneCompanyConfig phoneCompanyConfig);

    /**
     * 删除企业配置
     *
     * @param id 企业配置主键
     * @return 结果
     */
    public int deletePhoneCompanyConfigById(Long id);

    /**
     * 批量删除企业配置
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePhoneCompanyConfigByIds(Long[] ids);
}

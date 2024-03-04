package com.ruoyi.phone.service;

import java.util.List;

import com.ruoyi.phone.domain.PhoneCompanyConfig;
import com.ruoyi.phone.domain.PhoneCustomer;

/**
 * 企业配置Service接口
 *
 * @author ruoyi
 * @date 2024-03-04
 */
public interface IPhoneCompanyConfigService {
    /**
     * 查询企业配置
     *
     * @param id 企业配置主键
     * @return 企业配置
     */
    public PhoneCompanyConfig selectPhoneCompanyConfigById(Long id);

    /**
     * 查询微信配置
     *
     * @param appId 微信APPId
     * @return 微信配置
     */
    public PhoneCompanyConfig selectPhoneCompanyConfigByAppId(String appId);

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
     * 批量删除企业配置
     *
     * @param ids 需要删除的企业配置主键集合
     * @return 结果
     */
    public int deletePhoneCompanyConfigByIds(Long[] ids);

    /**
     * 删除企业配置信息
     *
     * @param id 企业配置主键
     * @return 结果
     */
    public int deletePhoneCompanyConfigById(Long id);

    /**
     * 加载参数缓存数据
     */
    public void loadingConfigCache();

    /**
     * 清空参数缓存数据
     */
    public void clearConfigCache();

    /**
     * 重置参数缓存数据
     */
    public void resetConfigCache();
}

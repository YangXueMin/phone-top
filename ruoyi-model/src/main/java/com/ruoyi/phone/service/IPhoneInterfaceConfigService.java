package com.ruoyi.phone.service;

import java.util.List;
import com.ruoyi.phone.domain.PhoneInterfaceConfig;

/**
 * 接口地址配置Service接口
 *
 * @author ruoyi
 * @date 2024-03-07
 */
public interface IPhoneInterfaceConfigService
{
    /**
     * 查询接口地址配置
     *
     * @param id 接口地址配置主键
     * @return 接口地址配置
     */
    public PhoneInterfaceConfig selectPhoneInterfaceConfigById(Long id);

    /**
     * 查询接口地址配置列表
     *
     * @param phoneInterfaceConfig 接口地址配置
     * @return 接口地址配置集合
     */
    public List<PhoneInterfaceConfig> selectPhoneInterfaceConfigList(PhoneInterfaceConfig phoneInterfaceConfig);

    /**
     * 新增接口地址配置
     *
     * @param phoneInterfaceConfig 接口地址配置
     * @return 结果
     */
    public int insertPhoneInterfaceConfig(PhoneInterfaceConfig phoneInterfaceConfig);

    /**
     * 修改接口地址配置
     *
     * @param phoneInterfaceConfig 接口地址配置
     * @return 结果
     */
    public int updatePhoneInterfaceConfig(PhoneInterfaceConfig phoneInterfaceConfig);

    /**
     * 批量删除接口地址配置
     *
     * @param ids 需要删除的接口地址配置主键集合
     * @return 结果
     */
    public int deletePhoneInterfaceConfigByIds(Long[] ids);

    /**
     * 删除接口地址配置信息
     *
     * @param id 接口地址配置主键
     * @return 结果
     */
    public int deletePhoneInterfaceConfigById(Long id);
}

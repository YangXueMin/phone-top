package com.ruoyi.phone.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.phone.mapper.PhoneInterfaceConfigMapper;
import com.ruoyi.phone.domain.PhoneInterfaceConfig;
import com.ruoyi.phone.service.IPhoneInterfaceConfigService;

/**
 * 接口地址配置Service业务层处理
 *
 * @author ruoyi
 * @date 2024-03-07
 */
@Service
public class PhoneInterfaceConfigServiceImpl implements IPhoneInterfaceConfigService
{
    @Autowired
    private PhoneInterfaceConfigMapper phoneInterfaceConfigMapper;

    /**
     * 查询接口地址配置
     *
     * @param id 接口地址配置主键
     * @return 接口地址配置
     */
    @Override
    public PhoneInterfaceConfig selectPhoneInterfaceConfigById(Long id)
    {
        return phoneInterfaceConfigMapper.selectPhoneInterfaceConfigById(id);
    }

    /**
     * 查询接口地址配置列表
     *
     * @param phoneInterfaceConfig 接口地址配置
     * @return 接口地址配置
     */
    @Override
    public List<PhoneInterfaceConfig> selectPhoneInterfaceConfigList(PhoneInterfaceConfig phoneInterfaceConfig)
    {
        return phoneInterfaceConfigMapper.selectPhoneInterfaceConfigList(phoneInterfaceConfig);
    }

    /**
     * 新增接口地址配置
     *
     * @param phoneInterfaceConfig 接口地址配置
     * @return 结果
     */
    @Override
    public int insertPhoneInterfaceConfig(PhoneInterfaceConfig phoneInterfaceConfig)
    {
        phoneInterfaceConfig.setCreateTime(DateUtils.getNowDate());
        return phoneInterfaceConfigMapper.insertPhoneInterfaceConfig(phoneInterfaceConfig);
    }

    /**
     * 修改接口地址配置
     *
     * @param phoneInterfaceConfig 接口地址配置
     * @return 结果
     */
    @Override
    public int updatePhoneInterfaceConfig(PhoneInterfaceConfig phoneInterfaceConfig)
    {
        phoneInterfaceConfig.setUpdateTime(DateUtils.getNowDate());
        return phoneInterfaceConfigMapper.updatePhoneInterfaceConfig(phoneInterfaceConfig);
    }

    /**
     * 批量删除接口地址配置
     *
     * @param ids 需要删除的接口地址配置主键
     * @return 结果
     */
    @Override
    public int deletePhoneInterfaceConfigByIds(Long[] ids)
    {
        return phoneInterfaceConfigMapper.deletePhoneInterfaceConfigByIds(ids);
    }

    /**
     * 删除接口地址配置信息
     *
     * @param id 接口地址配置主键
     * @return 结果
     */
    @Override
    public int deletePhoneInterfaceConfigById(Long id)
    {
        return phoneInterfaceConfigMapper.deletePhoneInterfaceConfigById(id);
    }
}

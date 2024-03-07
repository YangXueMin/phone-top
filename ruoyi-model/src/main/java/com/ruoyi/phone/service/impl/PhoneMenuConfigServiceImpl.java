package com.ruoyi.phone.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.phone.mapper.PhoneMenuConfigMapper;
import com.ruoyi.phone.domain.PhoneMenuConfig;
import com.ruoyi.phone.service.IPhoneMenuConfigService;

/**
 * 小程序菜单配置Service业务层处理
 *
 * @author ruoyi
 * @date 2024-03-07
 */
@Service
public class PhoneMenuConfigServiceImpl implements IPhoneMenuConfigService
{
    @Autowired
    private PhoneMenuConfigMapper phoneMenuConfigMapper;

    /**
     * 查询小程序菜单配置
     *
     * @param id 小程序菜单配置主键
     * @return 小程序菜单配置
     */
    @Override
    public PhoneMenuConfig selectPhoneMenuConfigById(Long id)
    {
        return phoneMenuConfigMapper.selectPhoneMenuConfigById(id);
    }

    /**
     * 查询小程序菜单配置列表
     *
     * @param phoneMenuConfig 小程序菜单配置
     * @return 小程序菜单配置
     */
    @Override
    public List<PhoneMenuConfig> selectPhoneMenuConfigList(PhoneMenuConfig phoneMenuConfig)
    {
        return phoneMenuConfigMapper.selectPhoneMenuConfigList(phoneMenuConfig);
    }

    /**
     * 新增小程序菜单配置
     *
     * @param phoneMenuConfig 小程序菜单配置
     * @return 结果
     */
    @Override
    public int insertPhoneMenuConfig(PhoneMenuConfig phoneMenuConfig)
    {
        phoneMenuConfig.setCreateTime(DateUtils.getNowDate());
        return phoneMenuConfigMapper.insertPhoneMenuConfig(phoneMenuConfig);
    }

    /**
     * 修改小程序菜单配置
     *
     * @param phoneMenuConfig 小程序菜单配置
     * @return 结果
     */
    @Override
    public int updatePhoneMenuConfig(PhoneMenuConfig phoneMenuConfig)
    {
        phoneMenuConfig.setUpdateTime(DateUtils.getNowDate());
        return phoneMenuConfigMapper.updatePhoneMenuConfig(phoneMenuConfig);
    }

    /**
     * 批量删除小程序菜单配置
     *
     * @param ids 需要删除的小程序菜单配置主键
     * @return 结果
     */
    @Override
    public int deletePhoneMenuConfigByIds(Long[] ids)
    {
        return phoneMenuConfigMapper.deletePhoneMenuConfigByIds(ids);
    }

    /**
     * 删除小程序菜单配置信息
     *
     * @param id 小程序菜单配置主键
     * @return 结果
     */
    @Override
    public int deletePhoneMenuConfigById(Long id)
    {
        return phoneMenuConfigMapper.deletePhoneMenuConfigById(id);
    }
}

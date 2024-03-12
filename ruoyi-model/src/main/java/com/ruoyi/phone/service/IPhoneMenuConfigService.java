package com.ruoyi.phone.service;

import java.util.List;

import com.ruoyi.phone.domain.PhoneMenuConfig;

/**
 * 小程序菜单配置Service接口
 *
 * @author ruoyi
 * @date 2024-03-07
 */
public interface IPhoneMenuConfigService {
    /**
     * 查询小程序菜单配置
     *
     * @param id 小程序菜单配置主键
     * @return 小程序菜单配置
     */
    public PhoneMenuConfig selectPhoneMenuConfigById(Long id);

    /**
     * 查询小程序菜单配置列表
     *
     * @param phoneMenuConfig 小程序菜单配置
     * @return 小程序菜单配置集合
     */
    public List<PhoneMenuConfig> selectPhoneMenuConfigList(PhoneMenuConfig phoneMenuConfig);

    /**
     * 新增小程序菜单配置
     *
     * @param phoneMenuConfig 小程序菜单配置
     * @return 结果
     */
    public int insertPhoneMenuConfig(PhoneMenuConfig phoneMenuConfig);

    /**
     * 修改小程序菜单配置
     *
     * @param phoneMenuConfig 小程序菜单配置
     * @return 结果
     */
    public int updatePhoneMenuConfig(PhoneMenuConfig phoneMenuConfig);

    /**
     * 批量删除小程序菜单配置
     *
     * @param ids 需要删除的小程序菜单配置主键集合
     * @return 结果
     */
    public int deletePhoneMenuConfigByIds(Long[] ids);

    /**
     * 删除小程序菜单配置信息
     *
     * @param id 小程序菜单配置主键
     * @return 结果
     */
    public int deletePhoneMenuConfigById(Long id);
}

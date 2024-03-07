package com.ruoyi.phone.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.ruoyi.phone.domain.PhoneMenuConfig;

/**
 * 小程序菜单配置Mapper接口
 *
 * @author ruoyi
 * @date 2024-03-07
 */
@Mapper
public interface PhoneMenuConfigMapper
{
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
     * 删除小程序菜单配置
     *
     * @param id 小程序菜单配置主键
     * @return 结果
     */
    public int deletePhoneMenuConfigById(Long id);

    /**
     * 批量删除小程序菜单配置
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePhoneMenuConfigByIds(Long[] ids);
}

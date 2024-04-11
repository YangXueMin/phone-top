package com.ruoyi.phone.service;

import java.util.List;

import com.ruoyi.phone.domain.PhoneMenuApp;

/**
 * 菜单关联Service接口
 *
 * @author ruoyi
 * @date 2024-04-11
 */
public interface IPhoneMenuAppService {
    /**
     * 查询菜单关联
     *
     * @param id 菜单关联主键
     * @return 菜单关联
     */
    public PhoneMenuApp selectPhoneMenuAppById(Long id);
    /**
     * 查询菜单关联
     *
     * @param appId 微信配置ID
     * @return 菜单关联
     */
    public List<PhoneMenuApp> selectPhoneMenuAppByAppId(String appId);

    /**
     * 查询菜单关联列表
     *
     * @param phoneMenuApp 菜单关联
     * @return 菜单关联集合
     */
    public List<PhoneMenuApp> selectPhoneMenuAppList(PhoneMenuApp phoneMenuApp);

    /**
     * 新增菜单关联
     *
     * @param phoneMenuApp 菜单关联
     * @return 结果
     */
    public int insertPhoneMenuApp(PhoneMenuApp phoneMenuApp);

    /**
     * 保存
     *
     * @param list 菜单关联
     * @return 结果
     */
    public int savePhoneMenuAppList(List<PhoneMenuApp> list);

    /**
     * 修改菜单关联
     *
     * @param phoneMenuApp 菜单关联
     * @return 结果
     */
    public int updatePhoneMenuApp(PhoneMenuApp phoneMenuApp);

    /**
     * 批量删除菜单关联
     *
     * @param ids 需要删除的菜单关联主键集合
     * @return 结果
     */
    public int deletePhoneMenuAppByIds(Long[] ids);

    /**
     * 删除菜单关联信息
     *
     * @param id 菜单关联主键
     * @return 结果
     */
    public int deletePhoneMenuAppById(Long id);
}

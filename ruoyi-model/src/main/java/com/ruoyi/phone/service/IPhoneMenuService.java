package com.ruoyi.phone.service;


import com.ruoyi.phone.domain.PhoneMenu;

import java.util.List;

/**
 * 菜单管理Service接口
 *
 * @author ruoyi
 * @date 2024-04-11
 */
public interface IPhoneMenuService {
    /**
     * 查询菜单管理
     *
     * @param id 菜单管理主键
     * @return 菜单管理
     */
    public PhoneMenu selectPhoneMenuById(Long id);

    /**
     * 查询菜单管理列表
     *
     * @param phoneMenu 菜单管理
     * @return 菜单管理集合
     */
    public List<PhoneMenu> selectPhoneMenuList(PhoneMenu phoneMenu);

    /**
     * 新增菜单管理
     *
     * @param phoneMenu 菜单管理
     * @return 结果
     */
    public int insertPhoneMenu(PhoneMenu phoneMenu);

    /**
     * 修改菜单管理
     *
     * @param phoneMenu 菜单管理
     * @return 结果
     */
    public int updatePhoneMenu(PhoneMenu phoneMenu);

    /**
     * 批量删除菜单管理
     *
     * @param ids 需要删除的菜单管理主键集合
     * @return 结果
     */
    public int deletePhoneMenuByIds(Long[] ids);

    /**
     * 删除菜单管理信息
     *
     * @param id 菜单管理主键
     * @return 结果
     */
    public int deletePhoneMenuById(Long id);
}

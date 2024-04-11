package com.ruoyi.phone.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.phone.domain.PhoneMenu;
import com.ruoyi.phone.mapper.PhoneMenuMapper;
import com.ruoyi.phone.service.IPhoneMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 菜单管理Service业务层处理
 *
 * @author ruoyi
 * @date 2024-04-11
 */
@Service
public class PhoneMenuServiceImpl implements IPhoneMenuService {
    @Autowired
    private PhoneMenuMapper phoneMenuMapper;

    /**
     * 查询菜单管理
     *
     * @param id 菜单管理主键
     * @return 菜单管理
     */
    @Override
    public PhoneMenu selectPhoneMenuById(Long id) {
        return phoneMenuMapper.selectPhoneMenuById(id);
    }

    /**
     * 查询菜单管理列表
     *
     * @param phoneMenu 菜单管理
     * @return 菜单管理
     */
    @Override
    public List<PhoneMenu> selectPhoneMenuList(PhoneMenu phoneMenu) {
        return phoneMenuMapper.selectPhoneMenuList(phoneMenu);
    }

    /**
     * 新增菜单管理
     *
     * @param phoneMenu 菜单管理
     * @return 结果
     */
    @Override
    public int insertPhoneMenu(PhoneMenu phoneMenu) {
        phoneMenu.setCreateTime(DateUtils.getNowDate());
        return phoneMenuMapper.insertPhoneMenu(phoneMenu);
    }

    /**
     * 修改菜单管理
     *
     * @param phoneMenu 菜单管理
     * @return 结果
     */
    @Override
    public int updatePhoneMenu(PhoneMenu phoneMenu) {
        phoneMenu.setUpdateTime(DateUtils.getNowDate());
        return phoneMenuMapper.updatePhoneMenu(phoneMenu);
    }

    /**
     * 批量删除菜单管理
     *
     * @param ids 需要删除的菜单管理主键
     * @return 结果
     */
    @Override
    public int deletePhoneMenuByIds(Long[] ids) {
        return phoneMenuMapper.deletePhoneMenuByIds(ids);
    }

    /**
     * 删除菜单管理信息
     *
     * @param id 菜单管理主键
     * @return 结果
     */
    @Override
    public int deletePhoneMenuById(Long id) {
        return phoneMenuMapper.deletePhoneMenuById(id);
    }
}

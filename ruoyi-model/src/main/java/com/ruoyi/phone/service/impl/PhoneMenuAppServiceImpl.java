package com.ruoyi.phone.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.ruoyi.common.core.domain.entity.WechatConfig;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.phone.domain.PhoneMenu;
import com.ruoyi.phone.mapper.PhoneMenuMapper;
import com.ruoyi.system.mapper.WechatConfigMapper;
import com.ruoyi.system.service.IWechatConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import com.ruoyi.phone.mapper.PhoneMenuAppMapper;
import com.ruoyi.phone.domain.PhoneMenuApp;
import com.ruoyi.phone.service.IPhoneMenuAppService;

/**
 * 菜单关联Service业务层处理
 *
 * @author ruoyi
 * @date 2024-04-11
 */
@Service
public class PhoneMenuAppServiceImpl implements IPhoneMenuAppService {
    @Autowired
    private PhoneMenuAppMapper phoneMenuAppMapper;
    @Autowired
    private PhoneMenuMapper phoneMenuMapper;
    @Autowired
    private IWechatConfigService wechatConfigService;

    /**
     * 查询菜单关联
     *
     * @param id 菜单关联主键
     * @return 菜单关联
     */
    @Override
    public PhoneMenuApp selectPhoneMenuAppById(Long id) {
        return phoneMenuAppMapper.selectPhoneMenuAppById(id);
    }

    @Override
    public List<PhoneMenuApp> selectPhoneMenuAppByAppId(String appId) {
        PhoneMenuApp phoneMenuApp = new PhoneMenuApp();
        phoneMenuApp.setAppId(appId);
        List<PhoneMenuApp> phoneMenuAppList = phoneMenuAppMapper.selectPhoneMenuAppList(phoneMenuApp);
        if (phoneMenuAppList != null && !phoneMenuAppList.isEmpty()) {
            return phoneMenuAppList;
        }
        WechatConfig wechatConfig = wechatConfigService.selectWechatConfigByAppId(appId);
        if (wechatConfig != null) {
            //如果没有，默认插入全部数据
            PhoneMenu phoneMenu = new PhoneMenu();
            phoneMenu.setStatus("1");
            List<PhoneMenu> phoneMenuList = phoneMenuMapper.selectPhoneMenuList(phoneMenu);
            phoneMenuAppList = new ArrayList<>();
            for (PhoneMenu menu : phoneMenuList) {
                PhoneMenuApp menuApp = new PhoneMenuApp();
                menuApp.setAppId(appId);
                menuApp.setDeptId(wechatConfig.getDeptId());
                menuApp.setMenuId(menu.getId());
                menuApp.setWechatConfig(wechatConfig);
                menuApp.setPhoneMenu(menu);
                phoneMenuAppList.add(menuApp);
            }
            return phoneMenuAppList;
        }
        return null;
    }

    /**
     * 查询菜单关联列表
     *
     * @param phoneMenuApp 菜单关联
     * @return 菜单关联
     */
    @Override
    public List<PhoneMenuApp> selectPhoneMenuAppList(PhoneMenuApp phoneMenuApp) {
        return phoneMenuAppMapper.selectPhoneMenuAppList(phoneMenuApp);
    }

    /**
     * 新增菜单关联
     *
     * @param phoneMenuApp 菜单关联
     * @return 结果
     */
    @Override
    public int insertPhoneMenuApp(PhoneMenuApp phoneMenuApp) {
        phoneMenuApp.setCreateTime(DateUtils.getNowDate());
        return phoneMenuAppMapper.insertPhoneMenuApp(phoneMenuApp);
    }

    @Override
    public int savePhoneMenuAppList(List<PhoneMenuApp> list) {
        int i = 0;
        if (list != null) {
            String appId = list.get(0).getAppId();
            WechatConfig wechatConfig = wechatConfigService.selectWechatConfigByAppId(appId);
            phoneMenuAppMapper.deletePhoneMenuAppByAppId(appId);
            for (PhoneMenuApp phoneMenuApp : list) {
                phoneMenuApp.setAppId(appId);
                phoneMenuApp.setDeptId(wechatConfig.getDeptId());
                phoneMenuAppMapper.insertPhoneMenuApp(phoneMenuApp);
                i++;
            }
        }
        return i;
    }

    /**
     * 修改菜单关联
     *
     * @param phoneMenuApp 菜单关联
     * @return 结果
     */
    @Override
    public int updatePhoneMenuApp(PhoneMenuApp phoneMenuApp) {
        phoneMenuApp.setUpdateTime(DateUtils.getNowDate());
        return phoneMenuAppMapper.updatePhoneMenuApp(phoneMenuApp);
    }

    /**
     * 批量删除菜单关联
     *
     * @param ids 需要删除的菜单关联主键
     * @return 结果
     */
    @Override
    public int deletePhoneMenuAppByIds(Long[] ids) {
        return phoneMenuAppMapper.deletePhoneMenuAppByIds(ids);
    }

    /**
     * 删除菜单关联信息
     *
     * @param id 菜单关联主键
     * @return 结果
     */
    @Override
    public int deletePhoneMenuAppById(Long id) {
        return phoneMenuAppMapper.deletePhoneMenuAppById(id);
    }
}

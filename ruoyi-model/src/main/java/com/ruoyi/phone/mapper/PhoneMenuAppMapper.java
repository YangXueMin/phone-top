package com.ruoyi.phone.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.ruoyi.phone.domain.PhoneMenuApp;

/**
 * 菜单关联Mapper接口
 * 
 * @author ruoyi
 * @date 2024-04-11
 */
@Mapper
public interface PhoneMenuAppMapper 
{
    /**
     * 查询菜单关联
     * 
     * @param id 菜单关联主键
     * @return 菜单关联
     */
    public PhoneMenuApp selectPhoneMenuAppById(Long id);

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
     * 修改菜单关联
     * 
     * @param phoneMenuApp 菜单关联
     * @return 结果
     */
    public int updatePhoneMenuApp(PhoneMenuApp phoneMenuApp);

    /**
     * 删除菜单关联
     * 
     * @param id 菜单关联主键
     * @return 结果
     */
    public int deletePhoneMenuAppById(Long id);

    /**
     * 删除菜单关联
     *
     * @param appId 公众号APPID
     * @return 结果
     */
    public int deletePhoneMenuAppByAppId(String appId);

    /**
     * 批量删除菜单关联
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePhoneMenuAppByIds(Long[] ids);
}

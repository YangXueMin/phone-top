package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.common.core.domain.entity.WechatConfig;

/**
 * 微信配置Service接口
 *
 * @author ruoyi
 * @date 2024-03-01
 */
public interface IWechatConfigService
{
    /**
     * 查询微信配置
     *
     * @param id 微信配置主键
     * @return 微信配置
     */
    public WechatConfig selectWechatConfigById(Long id);

    /**
     * 查询微信配置列表
     *
     * @param wechatConfig 微信配置
     * @return 微信配置集合
     */
    public List<WechatConfig> selectWechatConfigList(WechatConfig wechatConfig);

    /**
     * 新增微信配置
     *
     * @param wechatConfig 微信配置
     * @return 结果
     */
    public int insertWechatConfig(WechatConfig wechatConfig);

    /**
     * 修改微信配置
     *
     * @param wechatConfig 微信配置
     * @return 结果
     */
    public int updateWechatConfig(WechatConfig wechatConfig);

    /**
     * 批量删除微信配置
     *
     * @param ids 需要删除的微信配置主键集合
     * @return 结果
     */
    public int deleteWechatConfigByIds(Long[] ids);

    /**
     * 删除微信配置信息
     *
     * @param id 微信配置主键
     * @return 结果
     */
    public int deleteWechatConfigById(Long id);

    /**
     * 加载参数缓存数据
     */
    public void loadingConfigCache();

    /**
     * 清空参数缓存数据
     */
    public void clearConfigCache();

    /**
     * 重置参数缓存数据
     */
    public void resetConfigCache();

}

package com.ruoyi.system.service.impl;

import com.alibaba.fastjson2.JSON;
import com.ruoyi.common.constant.CacheConstants;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.core.domain.entity.WechatConfig;
import com.ruoyi.system.mapper.WechatConfigMapper;
import com.ruoyi.system.service.IWechatConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.List;

/**
 * 微信配置Service业务层处理
 *
 * @author ruoyi
 * @date 2024-03-01
 */
@Service
public class WechatConfigServiceImpl implements IWechatConfigService {
    @Autowired
    private WechatConfigMapper wechatConfigMapper;
    @Autowired
    private RedisCache redisCache;

    /**
     * 项目启动时，初始化参数到缓存
     */
    @PostConstruct
    public void init() {
        loadingConfigCache();
    }

    /**
     * 查询微信配置
     *
     * @param id 微信配置主键
     * @return 微信配置
     */
    @Override
    public WechatConfig selectWechatConfigById(Long id) {
        return wechatConfigMapper.selectWechatConfigById(id);
    }

    /**
     * 查询微信配置列表
     *
     * @param wechatConfig 微信配置
     * @return 微信配置
     */
    @Override
    public List<WechatConfig> selectWechatConfigList(WechatConfig wechatConfig) {
        return wechatConfigMapper.selectWechatConfigList(wechatConfig);
    }

    /**
     * 新增微信配置
     *
     * @param wechatConfig 微信配置
     * @return 结果
     */
    @Override
    public int insertWechatConfig(WechatConfig wechatConfig) {
        wechatConfig.setCreateTime(DateUtils.getNowDate());
        return wechatConfigMapper.insertWechatConfig(wechatConfig);
    }

    /**
     * 修改微信配置
     *
     * @param wechatConfig 微信配置
     * @return 结果
     */
    @Override
    public int updateWechatConfig(WechatConfig wechatConfig) {
        wechatConfig.setUpdateTime(DateUtils.getNowDate());
        return wechatConfigMapper.updateWechatConfig(wechatConfig);
    }

    /**
     * 批量删除微信配置
     *
     * @param ids 需要删除的微信配置主键
     * @return 结果
     */
    @Override
    public int deleteWechatConfigByIds(Long[] ids) {
        return wechatConfigMapper.deleteWechatConfigByIds(ids);
    }

    /**
     * 删除微信配置信息
     *
     * @param id 微信配置主键
     * @return 结果
     */
    @Override
    public int deleteWechatConfigById(Long id) {
        return wechatConfigMapper.deleteWechatConfigById(id);
    }

    /**
     * 加载参数缓存数据
     */
    @Override
    public void loadingConfigCache() {
        List<WechatConfig> configsList = wechatConfigMapper.selectWechatConfigList(new WechatConfig());
        for (WechatConfig config : configsList) {
            redisCache.setCacheObject(getCacheKey(config.getCompanyId()), JSON.toJSONString(config));
        }
    }

    /**
     * 清空参数缓存数据
     */
    @Override
    public void clearConfigCache() {
        Collection<String> keys = redisCache.keys(CacheConstants.WECHAT_CONFIG_KEY + "*");
        redisCache.deleteObject(keys);
    }

    /**
     * 重置参数缓存数据
     */
    @Override
    public void resetConfigCache() {
        clearConfigCache();
        loadingConfigCache();
    }

    /**
     * 设置cache key
     *
     * @param configKey 参数键
     * @return 缓存键key
     */
    private String getCacheKey(Long configKey) {
        return CacheConstants.WECHAT_CONFIG_KEY + configKey;
    }
}

package com.ruoyi.system.service.impl;

import com.alibaba.fastjson2.JSON;
import com.ruoyi.common.annotation.DataScope;
import com.ruoyi.common.constant.CacheConstants;
import com.ruoyi.common.core.domain.entity.WechatConfig;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.time.DateUtil;
import com.ruoyi.system.domain.SysCard;
import com.ruoyi.system.mapper.SysCardMapper;
import com.ruoyi.system.mapper.WechatConfigMapper;
import com.ruoyi.system.service.IWechatConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.Date;
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
    @Autowired
    private SysCardMapper sysCardMapper;

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

    @Override
    public WechatConfig selectWechatConfigByAppId(String appId) {
        WechatConfig wechatConfig;
        if (redisCache.hasKey(getCacheKey(appId))) {
            wechatConfig = JSON.parseObject(redisCache.getCacheObject(getCacheKey(appId)).toString(), WechatConfig.class);
        } else {
            wechatConfig = new WechatConfig();
            wechatConfig.setAppId(appId);
            List<WechatConfig> wechatConfigs = wechatConfigMapper.selectWechatConfigList(wechatConfig);
            if (wechatConfigs.size() > 0) {
                wechatConfig = wechatConfigs.get(0);
                redisCache.setCacheObject(getCacheKey(wechatConfig.getAppId()), JSON.toJSONString(wechatConfig));
            }
        }
        return wechatConfig;
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
        wechatConfig.setDeptId(SecurityUtils.getLoginUser().getUser().getDeptId());
        wechatConfig.setCreateTime(DateUtils.getNowDate());
        final int i = wechatConfigMapper.insertWechatConfig(wechatConfig);
        if (i > 0) {
            redisCache.setCacheObject(getCacheKey(wechatConfig.getAppId()), JSON.toJSONString(wechatConfig));
        }
        return i;
    }

    /**
     * 修改微信配置
     *
     * @param wechatConfig 微信配置
     * @return 结果
     */
    @Override
    public int updateWechatConfig(WechatConfig wechatConfig) {
        wechatConfig.setStatus(null);
        wechatConfig.setValidityPeriod(null);
        wechatConfig.setUpdateTime(DateUtils.getNowDate());
        int i = wechatConfigMapper.updateWechatConfig(wechatConfig);
        if (i > 0) {
            redisCache.deleteObject(getCacheKey(wechatConfig.getAppId()));
        }
        return i;
    }

    /**
     * 批量删除微信配置
     *
     * @param ids 需要删除的微信配置主键
     * @return 结果
     */
    @Override
    public int deleteWechatConfigByIds(Long[] ids) {
        clearConfigCache();
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
        final WechatConfig wechatConfig = wechatConfigMapper.selectWechatConfigById(id);
        final int i = wechatConfigMapper.deleteWechatConfigById(id);
        if (i > 0) {
            redisCache.deleteObject(getCacheKey(wechatConfig.getAppId()));
        }
        return i;
    }

    /**
     * 加载参数缓存数据
     */
    @Override
    public void loadingConfigCache() {
        List<WechatConfig> configsList = wechatConfigMapper.selectWechatConfigList(new WechatConfig());
        for (WechatConfig config : configsList) {
            redisCache.setCacheObject(getCacheKey(config.getAppId()), JSON.toJSONString(config));
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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public synchronized int cancel(Long id, SysCard sysCard) {
        WechatConfig wechatConfig = wechatConfigMapper.selectWechatConfigById(id);
        wechatConfig.setStatus("1");
        Date date = new Date();
        if(wechatConfig.getValidityPeriod() != null){
            date = wechatConfig.getValidityPeriod();
        }
        wechatConfig.setValidityPeriod(DateUtil.endOfDate(DateUtil.addDays(date,sysCard.getTimeSpan())));
        final int i = wechatConfigMapper.updateWechatConfig(wechatConfig);
        if (i > 0) {
            redisCache.deleteObject(getCacheKey(wechatConfig.getAppId()));
            //修改卡状态
            sysCard.setAppId(wechatConfig.getAppId());
            sysCard.setDeptId(wechatConfig.getDeptId());
            sysCard.setCancelStatus("2");
            sysCard.setCancelTime(new Date());
            sysCardMapper.updateSysCard(sysCard);
        }
        return i;
    }

    /**
     * 设置cache key
     *
     * @param configKey 参数键
     * @return 缓存键key
     */
    private String getCacheKey(String configKey) {
        return CacheConstants.WECHAT_CONFIG_KEY + configKey;
    }
}

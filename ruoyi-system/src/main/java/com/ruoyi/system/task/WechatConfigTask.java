package com.ruoyi.system.task;

import com.ruoyi.common.constant.CacheConstants;
import com.ruoyi.common.core.domain.entity.WechatConfig;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.system.mapper.WechatConfigMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author yangxuemin
 * @ClassName WechatConfigTask
 * @Description 微信配置过期时间监听
 * @date 2024/3/13 9:45 PM
 */
@Component("wechatConfigTask")
public class WechatConfigTask {
    @Autowired
    private WechatConfigMapper wechatConfigMapper;
    @Autowired
    private RedisCache redisCache;

    public void wechatConfig() {
        long now = System.currentTimeMillis();
        WechatConfig wechatConfig = new WechatConfig();
        wechatConfig.setStatus("1");
        List<WechatConfig> list = wechatConfigMapper.selectWechatConfigList(wechatConfig);
        for (WechatConfig config : list) {
            if (config.getValidityPeriod() !=null && config.getValidityPeriod().getTime() < now) {
                config.setStatus("2");
                wechatConfigMapper.updateWechatConfig(config);
                redisCache.deleteObject(getCacheKey(wechatConfig.getAppId()));
            }
        }
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

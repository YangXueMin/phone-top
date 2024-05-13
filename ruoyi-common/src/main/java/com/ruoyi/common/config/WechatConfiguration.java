package com.ruoyi.common.config;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.config.impl.WxMaDefaultConfigImpl;
import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import com.google.common.collect.Maps;
import com.ruoyi.common.config.properties.WechatPayProperties;
import com.ruoyi.common.constant.CacheConstants;
import com.ruoyi.common.core.domain.entity.WechatConfig;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import me.chanjar.weixin.common.error.WxRuntimeException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author ruoyi
 * @ClassName WechatConfigurati¬on
 * @Description
 * @date 2023/12/21 2:47 PM
 */
@Configuration
//引入WxPayService这个类 下面两个才会实例化
@ConditionalOnClass({WxPayService.class, WxMaService.class})
@RequiredArgsConstructor
public class WechatConfiguration {
    @Resource
    private WechatPayProperties payProperties;
    @Autowired
    private RedisCache redisCache;

    @Bean
    public WxMaService wxMaService() {
        List<WechatPayProperties.MpConfig> configs = this.payProperties.getConfigs();
        if (configs == null) {
            throw new WxRuntimeException("大哥，拜托先看下项目首页的说明（readme文件），添加下相关配置，注意别配错了！");
        }
        WxMaService maService = new WxMaServiceImpl();
        maService.setMultiConfigs(
                configs.stream()
                        .map(a -> {
                            WxMaDefaultConfigImpl config = new WxMaDefaultConfigImpl();
                            config.setAppid(a.getAppId());
                            config.setSecret(a.getSecret());
                            config.setToken(a.getToken());
                            config.setAesKey(a.getAesKey());
                            return config;
                        }).collect(Collectors.toMap(WxMaDefaultConfigImpl::getAppid, a -> a, (o, n) -> o)));
        return maService;
    }

    /**
     * 获取支付类型
     *
     * @param wechatConfig
     * @return WxPayService
     */
    public synchronized WxPayService wxPayService(WechatConfig wechatConfig) {
        String key = CacheConstants.WX_PAY_SERVICE_KEY + wechatConfig.getAppId();
        WxPayService wxPayService;
        if (redisCache.hasKey(key)) {
            wxPayService = redisCache.getCacheObject(key);
        } else {
            WxPayConfig wxPayConfig = new WxPayConfig();
            wxPayConfig.setAppId(wechatConfig.getAppId());
            wxPayConfig.setMchId(StringUtils.trimToNull(wechatConfig.getMchId()));
            wxPayConfig.setMchKey(StringUtils.trimToNull(wechatConfig.getMchKey()));
            wxPayConfig.setKeyPath(StringUtils.trimToNull(wechatConfig.getKeyPath()));
            // 可以指定是否使用沙箱环境
            wxPayConfig.setUseSandboxEnv(false);
            wxPayService = new WxPayServiceImpl();
            wxPayService.setConfig(wxPayConfig);
        }
        return wxPayService;
    }

    /**
     * 获取支付类型
     *
     * @param wechatConfig
     * @return WxPayService
     */
    public synchronized WxMpService wxMpService(WechatConfig wechatConfig) {
        String key = CacheConstants.WX_MP_SERVICE_KEY + wechatConfig.getAppId();
        WxMpService wxMpService;
        if (redisCache.hasKey(key)) {
            wxMpService = redisCache.getCacheObject(key);
        } else {
            WxMpDefaultConfigImpl configStorage = new WxMpDefaultConfigImpl();
            configStorage.setAppId(StringUtils.trimToNull(wechatConfig.getAppId()));
            configStorage.setSecret(StringUtils.trimToNull(wechatConfig.getAppSecret()));
            configStorage.setToken(wechatConfig.getToken());
            configStorage.setAesKey(wechatConfig.getAesKey());
            wxMpService = new WxMpServiceImpl();
            wxMpService.setWxMpConfigStorage(configStorage);
            redisCache.setCacheObject(key, wxMpService);
        }
        return wxMpService;
    }

}

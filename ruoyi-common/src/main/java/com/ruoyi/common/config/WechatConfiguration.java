package com.ruoyi.common.config;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.config.impl.WxMaDefaultConfigImpl;
import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import com.google.common.collect.Maps;
import com.ruoyi.common.config.properties.WechatPayProperties;
import com.ruoyi.common.core.domain.entity.WechatConfig;
import com.ruoyi.common.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import me.chanjar.weixin.common.error.WxRuntimeException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;
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
@ConditionalOnClass(WxPayService.class)
@RequiredArgsConstructor
public class WechatConfiguration {
    @Resource
    private WechatPayProperties payProperties;

    private static Map<String, WxPayService> wxPayServicesMap = Maps.newHashMap();

    private static Map<String, WxMpService> wxMpServicesMap = Maps.newHashMap();

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
        //获取集合中的 WxPayService
        if (wechatConfig != null) {
            WxPayService wxPayService = wxPayServicesMap.get(wechatConfig.getAppId());
            //集合中没有则创建
            if (wxPayService == null) {
                WxPayConfig wxPayConfig = new WxPayConfig();
                wxPayConfig.setAppId(wechatConfig.getAppId());
                wxPayConfig.setMchId(StringUtils.trimToNull(wechatConfig.getMchId()));
                wxPayConfig.setMchKey(StringUtils.trimToNull(wechatConfig.getMchKey()));
                wxPayConfig.setKeyPath(StringUtils.trimToNull(wechatConfig.getKeyPath()));
                wxPayService = new WxPayServiceImpl();
                wxPayService.setConfig(wxPayConfig);
                wxPayServicesMap.put(wechatConfig.getAppId(), wxPayService);
                return wxPayService;
            }
            return wxPayService;
        }
        return null;
    }

    /**
     * 获取支付类型
     *
     * @param wechatConfig
     * @return WxPayService
     */
    public synchronized WxMpService wxMpService(WechatConfig wechatConfig) {
        //获取集合中的 WxPayService
        if (wechatConfig != null) {
            WxMpService wxMpService = wxMpServicesMap.get(wechatConfig.getAppId());
            //集合中没有则创建
            if (wxMpService == null) {
                WxMpDefaultConfigImpl mpConfig = new WxMpDefaultConfigImpl();
                mpConfig.setAppId(StringUtils.trimToNull(wechatConfig.getAppId()));
                mpConfig.setSecret(StringUtils.trimToNull(wechatConfig.getAppSecret()));
                mpConfig.setToken(wechatConfig.getToken());
                mpConfig.setAesKey(wechatConfig.getAesKey());
                wxMpService = new WxMpServiceImpl();
                //设置配置文件
                wxMpService.setWxMpConfigStorage(mpConfig);
                wxMpServicesMap.put(wechatConfig.getAppId(), wxMpService);
                return wxMpService;
            }
            return wxMpService;
        }
        return null;
    }

}

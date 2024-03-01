package com.ruoyi.common.config;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.config.impl.WxMaDefaultConfigImpl;
import com.baomidou.mybatisplus.extension.exceptions.ApiException;
import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import com.ruoyi.common.config.properties.WechatPayProperties;
import com.ruoyi.common.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import me.chanjar.weixin.common.error.WxRuntimeException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yangxuemin
 * @ClassName WechatConfigurati¬on
 * @Description
 * @date 2023/12/21 2:47 PM
 */
@Configuration
//引入WxPayService这个类 下面两个才会实例化
@ConditionalOnClass(WxPayService.class)
@RequiredArgsConstructor
public class WechatConfiguration {
    @Autowired
    private WechatPayProperties payProperties;

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
     * 获取WxPayService
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public WxPayService wxPayService() {
        List<WechatPayProperties.MpConfig> configs = this.payProperties.getConfigs();
        if (configs == null) {
            throw new ApiException("没有微信小程序配置啊");
        }
        WxPayService wxPayService = new WxPayServiceImpl();
        this.payProperties.getConfigs().stream().forEach(merchant -> {
            WxPayConfig payConfig = new WxPayConfig();
            payConfig.setAppId(StringUtils.trimToNull(merchant.getAppId()));
            payConfig.setMchId(StringUtils.trimToNull(merchant.getMchId()));
            payConfig.setMchKey(StringUtils.trimToNull(merchant.getMchKey()));
            payConfig.setKeyPath(StringUtils.trimToNull(merchant.getKeyPath()));
            wxPayService.addConfig(merchant.getMchId(), payConfig);
            // 可以指定是否使用沙箱环境
            payConfig.setUseSandboxEnv(false);
        });
        return wxPayService;
    }

    /**
     * 获取公众号WxMpService
     *
     * @return
     */
    @Bean
    public WxMpService wxMpService() {
        // 代码里 getConfigs()处报错的同学，请注意仔细阅读项目说明，你的IDE需要引入lombok插件！！！！
        final List<WechatPayProperties.MpConfig> configs = this.payProperties.getConfigs();
        if (configs == null) {
            throw new RuntimeException("文件加载异常");
        }
        WxMpService service = new WxMpServiceImpl();
        service.setMultiConfigStorages(configs
                .stream().map(a -> {
                    WxMpDefaultConfigImpl configStorage = new WxMpDefaultConfigImpl();
                    configStorage.setAppId(a.getAppId());
                    configStorage.setSecret(a.getSecret());
                    configStorage.setToken(a.getToken());
                    configStorage.setAesKey(a.getAesKey());
                    return configStorage;
                }).collect(Collectors.toMap(WxMpDefaultConfigImpl::getAppId, a -> a, (o, n) -> o)));
        return service;
    }

}

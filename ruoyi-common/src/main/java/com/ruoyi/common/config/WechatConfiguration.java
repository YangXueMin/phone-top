package com.ruoyi.common.config;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.config.impl.WxMaDefaultConfigImpl;
import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import com.google.common.collect.Maps;
import com.ruoyi.common.config.properties.WechatMpProperties;
import com.ruoyi.common.config.properties.WechatPayProperties;
import com.ruoyi.common.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author yangxuemin
 * @ClassName WechatConfiguration
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
    @Autowired
    private WechatMpProperties mpProperties;


    /**
     * 获取小程序WxMaService
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public WxMaService wxMaService() {
        //实例这个 WxMaConfig 设置参数
        WxMaDefaultConfigImpl wxMaConfig = new WxMaDefaultConfigImpl();
        wxMaConfig.setAppid(StringUtils.trimToNull(this.payProperties.getAppId()));
        wxMaConfig.setSecret(StringUtils.trimToNull(this.payProperties.getAppSecret()));
        WxMaService wxMaService = new WxMaServiceImpl();
        //设置配置文件
        wxMaService.setWxMaConfig(wxMaConfig);
        return wxMaService;
    }

    /**
     * 获取WxPayService
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public WxPayService wxPayService() {
        //实例WxPayConfig 设置参数
        WxPayConfig payConfig = new WxPayConfig();
        payConfig.setAppId(StringUtils.trimToNull(this.payProperties.getAppId()));
        payConfig.setMchId(StringUtils.trimToNull(this.payProperties.getMchId()));
        payConfig.setMchKey(StringUtils.trimToNull(this.payProperties.getMchKey()));
        payConfig.setKeyPath(StringUtils.trimToNull(this.payProperties.getKeyPath()));

        // 可以指定是否使用沙箱环境
        payConfig.setUseSandboxEnv(false);

        WxPayService wxPayService = new WxPayServiceImpl();
        //设置配置文件
        wxPayService.setConfig(payConfig);
        return wxPayService;
    }

    /**
     * 获取公众号WxMpService
     *
     * @return
     */
    @Bean
    public WxMpService wxMpService() {
        //实例WxMpConfig 设置参数
        WxMpDefaultConfigImpl mpConfig = new WxMpDefaultConfigImpl();
        mpConfig.setAppId(StringUtils.trimToNull(this.mpProperties.getAppId()));
        mpConfig.setSecret(StringUtils.trimToNull(this.mpProperties.getAppSecret()));

        WxMpService wxMpService = new WxMpServiceImpl();
        //设置配置文件
        wxMpService.setWxMpConfigStorage(mpConfig);
        return wxMpService;
    }
}

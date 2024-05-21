package com.ruoyi.common.config;

import cn.binarywang.wx.miniapp.api.WxMaService;
import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import com.ruoyi.common.annotation.RefreshScope;
import com.ruoyi.common.core.domain.entity.WechatConfig;
import com.ruoyi.common.utils.StringUtils;
import lombok.RequiredArgsConstructor;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
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
public class WechatMultiConfiguration {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public static String sql = "SELECT " +
            " a.id, " +
            " a.dept_id, " +
            " a.title, " +
            " a.app_id, " +
            " a.app_secret, " +
            " a.token, " +
            " a.aes_key, " +
            " a.mch_id, " +
            " a.mch_key, " +
            " a.key_path, " +
            " a.validity_period, " +
            " a.STATUS, " +
            " a.menu_json, " +
            " a.create_by, " +
            " a.create_time, " +
            " a.update_by, " +
            " a.update_time, " +
            " a.remark " +
            "FROM " +
            " phone_wechat_config a";

    @Bean
    @RefreshScope
    public WxMpService wxMpService() {
        // 根据数据库内容来决定Bean的行为
        List<WechatConfig> wechatConfigList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(WechatConfig.class));
        return createWxMpService(wechatConfigList);
    }

    private WxMpService createWxMpService(List<WechatConfig> wechatConfigList) {
        WxMpService service = new WxMpServiceImpl();
        service.setMultiConfigStorages(wechatConfigList
                .stream().map(a -> {
                    WxMpDefaultConfigImpl configStorage = new WxMpDefaultConfigImpl();
                    configStorage.setAppId(a.getAppId());
                    configStorage.setSecret(a.getAppSecret());
                    configStorage.setToken(a.getToken());
                    configStorage.setAesKey(a.getAesKey());
                    return configStorage;
                }).collect(Collectors.toMap(WxMpDefaultConfigImpl::getAppId, a -> a, (o, n) -> o)));
        return service;
    }

    @Bean
    @RefreshScope
    public WxPayService wxPayService() {
        // 根据数据库内容来决定Bean的行为
        List<WechatConfig> wechatConfigList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(WechatConfig.class));
        return createWxPayService(wechatConfigList);
    }

    private WxPayService createWxPayService(List<WechatConfig> wechatConfigList) {
        WxPayService service = new WxPayServiceImpl();
        service.setMultiConfig(wechatConfigList
                .stream().map(a -> {
                    WxPayConfig wxPayConfig = new WxPayConfig();
                    wxPayConfig.setAppId(a.getAppId());
                    wxPayConfig.setMchId(StringUtils.trimToNull(a.getMchId()));
                    wxPayConfig.setMchKey(StringUtils.trimToNull(a.getMchKey()));
                    wxPayConfig.setKeyPath(StringUtils.trimToNull(a.getKeyPath()));
                    // 可以指定是否使用沙箱环境
                    wxPayConfig.setUseSandboxEnv(false);
                    return wxPayConfig;
                }).collect(Collectors.toMap(WxPayConfig::getAppId, a -> a, (o, n) -> o)));
        return service;
    }

}

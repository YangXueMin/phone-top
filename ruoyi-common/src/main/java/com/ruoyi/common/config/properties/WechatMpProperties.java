package com.ruoyi.common.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author yangxuemin
 * @ClassName WechatMpProperties
 * @Description
 * @date 2023/12/21 2:46 PM
 */
@Configuration
@ConfigurationProperties(prefix = "wechat.mp")
public class WechatMpProperties {
    /**
     * 公众号appId
     */
    private String appId;

    /**
     * 公众号应用密钥
     */
    private String appSecret;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }
}

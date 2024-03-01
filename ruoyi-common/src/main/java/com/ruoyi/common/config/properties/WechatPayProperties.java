package com.ruoyi.common.config.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author yangxuemin
 * @ClassName WechatPayProperties
 * @Description
 * @date 2023/12/21 2:48 PM
 */
@Configuration
@ConfigurationProperties(prefix = "wechat.pay")
public class WechatPayProperties {
    /**
     * 是否使用redis存储access token
     */
    private boolean useRedis;

    /**
     * 多个公众号配置信息
     */
    private List<MpConfig> configs;

    @Data
    public static class MpConfig {
        /**
         * 设置微信公众号的appid
         */
        private String appId;

        /**
         * 设置微信公众号的app secret
         */
        private String secret;

        /**
         * 设置微信公众号的token
         */
        private String token;

        /**
         * 设置微信公众号的EncodingAESKey
         */
        private String aesKey;

        /**
         * 微信支付商户号
         */
        @Value("mchId")
        private String mchId;
        /**
         * 微信支付商户密钥
         */
        @Value("mchKey")
        private String mchKey;
        /**
         * 服务商模式下的子商户公众账号ID，普通模式请不要配置，请在配置文件中将对应项删除
         */
        @Value("subAppId")
        private String subAppId;
        /**
         * 服务商模式下的子商户号，普通模式请不要配置，最好是请在配置文件中将对应项删除
         */
        @Value("subMchId")
        private String subMchId;
        /**
         * apiclient_cert.p12文件的绝对路径，或者如果放在项目中，请以classpath:开头指定
         */
        @Value("keyPath")
        private String keyPath;
        /**
         * 支付成功回调地址
         */
        @Value("paymentCallbackUrl")
        private String paymentCallbackUrl;
        /**
         * 退款成功回调地址
         */
        @Value("refundCallbackUrl")
        private String refundCallbackUrl;

        public String getAppId() {
            return appId;
        }

        public void setAppId(String appId) {
            this.appId = appId;
        }

        public String getSecret() {
            return secret;
        }

        public void setSecret(String secret) {
            this.secret = secret;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getAesKey() {
            return aesKey;
        }

        public void setAesKey(String aesKey) {
            this.aesKey = aesKey;
        }

        public String getMchId() {
            return mchId;
        }

        public void setMchId(String mchId) {
            this.mchId = mchId;
        }

        public String getMchKey() {
            return mchKey;
        }

        public void setMchKey(String mchKey) {
            this.mchKey = mchKey;
        }

        public String getSubAppId() {
            return subAppId;
        }

        public void setSubAppId(String subAppId) {
            this.subAppId = subAppId;
        }

        public String getSubMchId() {
            return subMchId;
        }

        public void setSubMchId(String subMchId) {
            this.subMchId = subMchId;
        }

        public String getKeyPath() {
            return keyPath;
        }

        public void setKeyPath(String keyPath) {
            this.keyPath = keyPath;
        }

        public String getPaymentCallbackUrl() {
            return paymentCallbackUrl;
        }

        public void setPaymentCallbackUrl(String paymentCallbackUrl) {
            this.paymentCallbackUrl = paymentCallbackUrl;
        }

        public String getRefundCallbackUrl() {
            return refundCallbackUrl;
        }

        public void setRefundCallbackUrl(String refundCallbackUrl) {
            this.refundCallbackUrl = refundCallbackUrl;
        }
    }

    public boolean isUseRedis() {
        return useRedis;
    }

    public void setUseRedis(boolean useRedis) {
        this.useRedis = useRedis;
    }

    public List<MpConfig> getConfigs() {
        return configs;
    }

    public void setConfigs(List<MpConfig> configs) {
        this.configs = configs;
    }
}

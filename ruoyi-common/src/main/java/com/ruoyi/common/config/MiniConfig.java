package com.ruoyi.common.config;

import cn.binarywang.wx.miniapp.api.*;
import cn.binarywang.wx.miniapp.api.impl.*;
import cn.binarywang.wx.miniapp.config.WxMaInMemoryConfig;
import me.chanjar.weixin.common.error.WxErrorException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author yangxuemin
 * @ClassName MiniConfig
 * @Description
 * @date 2022/10/8 9:00 AM
 */
@Component
public class MiniConfig {
    private static final Long  OUT_TIME=  7100000L;
    @Value("${wechat.miniApp.appid}")
    private String appid;
    @Value("${wechat.miniApp.appsecret}")
    private String appsecret;
    private static final Logger LOG = LoggerFactory.getLogger(MiniConfig.class);
    private String accessToken;
    private WxMaService service;
    private WxMaQrcodeService wxMaQrcodeService;
    private WxMaSecCheckService wxMaSecCheckService;
    private WxMaMsgService wxMaMsgService;
    private WxMaAnalysisService wxMaAnalysisService;
    private long weixinTokenStartTime;


    private void initToken(long refreshTime) {
        LOG.debug("开始初始化access_token........");
        final long oldTime = this.weixinTokenStartTime;
        this.weixinTokenStartTime = refreshTime;

        WxMaInMemoryConfig config = new WxMaInMemoryConfig();
        config.setAppid(appid);
        config.setSecret(appsecret);
        WxMaService service = new WxMaServiceImpl();
        service.setWxMaConfig(config);
        WxMaQrcodeService wxMaCodeService = new WxMaQrcodeServiceImpl(service);
        WxMaMsgService wxMaMsgService = new WxMaMsgServiceImpl(service);
        WxMaAnalysisService wxMaAnalysisService = new WxMaAnalysisServiceImpl(service);
        WxMaSecCheckService wxMaSecCheckService = new WxMaSecCheckServiceImpl(service);
        try {
            this.service = service;
            this.wxMaQrcodeService = wxMaCodeService;
            this.wxMaMsgService = wxMaMsgService;
            this.wxMaSecCheckService=wxMaSecCheckService;
            this.wxMaAnalysisService=wxMaAnalysisService;
            this.accessToken = service.getAccessToken();

        } catch (WxErrorException ex) {
            ex.printStackTrace();
            MiniConfig.LOG.error("accessToken 获取失败");
        }
    }

    public String getAccessToken() {
        long now = System.currentTimeMillis();
        long time = now - this.weixinTokenStartTime;
        if (time >  OUT_TIME || StringUtils.isEmpty(accessToken)) {
            this.initToken(now);
        }
        return this.accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public WxMaService getService() {
        if (service == null || service.getWxMaConfig() == null || StringUtils.isBlank(service.getWxMaConfig().getAppid())) {
            this.initToken(System.currentTimeMillis());
        }
        return this.service;
    }

    public WxMaQrcodeService getWxMaQrcodeService() {
        if (wxMaQrcodeService == null) {
            this.initToken(System.currentTimeMillis());
        }
        return this.wxMaQrcodeService;
    }

    public  WxMaSecCheckService getWxMaSecCheckService(){
        if (wxMaSecCheckService == null) {
            this.initToken(System.currentTimeMillis());
        }
        return this.wxMaSecCheckService;
    }
    public  WxMaAnalysisService getWxMaAnalysisService(){
        if (wxMaAnalysisService == null) {
            this.initToken(System.currentTimeMillis());
        }
        return this.wxMaAnalysisService;
    }

    public WxMaMsgService getWxMaMsgService() {
        if (wxMaMsgService == null) {
            this.initToken(System.currentTimeMillis());
        }
        return wxMaMsgService;
    }

    public void setService(WxMaService service) {
        this.service = service;
    }
}

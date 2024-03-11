package com.ruoyi.phone.controller.common;

import com.alibaba.fastjson2.JSON;
import com.ruoyi.common.config.WechatConfiguration;
import com.ruoyi.common.constant.CacheConstants;
import com.ruoyi.common.core.domain.entity.WechatConfig;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.system.service.IWechatConfigService;
import lombok.AllArgsConstructor;
import me.chanjar.weixin.common.bean.WxJsapiSignature;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

/**
 * jsapi 演示接口的 controller.
 *
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 * @date 2020-04-25
 */
@AllArgsConstructor
@RestController
@RequestMapping("/wx/jsapi/{appid}")
public class WxJsapiController {
    private final WechatConfiguration wechatConfiguration;
    private final IWechatConfigService wechatConfigService;
    private final RedisCache redisCache;

    @GetMapping("/getJsapiTicket")
    public WxJsapiSignature getJsapiTicket(@PathVariable String appid, @RequestParam("url") String url) throws WxErrorException {
        if (redisCache.hasKey(getCacheKey(appid + url))) {
            return JSON.parseObject(redisCache.getCacheObject(getCacheKey(appid + url)).toString(), WxJsapiSignature.class);
        }
        final WechatConfig wechatConfig = wechatConfigService.selectWechatConfigByAppId(appid);
        final WxJsapiSignature jsapiSignature = this.wechatConfiguration.wxMpService(wechatConfig).createJsapiSignature(url);
        //final String jsapiTicket = this.wechatConfiguration.wxMpService(wechatConfig).getJsapiTicket(true);
        redisCache.setCacheObject(getCacheKey(appid + url), JSON.toJSONString(jsapiSignature),1, TimeUnit.HOURS);
        return jsapiSignature;
    }

    /**
     * 设置cache key
     *
     * @param configKey 参数键
     * @return 缓存键key
     */
    private String getCacheKey(String configKey) {
        return CacheConstants.WECHAT_JSAPI_KEY + configKey;
    }
}

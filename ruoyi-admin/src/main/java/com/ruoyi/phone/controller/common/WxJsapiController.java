package com.ruoyi.phone.controller.common;

import com.alibaba.fastjson2.JSON;
import com.ruoyi.common.config.WechatMultiConfiguration;
import com.ruoyi.common.constant.CacheConstants;
import com.ruoyi.common.core.redis.RedisCache;
import lombok.AllArgsConstructor;
import me.chanjar.weixin.common.bean.WxJsapiSignature;
import me.chanjar.weixin.common.error.WxErrorException;
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
    private final WechatMultiConfiguration wechatMultiConfiguration;
    private final RedisCache redisCache;

    @GetMapping("/getJsapiTicket")
    public WxJsapiSignature getJsapiTicket(@PathVariable String appid, @RequestParam("url") String url) throws WxErrorException {
        if (redisCache.hasKey(getCacheKey(appid + url))) {
            return JSON.parseObject(redisCache.getCacheObject(getCacheKey(appid + url)).toString(), WxJsapiSignature.class);
        }
        final WxJsapiSignature jsapiSignature = this.wechatMultiConfiguration.wxMpService().switchoverTo(appid).createJsapiSignature(url);
        redisCache.setCacheObject(getCacheKey(appid + url), JSON.toJSONString(jsapiSignature), 1, TimeUnit.HOURS);
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

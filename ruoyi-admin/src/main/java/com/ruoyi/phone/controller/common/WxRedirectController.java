package com.ruoyi.phone.controller.common;

/**
 * @author ruoyi
 * @ClassName WxRedirectController
 * @Description
 * @date 2024/3/5 2:07 PM
 */

import com.ruoyi.common.config.WechatConfiguration;
import com.ruoyi.common.config.WechatTestConfiguration;
import com.ruoyi.common.constant.CacheConstants;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.WechatConfig;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.system.service.IWechatConfigService;
import lombok.AllArgsConstructor;
import me.chanjar.weixin.common.api.WxConsts;
import org.springframework.web.bind.annotation.*;

/**
 * @author Edward
 */
@AllArgsConstructor
@RestController
@RequestMapping("/wx/redirect/{appid}")
public class WxRedirectController {
    private final WechatTestConfiguration wechatTestConfiguration;

    @GetMapping("/greet")
    public String greetUser(@PathVariable String appid, @RequestParam("url")String url) {
        return this.wechatTestConfiguration.wxMpService().switchoverTo(appid).getOAuth2Service().buildAuthorizationUrl(url, WxConsts.OAuth2Scope.SNSAPI_USERINFO, null);
    }
}
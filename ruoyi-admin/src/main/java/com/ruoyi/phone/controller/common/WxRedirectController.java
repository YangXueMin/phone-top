package com.ruoyi.phone.controller.common;

/**
 * @author ruoyi
 * @ClassName WxRedirectController
 * @Description
 * @date 2024/3/5 2:07 PM
 */

import com.ruoyi.common.config.WechatMultiConfiguration;
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
    private final WechatMultiConfiguration wechatMultiConfiguration;

    @GetMapping("/greet")
    public String greetUser(@PathVariable String appid, @RequestParam("url") String url) {
        return this.wechatMultiConfiguration.wxMpService().switchoverTo(appid).getOAuth2Service().buildAuthorizationUrl(url, WxConsts.OAuth2Scope.SNSAPI_USERINFO, null);
    }
}
package com.ruoyi.phone.controller.common;

import com.ruoyi.common.config.WechatConfiguration;
import com.ruoyi.common.core.domain.entity.WechatConfig;
import com.ruoyi.system.service.IWechatConfigService;
import lombok.AllArgsConstructor;
import me.chanjar.weixin.common.bean.WxJsapiSignature;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/getJsapiTicket")
    public String getJsapiTicket(@PathVariable String appid) throws WxErrorException {
        final WechatConfig wechatConfig = wechatConfigService.selectWechatConfigByAppId(appid);
        final WxJsapiSignature jsapiSignature = this.wechatConfiguration.wxMpService(wechatConfig).createJsapiSignature("111");
        System.out.println(jsapiSignature);
        return this.wechatConfiguration.wxMpService(wechatConfig).getJsapiTicket(true);
    }
}

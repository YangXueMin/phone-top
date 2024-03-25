package com.ruoyi.common.config.builder;

import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutVoiceMessage;

/**
 * @author yangxuemin
 * @ClassName VoiceBuilder
 * @Description 语音消息
 * @date 2024/3/25 9:48 AM
 */
public class VoiceBuilder extends AbstractBuilder {
    @Override
    public WxMpXmlOutMessage build(String content, WxMpXmlMessage wxMessage, WxMpService service) {
        WxMpXmlOutVoiceMessage m = WxMpXmlOutMessage.VOICE().mediaId(content)
                .fromUser(wxMessage.getToUser()).toUser(wxMessage.getFromUser())
                .build();

        return m;
    }
}

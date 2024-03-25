package com.ruoyi.common.config.builder;

import com.alibaba.fastjson2.JSONObject;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMusicMessage;

/**
 * @author yangxuemin
 * @ClassName MusicBuilder
 * @Description 音乐消息
 * @date 2024/3/25 9:50 AM
 */
public class MusicBuilder extends AbstractBuilder {
    @Override
    public WxMpXmlOutMessage build(String content, WxMpXmlMessage wxMessage, WxMpService service) {
        JSONObject jsonObject = JSONObject.parseObject(content);
        WxMpXmlOutMusicMessage m = WxMpXmlOutMessage.MUSIC()
                .thumbMediaId(jsonObject.get("mediaId") != null ? jsonObject.getString("mediaId") : "")
                .musicUrl(jsonObject.get("musicUrl") != null ? jsonObject.getString("musicUrl") : "")
                .hqMusicUrl(jsonObject.get("hqMusicUrl") != null ? jsonObject.getString("hqMusicUrl") : "")
                .fromUser(wxMessage.getToUser()).toUser(wxMessage.getFromUser())
                .build();
        return m;
    }
}

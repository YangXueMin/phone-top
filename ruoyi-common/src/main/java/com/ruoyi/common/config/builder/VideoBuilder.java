package com.ruoyi.common.config.builder;

import com.alibaba.fastjson2.JSONObject;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutVideoMessage;

/**
 * @author yangxuemin
 * @ClassName VideoBuilder
 * @Description 视频消息
 * @date 2024/3/25 9:49 AM
 */
public class VideoBuilder extends AbstractBuilder {
    @Override
    public WxMpXmlOutMessage build(String content, WxMpXmlMessage wxMessage, WxMpService service) {
        JSONObject jsonObject = JSONObject.parseObject(content);
        WxMpXmlOutVideoMessage m = WxMpXmlOutMessage.VIDEO()
                .mediaId(jsonObject.get("mediaId") != null ? jsonObject.getString("mediaId") : "")
                .title(jsonObject.get("title") != null ? jsonObject.getString("title") : "")
                .description(jsonObject.get("description") != null ? jsonObject.getString("description") : "")
                .fromUser(wxMessage.getToUser()).toUser(wxMessage.getFromUser())
                .build();
        return m;
    }
}

package com.ruoyi.phone.handler;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.config.builder.*;
import com.ruoyi.common.core.domain.entity.PhoneWechatMessage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutNewsMessage;
import org.springframework.stereotype.Component;

/**
 * @author yangxuemin
 * @ClassName MessageUtil
 * @Description
 * @date 2024/3/25 2:50 PM
 */
@Component
public class MessageUtil {

    /**
     * 发送消息
     *
     * @param phoneWechatMessage
     * @param wxMpXmlMessage
     * @param wxMpService
     * @return
     */
    public WxMpXmlOutMessage sendMessage(PhoneWechatMessage phoneWechatMessage, WxMpXmlMessage wxMpXmlMessage, WxMpService wxMpService) {
        switch (phoneWechatMessage.getMsgType()) {
            case "text":
                //文本消息
                return new TextBuilder().build(phoneWechatMessage.getContent(), wxMpXmlMessage, wxMpService);
            case "image":
                //图片消息
                return new ImageBuilder().build(phoneWechatMessage.getMediaId(), wxMpXmlMessage, wxMpService);
            case "voice":
                //语音消息
                return new VoiceBuilder().build(phoneWechatMessage.getMediaId(), wxMpXmlMessage, wxMpService);
            case "video":
                //视频消息
                JSONObject videoJson = new JSONObject();
                videoJson.put("mediaId", phoneWechatMessage.getMediaId());
                videoJson.put("title", phoneWechatMessage.getTitle());
                videoJson.put("description", phoneWechatMessage.getDescription());
                return new VideoBuilder().build(videoJson.toString(), wxMpXmlMessage, wxMpService);
            case "music":
                //音乐消息
                JSONObject musicJson = new JSONObject();
                musicJson.put("mediaId", phoneWechatMessage.getMediaId());
                musicJson.put("musicUrl", phoneWechatMessage.getPicUrl());
                musicJson.put("hqMusicUrl", phoneWechatMessage.getHqPicUrl());
                return new MusicBuilder().build(musicJson.toString(), wxMpXmlMessage, wxMpService);
            case "news":
                //图文消息
                WxMpXmlOutNewsMessage.Item item = new WxMpXmlOutNewsMessage.Item();
                item.setPicUrl(phoneWechatMessage.getPicUrl());
                item.setDescription(phoneWechatMessage.getDescription());
                item.setTitle(phoneWechatMessage.getTitle());
                item.setUrl(phoneWechatMessage.getUrl());
                JSONArray array = new JSONArray();
                array.add(item);
                return new NewsBuilder().build(array.toString(), wxMpXmlMessage, wxMpService);
            default:
                return new TextBuilder().build("感谢关注", wxMpXmlMessage, wxMpService);
        }
    }
}

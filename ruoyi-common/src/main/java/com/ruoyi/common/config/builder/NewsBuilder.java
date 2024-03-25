package com.ruoyi.common.config.builder;

import com.alibaba.fastjson2.JSON;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutNewsMessage;

import java.util.List;

/**
 * @author yangxuemin
 * @ClassName NewsBuilder
 * @Description 图文消息
 * @date 2024/3/25 1:55 PM
 */
public class NewsBuilder extends AbstractBuilder {
    @Override
    public WxMpXmlOutMessage build(String content, WxMpXmlMessage wxMessage, WxMpService service) {
        List<WxMpXmlOutNewsMessage.Item> articles = JSON.parseArray(content, WxMpXmlOutNewsMessage.Item.class);
        WxMpXmlOutMessage.NEWS()
                .articles(articles)
                .fromUser(wxMessage.getToUser()).toUser(wxMessage.getFromUser())
                .build();
        return null;
    }
}

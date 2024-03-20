package com.ruoyi.phone.handler;

import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author ruoyi
 * @ClassName StoreCheckNotifyHandler
 * @Description 门店审核事件处理
 * @date 2024/3/1 10:49 AM
 */
@Component
public class StoreCheckNotifyHandler extends AbstractHandler {

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                    Map<String, Object> context, WxMpService wxMpService,
                                    WxSessionManager sessionManager) {
        // TODO 处理门店审核事件
        return null;
    }

}

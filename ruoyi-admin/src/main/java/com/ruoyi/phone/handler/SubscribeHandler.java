package com.ruoyi.phone.handler;

import com.ruoyi.common.config.builder.TextBuilder;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author yangxuemin
 * @ClassName SubscribeHandler
 * @Description 关注事件
 * @date 2024/3/1 10:12 AM
 */
@Component
public class SubscribeHandler extends AbstractHandler{
    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMpXmlMessage, Map<String, Object> map, WxMpService wxMpService, WxSessionManager wxSessionManager) throws WxErrorException {
        this.logger.info("新关注用户 OPENID: " + wxMpXmlMessage.getFromUser());
        // 获取微信用户基本信息
        try {
            WxMpUser userWxInfo = wxMpService.getUserService()
                    .userInfo(wxMpXmlMessage.getFromUser(), null);
            if (userWxInfo != null) {
                // TODO 可以添加关注用户到本地数据库
            }
        } catch (WxErrorException e) {
            if (e.getError().getErrorCode() == 48001) {
                this.logger.info("该公众号没有获取用户信息权限！");
            }
        }
        WxMpXmlOutMessage responseResult = null;
        try {
            responseResult = this.handleSpecial(wxMpXmlMessage);
        } catch (Exception e) {
            this.logger.error(e.getMessage(), e);
        }
        if (responseResult != null) {
            return responseResult;
        }
        try {
            return new TextBuilder().build("感谢关注", wxMpXmlMessage, wxMpService);
        } catch (Exception e) {
            this.logger.error(e.getMessage(), e);
        }

        return null;
    }
    /**
     * 处理特殊请求，比如如果是扫码进来的，可以做相应处理
     */
    private WxMpXmlOutMessage handleSpecial(WxMpXmlMessage wxMessage)
            throws Exception {
        //TODO
        return null;
    }

}

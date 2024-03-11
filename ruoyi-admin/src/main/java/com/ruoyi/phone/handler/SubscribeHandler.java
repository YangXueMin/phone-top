package com.ruoyi.phone.handler;

import com.alibaba.fastjson2.JSON;
import com.ruoyi.common.config.builder.TextBuilder;
import com.ruoyi.common.core.domain.entity.Member;
import com.ruoyi.common.core.domain.entity.WechatConfig;
import com.ruoyi.common.utils.SnowflakeGenerator;
import com.ruoyi.system.service.IMemberService;
import com.ruoyi.system.service.IWechatConfigService;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import me.chanjar.weixin.mp.config.WxMpConfigStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @author yangxuemin
 * @ClassName SubscribeHandler
 * @Description 关注事件
 * @date 2024/3/1 10:12 AM
 */
@Component
public class SubscribeHandler extends AbstractHandler {
    @Autowired
    private IMemberService memberService;
    @Autowired
    private IWechatConfigService wechatConfigService;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMpXmlMessage, Map<String, Object> map, WxMpService wxMpService, WxSessionManager wxSessionManager) throws WxErrorException {
        this.logger.info("新关注用户 OPENID: " + wxMpXmlMessage.getFromUser());

        final WxMpConfigStorage wxMpConfigStorage = wxMpService.getWxMpConfigStorage();
        // 获取微信用户基本信息
        try {
            WechatConfig wechatConfig = wechatConfigService.selectWechatConfigByAppId(wxMpConfigStorage.getAppId());
            WxMpUser userWxInfo = wxMpService.getUserService()
                    .userInfo(wxMpXmlMessage.getFromUser(), null);
            this.logger.info("新关注用户 OPENID: " + JSON.toJSONString(userWxInfo));
            if (userWxInfo != null) {
                // TODO 可以添加关注用户到本地数据库
                String openId = wxMpXmlMessage.getFromUser();
                Member member = memberService.getMemberByOpenId(openId);
                if (member == null) {
                    member = new Member();
                    member.setOpenId(wxMpXmlMessage.getFromUser());
                    //生成唯一会员码
                    SnowflakeGenerator.setDatacenterId(1);
                    SnowflakeGenerator.setMachineId(1);
                    member.setNumber(SnowflakeGenerator.nextId().toString());
                    member.setIsMember("0");
                    member.setIsSuperMember("0");
                    member.setIsBlacklist("0");
                    member.setBalance(BigDecimal.ZERO);
                }
                member.setCompanyId(wechatConfig.getCompanyId());
                member.setAppId(wxMpConfigStorage.getAppId());
                if (member.getId() != null) {
                    memberService.updateMember(member);
                } else {
                    memberService.insertMember(member);
                }
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
        System.out.println(wxMessage.getFromUser());
        Member fromMember = memberService.getMemberByOpenId(wxMessage.getFromUser());

        return null;
    }

}

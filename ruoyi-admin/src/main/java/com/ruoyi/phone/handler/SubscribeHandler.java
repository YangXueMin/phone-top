package com.ruoyi.phone.handler;

import com.alibaba.fastjson2.JSON;
import com.ruoyi.common.config.builder.TextBuilder;
import com.ruoyi.common.core.domain.entity.Member;
import com.ruoyi.common.core.domain.entity.PhoneWechatMessage;
import com.ruoyi.common.core.domain.entity.WechatConfig;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SnowflakeGenerator;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.time.DateUtil;
import com.ruoyi.phone.domain.PhoneCoupon;
import com.ruoyi.phone.domain.PhoneMemberCoupon;
import com.ruoyi.phone.service.IPhoneCouponService;
import com.ruoyi.phone.service.IPhoneMemberCouponService;
import com.ruoyi.system.service.IMemberService;
import com.ruoyi.system.service.IPhoneWechatMessageService;
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
import java.util.List;
import java.util.Map;

/**
 * @author ruoyi
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
    @Autowired
    private IPhoneCouponService phoneCouponService;
    @Autowired
    private IPhoneMemberCouponService phoneMemberCouponService;
    @Autowired
    private MessageUtil messageUtil;
    @Autowired
    private IPhoneWechatMessageService wechatMessageService;

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
                    member.setAncestors("0,");
                    member.setIsSuperMember("0");
                    member.setIsBlacklist("0");
                    member.setBalance(BigDecimal.ZERO);
                    if (StringUtils.equals("subscribe", wxMpXmlMessage.getEvent()) && StringUtils.isNotBlank(wxMpXmlMessage.getEventKey())) {
                        String higherOpenId = wxMpXmlMessage.getEventKey().substring(8);
                        Member higherMember = memberService.getMemberByOpenId(higherOpenId);
                        if (higherMember != null) {
                            member.setMemberId(higherMember.getId());
                            member.setAncestors(higherMember.getAncestors() + higherMember.getId() + ",");
                            //查询是否有优惠券邀请配置
                            PhoneCoupon phoneCoupon = new PhoneCoupon();
                            phoneCoupon.setAppId(higherMember.getAppId());
                            phoneCoupon.setDistributionMode("1");
                            phoneCoupon.setStatus("1");
                            List<PhoneCoupon> phoneCouponList = phoneCouponService.selectPhoneCouponListApi(phoneCoupon);
                            if (phoneCouponList != null && phoneCouponList.size() > 0) {
                                for (PhoneCoupon coupon : phoneCouponList) {
                                    int number = coupon.getNumber() != null ? coupon.getNumber().intValue() : 0;
                                    for (int i = 0; i < number; i++) {
                                        PhoneMemberCoupon phoneMemberCoupon = new PhoneMemberCoupon();
                                        phoneMemberCoupon.setDeptId(higherMember.getDeptId());
                                        phoneMemberCoupon.setAppId(higherMember.getAppId());
                                        phoneMemberCoupon.setMemberId(higherMember.getId());
                                        phoneMemberCoupon.setCouponId(coupon.getId());
                                        phoneMemberCoupon.setExpirationTime(DateUtil.endOfDate(DateUtil.addDays(DateUtils.getNowDate(), coupon.getTermValidity().intValue())));
                                        phoneMemberCoupon.setStatus("1");
                                        phoneMemberCoupon.setCreateTime(DateUtils.getNowDate());
                                        phoneMemberCouponService.insertPhoneMemberCoupon(phoneMemberCoupon);
                                    }
                                }
                            }
                        }
                    }
                }
                member.setDeptId(wechatConfig.getDeptId());
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
            PhoneWechatMessage phoneWechatMessage = new PhoneWechatMessage();
            phoneWechatMessage.setAppId(wxMpConfigStorage.getAppId());
            phoneWechatMessage.setTouchType("1");
            phoneWechatMessage.setStatus("1");
            List<PhoneWechatMessage> messageList = wechatMessageService.selectPhoneWechatMessageList(phoneWechatMessage);
            if(messageList.size() > 0){
                phoneWechatMessage = messageList.get(0);
                return messageUtil.sendMessage(phoneWechatMessage, wxMpXmlMessage, wxMpService);
            }
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

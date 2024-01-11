package com.ruoyi.framework.web.service;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.WxMaUserService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import cn.binarywang.wx.miniapp.util.WxMaConfigHolder;
import com.alibaba.fastjson2.JSON;
import com.ruoyi.common.config.WechatConfiguration;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.Member;
import com.ruoyi.common.core.domain.model.LoginMemberBody;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.exception.user.UserPasswordNotMatchException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.MessageUtils;
import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.common.utils.SnowflakeGenerator;
import com.ruoyi.common.utils.ip.IpUtils;
import com.ruoyi.framework.manager.AsyncManager;
import com.ruoyi.framework.manager.factory.AsyncFactory;
import com.ruoyi.framework.security.authentication.MemberAuthenticationToken;
import com.ruoyi.framework.security.handle.MemberAuthenticationProvider;
import com.ruoyi.shop.service.IMemberService;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author yangxuemin
 * @ClassName MemberLoginService
 * @Description
 * @date 2024/1/4 1:55 PM
 */
@Component
public class MemberLoginService {
    @Autowired
    private TokenService tokenService;
    @Resource
    private MemberAuthenticationProvider authenticationManager;
    @Autowired
    private WechatConfiguration wechatConfiguration;
    @Autowired
    private IMemberService memberService;

    /**
     * 登录
     *
     * @param loginMemberBody
     * @return
     */
    public String memberLogin(LoginMemberBody loginMemberBody) {
        //2.检查用户手机号是否已经注册,若未注册，直接注册成用户
        //调用微信登陆接口登陆成功自动生产token
        WxMaJscode2SessionResult session;
        String openId = null;
        try {
            WxMaService wxMaService = wechatConfiguration.wxMaService();
            WxMaUserService wxMaUserService = wxMaService.getUserService();
            session = wxMaUserService.getSessionInfo(loginMemberBody.getCode());
            openId = session.getOpenid();
        } catch (Exception e) {
            throw new InternalAuthenticationServiceException("获取openId错误.");
        }
        Member member = memberService.getMemberByOpenId(openId);
        if (member == null) {
            member = new Member();
            member.setOpenId(openId);
            member.setAvatar(loginMemberBody.getAvatarUrl());
            member.setName(loginMemberBody.getNickName());
            member.setMobile(loginMemberBody.getMobile());
            member.setIsMember("N");
            //生成唯一会员码
            SnowflakeGenerator.setDatacenterId(1);
            SnowflakeGenerator.setMachineId(1);
            member.setNumber(SnowflakeGenerator.nextId().toString());
            memberService.insertMember(member);
        }
        // 用户验证
        Authentication authentication;
        try {
            // 原来其实就这么一句话：该方法会去调用UserDetailsServiceImpl.loadUserByUsername。指的是原来若依自定义的UserDetailsServiceImpl
            //此处会让人很迷惑，特别是对新手来说。其实就是调用了AppUserDetailsServiceImpl中的loadUserByUsername方法
            //而这个方法的是通过AppAuthenticationProvider中去发起的。所以这个authenticationManager  其实就是注入的AppAuthenticationProvider
            //这个地方一定要注意！！！！！
            authentication = authenticationManager.authenticate(new MemberAuthenticationToken(openId, loginMemberBody));
        } catch (Exception e) {
            if (e instanceof BadCredentialsException) {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(loginMemberBody.getCode(), Constants.LOGIN_FAIL, MessageUtils.message("user.password.not.match")));
                throw new UserPasswordNotMatchException();
            } else {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(loginMemberBody.getCode(), Constants.LOGIN_FAIL, e.getMessage()));
                throw new ServiceException(e.getMessage());
            }
        }
        AsyncManager.me().execute(AsyncFactory.recordLogininfor(loginMemberBody.getCode(), Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success")));
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        recordLoginInfo(loginUser.getUserId());
        // 生成token
        return tokenService.createToken(loginUser);
    }

    /**
     * <pre>
     * 获取用户信息接口
     * </pre>
     */
    public AjaxResult info(String appid, String sessionKey,
                       String signature, String rawData, String encryptedData, String iv) {
        WxMaService wxMaService = wechatConfiguration.wxMaService();
        if (!wxMaService.switchover(appid)) {
            return AjaxResult.error(String.format("未找到对应appid=[%s]的配置，请核实！", appid));
        }

        // 用户信息校验
        if (!wxMaService.getUserService().checkUserInfo(sessionKey, rawData, signature)) {
            WxMaConfigHolder.remove();//清理ThreadLocal
            return AjaxResult.error("user check failed");
        }

        // 解密用户信息
        WxMaUserInfo userInfo = wechatConfiguration.wxMaService().getUserService().getUserInfo(sessionKey, encryptedData, iv);
        WxMaConfigHolder.remove();//清理ThreadLocal
        return AjaxResult.success(userInfo);
    }

    /**
     * <pre>
     * 获取用户绑定手机号信息
     * </pre>
     */
    public AjaxResult phone(String phoneCode) {
        WxMaService wxMaService = wechatConfiguration.wxMaService();
        // 解密
        WxMaPhoneNumberInfo phoneNoInfo = null;
        try {
            phoneNoInfo = wxMaService.getUserService().getPhoneNoInfo(phoneCode);
        } catch (WxErrorException e) {
            return AjaxResult.error("电话解密失败");
        }
        WxMaConfigHolder.remove();//清理ThreadLocal
        return AjaxResult.success(phoneNoInfo);
    }

    /**
     * 记录登录信息
     *
     * @param userId 用户ID
     */
    public void recordLoginInfo(Long userId) {
        Member member = new Member();
        member.setId(userId);
        member.setLoginIp(IpUtils.getIpAddr(ServletUtils.getRequest()));
        member.setLoginDate(DateUtils.getNowDate());
        memberService.updateMember(member);
    }

}

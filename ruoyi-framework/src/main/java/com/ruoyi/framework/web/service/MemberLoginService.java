package com.ruoyi.framework.web.service;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.binarywang.wx.miniapp.util.WxMaConfigHolder;
import com.alibaba.fastjson2.JSON;
import com.ruoyi.common.config.WechatConfiguration;
import com.ruoyi.common.constant.CacheConstants;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.Member;
import com.ruoyi.common.core.domain.entity.WechatConfig;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.core.redis.RedisCache;
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
import com.ruoyi.system.service.IMemberService;
import com.ruoyi.system.service.IWechatConfigService;
import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;
import me.chanjar.weixin.common.bean.oauth2.WxOAuth2AccessToken;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.service.WxOAuth2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

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
    @Autowired
    private IWechatConfigService wechatConfigService;
    @Autowired
    private RedisCache redisCache;

    /**
     * 设置cache key
     *
     * @param configKey 参数键
     * @return 缓存键key
     */
    private String getCacheKey(String configKey) {
        return CacheConstants.WECHAT_ACCESS_TOKEN_KEY + configKey;
    }

    /**
     * 登录
     *
     * @return
     */
    public String memberLogin(String appId, String code, ModelMap map) {
        final WechatConfig wechatConfig = wechatConfigService.selectWechatConfigByAppId(appId);
        //2.检查用户手机号是否已经注册,若未注册，直接注册成用户
        //调用微信登陆接口登陆成功自动生产token
        String openId, wxHeadImg, wxNickName;
        Integer wxSex;
        try {
            WxOAuth2Service oAuth2Service = wechatConfiguration.wxMpService(wechatConfig).getOAuth2Service();
            WxOAuth2AccessToken accessToken;
            if (redisCache.hasKey(getCacheKey(appId))) {
                accessToken = JSON.parseObject(redisCache.getCacheObject(getCacheKey(appId)).toString(), WxOAuth2AccessToken.class);
            } else {
                accessToken = oAuth2Service.getAccessToken(code);
                redisCache.setCacheObject(getCacheKey(appId), JSON.toJSONString(accessToken),1, TimeUnit.HOURS);
            }
            WxOAuth2UserInfo wxMpUser = oAuth2Service.getUserInfo(accessToken, null);
            openId = wxMpUser.getOpenid();
            wxHeadImg = wxMpUser.getHeadImgUrl();
            wxSex = wxMpUser.getSex();
            wxNickName = wxMpUser.getNickname();
        } catch (Exception e) {
            throw new InternalAuthenticationServiceException("获取openId错误.");
        }
        Member member = memberService.getMemberByOpenId(openId);
        if (member == null) {
            member = new Member();
            member.setOpenId(openId);
            //生成唯一会员码
            SnowflakeGenerator.setDatacenterId(1);
            SnowflakeGenerator.setMachineId(1);
            member.setNumber(SnowflakeGenerator.nextId().toString());
        } else {
            if (map.get("mobile") != null) {
                member.setMobile(map.get("mobile").toString());
            }
            member.setIsMember("0");
            member.setIsBlacklist("0");
            member.setBalance(BigDecimal.ZERO);
        }
        member.setCompanyId(wechatConfig.getCompanyId());
        member.setAppId(appId);
        member.setAvatar(wxHeadImg);
        member.setName(wxNickName);
        member.setSex(wxSex + "");
        if (member.getId() != null) {
            memberService.updateMember(member);
        } else {
            memberService.insertMember(member);
        }
        // 用户验证
        Authentication authentication;
        try {
            // 原来其实就这么一句话：该方法会去调用UserDetailsServiceImpl.loadUserByUsername。指的是原来若依自定义的UserDetailsServiceImpl
            //此处会让人很迷惑，特别是对新手来说。其实就是调用了AppUserDetailsServiceImpl中的loadUserByUsername方法
            //而这个方法的是通过AppAuthenticationProvider中去发起的。所以这个authenticationManager  其实就是注入的AppAuthenticationProvider
            //这个地方一定要注意！！！！！
            authentication = authenticationManager.authenticate(new MemberAuthenticationToken(openId, code));
        } catch (Exception e) {
            if (e instanceof BadCredentialsException) {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(code, Constants.LOGIN_FAIL, MessageUtils.message("user.password.not.match")));
                throw new UserPasswordNotMatchException();
            } else {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(code, Constants.LOGIN_FAIL, e.getMessage()));
                throw new ServiceException(e.getMessage());
            }
        }
        AsyncManager.me().execute(AsyncFactory.recordLogininfor(code, Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success")));
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        recordLoginInfo(loginUser.getUserId());
        // 生成token
        return tokenService.createToken(loginUser);
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

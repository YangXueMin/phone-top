package com.ruoyi.framework.security.handle;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.WxMaUserService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import com.ruoyi.common.config.WechatConfiguration;
import com.ruoyi.framework.security.authentication.WxAuthenticationToken;
import com.ruoyi.framework.web.service.SysPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * @author ruoyi
 * @ClassName WxAuthenticationProvider
 * @Description
 * @date 2022/10/7 10:15 PM
 */
@Component
public class WxAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    WechatConfiguration wechatConfiguration;
    @Autowired
    private SysPermissionService sysPermissionService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String code = authentication.getName();
        //调用微信登陆接口登陆成功自动生产token
        WxMaJscode2SessionResult session;
        String openId = null;
        try {
            WxMaService wxMaService = wechatConfiguration.wxMaService();
            WxMaUserService wxMaUserService = wxMaService.getUserService();
            session = wxMaUserService.getSessionInfo(code);
            openId = session.getOpenid();
        } catch (Exception e) {
            throw new InternalAuthenticationServiceException("获取openId错误.");
        }
        return new WxAuthenticationToken(code, openId, Collections.emptyList());
    }

    @Override
    public boolean supports(Class<?> aClass) {
        /**
         * providerManager会遍历所有
         * SecurityConfig中注册的provider集合
         * 根据此方法返回true或false来决定由哪个provider
         * 去校验请求过来的authentication
         */
        return (WxAuthenticationToken.class.isAssignableFrom(aClass));

    }
}

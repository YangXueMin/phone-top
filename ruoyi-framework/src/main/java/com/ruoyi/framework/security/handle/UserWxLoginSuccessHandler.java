package com.ruoyi.framework.security.handle;

import com.alibaba.fastjson2.JSON;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.model.WeChatUser;
import com.ruoyi.common.utils.ServletUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录成功处理类
 *
 * @author CL
 */
@Component
public class UserWxLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) {
        //更新用户表上次登录时间、更新人、更新时间等字段
        WeChatUser weChatUser = (WeChatUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ServletUtils.renderString(response, JSON.toJSONString(AjaxResult.success("登录成功")));
    }
}

package com.ruoyi.framework.security.handle;

import com.ruoyi.framework.security.authentication.MemberAuthenticationToken;
import com.ruoyi.framework.web.service.MemberDetailsServiceImpl;
import com.ruoyi.shop.service.IMemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * @author yangxuemin
 * @ClassName MemberAuthenticationProvider
 * @Description
 * @date 2024/1/4 1:49 PM
 */
@Slf4j
@Component
public class MemberAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private MemberDetailsServiceImpl memberDetailsServiceImpl;
    @Autowired
    private IMemberService memberService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        //这个获取表单输入中返回的用户名，此处获取到的是微信openId
        String openId = authentication.getName();
        UserDetails userInfo = memberDetailsServiceImpl.loadUserByUsername(openId);
        Collection<? extends GrantedAuthority> authorities = userInfo.getAuthorities();
        // 构建返回的用户登录成功的token
        return new MemberAuthenticationToken(userInfo, openId, authorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}

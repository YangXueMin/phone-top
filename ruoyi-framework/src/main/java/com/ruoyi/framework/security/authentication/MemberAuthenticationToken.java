package com.ruoyi.framework.security.authentication;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.util.Assert;

import java.util.Collection;

/**
 * @author ruoyi
 * @ClassName WxAuthenticationToken
 * @Description
 * @date 2023/2/20 1:50 PM
 */
public class MemberAuthenticationToken extends AbstractAuthenticationToken {
    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;
    private final Object principal;
    private Object memberBody;

    public MemberAuthenticationToken(Object principal,Object memberBody) {
        super(null);
        this.principal = principal;
        this.memberBody = memberBody;
        this.setAuthenticated(false);
    }

    public MemberAuthenticationToken(Object principal, Object memberBody, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.memberBody = memberBody;
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return this.memberBody;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        Assert.isTrue(!isAuthenticated, "Cannot set this openId to trusted - use constructor which takes a GrantedAuthority list instead");
        super.setAuthenticated(false);
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
    }
}

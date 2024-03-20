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
public class WxAuthenticationToken extends AbstractAuthenticationToken {
    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;
    private final Object code;
    private Object openId;

    public WxAuthenticationToken(Object code, Object openId) {
        super(null);
        this.code = code;
        this.openId = openId;
        this.setAuthenticated(false);
    }

    public WxAuthenticationToken(Object code, Object openId, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.code = code;
        this.openId = openId;
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return this.openId;
    }

    @Override
    public Object getPrincipal() {
        return this.code;
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

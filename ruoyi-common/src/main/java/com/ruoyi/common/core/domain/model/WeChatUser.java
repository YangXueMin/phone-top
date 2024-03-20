package com.ruoyi.common.core.domain.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * @author ruoyi
 * @ClassName WeChatUser
 * @Description
 * @date 2023/2/20 2:05 PM
 */
public class WeChatUser extends User {
    private String openId;

    public WeChatUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }
}

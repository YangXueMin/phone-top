package com.ruoyi.common.core.domain.model;

import lombok.ToString;

/**
 * @author yangxuemin
 * @ClassName LoginMemberBody
 * @Description
 * @date 2024/1/4 6:15 PM
 */

@ToString
public class LoginMemberBody {
    /**
     * 临时码
     */
    private String code;

    /**
     * 手机号
     */
    private String mobile;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}

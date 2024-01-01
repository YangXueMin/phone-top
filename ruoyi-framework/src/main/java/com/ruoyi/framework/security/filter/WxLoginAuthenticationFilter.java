package com.ruoyi.framework.security.filter;

import com.ruoyi.framework.security.authentication.WxAuthenticationToken;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @author yangxuemin
 * @ClassName WxLoginAuthenticationFilter
 * @Description
 * @date 2022/10/7 10:11 PM
 */
public class WxLoginAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    /**
     * 微信code
     */
    public static final String SPRING_SECURITY_CODE_KEY = "code";
    private static final AntPathRequestMatcher DEFAULT_ANT_PATH_REQUEST_MATCHER = new AntPathRequestMatcher("/login", "POST");
    private String codeParameter = SPRING_SECURITY_CODE_KEY;
    private boolean postOnly = true;
    private static final String METHOD_POST = "POST";

    public WxLoginAuthenticationFilter() {
        super(DEFAULT_ANT_PATH_REQUEST_MATCHER);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (this.postOnly && !METHOD_POST.equals(request.getMethod())) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        } else {
            String code = this.obtainCode(request);
            code = code != null ? code : "";
            code = code.trim();
            WxAuthenticationToken authRequest = new WxAuthenticationToken(code, null);
            this.setDetails(request, authRequest);
            return this.getAuthenticationManager().authenticate(authRequest);
        }
    }


    @Nullable
    protected String obtainCode(HttpServletRequest request) {
        return request.getParameter(this.codeParameter);
    }

    protected void setDetails(HttpServletRequest request, WxAuthenticationToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }

    public void setCodeParameter(String codeParameter) {
        Assert.hasText(codeParameter, "Code参数不能为空或空");
        this.codeParameter = codeParameter;
    }


    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }

    public final String getCodeParameter() {
        return this.codeParameter;
    }

}



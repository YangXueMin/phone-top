package com.ruoyi.framework.security.handle;

import java.io.IOException;
import java.io.Serializable;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson2.JSON;
import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.common.utils.StringUtils;

/**
 * 认证失败处理类 返回未授权
 *
 * @author ruoyi
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint, Serializable {
    private static final long serialVersionUID = -8970718410437077606L;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
            throws IOException {
        int code = HttpStatus.UNAUTHORIZED;
        String msg;
        if (exception instanceof BadCredentialsException) {
            msg = StringUtils.format("请求访问：{}，密码错误", request.getRequestURI());
        } else if (exception instanceof LockedException) {
            msg = StringUtils.format("请求访问：{}，账号被锁定，请联系管理员", request.getRequestURI());
        } else if (exception instanceof CredentialsExpiredException) {
            code = HttpStatus.FORBIDDEN;
            msg = StringUtils.format("请求访问：{}，密码已过期，请联系管理员", request.getRequestURI());
        } else if (exception instanceof AccountExpiredException) {
            code = HttpStatus.FORBIDDEN;
            msg = StringUtils.format("请求访问：{}，账号已过期，请联系管理员", request.getRequestURI());
        } else if (exception instanceof DisabledException) {
            msg = StringUtils.format("请求访问：{}，账号不可用，请联系管理员", request.getRequestURI());
        } else if(exception instanceof UsernameNotFoundException){
            msg = StringUtils.format("请求访问：{}，账号不存在", request.getRequestURI());
        }else {
            msg = StringUtils.format("请求访问：{}，认证失败，无法访问系统资源", request.getRequestURI());
        }
        ServletUtils.renderString(response, JSON.toJSONString(AjaxResult.error(code, msg)));
    }
}

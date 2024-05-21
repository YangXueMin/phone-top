package com.ruoyi.common.annotation;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

import java.lang.annotation.*;

/**
 * @author yangxuemin
 * @ClassName RefreshScope
 * @Description
 * @date 2024/5/20 9:05 PM
 */
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Scope("refresh")
@Documented
public @interface RefreshScope {
    // Scope代理模式之ScopedProxyMode，包含TARGET_CLASS、INTERFACES、DEFAULT、NO
    ScopedProxyMode proxyMode() default ScopedProxyMode.TARGET_CLASS;
}

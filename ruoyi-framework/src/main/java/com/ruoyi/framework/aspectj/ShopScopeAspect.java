package com.ruoyi.framework.aspectj;

import com.ruoyi.common.annotation.ShopScope;
import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * 数据过滤处理
 *
 * @author ruoyi
 */
@Aspect
@Component
public class ShopScopeAspect {

    /**
     * 数据权限过滤关键字
     */
    public static final String SHOP_SCOPE = "shopScope";

    @Before("@annotation(controllerShopScope)")
    public void doBefore(JoinPoint point, ShopScope controllerShopScope) throws Throwable {
        clearDataScope(point);
        handleDataScope(point, controllerShopScope);
    }

    protected void handleDataScope(final JoinPoint joinPoint, ShopScope controllerDataScope) {
        // 获取当前的用户
        LoginUser loginUser = SecurityUtils.getLoginUser();
        if (StringUtils.isNotNull(loginUser)) {
            SysUser currentUser = loginUser.getUser();
            // 如果是超级管理员，则不过滤数据
            if (StringUtils.isNotNull(currentUser) && !currentUser.isAdmin()) {
                dataScopeFilter(joinPoint, currentUser, controllerDataScope.shopAlias());
            }
        }
    }

    /**
     * 数据范围过滤
     *
     * @param joinPoint 切点
     * @param user      用户
     * @param shopAlias 店铺别名
     */
    public static void dataScopeFilter(JoinPoint joinPoint, SysUser user, String shopAlias) {
        StringBuilder sqlString = new StringBuilder();
        if (user.getShopIds().length > 0) {
            sqlString.append(StringUtils.format(
                    " OR {}.shop_id IN ( SELECT shop_id FROM sys_user_shop WHERE user_id = {} ) ", shopAlias, user.getShopIds()));
        }

        if (StringUtils.isNotBlank(sqlString.toString())) {
            Object params = joinPoint.getArgs()[0];
            if (StringUtils.isNotNull(params) && params instanceof BaseEntity) {
                BaseEntity baseEntity = (BaseEntity) params;
                baseEntity.getParams().put(SHOP_SCOPE, " AND (" + sqlString.substring(4) + ")");
            }
        }
    }

    /**
     * 拼接权限sql前先清空params.dataScope参数防止注入
     */
    private void clearDataScope(final JoinPoint joinPoint) {
        Object params = joinPoint.getArgs()[0];
        if (StringUtils.isNotNull(params) && params instanceof BaseEntity) {
            BaseEntity baseEntity = (BaseEntity) params;
            baseEntity.getParams().put(SHOP_SCOPE, "");
        }
    }
}

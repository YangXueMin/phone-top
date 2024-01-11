package com.ruoyi.shop.controller.system;

import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.Member;
import com.ruoyi.common.core.domain.model.LoginMemberBody;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.framework.web.service.MemberLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author yangxuemin
 * @ClassName MemberLoginController
 * @Description
 * @date 2024/1/4 4:50 PM
 */
@RestController
public class MemberLoginController {
    @Autowired
    private MemberLoginService memberLoginService;

    /**
     * 登录方法
     *
     * @param loginMemberBody 登录信息
     * @return 结果
     */
    @PostMapping("memberLogin")
    public AjaxResult memberLogin(@RequestBody LoginMemberBody loginMemberBody) {
        AjaxResult ajax = AjaxResult.success();
        // 生成令牌
        String token = memberLoginService.memberLogin(loginMemberBody);
        ajax.put(Constants.TOKEN, token);
        return ajax;
    }

    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    @GetMapping("getMemberInfo")
    public AjaxResult getInfo() {
        Member member = SecurityUtils.getLoginUser().getMember();
        AjaxResult ajax = AjaxResult.success();
        ajax.put("member", member);
        return ajax;
    }
}

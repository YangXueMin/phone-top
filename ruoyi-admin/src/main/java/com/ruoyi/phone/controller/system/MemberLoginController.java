package com.ruoyi.phone.controller.system;

import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.model.LoginMemberBody;
import com.ruoyi.framework.web.service.MemberLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
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
     * @return 结果
     */
    @PostMapping("memberLogin")
    public AjaxResult memberLogin(@RequestParam String appid, @RequestParam String code, ModelMap map) {
        AjaxResult ajax = AjaxResult.success();
        // 生成令牌
        String token = memberLoginService.memberLogin(appid, code, map);
        ajax.put(Constants.TOKEN, token);
        return ajax;
    }

    /**
     * <pre>
     * 获取用户绑定手机号信息
     * </pre>
     */
    @GetMapping("/api/wechat/phone")
    public AjaxResult phone(@RequestParam("phoneCode") String phoneCode) {
        return memberLoginService.phone(phoneCode);
    }
}

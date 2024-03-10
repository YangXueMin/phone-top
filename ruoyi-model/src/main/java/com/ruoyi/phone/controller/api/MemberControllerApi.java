package com.ruoyi.phone.controller.api;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.Member;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.web.service.TokenService;
import com.ruoyi.system.service.IMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author yangxuemin
 * @ClassName MemberControllerApi
 * @Description
 * @date 2024/1/10 10:33 AM
 */
@Api("会员管理")
@RestController
@RequestMapping("/api/phone/member")
public class MemberControllerApi extends BaseController {
    @Autowired
    private IMemberService memberService;
    @Autowired
    private TokenService tokenService;

    /**
     * 更新会员信息
     */
    @ApiOperation("更新会员信息")
    @PostMapping("/update")
    public AjaxResult update(@RequestBody Member member) {
        if (StringUtils.isNotEmpty(member.getMobile())
                && UserConstants.NOT_UNIQUE.equals(memberService.checkMobileUnique(member))) {
            return error("修改用户'" + member.getName() + "'失败，手机号码已存在");
        }
        if(StringUtils.isNotBlank(member.getPassword())){
            member.setPassword(SecurityUtils.encryptPassword(member.getPassword()));
        }
        final int i = memberService.updateMember(member);
        return success(i);
    }

    /**
     * 会员修改密码
     */
    @Log(title = "会员修改密码", businessType = BusinessType.UPDATE)
    @GetMapping("/updateMemberPwd")
    public AjaxResult updatePwd(String oldPassword, String newPassword) {
        LoginUser loginUser = getLoginUser();
        Long userId = loginUser.getUserId();
        String password = loginUser.getMember().getPassword();
        if (!SecurityUtils.matchesPassword(oldPassword, password)) {
            return error("修改密码失败，旧密码错误");
        }
        if (SecurityUtils.matchesPassword(newPassword, password)) {
            return error("新密码不能与旧密码相同");
        }
        if (memberService.resetMemberPwd(userId, SecurityUtils.encryptPassword(newPassword)) > 0) {
            // 更新缓存用户密码
            loginUser.getMember().setPassword(SecurityUtils.encryptPassword(newPassword));
            tokenService.setLoginUser(loginUser);
            return success();
        }
        return error("修改密码异常，请联系管理员");
    }

    /**
     * 获取会员信息
     */
    @ApiOperation("获取会员信息")
    @PostMapping("/getMemberInfo")
    public AjaxResult getMemberInfo() {
        return success(memberService.getMemberInfo());
    }

}

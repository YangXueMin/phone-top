package com.ruoyi.shop.controller.api;

import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.Member;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.service.IMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yangxuemin
 * @ClassName MemberControllerApi
 * @Description
 * @date 2024/1/10 10:33 AM
 */
@Api("会员管理")
@RestController
@RequestMapping("/api/shop/member")
public class MemberControllerApi extends BaseController {
    @Autowired
    private IMemberService memberService;

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
        final int i = memberService.updateMember(member);
        return success(i);
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

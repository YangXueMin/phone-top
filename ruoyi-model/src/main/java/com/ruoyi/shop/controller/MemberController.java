package com.ruoyi.shop.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.Member;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.service.TokenService;
import com.ruoyi.system.service.IMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 会员管理Controller
 *
 * @author ruoyi
 * @date 2024-01-05
 */
@Api("会员管理")
@RestController
@RequestMapping("/shop/member")
public class MemberController extends BaseController {
    @Autowired
    private IMemberService memberService;
    @Autowired
    private TokenService tokenService;

    /**
     * 查询会员管理列表
     */
    @ApiOperation("查询会员管理列表")
    @PreAuthorize("@ss.hasPermi('shop:member:list')")
    @GetMapping("/list")
    public TableDataInfo list(Member member) {
        startPage();
        List<Member> list = memberService.selectMemberList(member);
        return getDataTable(list);
    }

    /**
     * 导出会员管理列表
     */
    @PreAuthorize("@ss.hasPermi('shop:member:export')")
    @Log(title = "会员管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Member member) {
        List<Member> list = memberService.selectMemberList(member);
        ExcelUtil<Member> util = new ExcelUtil<Member>(Member.class);
        util.exportExcel(response, list, "会员管理数据");
    }

    /**
     * 获取会员管理详细信息
     */
    @ApiOperation("获取会员管理详细信息")
    @ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "Long", paramType = "path", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermi('shop:member:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(memberService.selectMemberById(id));
    }

    /**
     * 新增会员管理
     */
    @ApiOperation("新增会员管理")
    @PreAuthorize("@ss.hasPermi('shop:member:add')")
    @Log(title = "会员管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Member member) {
        return toAjax(memberService.insertMember(member));
    }

    /**
     * 修改会员管理
     */
    @ApiOperation("修改会员管理")
    @PreAuthorize("@ss.hasPermi('shop:member:edit')")
    @Log(title = "会员管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Member member) {
        return toAjax(memberService.updateMember(member));
    }


    /**
     * 重置密码
     */
    @Log(title = "会员重置密码", businessType = BusinessType.UPDATE)
    @PutMapping("/updateMemberPwd")
    public AjaxResult updatePwd(Long id) {
        LoginUser loginUser = getLoginUser();
        String newPassword = "123456";
        if (memberService.resetMemberPwd(id, SecurityUtils.encryptPassword(newPassword)) > 0) {
            // 更新缓存用户密码
            loginUser.getMember().setPassword(SecurityUtils.encryptPassword(newPassword));
            tokenService.setLoginUser(loginUser);
            return success();
        }
        return error("修改密码异常，请联系管理员");
    }

    /**
     * 删除会员管理
     */
    @ApiOperation("删除会员管理")
    @ApiImplicitParam(name = "ids", value = "ID数组", required = true, dataType = "Long[]", paramType = "path", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermi('shop:member:remove')")
    @Log(title = "会员管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(memberService.deleteMemberByIds(ids));
    }
}

package com.ruoyi.phone.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import com.ruoyi.phone.domain.PhoneMemberNow;
import com.ruoyi.phone.service.IPhoneMemberNowService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 用户须知配置Controller
 *
 * @author ruoyi
 * @date 2024-03-04
 */
@Api("用户须知配置")
@RestController
@RequestMapping("/phone/memberNow")
public class PhoneMemberNowController extends BaseController {
    @Autowired
    private IPhoneMemberNowService phoneMemberNowService;

    /**
     * 查询用户须知配置列表
     */
    @ApiOperation("查询用户须知配置列表")
    @PreAuthorize("@ss.hasPermi('phone:memberNow:list')")
    @GetMapping("/list")
    public TableDataInfo list(PhoneMemberNow phoneMemberNow)
    {
        startPage();
        List<PhoneMemberNow> list = phoneMemberNowService.selectPhoneMemberNowList(phoneMemberNow);
        return getDataTable(list);
    }

    /**
     * 导出用户须知配置列表
     */
    @PreAuthorize("@ss.hasPermi('phone:memberNow:export')")
    @Log(title = "用户须知配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PhoneMemberNow phoneMemberNow)
    {
        List<PhoneMemberNow> list = phoneMemberNowService.selectPhoneMemberNowList(phoneMemberNow);
        ExcelUtil<PhoneMemberNow> util = new ExcelUtil<PhoneMemberNow>(PhoneMemberNow.class);
        util.exportExcel(response, list, "用户须知配置数据");
    }

    /**
     * 获取用户须知配置详细信息
     */
    @ApiOperation("获取用户须知配置详细信息")
    @ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "Long", paramType = "path", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermi('phone:memberNow:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(phoneMemberNowService.selectPhoneMemberNowById(id));
    }

    /**
     * 新增用户须知配置
     */
    @ApiOperation("新增用户须知配置")
    @PreAuthorize("@ss.hasPermi('phone:memberNow:add')")
    @Log(title = "用户须知配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PhoneMemberNow phoneMemberNow)
    {
        return toAjax(phoneMemberNowService.insertPhoneMemberNow(phoneMemberNow));
    }

    /**
     * 修改用户须知配置
     */
    @ApiOperation("修改用户须知配置")
    @PreAuthorize("@ss.hasPermi('phone:memberNow:edit')")
    @Log(title = "用户须知配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PhoneMemberNow phoneMemberNow)
    {
        return toAjax(phoneMemberNowService.updatePhoneMemberNow(phoneMemberNow));
    }

    /**
     * 删除用户须知配置
     */
    @ApiOperation("删除用户须知配置")
    @ApiImplicitParam(name = "ids", value = "ID数组", required = true, dataType = "Long[]", paramType = "path", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermi('phone:memberNow:remove')")
    @Log(title = "用户须知配置", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(phoneMemberNowService.deletePhoneMemberNowByIds(ids));
    }
}

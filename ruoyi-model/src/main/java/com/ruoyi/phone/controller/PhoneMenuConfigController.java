package com.ruoyi.phone.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.annotation.DataScope;
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
import com.ruoyi.phone.domain.PhoneMenuConfig;
import com.ruoyi.phone.service.IPhoneMenuConfigService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 小程序菜单配置Controller
 *
 * @author ruoyi
 * @date 2024-03-07
 */
@Api("小程序菜单配置")
@RestController
@RequestMapping("/phone/menuConfig")
public class PhoneMenuConfigController extends BaseController {
    @Autowired
    private IPhoneMenuConfigService phoneMenuConfigService;

    /**
     * 查询小程序菜单配置列表
     */
    @ApiOperation("查询小程序菜单配置列表")
    @PreAuthorize("@ss.hasPermi('phone:menuConfig:list')")
    @DataScope(deptAlias = "d", userAlias = "a")
    @GetMapping("/list")
    public TableDataInfo list(PhoneMenuConfig phoneMenuConfig)
    {
        startPage();
        List<PhoneMenuConfig> list = phoneMenuConfigService.selectPhoneMenuConfigList(phoneMenuConfig);
        return getDataTable(list);
    }

    /**
     * 导出小程序菜单配置列表
     */
    @PreAuthorize("@ss.hasPermi('phone:menuConfig:export')")
    @Log(title = "小程序菜单配置", businessType = BusinessType.EXPORT)
    @DataScope(deptAlias = "d", userAlias = "a")
    @PostMapping("/export")
    public void export(HttpServletResponse response, PhoneMenuConfig phoneMenuConfig)
    {
        List<PhoneMenuConfig> list = phoneMenuConfigService.selectPhoneMenuConfigList(phoneMenuConfig);
        ExcelUtil<PhoneMenuConfig> util = new ExcelUtil<PhoneMenuConfig>(PhoneMenuConfig.class);
        util.exportExcel(response, list, "小程序菜单配置数据");
    }

    /**
     * 获取小程序菜单配置详细信息
     */
    @ApiOperation("获取小程序菜单配置详细信息")
    @ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "Long", paramType = "path", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermi('phone:menuConfig:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(phoneMenuConfigService.selectPhoneMenuConfigById(id));
    }

    /**
     * 新增小程序菜单配置
     */
    @ApiOperation("新增小程序菜单配置")
    @PreAuthorize("@ss.hasPermi('phone:menuConfig:add')")
    @Log(title = "小程序菜单配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PhoneMenuConfig phoneMenuConfig)
    {
        return toAjax(phoneMenuConfigService.insertPhoneMenuConfig(phoneMenuConfig));
    }

    /**
     * 修改小程序菜单配置
     */
    @ApiOperation("修改小程序菜单配置")
    @PreAuthorize("@ss.hasPermi('phone:menuConfig:edit')")
    @Log(title = "小程序菜单配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PhoneMenuConfig phoneMenuConfig)
    {
        return toAjax(phoneMenuConfigService.updatePhoneMenuConfig(phoneMenuConfig));
    }

    /**
     * 删除小程序菜单配置
     */
    @ApiOperation("删除小程序菜单配置")
    @ApiImplicitParam(name = "ids", value = "ID数组", required = true, dataType = "Long[]", paramType = "path", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermi('phone:menuConfig:remove')")
    @Log(title = "小程序菜单配置", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(phoneMenuConfigService.deletePhoneMenuConfigByIds(ids));
    }
}

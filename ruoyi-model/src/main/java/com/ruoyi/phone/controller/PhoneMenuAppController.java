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
import com.ruoyi.phone.domain.PhoneMenuApp;
import com.ruoyi.phone.service.IPhoneMenuAppService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 菜单关联Controller
 *
 * @author ruoyi
 * @date 2024-04-11
 */
@Api("菜单关联")
@RestController
@RequestMapping("/phone/menuApp")
public class PhoneMenuAppController extends BaseController {
    @Autowired
    private IPhoneMenuAppService phoneMenuAppService;

    /**
     * 查询菜单关联列表
     */
    @ApiOperation("查询菜单关联列表")
    @PreAuthorize("@ss.hasPermi('phone:menuApp:list')")
    @GetMapping("/list")
    public TableDataInfo list(PhoneMenuApp phoneMenuApp) {
        startPage();
        List<PhoneMenuApp> list = phoneMenuAppService.selectPhoneMenuAppList(phoneMenuApp);
        return getDataTable(list);
    }

    /**
     * 导出菜单关联列表
     */
    @PreAuthorize("@ss.hasPermi('phone:menuApp:export')")
    @Log(title = "菜单关联", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PhoneMenuApp phoneMenuApp) {
        List<PhoneMenuApp> list = phoneMenuAppService.selectPhoneMenuAppList(phoneMenuApp);
        ExcelUtil<PhoneMenuApp> util = new ExcelUtil<PhoneMenuApp>(PhoneMenuApp.class);
        util.exportExcel(response, list, "菜单关联数据");
    }

    /**
     * 获取菜单关联详细信息
     */
    @ApiOperation("获取菜单关联详细信息")
    @ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "Long", paramType = "path", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermi('phone:menuApp:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(phoneMenuAppService.selectPhoneMenuAppById(id));
    }

    /**
     * 新增菜单关联
     */
    @ApiOperation("新增菜单关联")
    @PreAuthorize("@ss.hasPermi('phone:menuApp:add')")
    @Log(title = "菜单关联", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PhoneMenuApp phoneMenuApp) {
        return toAjax(phoneMenuAppService.insertPhoneMenuApp(phoneMenuApp));
    }

    /**
     * 批量新增菜单关联
     */
    @ApiOperation("批量新增菜单关联")
    @PreAuthorize("@ss.hasPermi('phone:menuApp:add')")
    @Log(title = "菜单关联", businessType = BusinessType.INSERT)
    @PostMapping(value = "saveList")
    public AjaxResult saveList(@RequestBody List<PhoneMenuApp> list) {
        return toAjax(phoneMenuAppService.savePhoneMenuAppList(list));
    }

    /**
     * 修改菜单关联
     */
    @ApiOperation("修改菜单关联")
    @PreAuthorize("@ss.hasPermi('phone:menuApp:edit')")
    @Log(title = "菜单关联", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PhoneMenuApp phoneMenuApp) {
        return toAjax(phoneMenuAppService.updatePhoneMenuApp(phoneMenuApp));
    }

    /**
     * 删除菜单关联
     */
    @ApiOperation("删除菜单关联")
    @ApiImplicitParam(name = "ids", value = "ID数组", required = true, dataType = "Long[]", paramType = "path", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermi('phone:menuApp:remove')")
    @Log(title = "菜单关联", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(phoneMenuAppService.deletePhoneMenuAppByIds(ids));
    }
}

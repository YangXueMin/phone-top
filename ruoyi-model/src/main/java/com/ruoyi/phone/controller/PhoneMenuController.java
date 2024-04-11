package com.ruoyi.phone.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.phone.domain.PhoneMenu;
import com.ruoyi.phone.service.IPhoneMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 菜单管理Controller
 *
 * @author ruoyi
 * @date 2024-04-11
 */
@Api("菜单管理")
@RestController
@RequestMapping("/phone/phoneMenu")
public class PhoneMenuController extends BaseController {
    @Autowired
    private IPhoneMenuService phoneMenuService;

    /**
     * 查询菜单管理列表
     */
    @ApiOperation("查询菜单管理列表")
    @PreAuthorize("@ss.hasPermi('phone:phoneMenu:list')")
    @GetMapping("/list")
    public TableDataInfo list(PhoneMenu phoneMenu) {
        startPage();
        List<PhoneMenu> list = phoneMenuService.selectPhoneMenuList(phoneMenu);
        return getDataTable(list);
    }

    /**
     * 导出菜单管理列表
     */
    @PreAuthorize("@ss.hasPermi('phone:phoneMenu:export')")
    @Log(title = "菜单管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PhoneMenu phoneMenu) {
        List<PhoneMenu> list = phoneMenuService.selectPhoneMenuList(phoneMenu);
        ExcelUtil<PhoneMenu> util = new ExcelUtil<PhoneMenu>(PhoneMenu.class);
        util.exportExcel(response, list, "菜单管理数据");
    }

    /**
     * 获取菜单管理详细信息
     */
    @ApiOperation("获取菜单管理详细信息")
    @ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "Long", paramType = "path", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermi('phone:phoneMenu:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(phoneMenuService.selectPhoneMenuById(id));
    }

    /**
     * 新增菜单管理
     */
    @ApiOperation("新增菜单管理")
    @PreAuthorize("@ss.hasPermi('phone:phoneMenu:add')")
    @Log(title = "菜单管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PhoneMenu phoneMenu) {
        return toAjax(phoneMenuService.insertPhoneMenu(phoneMenu));
    }

    /**
     * 修改菜单管理
     */
    @ApiOperation("修改菜单管理")
    @PreAuthorize("@ss.hasPermi('phone:phoneMenu:edit')")
    @Log(title = "菜单管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PhoneMenu phoneMenu) {
        return toAjax(phoneMenuService.updatePhoneMenu(phoneMenu));
    }

    /**
     * 删除菜单管理
     */
    @ApiOperation("删除菜单管理")
    @ApiImplicitParam(name = "ids", value = "ID数组", required = true, dataType = "Long[]", paramType = "path", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermi('phone:phoneMenu:remove')")
    @Log(title = "菜单管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(phoneMenuService.deletePhoneMenuByIds(ids));
    }
}

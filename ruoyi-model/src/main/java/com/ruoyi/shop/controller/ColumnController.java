package com.ruoyi.shop.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.shop.domain.Column;
import com.ruoyi.shop.service.IColumnService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 栏目设置Controller
 *
 * @author ruoyi
 * @date 2024-01-05
 */
@Api("栏目设置")
@RestController
@RequestMapping("/shop/column")
public class ColumnController extends BaseController {
    @Autowired
    private IColumnService columnService;

    /**
     * 查询栏目设置列表
     */
    @ApiOperation("查询栏目设置列表")
    @PreAuthorize("@ss.hasPermi('shop:column:list')")
    @GetMapping("/list")
    public TableDataInfo list(Column column) {
        startPage();
        List<Column> list = columnService.selectColumnList(column);
        return getDataTable(list);
    }

    /**
     * 导出栏目设置列表
     */
    @PreAuthorize("@ss.hasPermi('shop:column:export')")
    @Log(title = "栏目设置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Column column) {
        List<Column> list = columnService.selectColumnList(column);
        ExcelUtil<Column> util = new ExcelUtil<Column>(Column.class);
        util.exportExcel(response, list, "栏目设置数据");
    }

    /**
     * 获取栏目设置详细信息
     */
    @ApiOperation("获取栏目设置详细信息")
    @ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "Long", paramType = "path", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermi('shop:column:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(columnService.selectColumnById(id));
    }

    /**
     * 新增栏目设置
     */
    @ApiOperation("新增栏目设置")
    @PreAuthorize("@ss.hasPermi('shop:column:add')")
    @Log(title = "栏目设置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Column column) {
        return toAjax(columnService.insertColumn(column));
    }

    /**
     * 修改栏目设置
     */
    @ApiOperation("修改栏目设置")
    @PreAuthorize("@ss.hasPermi('shop:column:edit')")
    @Log(title = "栏目设置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Column column) {
        return toAjax(columnService.updateColumn(column));
    }

    /**
     * 删除栏目设置
     */
    @ApiOperation("删除栏目设置")
    @ApiImplicitParam(name = "ids", value = "ID数组", required = true, dataType = "Long[]", paramType = "path", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermi('shop:column:remove')")
    @Log(title = "栏目设置", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(columnService.deleteColumnByIds(ids));
    }

    /**
     * 获取栏目树列表
     */
    @ApiOperation("获取栏目数列表")
    @PreAuthorize("@ss.hasPermi('shop:column:list')")
    @GetMapping("/columnTree")
    public AjaxResult typeTree(Column column) {
        return success(columnService.selectTreeList(column));
    }
}

package com.ruoyi.shop.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.shop.domain.DayActivity;
import com.ruoyi.shop.service.IDayActivityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 会员日活动Controller
 *
 * @author ruoyi
 * @date 2024-01-12
 */
@Api("会员日活动")
@RestController
@RequestMapping("/shop/dayActivity")
public class DayActivityController extends BaseController {
    @Autowired
    private IDayActivityService dayActivityService;

    /**
     * 查询会员日活动列表
     */
    @ApiOperation("查询会员日活动列表")
    @PreAuthorize("@ss.hasPermi('shop:dayActivity:list')")
    @GetMapping("/list")
    public TableDataInfo list(DayActivity dayActivity) {
        startPage();
        List<DayActivity> list = dayActivityService.selectDayActivityList(dayActivity);
        return getDataTable(list);
    }

    /**
     * 导出会员日活动列表
     */
    @PreAuthorize("@ss.hasPermi('shop:dayActivity:export')")
    @Log(title = "会员日活动", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, DayActivity dayActivity) {
        List<DayActivity> list = dayActivityService.selectDayActivityList(dayActivity);
        ExcelUtil<DayActivity> util = new ExcelUtil<DayActivity>(DayActivity.class);
        util.exportExcel(response, list, "会员日活动数据");
    }

    /**
     * 获取会员日活动详细信息
     */
    @ApiOperation("获取会员日活动详细信息")
    @ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "Long", paramType = "path", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermi('shop:dayActivity:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(dayActivityService.selectDayActivityById(id));
    }

    /**
     * 新增会员日活动
     */
    @ApiOperation("新增会员日活动")
    @PreAuthorize("@ss.hasPermi('shop:dayActivity:add')")
    @Log(title = "会员日活动", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DayActivity dayActivity) {
        return toAjax(dayActivityService.insertDayActivity(dayActivity));
    }

    /**
     * 修改会员日活动
     */
    @ApiOperation("修改会员日活动")
    @PreAuthorize("@ss.hasPermi('shop:dayActivity:edit')")
    @Log(title = "会员日活动", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DayActivity dayActivity) {
        return toAjax(dayActivityService.updateDayActivity(dayActivity));
    }

    /**
     * 删除会员日活动
     */
    @ApiOperation("删除会员日活动")
    @ApiImplicitParam(name = "ids", value = "ID数组", required = true, dataType = "Long[]", paramType = "path", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermi('shop:dayActivity:remove')")
    @Log(title = "会员日活动", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(dayActivityService.deleteDayActivityByIds(ids));
    }
}

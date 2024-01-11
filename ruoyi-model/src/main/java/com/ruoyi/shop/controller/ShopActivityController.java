package com.ruoyi.shop.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.shop.domain.ShopActivity;
import com.ruoyi.shop.service.IShopActivityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 活动管理Controller
 *
 * @author ruoyi
 * @date 2024-01-11
 */
@Api("活动管理")
@RestController
@RequestMapping("/shop/activity")
public class ShopActivityController extends BaseController {
    @Autowired
    private IShopActivityService shopActivityService;

    /**
     * 查询活动管理列表
     */
    @ApiOperation("查询活动管理列表")
    @PreAuthorize("@ss.hasPermi('shop:activity:list')")
    @GetMapping("/list")
    public TableDataInfo list(ShopActivity shopActivity) {
        startPage();
        List<ShopActivity> list = shopActivityService.selectShopActivityList(shopActivity);
        return getDataTable(list);
    }

    /**
     * 导出活动管理列表
     */
    @PreAuthorize("@ss.hasPermi('shop:activity:export')")
    @Log(title = "活动管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ShopActivity shopActivity) {
        List<ShopActivity> list = shopActivityService.selectShopActivityList(shopActivity);
        ExcelUtil<ShopActivity> util = new ExcelUtil<ShopActivity>(ShopActivity.class);
        util.exportExcel(response, list, "活动管理数据");
    }

    /**
     * 获取活动管理详细信息
     */
    @ApiOperation("获取活动管理详细信息")
    @ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "Long", paramType = "path", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermi('shop:activity:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(shopActivityService.selectShopActivityById(id));
    }

    /**
     * 新增活动管理
     */
    @ApiOperation("新增活动管理")
    @PreAuthorize("@ss.hasPermi('shop:activity:add')")
    @Log(title = "活动管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ShopActivity shopActivity) {
        return toAjax(shopActivityService.insertShopActivity(shopActivity));
    }

    /**
     * 修改活动管理
     */
    @ApiOperation("修改活动管理")
    @PreAuthorize("@ss.hasPermi('shop:activity:edit')")
    @Log(title = "活动管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ShopActivity shopActivity) {
        return toAjax(shopActivityService.updateShopActivity(shopActivity));
    }

    /**
     * 删除活动管理
     */
    @ApiOperation("删除活动管理")
    @ApiImplicitParam(name = "ids", value = "ID数组", required = true, dataType = "Long[]", paramType = "path", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermi('shop:activity:remove')")
    @Log(title = "活动管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(shopActivityService.deleteShopActivityByIds(ids));
    }
}

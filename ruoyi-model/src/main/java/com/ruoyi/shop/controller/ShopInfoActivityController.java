package com.ruoyi.shop.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.shop.domain.ShopInfoActivity;
import com.ruoyi.shop.service.IShopInfoActivityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 店铺信息活动配置Controller
 *
 * @author ruoyi
 * @date 2024-01-17
 */
@Api("店铺信息活动配置")
@RestController
@RequestMapping("/shop/infoActivity")
public class ShopInfoActivityController extends BaseController {
    @Autowired
    private IShopInfoActivityService shopInfoActivityService;

    /**
     * 查询店铺信息活动配置列表
     */
    @ApiOperation("查询店铺信息活动配置列表")
    @PreAuthorize("@ss.hasPermi('shop:infoActivity:list')")
    @GetMapping("/list")
    public TableDataInfo list(ShopInfoActivity shopInfoActivity) {
        startPage();
        List<ShopInfoActivity> list = shopInfoActivityService.selectShopInfoActivityList(shopInfoActivity);
        return getDataTable(list);
    }

    /**
     * 导出店铺信息活动配置列表
     */
    @PreAuthorize("@ss.hasPermi('shop:infoActivity:export')")
    @Log(title = "店铺信息活动配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ShopInfoActivity shopInfoActivity) {
        List<ShopInfoActivity> list = shopInfoActivityService.selectShopInfoActivityList(shopInfoActivity);
        ExcelUtil<ShopInfoActivity> util = new ExcelUtil<ShopInfoActivity>(ShopInfoActivity.class);
        util.exportExcel(response, list, "店铺信息活动配置数据");
    }

    /**
     * 获取店铺信息活动配置详细信息
     */
    @ApiOperation("获取店铺信息活动配置详细信息")
    @ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "Long", paramType = "path", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermi('shop:infoActivity:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(shopInfoActivityService.selectShopInfoActivityById(id));
    }

    /**
     * 新增店铺信息活动配置
     */
    @ApiOperation("新增店铺信息活动配置")
    @PreAuthorize("@ss.hasPermi('shop:infoActivity:add')")
    @Log(title = "店铺信息活动配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody List<ShopInfoActivity> shopInfoActivityList) {
        return toAjax(shopInfoActivityService.insertShopInfoActivity(shopInfoActivityList));
    }

    /**
     * 修改店铺信息活动配置
     */
    @ApiOperation("修改店铺信息活动配置")
    @PreAuthorize("@ss.hasPermi('shop:infoActivity:edit')")
    @Log(title = "店铺信息活动配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ShopInfoActivity shopInfoActivity) {
        return toAjax(shopInfoActivityService.updateShopInfoActivity(shopInfoActivity));
    }

    /**
     * 删除店铺信息活动配置
     */
    @ApiOperation("删除店铺信息活动配置")
    @ApiImplicitParam(name = "ids", value = "ID数组", required = true, dataType = "Long[]", paramType = "path", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermi('shop:infoActivity:remove')")
    @Log(title = "店铺信息活动配置", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(shopInfoActivityService.deleteShopInfoActivityByIds(ids));
    }
}

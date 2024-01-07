package com.ruoyi.shop.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.shop.domain.BannerType;
import com.ruoyi.shop.service.IBannerTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * banner类型Controller
 *
 * @author ruoyi
 * @date 2024-01-07
 */
@Api("banner类型")
@RestController
@RequestMapping("/shop/bannerType")
public class BannerTypeController extends BaseController {
    @Autowired
    private IBannerTypeService bannerTypeService;

    /**
     * 查询banner类型列表
     */
    @ApiOperation("查询banner类型列表")
    @PreAuthorize("@ss.hasPermi('shop:bannerType:list')")
    @GetMapping("/list")
    public TableDataInfo list(BannerType bannerType) {
        startPage();
        List<BannerType> list = bannerTypeService.selectBannerTypeList(bannerType);
        return getDataTable(list);
    }

    /**
     * 导出banner类型列表
     */
    @PreAuthorize("@ss.hasPermi('shop:bannerType:export')")
    @Log(title = "banner类型", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BannerType bannerType) {
        List<BannerType> list = bannerTypeService.selectBannerTypeList(bannerType);
        ExcelUtil<BannerType> util = new ExcelUtil<BannerType>(BannerType.class);
        util.exportExcel(response, list, "banner类型数据");
    }

    /**
     * 获取banner类型详细信息
     */
    @ApiOperation("获取banner类型详细信息")
    @ApiImplicitParam(name = "typeId", value = "ID", required = true, dataType = "Long", paramType = "path", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermi('shop:bannerType:query')")
    @GetMapping(value = "/{typeId}")
    public AjaxResult getInfo(@PathVariable("typeId") Long typeId) {
        return success(bannerTypeService.selectBannerTypeByTypeId(typeId));
    }

    /**
     * 新增banner类型
     */
    @ApiOperation("新增banner类型")
    @PreAuthorize("@ss.hasPermi('shop:bannerType:add')")
    @Log(title = "banner类型", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BannerType bannerType) {
        return toAjax(bannerTypeService.insertBannerType(bannerType));
    }

    /**
     * 修改banner类型
     */
    @ApiOperation("修改banner类型")
    @PreAuthorize("@ss.hasPermi('shop:bannerType:edit')")
    @Log(title = "banner类型", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BannerType bannerType) {
        return toAjax(bannerTypeService.updateBannerType(bannerType));
    }

    /**
     * 删除banner类型
     */
    @ApiOperation("删除banner类型")
    @ApiImplicitParam(name = "typeIds", value = "ID数组", required = true, dataType = "Long[]", paramType = "path", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermi('shop:bannerType:remove')")
    @Log(title = "banner类型", businessType = BusinessType.DELETE)
    @DeleteMapping("/{typeIds}")
    public AjaxResult remove(@PathVariable Long[] typeIds) {
        return toAjax(bannerTypeService.deleteBannerTypeByTypeIds(typeIds));
    }

    /**
     * 获取内容分类树列表
     */
    @ApiOperation("获取内容分类树列表")
    @PreAuthorize("@ss.hasPermi('shop:bannerType:list')")
    @GetMapping("/typeTree")
    public AjaxResult typeTree(BannerType bannerType) {
        return success(bannerTypeService.selectTreeList(bannerType));
    }
}

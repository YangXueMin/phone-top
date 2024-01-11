package com.ruoyi.shop.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.shop.domain.ShopActivityGoods;
import com.ruoyi.shop.service.IShopActivityGoodsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 活动商品Controller
 *
 * @author ruoyi
 * @date 2024-01-11
 */
@Api("活动商品")
@RestController
@RequestMapping("/shop/activityGoods")
public class ShopActivityGoodsController extends BaseController {
    @Autowired
    private IShopActivityGoodsService shopActivityGoodsService;

    /**
     * 查询活动商品列表
     */
    @ApiOperation("查询活动商品列表")
    @PreAuthorize("@ss.hasPermi('shop:activityGoods:list')")
    @GetMapping("/list")
    public TableDataInfo list(ShopActivityGoods shopActivityGoods) {
        startPage();
        List<ShopActivityGoods> list = shopActivityGoodsService.selectShopActivityGoodsList(shopActivityGoods);
        return getDataTable(list);
    }

    /**
     * 导出活动商品列表
     */
    @PreAuthorize("@ss.hasPermi('shop:activityGoods:export')")
    @Log(title = "活动商品", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ShopActivityGoods shopActivityGoods) {
        List<ShopActivityGoods> list = shopActivityGoodsService.selectShopActivityGoodsList(shopActivityGoods);
        ExcelUtil<ShopActivityGoods> util = new ExcelUtil<ShopActivityGoods>(ShopActivityGoods.class);
        util.exportExcel(response, list, "活动商品数据");
    }

    /**
     * 获取活动商品详细信息
     */
    @ApiOperation("获取活动商品详细信息")
    @ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "Long", paramType = "path", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermi('shop:activityGoods:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(shopActivityGoodsService.selectShopActivityGoodsById(id));
    }

    /**
     * 新增活动商品
     */
    @ApiOperation("新增活动商品")
    @PreAuthorize("@ss.hasPermi('shop:activityGoods:add')")
    @Log(title = "活动商品", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ShopActivityGoods shopActivityGoods) {
        return toAjax(shopActivityGoodsService.insertShopActivityGoods(shopActivityGoods));
    }

    /**
     * 修改活动商品
     */
    @ApiOperation("修改活动商品")
    @PreAuthorize("@ss.hasPermi('shop:activityGoods:edit')")
    @Log(title = "活动商品", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ShopActivityGoods shopActivityGoods) {
        return toAjax(shopActivityGoodsService.updateShopActivityGoods(shopActivityGoods));
    }

    /**
     * 删除活动商品
     */
    @ApiOperation("删除活动商品")
    @ApiImplicitParam(name = "ids", value = "ID数组", required = true, dataType = "Long[]", paramType = "path", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermi('shop:activityGoods:remove')")
    @Log(title = "活动商品", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(shopActivityGoodsService.deleteShopActivityGoodsByIds(ids));
    }
}

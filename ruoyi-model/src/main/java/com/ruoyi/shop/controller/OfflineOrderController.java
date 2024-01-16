package com.ruoyi.shop.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.shop.domain.OfflineOrder;
import com.ruoyi.shop.service.IOfflineOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 线下订单Controller
 *
 * @author ruoyi
 * @date 2024-01-14
 */
@Api("线下订单")
@RestController
@RequestMapping("/shop/offlineOrder")
public class OfflineOrderController extends BaseController {
    @Autowired
    private IOfflineOrderService offlineOrderService;

    /**
     * 查询线下订单列表
     */
    @ApiOperation("查询线下订单列表")
    @PreAuthorize("@ss.hasPermi('shop:offlineOrder:list')")
    @GetMapping("/list")
    public TableDataInfo list(OfflineOrder offlineOrder) {
        startPage();
        List<OfflineOrder> list = offlineOrderService.selectOfflineOrderList(offlineOrder);
        return getDataTable(list);
    }

    /**
     * 导出线下订单列表
     */
    @PreAuthorize("@ss.hasPermi('shop:offlineOrder:export')")
    @Log(title = "线下订单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, OfflineOrder offlineOrder) {
        List<OfflineOrder> list = offlineOrderService.selectOfflineOrderList(offlineOrder);
        ExcelUtil<OfflineOrder> util = new ExcelUtil<OfflineOrder>(OfflineOrder.class);
        util.exportExcel(response, list, "线下订单数据");
    }

    /**
     * 获取线下订单详细信息
     */
    @ApiOperation("获取线下订单详细信息")
    @ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "Long", paramType = "path", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermi('shop:offlineOrder:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(offlineOrderService.selectOfflineOrderById(id));
    }

    /**
     * 新增线下订单
     */
    @ApiOperation("新增线下订单")
    @PreAuthorize("@ss.hasPermi('shop:offlineOrder:add')")
    @Log(title = "线下订单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody OfflineOrder offlineOrder) {
        return success(offlineOrderService.insertOfflineOrder(offlineOrder));
    }

    /**
     * 修改线下订单
     */
    @ApiOperation("修改线下订单")
    @PreAuthorize("@ss.hasPermi('shop:offlineOrder:edit')")
    @Log(title = "线下订单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody OfflineOrder offlineOrder) {
        return toAjax(offlineOrderService.updateOfflineOrder(offlineOrder));
    }

    /**
     * 删除线下订单
     */
    @ApiOperation("删除线下订单")
    @ApiImplicitParam(name = "ids", value = "ID数组", required = true, dataType = "Long[]", paramType = "path", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermi('shop:offlineOrder:remove')")
    @Log(title = "线下订单", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(offlineOrderService.deleteOfflineOrderByIds(ids));
    }
}

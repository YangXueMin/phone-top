package com.ruoyi.shop.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.shop.domain.Order;
import com.ruoyi.shop.service.IOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 订单记录Controller
 *
 * @author ruoyi
 * @date 2024-01-10
 */
@Api("订单记录")
@RestController
@RequestMapping("/shop/order")
public class OrderController extends BaseController {
    @Autowired
    private IOrderService orderService;

    /**
     * 查询订单记录列表
     */
    @ApiOperation("查询订单记录列表")
    @PreAuthorize("@ss.hasPermi('shop:order:list')")
    @GetMapping("/list")
    public TableDataInfo list(Order order) {
        startPage();
        List<Order> list = orderService.selectOrderList(order);
        return getDataTable(list);
    }

    /**
     * 导出订单记录列表
     */
    @PreAuthorize("@ss.hasPermi('shop:order:export')")
    @Log(title = "订单记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Order order) {
        List<Order> list = orderService.selectOrderList(order);
        ExcelUtil<Order> util = new ExcelUtil<Order>(Order.class);
        util.exportExcel(response, list, "订单记录数据");
    }

    /**
     * 获取订单记录详细信息
     */
    @ApiOperation("获取订单记录详细信息")
    @ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "Long", paramType = "path", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermi('shop:order:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(orderService.selectOrderById(id));
    }

    /**
     * 新增订单记录
     */
    @ApiOperation("新增订单记录")
    @PreAuthorize("@ss.hasPermi('shop:order:add')")
    @Log(title = "订单记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Order order) {
        return toAjax(orderService.insertOrder(order));
    }

    /**
     * 修改订单记录
     */
    @ApiOperation("修改订单记录")
    @PreAuthorize("@ss.hasPermi('shop:order:edit')")
    @Log(title = "订单记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Order order) {
        return toAjax(orderService.updateOrder(order));
    }

    /**
     * 删除订单记录
     */
    @ApiOperation("删除订单记录")
    @ApiImplicitParam(name = "ids", value = "ID数组", required = true, dataType = "Long[]", paramType = "path", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermi('shop:order:remove')")
    @Log(title = "订单记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(orderService.deleteOrderByIds(ids));
    }
}

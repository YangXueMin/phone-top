package com.ruoyi.shop.controller;

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
import com.ruoyi.shop.domain.RechargeOrder;
import com.ruoyi.shop.service.IRechargeOrderService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 充值记录Controller
 *
 * @author ruoyi
 * @date 2024-01-11
 */
@Api("充值记录")
@RestController
@RequestMapping("/shop/rechargeOrder")
public class RechargeOrderController extends BaseController {
    @Autowired
    private IRechargeOrderService rechargeOrderService;

    /**
     * 查询充值记录列表
     */
    @ApiOperation("查询充值记录列表")
    @PreAuthorize("@ss.hasPermi('shop:rechargeOrder:list')")
    @GetMapping("/list")
    public TableDataInfo list(RechargeOrder rechargeOrder) {
        startPage();
        List<RechargeOrder> list = rechargeOrderService.selectRechargeOrderList(rechargeOrder);
        return getDataTable(list);
    }

    /**
     * 导出充值记录列表
     */
    @PreAuthorize("@ss.hasPermi('shop:rechargeOrder:export')")
    @Log(title = "充值记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, RechargeOrder rechargeOrder) {
        List<RechargeOrder> list = rechargeOrderService.selectRechargeOrderList(rechargeOrder);
        ExcelUtil<RechargeOrder> util = new ExcelUtil<RechargeOrder>(RechargeOrder.class);
        util.exportExcel(response, list, "充值记录数据");
    }

    /**
     * 获取充值记录详细信息
     */
    @ApiOperation("获取充值记录详细信息")
    @ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "Long", paramType = "path", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermi('shop:rechargeOrder:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(rechargeOrderService.selectRechargeOrderById(id));
    }

    /**
     * 新增充值记录
     */
    @ApiOperation("新增充值记录")
    @PreAuthorize("@ss.hasPermi('shop:rechargeOrder:add')")
    @Log(title = "充值记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody RechargeOrder rechargeOrder) {
        return success(rechargeOrderService.insertRechargeOrder(rechargeOrder));
    }

    /**
     * 修改充值记录
     */
    @ApiOperation("修改充值记录")
    @PreAuthorize("@ss.hasPermi('shop:rechargeOrder:edit')")
    @Log(title = "充值记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody RechargeOrder rechargeOrder) {
        return toAjax(rechargeOrderService.updateRechargeOrder(rechargeOrder));
    }

    /**
     * 删除充值记录
     */
    @ApiOperation("删除充值记录")
    @ApiImplicitParam(name = "ids", value = "ID数组", required = true, dataType = "Long[]", paramType = "path", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermi('shop:rechargeOrder:remove')")
    @Log(title = "充值记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(rechargeOrderService.deleteRechargeOrderByIds(ids));
    }
}

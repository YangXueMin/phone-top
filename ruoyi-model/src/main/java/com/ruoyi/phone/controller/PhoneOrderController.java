package com.ruoyi.phone.controller;

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
import com.ruoyi.phone.domain.PhoneOrder;
import com.ruoyi.phone.service.IPhoneOrderService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 订单记录Controller
 *
 * @author ruoyi
 * @date 2024-03-05
 */
@Api("订单记录")
@RestController
@RequestMapping("/phone/order")
public class PhoneOrderController extends BaseController {
    @Autowired
    private IPhoneOrderService phoneOrderService;

    /**
     * 查询订单记录列表
     */
    @ApiOperation("查询订单记录列表")
    @PreAuthorize("@ss.hasPermi('phone:order:list')")
    @GetMapping("/list")
    public TableDataInfo list(PhoneOrder phoneOrder)
    {
        startPage();
        List<PhoneOrder> list = phoneOrderService.selectPhoneOrderList(phoneOrder);
        return getDataTable(list);
    }

    /**
     * 导出订单记录列表
     */
    @PreAuthorize("@ss.hasPermi('phone:order:export')")
    @Log(title = "订单记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PhoneOrder phoneOrder)
    {
        List<PhoneOrder> list = phoneOrderService.selectPhoneOrderList(phoneOrder);
        ExcelUtil<PhoneOrder> util = new ExcelUtil<PhoneOrder>(PhoneOrder.class);
        util.exportExcel(response, list, "订单记录数据");
    }

    /**
     * 获取订单记录详细信息
     */
    @ApiOperation("获取订单记录详细信息")
    @ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "Long", paramType = "path", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermi('phone:order:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(phoneOrderService.selectPhoneOrderById(id));
    }

    /**
     * 新增订单记录
     */
    @ApiOperation("新增订单记录")
    @PreAuthorize("@ss.hasPermi('phone:order:add')")
    @Log(title = "订单记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PhoneOrder phoneOrder)
    {
        return toAjax(phoneOrderService.insertPhoneOrder(phoneOrder));
    }

    /**
     * 修改订单记录
     */
    @ApiOperation("修改订单记录")
    @PreAuthorize("@ss.hasPermi('phone:order:edit')")
    @Log(title = "订单记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PhoneOrder phoneOrder)
    {
        return toAjax(phoneOrderService.updatePhoneOrder(phoneOrder));
    }

    /**
     * 删除订单记录
     */
    @ApiOperation("删除订单记录")
    @ApiImplicitParam(name = "ids", value = "ID数组", required = true, dataType = "Long[]", paramType = "path", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermi('phone:order:remove')")
    @Log(title = "订单记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(phoneOrderService.deletePhoneOrderByIds(ids));
    }
}

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
import com.ruoyi.phone.domain.PhoneCustomer;
import com.ruoyi.phone.service.IPhoneCustomerService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 客服配置Controller
 *
 * @author ruoyi
 * @date 2024-03-04
 */
@Api("客服配置")
@RestController
@RequestMapping("/phone/customer")
public class PhoneCustomerController extends BaseController {
    @Autowired
    private IPhoneCustomerService phoneCustomerService;

    /**
     * 查询客服配置列表
     */
    @ApiOperation("查询客服配置列表")
    @GetMapping("/list")
    public TableDataInfo list(PhoneCustomer phoneCustomer)
    {
        startPage();
        List<PhoneCustomer> list = phoneCustomerService.selectPhoneCustomerList(phoneCustomer);
        return getDataTable(list);
    }

    /**
     * 导出客服配置列表
     */
    @PreAuthorize("@ss.hasPermi('phone:customer:export')")
    @Log(title = "客服配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PhoneCustomer phoneCustomer)
    {
        List<PhoneCustomer> list = phoneCustomerService.selectPhoneCustomerList(phoneCustomer);
        ExcelUtil<PhoneCustomer> util = new ExcelUtil<PhoneCustomer>(PhoneCustomer.class);
        util.exportExcel(response, list, "客服配置数据");
    }

    /**
     * 获取客服配置详细信息
     */
    @ApiOperation("获取客服配置详细信息")
    @ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "Long", paramType = "path", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermi('phone:customer:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(phoneCustomerService.selectPhoneCustomerById(id));
    }

    /**
     * 新增客服配置
     */
    @ApiOperation("新增客服配置")
    @PreAuthorize("@ss.hasPermi('phone:customer:add')")
    @Log(title = "客服配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PhoneCustomer phoneCustomer)
    {
        return toAjax(phoneCustomerService.insertPhoneCustomer(phoneCustomer));
    }

    /**
     * 修改客服配置
     */
    @ApiOperation("修改客服配置")
    @PreAuthorize("@ss.hasPermi('phone:customer:edit')")
    @Log(title = "客服配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PhoneCustomer phoneCustomer)
    {
        return toAjax(phoneCustomerService.updatePhoneCustomer(phoneCustomer));
    }

    /**
     * 删除客服配置
     */
    @ApiOperation("删除客服配置")
    @ApiImplicitParam(name = "ids", value = "ID数组", required = true, dataType = "Long[]", paramType = "path", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermi('phone:customer:remove')")
    @Log(title = "客服配置", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(phoneCustomerService.deletePhoneCustomerByIds(ids));
    }
}

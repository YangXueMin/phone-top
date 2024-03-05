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
import com.ruoyi.phone.domain.PhonePrice;
import com.ruoyi.phone.service.IPhonePriceService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 价格配置Controller
 *
 * @author ruoyi
 * @date 2024-03-05
 */
@Api("价格配置")
@RestController
@RequestMapping("/phone/price")
public class PhonePriceController extends BaseController {
    @Autowired
    private IPhonePriceService phonePriceService;

    /**
     * 查询价格配置列表
     */
    @ApiOperation("查询价格配置列表")
    @PreAuthorize("@ss.hasPermi('phone:price:list')")
    @GetMapping("/list")
    public TableDataInfo list(PhonePrice phonePrice)
    {
        startPage();
        List<PhonePrice> list = phonePriceService.selectPhonePriceList(phonePrice);
        return getDataTable(list);
    }

    /**
     * 导出价格配置列表
     */
    @PreAuthorize("@ss.hasPermi('phone:price:export')")
    @Log(title = "价格配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PhonePrice phonePrice)
    {
        List<PhonePrice> list = phonePriceService.selectPhonePriceList(phonePrice);
        ExcelUtil<PhonePrice> util = new ExcelUtil<PhonePrice>(PhonePrice.class);
        util.exportExcel(response, list, "价格配置数据");
    }

    /**
     * 获取价格配置详细信息
     */
    @ApiOperation("获取价格配置详细信息")
    @ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "Long", paramType = "path", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermi('phone:price:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(phonePriceService.selectPhonePriceById(id));
    }

    /**
     * 新增价格配置
     */
    @ApiOperation("新增价格配置")
    @PreAuthorize("@ss.hasPermi('phone:price:add')")
    @Log(title = "价格配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PhonePrice phonePrice)
    {
        return toAjax(phonePriceService.insertPhonePrice(phonePrice));
    }

    /**
     * 修改价格配置
     */
    @ApiOperation("修改价格配置")
    @PreAuthorize("@ss.hasPermi('phone:price:edit')")
    @Log(title = "价格配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PhonePrice phonePrice)
    {
        return toAjax(phonePriceService.updatePhonePrice(phonePrice));
    }

    /**
     * 删除价格配置
     */
    @ApiOperation("删除价格配置")
    @ApiImplicitParam(name = "ids", value = "ID数组", required = true, dataType = "Long[]", paramType = "path", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermi('phone:price:remove')")
    @Log(title = "价格配置", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(phonePriceService.deletePhonePriceByIds(ids));
    }
}

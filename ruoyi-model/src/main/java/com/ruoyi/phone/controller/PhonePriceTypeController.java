package com.ruoyi.phone.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.annotation.DataScope;
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
import com.ruoyi.phone.domain.PhonePriceType;
import com.ruoyi.phone.service.IPhonePriceTypeService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 价格类型Controller
 *
 * @author ruoyi
 * @date 2024-03-08
 */
@Api("价格类型")
@RestController
@RequestMapping("/phone/priceType")
public class PhonePriceTypeController extends BaseController {
    @Autowired
    private IPhonePriceTypeService phonePriceTypeService;

    /**
     * 查询价格类型列表
     */
    @ApiOperation("查询价格类型列表")
    @PreAuthorize("@ss.hasPermi('phone:priceType:list')")
    @DataScope(deptAlias = "d", userAlias = "a")
    @GetMapping("/list")
    public TableDataInfo list(PhonePriceType phonePriceType)
    {
        startPage();
        List<PhonePriceType> list = phonePriceTypeService.selectPhonePriceTypeList(phonePriceType);
        return getDataTable(list);
    }

    /**
     * 导出价格类型列表
     */
    @PreAuthorize("@ss.hasPermi('phone:priceType:export')")
    @Log(title = "价格类型", businessType = BusinessType.EXPORT)
    @DataScope(deptAlias = "d", userAlias = "a")
    @PostMapping("/export")
    public void export(HttpServletResponse response, PhonePriceType phonePriceType)
    {
        List<PhonePriceType> list = phonePriceTypeService.selectPhonePriceTypeList(phonePriceType);
        ExcelUtil<PhonePriceType> util = new ExcelUtil<PhonePriceType>(PhonePriceType.class);
        util.exportExcel(response, list, "价格类型数据");
    }

    /**
     * 获取价格类型详细信息
     */
    @ApiOperation("获取价格类型详细信息")
    @ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "Long", paramType = "path", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermi('phone:priceType:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(phonePriceTypeService.selectPhonePriceTypeById(id));
    }

    /**
     * 新增价格类型
     */
    @ApiOperation("新增价格类型")
    @PreAuthorize("@ss.hasPermi('phone:priceType:add')")
    @Log(title = "价格类型", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PhonePriceType phonePriceType)
    {
        return toAjax(phonePriceTypeService.insertPhonePriceType(phonePriceType));
    }

    /**
     * 修改价格类型
     */
    @ApiOperation("修改价格类型")
    @PreAuthorize("@ss.hasPermi('phone:priceType:edit')")
    @Log(title = "价格类型", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PhonePriceType phonePriceType)
    {
        return toAjax(phonePriceTypeService.updatePhonePriceType(phonePriceType));
    }

    /**
     * 删除价格类型
     */
    @ApiOperation("删除价格类型")
    @ApiImplicitParam(name = "ids", value = "ID数组", required = true, dataType = "Long[]", paramType = "path", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermi('phone:priceType:remove')")
    @Log(title = "价格类型", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(phonePriceTypeService.deletePhonePriceTypeByIds(ids));
    }
}

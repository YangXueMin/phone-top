package com.ruoyi.shop.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.shop.domain.CompanyGoodsSpecs;
import com.ruoyi.shop.service.ICompanyGoodsSpecsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 企业商品规格Controller
 *
 * @author ruoyi
 * @date 2024-01-06
 */
@Api("企业商品规格")
@RestController
@RequestMapping("/shop/companySpecs")
public class CompanyGoodsSpecsController extends BaseController {
    @Autowired
    private ICompanyGoodsSpecsService companyGoodsSpecsService;

    /**
     * 查询企业商品规格列表
     */
    @ApiOperation("查询企业商品规格列表")
    @PreAuthorize("@ss.hasPermi('shop:companySpecs:list')")
    @GetMapping("/list")
    public TableDataInfo list(CompanyGoodsSpecs companyGoodsSpecs) {
        startPage();
        List<CompanyGoodsSpecs> list = companyGoodsSpecsService.selectCompanyGoodsSpecsList(companyGoodsSpecs);
        return getDataTable(list);
    }

    /**
     * 导出企业商品规格列表
     */
    @PreAuthorize("@ss.hasPermi('shop:companySpecs:export')")
    @Log(title = "企业商品规格", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CompanyGoodsSpecs companyGoodsSpecs) {
        List<CompanyGoodsSpecs> list = companyGoodsSpecsService.selectCompanyGoodsSpecsList(companyGoodsSpecs);
        ExcelUtil<CompanyGoodsSpecs> util = new ExcelUtil<>(CompanyGoodsSpecs.class);
        util.exportExcel(response, list, "企业商品规格数据");
    }

    /**
     * 获取企业商品规格详细信息
     */
    @ApiOperation("获取企业商品规格详细信息")
    @ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "Long", paramType = "path", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermi('shop:companySpecs:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(companyGoodsSpecsService.selectCompanyGoodsSpecsById(id));
    }

    /**
     * 新增企业商品规格
     */
    @ApiOperation("新增企业商品规格")
    @PreAuthorize("@ss.hasPermi('shop:companySpecs:add')")
    @Log(title = "企业商品规格", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CompanyGoodsSpecs companyGoodsSpecs) {
        return toAjax(companyGoodsSpecsService.insertCompanyGoodsSpecs(companyGoodsSpecs));
    }

    /**
     * 修改企业商品规格
     */
    @ApiOperation("修改企业商品规格")
    @PreAuthorize("@ss.hasPermi('shop:companySpecs:edit')")
    @Log(title = "企业商品规格", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CompanyGoodsSpecs companyGoodsSpecs) {
        return toAjax(companyGoodsSpecsService.updateCompanyGoodsSpecs(companyGoodsSpecs));
    }

    /**
     * 删除企业商品规格
     */
    @ApiOperation("删除企业商品规格")
    @ApiImplicitParam(name = "ids", value = "ID数组", required = true, dataType = "Long[]", paramType = "path", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermi('shop:companySpecs:remove')")
    @Log(title = "企业商品规格", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(companyGoodsSpecsService.deleteCompanyGoodsSpecsByIds(ids));
    }
}

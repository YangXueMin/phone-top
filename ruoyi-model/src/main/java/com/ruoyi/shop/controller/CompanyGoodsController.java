package com.ruoyi.shop.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.shop.domain.CompanyGoods;
import com.ruoyi.shop.domain.Goods;
import com.ruoyi.shop.service.ICompanyGoodsService;
import com.ruoyi.shop.service.IGoodsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 企业商品Controller
 *
 * @author ruoyi
 * @date 2024-01-06
 */
@Api("企业商品")
@RestController
@RequestMapping("/shop/companyGoods")
public class CompanyGoodsController extends BaseController {
    @Autowired
    private ICompanyGoodsService companyGoodsService;

    /**
     * 查询企业商品列表
     */
    @ApiOperation("查询企业商品列表")
    @PreAuthorize("@ss.hasPermi('shop:companyGoods:list')")
    @GetMapping("/list")
    public TableDataInfo list(CompanyGoods companyGoods)
    {
        startPage();
        List<CompanyGoods> list = companyGoodsService.selectCompanyGoodsList(companyGoods);
        return getDataTable(list);
    }

    /**
     * 导出企业商品列表
     */
    @PreAuthorize("@ss.hasPermi('shop:companyGoods:export')")
    @Log(title = "企业商品", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CompanyGoods companyGoods)
    {
        List<CompanyGoods> list = companyGoodsService.selectCompanyGoodsList(companyGoods);
        ExcelUtil<CompanyGoods> util = new ExcelUtil<>(CompanyGoods.class);
        util.exportExcel(response, list, "企业商品数据");
    }

    /**
     * 获取企业商品详细信息
     */
    @ApiOperation("获取企业商品详细信息")
    @ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "Long", paramType = "path", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermi('shop:companyGoods:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(companyGoodsService.selectCompanyGoodsById(id));
    }

    /**
     * 新增企业商品
     */
    @ApiOperation("新增企业商品")
    @PreAuthorize("@ss.hasPermi('shop:companyGoods:add')")
    @Log(title = "企业商品", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CompanyGoods companyGoods)
    {
        return toAjax(companyGoodsService.insertCompanyGoods(companyGoods));
    }

    /**
     * 修改企业商品
     */
    @ApiOperation("修改企业商品")
    @PreAuthorize("@ss.hasPermi('shop:companyGoods:edit')")
    @Log(title = "企业商品", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CompanyGoods companyGoods)
    {
        return toAjax(companyGoodsService.updateCompanyGoods(companyGoods));
    }

    /**
     * 删除企业商品
     */
    @ApiOperation("删除企业商品")
    @ApiImplicitParam(name = "ids", value = "ID数组", required = true, dataType = "Long[]", paramType = "path", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermi('shop:companyGoods:remove')")
    @Log(title = "企业商品", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(companyGoodsService.deleteCompanyGoodsByIds(ids));
    }
}

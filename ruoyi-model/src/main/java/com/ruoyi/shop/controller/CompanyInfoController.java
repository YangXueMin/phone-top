package com.ruoyi.shop.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.shop.domain.CompanyInfo;
import com.ruoyi.shop.service.ICompanyInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 企业信息配置Controller
 *
 * @author ruoyi
 * @date 2024-01-05
 */
@Api("企业信息配置")
@RestController
@RequestMapping("/shop/companyInfo")
public class CompanyInfoController extends BaseController {
    @Autowired
    private ICompanyInfoService companyInfoService;

    /**
     * 查询企业信息配置列表
     */
    @ApiOperation("查询企业信息配置列表")
    @PreAuthorize("@ss.hasPermi('shop:companyInfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(CompanyInfo companyInfo) {
        startPage();
        List<CompanyInfo> list = companyInfoService.selectCompanyInfoList(companyInfo);
        return getDataTable(list);
    }

    /**
     * 导出企业信息配置列表
     */
    @PreAuthorize("@ss.hasPermi('shop:companyInfo:export')")
    @Log(title = "企业信息配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CompanyInfo companyInfo) {
        List<CompanyInfo> list = companyInfoService.selectCompanyInfoList(companyInfo);
        ExcelUtil<CompanyInfo> util = new ExcelUtil<CompanyInfo>(CompanyInfo.class);
        util.exportExcel(response, list, "企业信息配置数据");
    }

    /**
     * 获取企业信息配置详细信息
     */
    @ApiOperation("获取企业信息配置详细信息")
    @ApiImplicitParam(name = "companyId", value = "企业ID", required = true, dataType = "Long", paramType = "path", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermi('shop:companyInfo:query')")
    @GetMapping(value = "/{companyId}")
    public AjaxResult getInfo(@PathVariable("companyId") Long companyId) {
        return success(companyInfoService.selectCompanyInfoByCompanyId(companyId));
    }

    /**
     * 新增企业信息配置
     */
    @ApiOperation("新增企业信息配置")
    @PreAuthorize("@ss.hasPermi('shop:companyInfo:add')")
    @Log(title = "企业信息配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CompanyInfo companyInfo) {
        return toAjax(companyInfoService.insertCompanyInfo(companyInfo));
    }

    /**
     * 修改企业信息配置
     */
    @ApiOperation("修改企业信息配置")
    @PreAuthorize("@ss.hasPermi('shop:companyInfo:edit')")
    @Log(title = "企业信息配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CompanyInfo companyInfo) {
        return toAjax(companyInfoService.updateCompanyInfo(companyInfo));
    }

    /**
     * 删除企业信息配置
     */
    @ApiOperation("删除企业信息配置")
    @ApiImplicitParam(name = "ids", value = "ID数组", required = true, dataType = "Long[]", paramType = "path", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermi('shop:companyInfo:remove')")
    @Log(title = "企业信息配置", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(companyInfoService.deleteCompanyInfoByIds(ids));
    }
}

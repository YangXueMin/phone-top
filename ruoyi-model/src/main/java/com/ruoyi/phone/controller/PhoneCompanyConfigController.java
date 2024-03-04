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
import com.ruoyi.phone.domain.PhoneCompanyConfig;
import com.ruoyi.phone.service.IPhoneCompanyConfigService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 企业配置Controller
 *
 * @author ruoyi
 * @date 2024-03-04
 */
@Api("企业配置")
@RestController
@RequestMapping("/phone/companyConfig")
public class PhoneCompanyConfigController extends BaseController {
    @Autowired
    private IPhoneCompanyConfigService phoneCompanyConfigService;

    /**
     * 查询企业配置列表
     */
    @ApiOperation("查询企业配置列表")
    @PreAuthorize("@ss.hasPermi('phone:companyConfig:list')")
    @GetMapping("/list")
    public TableDataInfo list(PhoneCompanyConfig phoneCompanyConfig)
    {
        startPage();
        List<PhoneCompanyConfig> list = phoneCompanyConfigService.selectPhoneCompanyConfigList(phoneCompanyConfig);
        return getDataTable(list);
    }

    /**
     * 导出企业配置列表
     */
    @PreAuthorize("@ss.hasPermi('phone:companyConfig:export')")
    @Log(title = "企业配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PhoneCompanyConfig phoneCompanyConfig)
    {
        List<PhoneCompanyConfig> list = phoneCompanyConfigService.selectPhoneCompanyConfigList(phoneCompanyConfig);
        ExcelUtil<PhoneCompanyConfig> util = new ExcelUtil<PhoneCompanyConfig>(PhoneCompanyConfig.class);
        util.exportExcel(response, list, "企业配置数据");
    }

    /**
     * 获取企业配置详细信息
     */
    @ApiOperation("获取企业配置详细信息")
    @ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "Long", paramType = "path", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermi('phone:companyConfig:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(phoneCompanyConfigService.selectPhoneCompanyConfigById(id));
    }

    /**
     * 新增企业配置
     */
    @ApiOperation("新增企业配置")
    @PreAuthorize("@ss.hasPermi('phone:companyConfig:add')")
    @Log(title = "企业配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PhoneCompanyConfig phoneCompanyConfig)
    {
        return toAjax(phoneCompanyConfigService.insertPhoneCompanyConfig(phoneCompanyConfig));
    }

    /**
     * 修改企业配置
     */
    @ApiOperation("修改企业配置")
    @PreAuthorize("@ss.hasPermi('phone:companyConfig:edit')")
    @Log(title = "企业配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PhoneCompanyConfig phoneCompanyConfig)
    {
        return toAjax(phoneCompanyConfigService.updatePhoneCompanyConfig(phoneCompanyConfig));
    }

    /**
     * 删除企业配置
     */
    @ApiOperation("删除企业配置")
    @ApiImplicitParam(name = "ids", value = "ID数组", required = true, dataType = "Long[]", paramType = "path", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermi('phone:companyConfig:remove')")
    @Log(title = "企业配置", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(phoneCompanyConfigService.deletePhoneCompanyConfigByIds(ids));
    }
}

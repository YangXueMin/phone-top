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
import com.ruoyi.phone.domain.PhoneInterfaceConfig;
import com.ruoyi.phone.service.IPhoneInterfaceConfigService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 接口地址配置Controller
 *
 * @author ruoyi
 * @date 2024-03-07
 */
@Api("接口地址配置")
@RestController
@RequestMapping("/phone/interfaceConfig")
public class PhoneInterfaceConfigController extends BaseController {
    @Autowired
    private IPhoneInterfaceConfigService phoneInterfaceConfigService;

    /**
     * 查询接口地址配置列表
     */
    @ApiOperation("查询接口地址配置列表")
    @PreAuthorize("@ss.hasPermi('phone:interfaceConfig:list')")
    @GetMapping("/list")
    public TableDataInfo list(PhoneInterfaceConfig phoneInterfaceConfig)
    {
        startPage();
        List<PhoneInterfaceConfig> list = phoneInterfaceConfigService.selectPhoneInterfaceConfigList(phoneInterfaceConfig);
        return getDataTable(list);
    }

    /**
     * 导出接口地址配置列表
     */
    @PreAuthorize("@ss.hasPermi('phone:interfaceConfig:export')")
    @Log(title = "接口地址配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PhoneInterfaceConfig phoneInterfaceConfig)
    {
        List<PhoneInterfaceConfig> list = phoneInterfaceConfigService.selectPhoneInterfaceConfigList(phoneInterfaceConfig);
        ExcelUtil<PhoneInterfaceConfig> util = new ExcelUtil<PhoneInterfaceConfig>(PhoneInterfaceConfig.class);
        util.exportExcel(response, list, "接口地址配置数据");
    }

    /**
     * 获取接口地址配置详细信息
     */
    @ApiOperation("获取接口地址配置详细信息")
    @ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "Long", paramType = "path", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermi('phone:interfaceConfig:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(phoneInterfaceConfigService.selectPhoneInterfaceConfigById(id));
    }

    /**
     * 新增接口地址配置
     */
    @ApiOperation("新增接口地址配置")
    @PreAuthorize("@ss.hasPermi('phone:interfaceConfig:add')")
    @Log(title = "接口地址配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PhoneInterfaceConfig phoneInterfaceConfig)
    {
        return toAjax(phoneInterfaceConfigService.insertPhoneInterfaceConfig(phoneInterfaceConfig));
    }

    /**
     * 修改接口地址配置
     */
    @ApiOperation("修改接口地址配置")
    @PreAuthorize("@ss.hasPermi('phone:interfaceConfig:edit')")
    @Log(title = "接口地址配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PhoneInterfaceConfig phoneInterfaceConfig)
    {
        return toAjax(phoneInterfaceConfigService.updatePhoneInterfaceConfig(phoneInterfaceConfig));
    }

    /**
     * 删除接口地址配置
     */
    @ApiOperation("删除接口地址配置")
    @ApiImplicitParam(name = "ids", value = "ID数组", required = true, dataType = "Long[]", paramType = "path", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermi('phone:interfaceConfig:remove')")
    @Log(title = "接口地址配置", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(phoneInterfaceConfigService.deletePhoneInterfaceConfigByIds(ids));
    }
}

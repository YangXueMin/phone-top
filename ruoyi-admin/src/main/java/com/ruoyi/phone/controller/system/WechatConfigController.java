package com.ruoyi.phone.controller.system;

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
import com.ruoyi.common.core.domain.entity.WechatConfig;
import com.ruoyi.system.service.IWechatConfigService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 微信配置Controller
 *
 * @author ruoyi
 * @date 2024-03-01
 */
@Api("微信配置")
@RestController
@RequestMapping("/system/wechatConfig")
public class WechatConfigController extends BaseController {
    @Autowired
    private IWechatConfigService wechatConfigService;

    /**
     * 查询微信配置列表
     */
    @ApiOperation("查询微信配置列表")
    @PreAuthorize("@ss.hasPermi('system:wechatConfig:list')")
    @DataScope(deptAlias = "d", userAlias = "a")
    @GetMapping("/list")
    public TableDataInfo list(WechatConfig wechatConfig) {
        startPage();
        List<WechatConfig> list = wechatConfigService.selectWechatConfigList(wechatConfig);
        return getDataTable(list);
    }

    /**
     * 导出微信配置列表
     */
    @PreAuthorize("@ss.hasPermi('system:wechatConfig:export')")
    @Log(title = "微信配置", businessType = BusinessType.EXPORT)
    @DataScope(deptAlias = "d", userAlias = "a")
    @PostMapping("/export")
    public void export(HttpServletResponse response, WechatConfig wechatConfig) {
        List<WechatConfig> list = wechatConfigService.selectWechatConfigList(wechatConfig);
        ExcelUtil<WechatConfig> util = new ExcelUtil<WechatConfig>(WechatConfig.class);
        util.exportExcel(response, list, "微信配置数据");
    }

    /**
     * 获取微信配置详细信息
     */
    @ApiOperation("获取微信配置详细信息")
    @ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "Long", paramType = "path", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermi('system:wechatConfig:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(wechatConfigService.selectWechatConfigById(id));
    }

    /**
     * 新增微信配置
     */
    @ApiOperation("新增微信配置")
    @PreAuthorize("@ss.hasPermi('system:wechatConfig:add')")
    @Log(title = "微信配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WechatConfig wechatConfig) {
        return toAjax(wechatConfigService.insertWechatConfig(wechatConfig));
    }

    /**
     * 修改微信配置
     */
    @ApiOperation("修改微信配置")
    @PreAuthorize("@ss.hasPermi('system:wechatConfig:edit')")
    @Log(title = "微信配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WechatConfig wechatConfig) {
        return toAjax(wechatConfigService.updateWechatConfig(wechatConfig));
    }

    /**
     * 删除微信配置
     */
    @ApiOperation("删除微信配置")
    @ApiImplicitParam(name = "ids", value = "ID数组", required = true, dataType = "Long[]", paramType = "path", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermi('system:wechatConfig:remove')")
    @Log(title = "微信配置", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(wechatConfigService.deleteWechatConfigByIds(ids));
    }
}

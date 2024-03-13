package com.ruoyi.system.controller;

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
import com.ruoyi.system.domain.SysCard;
import com.ruoyi.system.service.ISysCardService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 后台卡密管理Controller
 *
 * @author ruoyi
 * @date 2024-03-13
 */
@Api("后台卡密管理")
@RestController
@RequestMapping("/system/sysCard")
public class SysCardController extends BaseController {
    @Autowired
    private ISysCardService sysCardService;

    /**
     * 查询后台卡密管理列表
     */
    @ApiOperation("查询后台卡密管理列表")
    @PreAuthorize("@ss.hasPermi('system:sysCard:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysCard sysCard)
    {
        startPage();
        List<SysCard> list = sysCardService.selectSysCardList(sysCard);
        return getDataTable(list);
    }

    /**
     * 导出后台卡密管理列表
     */
    @PreAuthorize("@ss.hasPermi('system:sysCard:export')")
    @Log(title = "后台卡密管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysCard sysCard)
    {
        List<SysCard> list = sysCardService.selectSysCardList(sysCard);
        ExcelUtil<SysCard> util = new ExcelUtil<SysCard>(SysCard.class);
        util.exportExcel(response, list, "后台卡密管理数据");
    }

    /**
     * 获取后台卡密管理详细信息
     */
    @ApiOperation("获取后台卡密管理详细信息")
    @ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "Long", paramType = "path", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermi('system:sysCard:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(sysCardService.selectSysCardById(id));
    }

    /**
     * 新增后台卡密管理
     */
    @ApiOperation("新增后台卡密管理")
    @PreAuthorize("@ss.hasPermi('system:sysCard:add')")
    @Log(title = "后台卡密管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysCard sysCard)
    {
        return toAjax(sysCardService.insertSysCard(sysCard));
    }

    /**
     * 修改后台卡密管理
     */
    @ApiOperation("修改后台卡密管理")
    @PreAuthorize("@ss.hasPermi('system:sysCard:edit')")
    @Log(title = "后台卡密管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysCard sysCard)
    {
        return toAjax(sysCardService.updateSysCard(sysCard));
    }

    /**
     * 删除后台卡密管理
     */
    @ApiOperation("删除后台卡密管理")
    @ApiImplicitParam(name = "ids", value = "ID数组", required = true, dataType = "Long[]", paramType = "path", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermi('system:sysCard:remove')")
    @Log(title = "后台卡密管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(sysCardService.deleteSysCardByIds(ids));
    }
}

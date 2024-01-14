package com.ruoyi.shop.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.shop.domain.BalanceInfo;
import com.ruoyi.shop.service.IBalanceInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 余额消费记录Controller
 *
 * @author ruoyi
 * @date 2024-01-14
 */
@Api("余额消费记录")
@RestController
@RequestMapping("/shop/balanceInfo")
public class BalanceInfoController extends BaseController {
    @Autowired
    private IBalanceInfoService balanceInfoService;

    /**
     * 查询余额消费记录列表
     */
    @ApiOperation("查询余额消费记录列表")
    @PreAuthorize("@ss.hasPermi('shop:balanceInfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(BalanceInfo balanceInfo) {
        startPage();
        List<BalanceInfo> list = balanceInfoService.selectBalanceInfoList(balanceInfo);
        return getDataTable(list);
    }

    /**
     * 导出余额消费记录列表
     */
    @PreAuthorize("@ss.hasPermi('shop:balanceInfo:export')")
    @Log(title = "余额消费记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BalanceInfo balanceInfo) {
        List<BalanceInfo> list = balanceInfoService.selectBalanceInfoList(balanceInfo);
        ExcelUtil<BalanceInfo> util = new ExcelUtil<BalanceInfo>(BalanceInfo.class);
        util.exportExcel(response, list, "余额消费记录数据");
    }

    /**
     * 获取余额消费记录详细信息
     */
    @ApiOperation("获取余额消费记录详细信息")
    @ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "Long", paramType = "path", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermi('shop:balanceInfo:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(balanceInfoService.selectBalanceInfoById(id));
    }

    /**
     * 新增余额消费记录
     */
    @ApiOperation("新增余额消费记录")
    @PreAuthorize("@ss.hasPermi('shop:balanceInfo:add')")
    @Log(title = "余额消费记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BalanceInfo balanceInfo) {
        return toAjax(balanceInfoService.insertBalanceInfo(balanceInfo));
    }

    /**
     * 修改余额消费记录
     */
    @ApiOperation("修改余额消费记录")
    @PreAuthorize("@ss.hasPermi('shop:balanceInfo:edit')")
    @Log(title = "余额消费记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BalanceInfo balanceInfo) {
        return toAjax(balanceInfoService.updateBalanceInfo(balanceInfo));
    }

    /**
     * 删除余额消费记录
     */
    @ApiOperation("删除余额消费记录")
    @ApiImplicitParam(name = "ids", value = "ID数组", required = true, dataType = "Long[]", paramType = "path", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermi('shop:balanceInfo:remove')")
    @Log(title = "余额消费记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(balanceInfoService.deleteBalanceInfoByIds(ids));
    }
}

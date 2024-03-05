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
import com.ruoyi.phone.domain.PhoneBalanceLog;
import com.ruoyi.phone.service.IPhoneBalanceLogService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 余额充值记录Controller
 *
 * @author ruoyi
 * @date 2024-03-05
 */
@Api("余额充值记录")
@RestController
@RequestMapping("/phone/balanceLog")
public class PhoneBalanceLogController extends BaseController {
    @Autowired
    private IPhoneBalanceLogService phoneBalanceLogService;

    /**
     * 查询余额充值记录列表
     */
    @ApiOperation("查询余额充值记录列表")
    @PreAuthorize("@ss.hasPermi('phone:balanceLog:list')")
    @GetMapping("/list")
    public TableDataInfo list(PhoneBalanceLog phoneBalanceLog)
    {
        startPage();
        List<PhoneBalanceLog> list = phoneBalanceLogService.selectPhoneBalanceLogList(phoneBalanceLog);
        return getDataTable(list);
    }

    /**
     * 导出余额充值记录列表
     */
    @PreAuthorize("@ss.hasPermi('phone:balanceLog:export')")
    @Log(title = "余额充值记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PhoneBalanceLog phoneBalanceLog)
    {
        List<PhoneBalanceLog> list = phoneBalanceLogService.selectPhoneBalanceLogList(phoneBalanceLog);
        ExcelUtil<PhoneBalanceLog> util = new ExcelUtil<PhoneBalanceLog>(PhoneBalanceLog.class);
        util.exportExcel(response, list, "余额充值记录数据");
    }

    /**
     * 获取余额充值记录详细信息
     */
    @ApiOperation("获取余额充值记录详细信息")
    @ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "Long", paramType = "path", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermi('phone:balanceLog:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(phoneBalanceLogService.selectPhoneBalanceLogById(id));
    }

    /**
     * 新增余额充值记录
     */
    @ApiOperation("新增余额充值记录")
    @PreAuthorize("@ss.hasPermi('phone:balanceLog:add')")
    @Log(title = "余额充值记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PhoneBalanceLog phoneBalanceLog)
    {
        return toAjax(phoneBalanceLogService.insertPhoneBalanceLog(phoneBalanceLog));
    }

    /**
     * 修改余额充值记录
     */
    @ApiOperation("修改余额充值记录")
    @PreAuthorize("@ss.hasPermi('phone:balanceLog:edit')")
    @Log(title = "余额充值记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PhoneBalanceLog phoneBalanceLog)
    {
        return toAjax(phoneBalanceLogService.updatePhoneBalanceLog(phoneBalanceLog));
    }

    /**
     * 删除余额充值记录
     */
    @ApiOperation("删除余额充值记录")
    @ApiImplicitParam(name = "ids", value = "ID数组", required = true, dataType = "Long[]", paramType = "path", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermi('phone:balanceLog:remove')")
    @Log(title = "余额充值记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(phoneBalanceLogService.deletePhoneBalanceLogByIds(ids));
    }
}

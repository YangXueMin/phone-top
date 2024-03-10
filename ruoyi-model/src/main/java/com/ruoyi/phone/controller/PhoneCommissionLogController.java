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
import com.ruoyi.phone.domain.PhoneCommissionLog;
import com.ruoyi.phone.service.IPhoneCommissionLogService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 佣金提现记录Controller
 *
 * @author ruoyi
 * @date 2024-03-05
 */
@Api("佣金提现记录")
@RestController
@RequestMapping("/phone/commissionLog")
public class PhoneCommissionLogController extends BaseController {
    @Autowired
    private IPhoneCommissionLogService phoneCommissionLogService;

    /**
     * 查询佣金提现记录列表
     */
    @ApiOperation("查询佣金提现记录列表")
    @PreAuthorize("@ss.hasPermi('phone:commissionLog:list')")
    @GetMapping("/list")
    public TableDataInfo list(PhoneCommissionLog phoneCommissionLog)
    {
        startPage();
        List<PhoneCommissionLog> list = phoneCommissionLogService.selectPhoneCommissionLogList(phoneCommissionLog);
        return getDataTable(list);
    }

    /**
     * 导出佣金提现记录列表
     */
    @PreAuthorize("@ss.hasPermi('phone:commissionLog:export')")
    @Log(title = "佣金提现记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PhoneCommissionLog phoneCommissionLog)
    {
        List<PhoneCommissionLog> list = phoneCommissionLogService.selectPhoneCommissionLogList(phoneCommissionLog);
        ExcelUtil<PhoneCommissionLog> util = new ExcelUtil<PhoneCommissionLog>(PhoneCommissionLog.class);
        util.exportExcel(response, list, "佣金提现记录数据");
    }

    /**
     * 获取佣金提现记录详细信息
     */
    @ApiOperation("获取佣金提现记录详细信息")
    @ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "Long", paramType = "path", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermi('phone:commissionLog:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(phoneCommissionLogService.selectPhoneCommissionLogById(id));
    }

    /**
     * 新增佣金提现记录
     */
    @ApiOperation("新增佣金提现记录")
    @PreAuthorize("@ss.hasPermi('phone:commissionLog:add')")
    @Log(title = "佣金提现记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PhoneCommissionLog phoneCommissionLog)
    {
        return success(phoneCommissionLogService.insertPhoneCommissionLog(phoneCommissionLog));
    }

    /**
     * 修改佣金提现记录
     */
    @ApiOperation("修改佣金提现记录")
    @PreAuthorize("@ss.hasPermi('phone:commissionLog:edit')")
    @Log(title = "佣金提现记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PhoneCommissionLog phoneCommissionLog)
    {
        return toAjax(phoneCommissionLogService.updatePhoneCommissionLog(phoneCommissionLog));
    }

    /**
     * 删除佣金提现记录
     */
    @ApiOperation("删除佣金提现记录")
    @ApiImplicitParam(name = "ids", value = "ID数组", required = true, dataType = "Long[]", paramType = "path", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermi('phone:commissionLog:remove')")
    @Log(title = "佣金提现记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(phoneCommissionLogService.deletePhoneCommissionLogByIds(ids));
    }
}

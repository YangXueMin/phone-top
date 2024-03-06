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
import com.ruoyi.phone.domain.PhoneMemberCardLog;
import com.ruoyi.phone.service.IPhoneMemberCardLogService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 会员卡充值记录Controller
 *
 * @author ruoyi
 * @date 2024-03-05
 */
@Api("会员卡充值记录")
@RestController
@RequestMapping("/phone/cardLog")
public class PhoneMemberCardLogController extends BaseController {
    @Autowired
    private IPhoneMemberCardLogService phoneMemberCardLogService;

    /**
     * 查询会员卡充值记录列表
     */
    @ApiOperation("查询会员卡充值记录列表")
    @PreAuthorize("@ss.hasPermi('phone:cardLog:list')")
    @GetMapping("/list")
    public TableDataInfo list(PhoneMemberCardLog phoneMemberCardLog) {
        startPage();
        List<PhoneMemberCardLog> list = phoneMemberCardLogService.selectPhoneMemberCardLogList(phoneMemberCardLog);
        return getDataTable(list);
    }

    /**
     * 导出会员卡充值记录列表
     */
    @PreAuthorize("@ss.hasPermi('phone:cardLog:export')")
    @Log(title = "会员卡充值记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PhoneMemberCardLog phoneMemberCardLog) {
        List<PhoneMemberCardLog> list = phoneMemberCardLogService.selectPhoneMemberCardLogList(phoneMemberCardLog);
        ExcelUtil<PhoneMemberCardLog> util = new ExcelUtil<PhoneMemberCardLog>(PhoneMemberCardLog.class);
        util.exportExcel(response, list, "会员卡充值记录数据");
    }

    /**
     * 获取会员卡充值记录详细信息
     */
    @ApiOperation("获取会员卡充值记录详细信息")
    @ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "Long", paramType = "path", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermi('phone:cardLog:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(phoneMemberCardLogService.selectPhoneMemberCardLogById(id));
    }

    /**
     * 新增会员卡充值记录
     */
    @ApiOperation("新增会员卡充值记录")
    @PreAuthorize("@ss.hasPermi('phone:cardLog:add')")
    @Log(title = "会员卡充值记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PhoneMemberCardLog phoneMemberCardLog) {
        return success(phoneMemberCardLogService.insertPhoneMemberCardLog(phoneMemberCardLog));
    }

    /**
     * 修改会员卡充值记录
     */
    @ApiOperation("修改会员卡充值记录")
    @PreAuthorize("@ss.hasPermi('phone:cardLog:edit')")
    @Log(title = "会员卡充值记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PhoneMemberCardLog phoneMemberCardLog) {
        return toAjax(phoneMemberCardLogService.updatePhoneMemberCardLog(phoneMemberCardLog));
    }

    /**
     * 删除会员卡充值记录
     */
    @ApiOperation("删除会员卡充值记录")
    @ApiImplicitParam(name = "ids", value = "ID数组", required = true, dataType = "Long[]", paramType = "path", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermi('phone:cardLog:remove')")
    @Log(title = "会员卡充值记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(phoneMemberCardLogService.deletePhoneMemberCardLogByIds(ids));
    }
}

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
import com.ruoyi.phone.domain.PhonePayMenthod;
import com.ruoyi.phone.service.IPhonePayMenthodService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 充值方式配置Controller
 *
 * @author ruoyi
 * @date 2024-04-11
 */
@Api("充值方式配置")
@RestController
@RequestMapping("/phone/payMenthod")
public class PhonePayMenthodController extends BaseController {
    @Autowired
    private IPhonePayMenthodService phonePayMenthodService;

    /**
     * 查询充值方式配置列表
     */
    @ApiOperation("查询充值方式配置列表")
    @PreAuthorize("@ss.hasPermi('phone:payMenthod:list')")
    @GetMapping("/list")
    public TableDataInfo list(PhonePayMenthod phonePayMenthod) {
        startPage();
        List<PhonePayMenthod> list = phonePayMenthodService.selectPhonePayMenthodList(phonePayMenthod);
        return getDataTable(list);
    }

    /**
     * 导出充值方式配置列表
     */
    @PreAuthorize("@ss.hasPermi('phone:payMenthod:export')")
    @Log(title = "充值方式配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PhonePayMenthod phonePayMenthod) {
        List<PhonePayMenthod> list = phonePayMenthodService.selectPhonePayMenthodList(phonePayMenthod);
        ExcelUtil<PhonePayMenthod> util = new ExcelUtil<PhonePayMenthod>(PhonePayMenthod.class);
        util.exportExcel(response, list, "充值方式配置数据");
    }

    /**
     * 获取充值方式配置详细信息
     */
    @ApiOperation("获取充值方式配置详细信息")
    @ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "Long", paramType = "path", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermi('phone:payMenthod:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(phonePayMenthodService.selectPhonePayMenthodById(id));
    }

    /**
     * 新增充值方式配置
     */
    @ApiOperation("新增充值方式配置")
    @PreAuthorize("@ss.hasPermi('phone:payMenthod:add')")
    @Log(title = "充值方式配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PhonePayMenthod phonePayMenthod) {
        return toAjax(phonePayMenthodService.insertPhonePayMenthod(phonePayMenthod));
    }

    /**
     * 新增充值方式配置
     */
    @ApiOperation("新增充值方式配置")
    @PreAuthorize("@ss.hasPermi('phone:payMenthod:add')")
    @Log(title = "充值方式配置", businessType = BusinessType.INSERT)
    @PostMapping("saveList")
    public AjaxResult saveList(@RequestBody List<PhonePayMenthod> list) {
        return toAjax(phonePayMenthodService.saveBach(list));
    }

    /**
     * 修改充值方式配置
     */
    @ApiOperation("修改充值方式配置")
    @PreAuthorize("@ss.hasPermi('phone:payMenthod:edit')")
    @Log(title = "充值方式配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PhonePayMenthod phonePayMenthod) {
        return toAjax(phonePayMenthodService.updatePhonePayMenthod(phonePayMenthod));
    }

    /**
     * 删除充值方式配置
     */
    @ApiOperation("删除充值方式配置")
    @ApiImplicitParam(name = "ids", value = "ID数组", required = true, dataType = "Long[]", paramType = "path", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermi('phone:payMenthod:remove')")
    @Log(title = "充值方式配置", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(phonePayMenthodService.deletePhonePayMenthodByIds(ids));
    }
}

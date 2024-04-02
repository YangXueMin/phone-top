package com.ruoyi.phone.controller.system;

import com.ruoyi.common.annotation.DataScope;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.PhoneWechatMessage;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.service.IPhoneWechatMessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 微信公众号消息类型配置Controller
 *
 * @author ruoyi
 * @date 2024-03-25
 */
@Api("微信公众号消息类型配置")
@RestController
@RequestMapping("/system/wechatMessage")
public class PhoneWechatMessageController extends BaseController {
    @Autowired
    private IPhoneWechatMessageService phoneWechatMessageService;

    /**
     * 查询微信公众号消息类型配置列表
     */
    @ApiOperation("查询微信公众号消息类型配置列表")
    @PreAuthorize("@ss.hasPermi('system:wechatMessage:list')")
    @DataScope(deptAlias = "d", userAlias = "a")
    @GetMapping("/list")
    public TableDataInfo list(PhoneWechatMessage phoneWechatMessage) {
        startPage();
        List<PhoneWechatMessage> list = phoneWechatMessageService.selectPhoneWechatMessageList(phoneWechatMessage);
        return getDataTable(list);
    }

    /**
     * 导出微信公众号消息类型配置列表
     */
    @PreAuthorize("@ss.hasPermi('system:wechatMessage:export')")
    @DataScope(deptAlias = "d", userAlias = "a")
    @Log(title = "微信公众号消息类型配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PhoneWechatMessage phoneWechatMessage) {
        List<PhoneWechatMessage> list = phoneWechatMessageService.selectPhoneWechatMessageList(phoneWechatMessage);
        ExcelUtil<PhoneWechatMessage> util = new ExcelUtil<PhoneWechatMessage>(PhoneWechatMessage.class);
        util.exportExcel(response, list, "微信公众号消息类型配置数据");
    }

    /**
     * 获取微信公众号消息类型配置详细信息
     */
    @ApiOperation("获取微信公众号消息类型配置详细信息")
    @ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "Long", paramType = "path", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermi('system:wechatMessage:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(phoneWechatMessageService.selectPhoneWechatMessageById(id));
    }

    /**
     * 新增微信公众号消息类型配置
     */
    @ApiOperation("新增微信公众号消息类型配置")
    @PreAuthorize("@ss.hasPermi('system:wechatMessage:add')")
    @Log(title = "微信公众号消息类型配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PhoneWechatMessage phoneWechatMessage) {
        return toAjax(phoneWechatMessageService.insertPhoneWechatMessage(phoneWechatMessage));
    }

    /**
     * 修改微信公众号消息类型配置
     */
    @ApiOperation("修改微信公众号消息类型配置")
    @PreAuthorize("@ss.hasPermi('system:wechatMessage:edit')")
    @Log(title = "微信公众号消息类型配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PhoneWechatMessage phoneWechatMessage) {
        return toAjax(phoneWechatMessageService.updatePhoneWechatMessage(phoneWechatMessage));
    }

    /**
     * 删除微信公众号消息类型配置
     */
    @ApiOperation("删除微信公众号消息类型配置")
    @ApiImplicitParam(name = "ids", value = "ID数组", required = true, dataType = "Long[]", paramType = "path", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermi('system:wechatMessage:remove')")
    @Log(title = "微信公众号消息类型配置", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(phoneWechatMessageService.deletePhoneWechatMessageByIds(ids));
    }

    /**
     * 获取公众号素材接口
     */
    @ApiOperation("获取公众号素材接口")
    @GetMapping("findMaterialList")
    public AjaxResult findMaterialList(@RequestParam String appId) {
        phoneWechatMessageService.findMaterialList(appId);
        return toAjax(true);
    }
}

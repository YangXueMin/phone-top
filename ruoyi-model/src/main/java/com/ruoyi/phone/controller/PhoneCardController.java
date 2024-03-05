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
import com.ruoyi.phone.domain.PhoneCard;
import com.ruoyi.phone.service.IPhoneCardService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 卡密管理Controller
 *
 * @author ruoyi
 * @date 2024-03-04
 */
@Api("卡密管理")
@RestController
@RequestMapping("/phone/card")
public class PhoneCardController extends BaseController {
    @Autowired
    private IPhoneCardService phoneCardService;

    /**
     * 查询卡密管理列表
     */
    @ApiOperation("查询卡密管理列表")
    @PreAuthorize("@ss.hasPermi('phone:card:list')")
    @GetMapping("/list")
    public TableDataInfo list(PhoneCard phoneCard)
    {
        startPage();
        List<PhoneCard> list = phoneCardService.selectPhoneCardList(phoneCard);
        return getDataTable(list);
    }

    /**
     * 导出卡密管理列表
     */
    @PreAuthorize("@ss.hasPermi('phone:card:export')")
    @Log(title = "卡密管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PhoneCard phoneCard)
    {
        List<PhoneCard> list = phoneCardService.selectPhoneCardList(phoneCard);
        ExcelUtil<PhoneCard> util = new ExcelUtil<PhoneCard>(PhoneCard.class);
        util.exportExcel(response, list, "卡密管理数据");
    }

    /**
     * 获取卡密管理详细信息
     */
    @ApiOperation("获取卡密管理详细信息")
    @ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "Long", paramType = "path", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermi('phone:card:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(phoneCardService.selectPhoneCardById(id));
    }

    /**
     * 新增卡密管理
     */
    @ApiOperation("新增卡密管理")
    @PreAuthorize("@ss.hasPermi('phone:card:add')")
    @Log(title = "卡密管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PhoneCard phoneCard)
    {
        return toAjax(phoneCardService.insertPhoneCard(phoneCard));
    }

    /**
     * 修改卡密管理
     */
    @ApiOperation("修改卡密管理")
    @PreAuthorize("@ss.hasPermi('phone:card:edit')")
    @Log(title = "卡密管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PhoneCard phoneCard)
    {
        return toAjax(phoneCardService.updatePhoneCard(phoneCard));
    }

    /**
     * 删除卡密管理
     */
    @ApiOperation("删除卡密管理")
    @ApiImplicitParam(name = "ids", value = "ID数组", required = true, dataType = "Long[]", paramType = "path", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermi('phone:card:remove')")
    @Log(title = "卡密管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(phoneCardService.deletePhoneCardByIds(ids));
    }
}

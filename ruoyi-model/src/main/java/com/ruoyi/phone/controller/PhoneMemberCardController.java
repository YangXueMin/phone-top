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
import com.ruoyi.phone.domain.PhoneMemberCard;
import com.ruoyi.phone.service.IPhoneMemberCardService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 会员卡管理Controller
 *
 * @author ruoyi
 * @date 2024-03-04
 */
@Api("会员卡管理")
@RestController
@RequestMapping("/phone/memberCard")
public class PhoneMemberCardController extends BaseController {
    @Autowired
    private IPhoneMemberCardService phoneMemberCardService;

    /**
     * 查询会员卡管理列表
     */
    @ApiOperation("查询会员卡管理列表")
    @PreAuthorize("@ss.hasPermi('phone:memberCard:list')")
    @GetMapping("/list")
    public TableDataInfo list(PhoneMemberCard phoneMemberCard)
    {
        startPage();
        List<PhoneMemberCard> list = phoneMemberCardService.selectPhoneMemberCardList(phoneMemberCard);
        return getDataTable(list);
    }

    /**
     * 导出会员卡管理列表
     */
    @PreAuthorize("@ss.hasPermi('phone:memberCard:export')")
    @Log(title = "会员卡管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PhoneMemberCard phoneMemberCard)
    {
        List<PhoneMemberCard> list = phoneMemberCardService.selectPhoneMemberCardList(phoneMemberCard);
        ExcelUtil<PhoneMemberCard> util = new ExcelUtil<PhoneMemberCard>(PhoneMemberCard.class);
        util.exportExcel(response, list, "会员卡管理数据");
    }

    /**
     * 获取会员卡管理详细信息
     */
    @ApiOperation("获取会员卡管理详细信息")
    @ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "Long", paramType = "path", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermi('phone:memberCard:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(phoneMemberCardService.selectPhoneMemberCardById(id));
    }

    /**
     * 新增会员卡管理
     */
    @ApiOperation("新增会员卡管理")
    @PreAuthorize("@ss.hasPermi('phone:memberCard:add')")
    @Log(title = "会员卡管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PhoneMemberCard phoneMemberCard)
    {
        return toAjax(phoneMemberCardService.insertPhoneMemberCard(phoneMemberCard));
    }

    /**
     * 修改会员卡管理
     */
    @ApiOperation("修改会员卡管理")
    @PreAuthorize("@ss.hasPermi('phone:memberCard:edit')")
    @Log(title = "会员卡管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PhoneMemberCard phoneMemberCard)
    {
        return toAjax(phoneMemberCardService.updatePhoneMemberCard(phoneMemberCard));
    }

    /**
     * 删除会员卡管理
     */
    @ApiOperation("删除会员卡管理")
    @ApiImplicitParam(name = "ids", value = "ID数组", required = true, dataType = "Long[]", paramType = "path", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermi('phone:memberCard:remove')")
    @Log(title = "会员卡管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(phoneMemberCardService.deletePhoneMemberCardByIds(ids));
    }
}

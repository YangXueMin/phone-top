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
import com.ruoyi.phone.domain.PhoneMemberCoupon;
import com.ruoyi.phone.service.IPhoneMemberCouponService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 会员卡券管理关系Controller
 *
 * @author ruoyi
 * @date 2024-03-07
 */
@Api("会员卡券管理关系")
@RestController
@RequestMapping("/phone/memberCoupon")
public class PhoneMemberCouponController extends BaseController {
    @Autowired
    private IPhoneMemberCouponService phoneMemberCouponService;

    /**
     * 查询会员卡券管理关系列表
     */
    @ApiOperation("查询会员卡券管理关系列表")
    @PreAuthorize("@ss.hasPermi('phone:memberCoupon:list')")
    @GetMapping("/list")
    public TableDataInfo list(PhoneMemberCoupon phoneMemberCoupon)
    {
        startPage();
        List<PhoneMemberCoupon> list = phoneMemberCouponService.selectPhoneMemberCouponList(phoneMemberCoupon);
        return getDataTable(list);
    }

    /**
     * 导出会员卡券管理关系列表
     */
    @PreAuthorize("@ss.hasPermi('phone:memberCoupon:export')")
    @Log(title = "会员卡券管理关系", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PhoneMemberCoupon phoneMemberCoupon)
    {
        List<PhoneMemberCoupon> list = phoneMemberCouponService.selectPhoneMemberCouponList(phoneMemberCoupon);
        ExcelUtil<PhoneMemberCoupon> util = new ExcelUtil<PhoneMemberCoupon>(PhoneMemberCoupon.class);
        util.exportExcel(response, list, "会员卡券管理关系数据");
    }

    /**
     * 获取会员卡券管理关系详细信息
     */
    @ApiOperation("获取会员卡券管理关系详细信息")
    @ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "Long", paramType = "path", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermi('phone:memberCoupon:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(phoneMemberCouponService.selectPhoneMemberCouponById(id));
    }

    /**
     * 新增会员卡券管理关系
     */
    @ApiOperation("新增会员卡券管理关系")
    @PreAuthorize("@ss.hasPermi('phone:memberCoupon:add')")
    @Log(title = "会员卡券管理关系", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PhoneMemberCoupon phoneMemberCoupon)
    {
        return toAjax(phoneMemberCouponService.insertPhoneMemberCoupon(phoneMemberCoupon));
    }

    /**
     * 修改会员卡券管理关系
     */
    @ApiOperation("修改会员卡券管理关系")
    @PreAuthorize("@ss.hasPermi('phone:memberCoupon:edit')")
    @Log(title = "会员卡券管理关系", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PhoneMemberCoupon phoneMemberCoupon)
    {
        return toAjax(phoneMemberCouponService.updatePhoneMemberCoupon(phoneMemberCoupon));
    }

    /**
     * 删除会员卡券管理关系
     */
    @ApiOperation("删除会员卡券管理关系")
    @ApiImplicitParam(name = "ids", value = "ID数组", required = true, dataType = "Long[]", paramType = "path", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermi('phone:memberCoupon:remove')")
    @Log(title = "会员卡券管理关系", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(phoneMemberCouponService.deletePhoneMemberCouponByIds(ids));
    }
}

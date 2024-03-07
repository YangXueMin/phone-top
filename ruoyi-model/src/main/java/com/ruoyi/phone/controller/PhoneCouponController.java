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
import com.ruoyi.phone.domain.PhoneCoupon;
import com.ruoyi.phone.service.IPhoneCouponService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 优惠券管理Controller
 *
 * @author ruoyi
 * @date 2024-03-07
 */
@Api("优惠券管理")
@RestController
@RequestMapping("/phone/coupon")
public class PhoneCouponController extends BaseController {
    @Autowired
    private IPhoneCouponService phoneCouponService;

    /**
     * 查询优惠券管理列表
     */
    @ApiOperation("查询优惠券管理列表")
    @PreAuthorize("@ss.hasPermi('phone:coupon:list')")
    @GetMapping("/list")
    public TableDataInfo list(PhoneCoupon phoneCoupon)
    {
        startPage();
        List<PhoneCoupon> list = phoneCouponService.selectPhoneCouponList(phoneCoupon);
        return getDataTable(list);
    }

    /**
     * 导出优惠券管理列表
     */
    @PreAuthorize("@ss.hasPermi('phone:coupon:export')")
    @Log(title = "优惠券管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PhoneCoupon phoneCoupon)
    {
        List<PhoneCoupon> list = phoneCouponService.selectPhoneCouponList(phoneCoupon);
        ExcelUtil<PhoneCoupon> util = new ExcelUtil<PhoneCoupon>(PhoneCoupon.class);
        util.exportExcel(response, list, "优惠券管理数据");
    }

    /**
     * 获取优惠券管理详细信息
     */
    @ApiOperation("获取优惠券管理详细信息")
    @ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "Long", paramType = "path", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermi('phone:coupon:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(phoneCouponService.selectPhoneCouponById(id));
    }

    /**
     * 新增优惠券管理
     */
    @ApiOperation("新增优惠券管理")
    @PreAuthorize("@ss.hasPermi('phone:coupon:add')")
    @Log(title = "优惠券管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PhoneCoupon phoneCoupon)
    {
        return toAjax(phoneCouponService.insertPhoneCoupon(phoneCoupon));
    }

    /**
     * 修改优惠券管理
     */
    @ApiOperation("修改优惠券管理")
    @PreAuthorize("@ss.hasPermi('phone:coupon:edit')")
    @Log(title = "优惠券管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PhoneCoupon phoneCoupon)
    {
        return toAjax(phoneCouponService.updatePhoneCoupon(phoneCoupon));
    }

    /**
     * 删除优惠券管理
     */
    @ApiOperation("删除优惠券管理")
    @ApiImplicitParam(name = "ids", value = "ID数组", required = true, dataType = "Long[]", paramType = "path", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermi('phone:coupon:remove')")
    @Log(title = "优惠券管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(phoneCouponService.deletePhoneCouponByIds(ids));
    }
}

package com.ruoyi.shop.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.shop.service.CardCouponService;
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
import com.ruoyi.shop.domain.Coupon;
import com.ruoyi.shop.service.ICouponService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 优惠券配置Controller
 *
 * @author ruoyi
 * @date 2024-01-06
 */
@Api("优惠券配置")
@RestController
@RequestMapping("/shop/coupon")
public class CouponController extends BaseController {
    @Autowired
    private ICouponService couponService;
    @Autowired
    private CardCouponService cardCouponService;

    /**
     * 查询优惠券配置列表
     */
    @ApiOperation("查询优惠券配置列表")
    @PreAuthorize("@ss.hasPermi('shop:coupon:list')")
    @GetMapping("/list")
    public TableDataInfo list(Coupon coupon) {
        startPage();
        List<Coupon> list = couponService.selectCouponList(coupon);
        return getDataTable(list);
    }

    /**
     * 导出优惠券配置列表
     */
    @PreAuthorize("@ss.hasPermi('shop:coupon:export')")
    @Log(title = "优惠券配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Coupon coupon) {
        List<Coupon> list = couponService.selectCouponList(coupon);
        ExcelUtil<Coupon> util = new ExcelUtil<Coupon>(Coupon.class);
        util.exportExcel(response, list, "优惠券配置数据");
    }

    /**
     * 获取优惠券配置详细信息
     */
    @ApiOperation("获取优惠券配置详细信息")
    @ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "Long", paramType = "path", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermi('shop:coupon:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(couponService.selectCouponById(id));
    }

    /**
     * 新增优惠券配置
     */
    @ApiOperation("新增优惠券配置")
    @PreAuthorize("@ss.hasPermi('shop:coupon:add')")
    @Log(title = "优惠券配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Coupon coupon) {
        return toAjax(couponService.insertCoupon(coupon));
    }

    /**
     * 修改优惠券配置
     */
    @ApiOperation("修改优惠券配置")
    @PreAuthorize("@ss.hasPermi('shop:coupon:edit')")
    @Log(title = "优惠券配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Coupon coupon) {
        return toAjax(couponService.updateCoupon(coupon));
    }

    /**
     * 删除优惠券配置
     */
    @ApiOperation("删除优惠券配置")
    @ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "Long", paramType = "path", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermi('shop:coupon:remove')")
    @Log(title = "优惠券配置", businessType = BusinessType.DELETE)
    @DeleteMapping("/{id}")
    public AjaxResult remove(@PathVariable Long id) {
        final int i = cardCouponService.selectCountCardCouponByCouponId(id);
        if (i > 0) {
            return warn("该优惠券已被使用,不允许删除");
        }
        return toAjax(couponService.deleteCouponById(id));
    }
}

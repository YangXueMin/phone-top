package com.ruoyi.shop.controller;

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
import com.ruoyi.shop.domain.RechargeOrderCoupon;
import com.ruoyi.shop.service.IRechargeOrderCouponService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 充值订单与优惠券关联Controller
 *
 * @author ruoyi
 * @date 2024-01-11
 */
@Api("充值订单与优惠券关联")
@RestController
@RequestMapping("/shop/rechargeOrderCoupon")
public class RechargeOrderCouponController extends BaseController {
    @Autowired
    private IRechargeOrderCouponService rechargeOrderCouponService;

    /**
     * 查询充值订单与优惠券关联列表
     */
    @ApiOperation("查询充值订单与优惠券关联列表")
    @GetMapping("/list")
    public TableDataInfo list(RechargeOrderCoupon rechargeOrderCoupon) {
        startPage();
        List<RechargeOrderCoupon> list = rechargeOrderCouponService.selectRechargeOrderCouponList(rechargeOrderCoupon);
        return getDataTable(list);
    }

    /**
     * 导出充值订单与优惠券关联列表
     */
    @PreAuthorize("@ss.hasPermi('shop:rechargeOrderCoupon:export')")
    @Log(title = "充值订单与优惠券关联", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, RechargeOrderCoupon rechargeOrderCoupon) {
        List<RechargeOrderCoupon> list = rechargeOrderCouponService.selectRechargeOrderCouponList(rechargeOrderCoupon);
        ExcelUtil<RechargeOrderCoupon> util = new ExcelUtil<RechargeOrderCoupon>(RechargeOrderCoupon.class);
        util.exportExcel(response, list, "充值订单与优惠券关联数据");
    }

    /**
     * 获取充值订单与优惠券关联详细信息
     */
    @ApiOperation("获取充值订单与优惠券关联详细信息")
    @ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "Long", paramType = "path", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermi('shop:rechargeOrderCoupon:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(rechargeOrderCouponService.selectRechargeOrderCouponById(id));
    }

    /**
     * 新增充值订单与优惠券关联
     */
    @ApiOperation("新增充值订单与优惠券关联")
    @PreAuthorize("@ss.hasPermi('shop:rechargeOrderCoupon:add')")
    @Log(title = "充值订单与优惠券关联", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody RechargeOrderCoupon rechargeOrderCoupon) {
        return toAjax(rechargeOrderCouponService.insertRechargeOrderCoupon(rechargeOrderCoupon));
    }

    /**
     * 修改充值订单与优惠券关联
     */
    @ApiOperation("修改充值订单与优惠券关联")
    @PreAuthorize("@ss.hasPermi('shop:rechargeOrderCoupon:edit')")
    @Log(title = "充值订单与优惠券关联", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody RechargeOrderCoupon rechargeOrderCoupon) {
        return toAjax(rechargeOrderCouponService.updateRechargeOrderCoupon(rechargeOrderCoupon));
    }

    /**
     * 删除充值订单与优惠券关联
     */
    @ApiOperation("删除充值订单与优惠券关联")
    @ApiImplicitParam(name = "ids", value = "ID数组", required = true, dataType = "Long[]", paramType = "path", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermi('shop:rechargeOrderCoupon:remove')")
    @Log(title = "充值订单与优惠券关联", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(rechargeOrderCouponService.deleteRechargeOrderCouponByIds(ids));
    }
}

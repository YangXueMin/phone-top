package com.ruoyi.phone.controller.api;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.phone.domain.PhoneMemberCoupon;
import com.ruoyi.phone.service.IPhoneMemberCouponService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author yangxuemin
 * @ClassName MemberCouponControllerApi
 * @Description
 * @date 2024/3/11 10:31 PM
 */
@Api("优惠券管理")
@RestController
@RequestMapping("/api/phone/coupon")
public class MemberCouponControllerApi extends BaseController {
    private final IPhoneMemberCouponService phoneMemberCouponService;

    public MemberCouponControllerApi(IPhoneMemberCouponService phoneMemberCouponService) {
        this.phoneMemberCouponService = phoneMemberCouponService;
    }

    /**
     * 查询会员卡券管理关系列表
     */
    @ApiOperation("查询会员卡券管理关系列表")
    @GetMapping("/list")
    public TableDataInfo list(PhoneMemberCoupon phoneMemberCoupon) {
        startPage();
        List<PhoneMemberCoupon> list = phoneMemberCouponService.selectPhoneMemberCouponList(phoneMemberCoupon);
        return getDataTable(list);
    }

    /**
     * 查询会员卡券管理关系所有列表
     */
    @ApiOperation("查询会员卡券管理关系所有列表")
    @GetMapping("/allList")
    public AjaxResult allList(PhoneMemberCoupon phoneMemberCoupon) {
        List<PhoneMemberCoupon> list = phoneMemberCouponService.selectPhoneMemberCouponList(phoneMemberCoupon);
        return success(list);
    }
}

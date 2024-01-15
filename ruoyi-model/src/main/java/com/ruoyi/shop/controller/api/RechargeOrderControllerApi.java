package com.ruoyi.shop.controller.api;

import com.github.binarywang.wxpay.bean.order.WxPayMpOrderResult;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.shop.domain.Order;
import com.ruoyi.shop.domain.RechargeOrder;
import com.ruoyi.shop.domain.RechargeOrderCoupon;
import com.ruoyi.shop.service.IRechargeOrderCouponService;
import com.ruoyi.shop.service.IRechargeOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author yangxuemin
 * @ClassName RechargeOrderController
 * @Description
 * @date 2024/1/12 9:18 AM
 */
@Api("充值管理")
@RestController
@RequestMapping("/api/shop/rechargeOrder")
public class RechargeOrderControllerApi extends BaseController {
    @Autowired
    private IRechargeOrderService rechargeOrderService;
    @Autowired
    private IRechargeOrderCouponService iRechargeOrderCouponService;

    @ApiOperation("获取充值卡券集合")
    @PostMapping("/findOrderCouponList")
    public AjaxResult findOrderCouponList(@RequestBody RechargeOrderCoupon rechargeOrderCoupon) {
        List<RechargeOrderCoupon> rechargeOrderCouponList = iRechargeOrderCouponService.selectRechargeOrderCouponList(rechargeOrderCoupon);
        return success(rechargeOrderCouponList);
    }

    @ApiOperation("获取用户卡券数量")
    @GetMapping("/getOrderCouponNumber")
    public AjaxResult getOrderCouponNumber() {
        return success(iRechargeOrderCouponService.getOrderCouponNumber());
    }


    @ApiOperation("获取充值订单列表")
    @PostMapping("/findList")
    public AjaxResult findList(@RequestBody RechargeOrder rechargeOrder) {
        return success(rechargeOrderService.selectRechargeOrderList(rechargeOrder));
    }

    /**
     * 获取订单详情
     */
    @ApiOperation("获取订单详情")
    @GetMapping("/get")
    public AjaxResult get(@RequestParam("id") Long id) {
        return success(rechargeOrderService.selectRechargeOrderById(id));
    }


    /**
     * 创建订单
     */
    @ApiOperation("创建订单")
    @PostMapping("/create")
    public AjaxResult create(@RequestBody RechargeOrder rechargeOrder) {
        final int i = rechargeOrderService.insertRechargeOrder(rechargeOrder);
        return success(i);
    }

    /**
     * 发起支付
     */
    @ApiOperation("发起支付")
    @PostMapping("/pay")
    public AjaxResult pay(@RequestBody RechargeOrder rechargeOrder) {
        rechargeOrder = rechargeOrderService.selectRechargeOrderById(rechargeOrder.getId());
        WxPayMpOrderResult pay = rechargeOrderService.pay(rechargeOrder);
        return success(pay);
    }

    /**
     * 支付回调通知处理
     */
    @ApiOperation("支付回调通知处理")
    @PostMapping("/payOrderNotify")
    public String payOrderNotify(@RequestBody String xmlData) {
        return rechargeOrderService.payOrderNotify(xmlData);
    }

    /**
     * 退款
     *
     * @param rechargeOrder
     * @return
     */
    @ApiOperation(value = "退款")
    @PostMapping("/refund")
    public AjaxResult refund(@RequestBody RechargeOrder rechargeOrder) {
        rechargeOrder = rechargeOrderService.selectRechargeOrderById(rechargeOrder.getId());
        if (rechargeOrder == null) {
            return warn("订单不存在");
        }
        return success(rechargeOrderService.refund(rechargeOrder));
    }

    /**
     * 退款回调通知
     *
     * @param xmlData
     * @return
     */
    @ApiOperation(value = "退款回调通知")
    @PostMapping("/notify/refund")
    public String refundNotify(@RequestBody String xmlData) {
        return rechargeOrderService.refundNotify(xmlData);
    }

    /**
     * 取消订单
     *
     * @param rechargeOrder
     * @return
     */
    @ApiOperation(value = "取消订单")
    @PostMapping("/cancel")
    public AjaxResult cancel(@RequestBody RechargeOrder rechargeOrder) {
        rechargeOrder = rechargeOrderService.selectRechargeOrderById(rechargeOrder.getId());
        if (rechargeOrder == null) {
            return warn("订单不存在");
        }
        return success(rechargeOrderService.cancel(rechargeOrder));
    }
}

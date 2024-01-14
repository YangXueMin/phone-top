package com.ruoyi.shop.controller.api;

import com.github.binarywang.wxpay.bean.order.WxPayMpOrderResult;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.shop.domain.Order;
import com.ruoyi.shop.service.IOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author yangxuemin
 * @ClassName OrderControllerApi
 * @Description 订单管理
 * @date 2024/1/12 9:18 AM
 */
@Api("订单管理")
@RestController
@RequestMapping("/api/shop/order")
public class OrderControllerApi extends BaseController {
    @Autowired
    private IOrderService orderService;

    /**
     * 获取订单列表
     */
    @ApiOperation("获取订单列表")
    @PostMapping("/findList")
    public AjaxResult findList(@RequestBody Order order) {
        return success(orderService.selectOrderList(order));
    }

    /**
     * 获取订单详情
     */
    @ApiOperation("获取订单详情")
    @GetMapping("/get")
    public AjaxResult get(@RequestParam("id") Long id) {
        return success(orderService.selectOrderById(id));
    }

    /**
     * 创建订单
     */
    @ApiOperation("创建订单")
    @PostMapping("/create")
    public AjaxResult create(@RequestBody Order order) {
        final int i = orderService.insertOrder(order);
        return success(i);
    }

    /**
     * 发起支付
     */
    @ApiOperation("发起支付")
    @PostMapping("/pay")
    public AjaxResult pay(@RequestBody Order order) {
        WxPayMpOrderResult pay = orderService.pay(order);
        return success(pay);
    }

    /**
     * 支付回调通知处理
     */
    @ApiOperation("支付回调通知处理")
    @PostMapping("/payOrderNotify")
    public String payOrderNotify(@RequestBody String xmlData) {
        return orderService.payOrderNotify(xmlData);
    }
}

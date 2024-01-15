package com.ruoyi.shop.controller.api;

import com.github.binarywang.wxpay.bean.order.WxPayMpOrderResult;
import com.github.binarywang.wxpay.bean.request.WxPayRefundRequest;
import com.github.binarywang.wxpay.bean.result.WxPayRefundResult;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.Member;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.shop.domain.Order;
import com.ruoyi.shop.service.IMemberService;
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
    @Autowired
    private IMemberService memberService;

    /**
     * 获取订单列表
     */
    @ApiOperation("获取订单列表")
    @PostMapping("/findList")
    public AjaxResult findList(@RequestBody Order order) {
        return success(orderService.selectOrderListApi(order));
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
        //如果是用余额支付，判断用户余额是否充足
        if (StringUtils.equals("1", order.getPayType())) {
            Member member = memberService.selectMemberById(order.getMemberId());
            if (member.getBalance().compareTo(order.getMoney()) < 0) {
                return warn("余额不足，请充值");
            }
        }
        final int i = orderService.insertOrder(order);
        return success(i);
    }

    /**
     * 发起支付
     */
    @ApiOperation("发起支付")
    @PostMapping("/pay")
    public AjaxResult pay(@RequestBody Order order) {
        order = orderService.selectOrderById(order.getId());
        if (order == null) {
            return warn("订单不存在");
        }
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


    /**
     * 退款
     *
     * @param order
     * @return
     */
    @ApiOperation(value = "退款")
    @PostMapping("/refund")
    public AjaxResult refund(@RequestBody Order order) {
        order = orderService.selectOrderById(order.getId());
        if (order == null) {
            return warn("订单不存在");
        }
        if (!StringUtils.equals("1", order.getOrderStatus())) {
            return warn("订单已使用或已退款");
        }
        return success(orderService.refund(order));
    }

    /**
     * 余额退款或取消订单
     *
     * @param order
     * @return
     */
    @ApiOperation(value = "余额退款或取消订单")
    @PostMapping("/balanceRefund")
    public AjaxResult balanceRefund(@RequestBody Order order) {
        String orderStatus = order.getOrderStatus();
        order = orderService.selectOrderById(order.getId());
        if (order == null) {
            return warn("订单不存在");
        }
        if (!StringUtils.equals("1", order.getOrderStatus())) {
            return warn("订单已使用或已退款或已取消");
        }
        order.setOrderStatus(orderStatus);
        return success(orderService.balanceRefund(order));
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
        return orderService.refundNotify(xmlData);
    }

}

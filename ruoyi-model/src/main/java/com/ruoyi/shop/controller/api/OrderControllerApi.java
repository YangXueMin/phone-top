package com.ruoyi.shop.controller.api;

import com.alibaba.fastjson2.JSON;
import com.github.binarywang.wxpay.bean.order.WxPayMpOrderResult;
import com.ruoyi.common.constant.CacheConstants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.Member;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.page.TableSupport;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.shop.domain.Order;
import com.ruoyi.shop.domain.RechargeOrderCoupon;
import com.ruoyi.shop.service.IOrderService;
import com.ruoyi.shop.service.IRechargeOrderCouponService;
import com.ruoyi.system.service.IMemberService;
import com.ruoyi.system.service.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
    @Autowired
    private ISysUserService sysUserService;
    @Autowired
    private IRechargeOrderCouponService rechargeOrderCouponService;
    @Autowired
    private RedisCache redisCache;
    @Value(value = "${user.password.maxRetryCount}")
    private int maxRetryCount;
    @Value(value = "${user.password.lockTime}")
    private int lockTime;

    /**
     * 获取订单列表
     */
    @ApiOperation("获取订单列表")
    @PostMapping("/findList")
    public TableDataInfo findList(@RequestBody Order order) {
        if(ServletUtils.getParameter(TableSupport.PAGE_NUM) != null){
            startPage();
        }
        return getDataTable(orderService.selectOrderListApi(order));
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
        logger.info("接收到参数：{}", JSON.toJSONString(order));
        //如果是用余额支付，判断用户余额是否充足
        if (StringUtils.equals("1", order.getPayType()) && order.getCardId() == null) {
            Member member = memberService.selectMemberById(order.getMemberId());
            if (member.getBalance().compareTo(order.getMoney()) < 0) {
                return warn("余额不足，请充值");
            }
        }
        //判断优惠券是否使用过
        if (StringUtils.isNotBlank(order.getCouponList())) {
            String[] couponList = order.getCouponList().split(",");
            for (String couponId : couponList) {
                RechargeOrderCoupon rechargeOrderCoupon = rechargeOrderCouponService.selectRechargeOrderCouponById(Long.parseLong(couponId));
                if (rechargeOrderCoupon.getId() != null && !StringUtils.equals("1", rechargeOrderCoupon.getStatus())) {
                    return warn("优惠券已使用或已过期，请重新选择");
                }
            }

        }
        return success(orderService.insertOrder(order));
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
     * 发起支付
     */
    @ApiOperation("发起支付")
    @PostMapping("/payOrder")
    public AjaxResult payOrder(@RequestBody Order order) {
        order = orderService.selectOrderById(order.getId());
        if (order == null) {
            return warn("订单不存在");
        }
        WxPayMpOrderResult pay = orderService.payOrder(order);
        return success(pay);
    }

    /**
     * 支付回调通知处理
     */
    @ApiOperation("支付回调通知处理")
    @PostMapping("/payNotify")
    public String payNotify(@RequestBody String xmlData) {
        return orderService.payNotify(xmlData);
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
        if (StringUtils.equals("2", order.getOrderStatus())) {
            return success(orderService.refund(order));
        }
        return warn("订单已使用或已退款");
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
        boolean flag = false;
        if (StringUtils.equals("4",orderStatus) && StringUtils.equals("1", order.getOrderStatus())) {
            flag = true;
        }
        if(StringUtils.equals("3",orderStatus) && StringUtils.equals("2", order.getOrderStatus())){
            flag = true;
        }
        if(flag){
            order.setOrderStatus(orderStatus);
            return success(orderService.balanceRefund(order));
        }
        return warn("订单已使用或已退款或已取消");

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

    /**
     * 核销订单
     *
     * @param order
     * @return
     */
    @ApiOperation(value = "核销订单")
    @PostMapping("/cancel")
    public AjaxResult cancel(@RequestBody Order order) {
        Long userId = order.getUserId();
        order = orderService.selectOrderById(order.getId());
        if (order == null) {
            return warn("订单不存在");
        }
        if (!StringUtils.equals("2", order.getOrderStatus())) {
            return warn("订单已使用或已退款或已取消");
        }
        if (StringUtils.equals("2", order.getCancelStatus())) {
            return warn("订单已核销");
        }
        //判断核销人员ID是否有门店权限
        List<Long> shopIdList = sysUserService.findShopIdsByUserId(userId);
        if (shopIdList.size() > 0) {
            Order finalOrder = order;
            boolean containsTargetId = shopIdList.stream().anyMatch(id -> id.equals(finalOrder.getShopId()));
            if(!containsTargetId){
                return warn("当前核销人员无门店权限");
            }
            order.setUserId(userId);
            return success(orderService.cancelOrder(order));
        }
        return warn("无权限");
    }

    /**
     * 支付密码错误次数缓存键名
     *
     * @param id 会员ID
     * @return 缓存键key
     */
    private String getCacheKey(Long id) {
        return CacheConstants.PWD_ERR_PAY_KEY + id;
    }

}

package com.ruoyi.phone.controller.api;

import com.alibaba.fastjson2.JSON;
import com.github.binarywang.wxpay.bean.order.WxPayMpOrderResult;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.phone.domain.PhoneOrder;
import com.ruoyi.phone.domain.TopNotifyRequest;
import com.ruoyi.phone.service.IPhoneOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author yangxuemin
 * @ClassName OrderControllerApi
 * @Description
 * @date 2024/3/6 10:26 AM
 */
@Api("订单管理")
@RestController
@RequestMapping("/api/phone/order")
public class OrderControllerApi extends BaseController {
    private final IPhoneOrderService phoneOrderService;

    public OrderControllerApi(IPhoneOrderService phoneOrderService) {
        this.phoneOrderService = phoneOrderService;
    }

    /**
     * 查询订单记录列表
     */
    @ApiOperation("查询订单记录列表")
    @GetMapping("/list")
    public TableDataInfo list(PhoneOrder phoneOrder) {
        startPage();
        List<PhoneOrder> list = phoneOrderService.selectPhoneOrderList(phoneOrder);
        return getDataTable(list);
    }

    /**
     * 获取订单记录数量
     */
    @ApiOperation("获取订单记录数量")
    @GetMapping("/getCount")
    public AjaxResult getCount(PhoneOrder phoneOrder) {
        return success(phoneOrderService.selectPhoneOrderCount(phoneOrder));
    }

    /**
     * 获取订单记录详细信息
     */
    @ApiOperation("获取订单记录详细信息")
    @GetMapping(value = "getInfo")
    public AjaxResult getInfo(@RequestParam("id") Long id) {
        return success(phoneOrderService.selectPhoneOrderById(id));
    }

    /**
     * 创建充值订单
     */
    @ApiOperation("创建充值订单")
    @PostMapping("/create")
    public AjaxResult create(@RequestBody PhoneOrder phoneOrder) {
        logger.info("接收到参数：{}", JSON.toJSONString(phoneOrder));
        return success(phoneOrderService.insertPhoneOrder(phoneOrder));
    }

    /**
     * 发起支付
     */
    @ApiOperation("发起支付")
    @PostMapping("/pay")
    public AjaxResult pay(@RequestBody PhoneOrder phoneOrder) {
        phoneOrder = phoneOrderService.selectPhoneOrderById(phoneOrder.getId());
        if (phoneOrder == null) {
            return warn("订单不存在");
        }
        WxPayMpOrderResult pay = phoneOrderService.pay(phoneOrder);
        return success(pay);
    }

    /**
     * 支付回调通知处理
     */
    @ApiOperation("支付回调通知处理")
    @PostMapping("/payNotify")
    public String payNotify(@RequestBody String xmlData) {
        return phoneOrderService.payNotify(xmlData);
    }

    /**
     * 充值结果通知
     */
    @ApiOperation("充值结果通知")
    @PostMapping("/topNotify")
    public String topNotify(@RequestBody TopNotifyRequest requestBody) {
        return phoneOrderService.topNotify(requestBody);
    }


}

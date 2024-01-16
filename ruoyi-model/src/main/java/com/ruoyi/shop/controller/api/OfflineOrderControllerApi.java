package com.ruoyi.shop.controller.api;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.shop.domain.OfflineOrder;
import com.ruoyi.shop.service.IOfflineOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author yangxuemin
 * @ClassName OfflineOrderControllerApi
 * @Description
 * @date 2024/1/14 6:59 PM
 */
@Api("线下订单管理")
@RestController
@RequestMapping("/api/shop/offlineOrder")
public class OfflineOrderControllerApi extends BaseController {
    @Autowired
    private IOfflineOrderService offlineOrderService;

    /**
     * 获取线下订单列表
     */
    @ApiOperation("获取线下订单列表")
    @PostMapping("/findList")
    public AjaxResult findList(@RequestBody OfflineOrder offlineOrder) {
        return success(offlineOrderService.selectOfflineOrderListApi(offlineOrder));
    }

    /**
     * 获取线下订单详情
     */
    @ApiOperation("获取线下订单详情")
    @GetMapping("/get")
    public AjaxResult get(@RequestParam("id") Long id) {
        return success(offlineOrderService.selectOfflineOrderById(id));
    }


    /**
     * 创建线下订单
     */
    @ApiOperation("创建线下订单")
    @PostMapping("/create")
    public AjaxResult create(@RequestBody OfflineOrder offlineOrder) {
        return success(offlineOrderService.insertOfflineOrder(offlineOrder));
    }

    /**
     * 确认线下订单
     */
    @ApiOperation("确认线下订单")
    @PostMapping("/confirm")
    public AjaxResult confirm(@RequestBody OfflineOrder offlineOrder) {
        final int i = offlineOrderService.updateOfflineOrder(offlineOrder);
        return success(i);
    }

    /**
     * 发起退款
     */
    @ApiOperation("发起退款")
    @PostMapping("/balanceRefund")
    public AjaxResult balanceRefund(@RequestBody OfflineOrder offlineOrder) {
        offlineOrder = offlineOrderService.selectOfflineOrderById(offlineOrder.getId());
        if (offlineOrder == null) {
            return warn("线下订单不存在");
        }
        if (!StringUtils.equals("1", offlineOrder.getOrderStatus())) {
            return warn("线下订单已使用或已退款");
        }
        return success(offlineOrderService.balanceRefund(offlineOrder));
    }
}

package com.ruoyi.shop.controller.api;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.shop.domain.OfflineOrder;
import com.ruoyi.shop.domain.Order;
import com.ruoyi.shop.service.IOfflineOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yangxuemin
 * @ClassName OfflineOrderControllerApi
 * @Description
 * @date 2024/1/14 6:59 PM
 */
@Api("订单管理")
@RestController
@RequestMapping("/api/shop/offlineOrder")
public class OfflineOrderControllerApi extends BaseController {
    @Autowired
    private IOfflineOrderService offlineOrderService;

    /**
     * 创建订单
     */
    @ApiOperation("创建订单")
    @PostMapping("/create")
    public AjaxResult create(@RequestBody OfflineOrder offlineOrder) {
        final int i = offlineOrderService.insertOfflineOrder(offlineOrder);
        return success(i);
    }

    /**
     * 确认订单
     */
    @ApiOperation("确认订单")
    @PostMapping("/confirm")
    public AjaxResult confirm(@RequestBody OfflineOrder offlineOrder) {
        final int i = offlineOrderService.updateOfflineOrder(offlineOrder);
        return success(i);
    }
}

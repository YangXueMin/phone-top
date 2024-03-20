package com.ruoyi.phone.controller.api;

import com.alibaba.fastjson2.JSON;
import com.github.binarywang.wxpay.bean.order.WxPayMpOrderResult;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.phone.domain.PhoneBalanceLog;
import com.ruoyi.phone.service.IPhoneBalanceLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ruoyi
 * @ClassName BalanceControllerApi
 * @Description
 * @date 2024/3/7 4:40 PM
 */
@Api("余额充值管理")
@RestController
@RequestMapping("/api/phone/balance")
public class BalanceControllerApi extends BaseController {
    private final IPhoneBalanceLogService phoneBalanceLogService;


    public BalanceControllerApi(IPhoneBalanceLogService phoneBalanceLogService) {
        this.phoneBalanceLogService = phoneBalanceLogService;
    }

    /**
     * 查询余额充值记录列表
     */
    @ApiOperation("查询余额充值记录列表")
    @GetMapping("/list")
    public TableDataInfo list(PhoneBalanceLog phoneBalanceLog) {
        startPage();
        List<PhoneBalanceLog> list = phoneBalanceLogService.selectPhoneBalanceLogList(phoneBalanceLog);
        return getDataTable(list);
    }

    /**
     * 创建余额充值订单
     */
    @ApiOperation("创建余额充值订单")
    @PostMapping("/create")
    public AjaxResult create(@RequestBody PhoneBalanceLog phoneBalanceLog) {
        logger.info("接收到参数：{}", JSON.toJSONString(phoneBalanceLog));
        return success(phoneBalanceLogService.insertPhoneBalanceLog(phoneBalanceLog));
    }

    /**
     * 发起支付
     */
    @ApiOperation("发起支付")
    @PostMapping("/pay")
    public AjaxResult pay(@RequestBody PhoneBalanceLog phoneBalanceLog) {
        phoneBalanceLog = phoneBalanceLogService.selectPhoneBalanceLogById(phoneBalanceLog.getId());
        if (phoneBalanceLog == null) {
            return warn("订单不存在");
        }
        WxPayMpOrderResult pay = phoneBalanceLogService.pay(phoneBalanceLog);
        return success(pay);
    }

    /**
     * 支付回调通知处理
     */
    @ApiOperation("支付回调通知处理")
    @PostMapping("/payNotify")
    public String payNotify( @RequestBody String xmlData) {
        return phoneBalanceLogService.payNotify( xmlData);
    }
}

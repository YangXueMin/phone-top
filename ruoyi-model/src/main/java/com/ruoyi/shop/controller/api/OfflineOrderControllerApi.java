package com.ruoyi.shop.controller.api;

import com.github.binarywang.wxpay.bean.order.WxPayMpOrderResult;
import com.ruoyi.common.constant.CacheConstants;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.Member;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.page.TableSupport;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.exception.user.UserPasswordRetryLimitExceedException;
import com.ruoyi.common.utils.MessageUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.manager.AsyncManager;
import com.ruoyi.framework.manager.factory.AsyncFactory;
import com.ruoyi.shop.domain.OfflineOrder;
import com.ruoyi.shop.domain.Order;
import com.ruoyi.shop.service.IOfflineOrderService;
import com.ruoyi.system.service.IMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

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
    @Autowired
    private IMemberService memberService;
    @Autowired
    private RedisCache redisCache;
    @Value(value = "${user.password.maxRetryCount}")
    private int maxRetryCount;
    @Value(value = "${user.password.lockTime}")
    private int lockTime;

    /**
     * 获取线下订单列表
     */
    @ApiOperation("获取线下订单列表")
    @PostMapping("/findList")
    public TableDataInfo findList(@RequestBody OfflineOrder offlineOrder) {
        if(ServletUtils.getParameter(TableSupport.PAGE_NUM) != null){
            startPage();
        }
        return getDataTable(offlineOrderService.selectOfflineOrderListApi(offlineOrder));
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
        Member member = memberService.selectMemberById(offlineOrder.getMemberId());
        Integer retryCount = redisCache.getCacheObject(getCacheKey(offlineOrder.getMemberId()));

        if (retryCount == null) {
            retryCount = 0;
        }

        if (retryCount >= maxRetryCount) {
            return error("密码输入次数已超最大，请稍等或联系管理员");
        }
        boolean matches = SecurityUtils.matchesPassword(offlineOrder.getPassword(), member.getPassword());
        if(!matches){
            retryCount = retryCount + 1;
            redisCache.setCacheObject(getCacheKey(offlineOrder.getMemberId()), retryCount, lockTime, TimeUnit.MINUTES);
            return error("密码错误，请重新输入");
        }
        if (member.getBalance().compareTo(offlineOrder.getMoney()) < 1) {
            return warn("余额不足，请充值");
        }
        final int i = offlineOrderService.updateOfflineOrder(offlineOrder);
        return success(i);
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

    /**
     * 发起支付
     */
    @ApiOperation("发起支付")
    @PostMapping("/pay")
    public AjaxResult payOrder(@RequestBody OfflineOrder offlineOrder) {
        offlineOrder = offlineOrderService.selectOfflineOrderById(offlineOrder.getId());
        if (offlineOrder == null) {
            return warn("订单不存在");
        }
        WxPayMpOrderResult pay = offlineOrderService.pay(offlineOrder);
        return success(pay);
    }

    /**
     * 支付回调通知处理
     */
    @ApiOperation("支付回调通知处理")
    @PostMapping("/payNotify")
    public String payNotify(@RequestBody String xmlData) {
        return offlineOrderService.payNotify(xmlData);
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

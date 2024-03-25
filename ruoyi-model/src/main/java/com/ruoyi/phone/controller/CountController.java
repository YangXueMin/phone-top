package com.ruoyi.phone.controller;

import com.ruoyi.common.annotation.DataScope;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.DataRequest;
import com.ruoyi.common.core.domain.entity.Member;
import com.ruoyi.phone.domain.PhoneCommissionLog;
import com.ruoyi.phone.domain.PhoneOrder;
import com.ruoyi.phone.service.IPhoneCommissionLogService;
import com.ruoyi.phone.service.IPhoneOrderService;
import com.ruoyi.phone.service.PhoneCountService;
import com.ruoyi.system.service.IMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ruoyi
 * @ClassName CountController
 * @Description
 * @date 2024/3/17 9:52 AM
 */
@Api("数据统计接口")
@RestController
@RequestMapping("/phone/count")
public class CountController extends BaseController {
    @Autowired
    private IMemberService memberService;
    @Autowired
    private IPhoneOrderService phoneOrderService;
    @Autowired
    private IPhoneCommissionLogService phoneCommissionLogService;
    @Autowired
    private PhoneCountService phoneCountService;

    /**
     * 获取会员基础数据统计接口
     */
    @ApiOperation("获取会员基础数据统计接口")
    @DataScope(deptAlias = "d")
    @PostMapping("/getMemberDayCount")
    public AjaxResult getMemberDayCount(@RequestBody Member member) {
        return success(memberService.getMemberDayCount(member));
    }

    /**
     * 获取订单基础数据统计接口
     */
    @ApiOperation("获取订单基础数据统计接口")
    @DataScope(deptAlias = "d")
    @PostMapping("/getOrderDayCount")
    public AjaxResult getOrderDayCount(@RequestBody PhoneOrder phoneOrder) {
        return success(phoneOrderService.getOrderDayCount(phoneOrder));
    }

    /**
     * 获取订单基础数据金额统计接口
     */
    @ApiOperation("获取订单基础数据金额统计接口")
    @DataScope(deptAlias = "d")
    @PostMapping("/getOrderDayCountMoney")
    public AjaxResult getOrderDayCountMoney(@RequestBody PhoneOrder phoneOrder) {
        return success(phoneOrderService.getOrderDayCountMoney(phoneOrder));
    }

    /**
     * 获取提现金额基础数据金额统计接口
     */
    @ApiOperation("获取提现金额基础数据金额统计接口")
    @DataScope(deptAlias = "d")
    @PostMapping("/getCommissionDayCountMoney")
    public AjaxResult getCommissionDayCountMoney(@RequestBody PhoneCommissionLog phoneCommissionLog) {
        return success(phoneCommissionLogService.getCommissionDayCountMoney(phoneCommissionLog));
    }

    /**
     * 统计数据按月返回
     */
    @ApiOperation("统计数据按月返回")
    @DataScope(deptAlias = "d")
    @PostMapping("/getMonthCount")
    public AjaxResult getMonthCount(@RequestBody PhoneOrder phoneOrder) {
        return success(phoneCountService.getMonthCount(phoneOrder));
    }

    /**
     * 统计列表数据
     */
    @ApiOperation("统计列表数据")
    @DataScope(deptAlias = "d")
    @PostMapping("/getListCount")
    public AjaxResult getListCount(@RequestBody DataRequest dataRequest) {
        return success(phoneCountService.getListCount(dataRequest));
    }

}

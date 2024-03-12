package com.ruoyi.phone.controller.api;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.phone.domain.PhoneCommissionLog;
import com.ruoyi.phone.service.IPhoneCommissionLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author yangxuemin
 * @ClassName PhoneCommissionConfigController
 * @Description
 * @date 2024/3/10 9:04 PM
 */
@Api("佣金管理")
@RestController
@RequestMapping("/api/phone/commission")
public class PhoneCommissionControllerApi extends BaseController {
    private final IPhoneCommissionLogService phoneCommissionLogService;

    public PhoneCommissionControllerApi(IPhoneCommissionLogService phoneCommissionLogService) {
        this.phoneCommissionLogService = phoneCommissionLogService;
    }

    /**
     * 查询佣金提现记录列表
     */
    @ApiOperation("查询佣金提现记录列表")
    @GetMapping("/list")
    public TableDataInfo list(PhoneCommissionLog phoneCommissionLog) {
        startPage();
        List<PhoneCommissionLog> list = phoneCommissionLogService.selectPhoneCommissionLogList(phoneCommissionLog);
        return getDataTable(list);
    }

    /**
     * 发起佣金提现
     */
    @ApiOperation("发起佣金提现")
    @Log(title = "发起佣金提现", businessType = BusinessType.INSERT)
    @PostMapping(value = "initiateCommission")
    public AjaxResult initiateCommission(@RequestBody PhoneCommissionLog phoneCommissionLog) {
        //判断是否有待审批的佣金提现记录
        PhoneCommissionLog query = new PhoneCommissionLog();
        query.setMemberId(phoneCommissionLog.getMemberId());
        query.setAuditStatus("1");
        List<PhoneCommissionLog> phoneCommissionLogList = phoneCommissionLogService.selectPhoneCommissionLogList(query);
        if (phoneCommissionLogList.size() > 0) {
            return error("有待审批的佣金提现记录，请联系客服");
        }
        return success(phoneCommissionLogService.insertPhoneCommissionLog(phoneCommissionLog));
    }
}

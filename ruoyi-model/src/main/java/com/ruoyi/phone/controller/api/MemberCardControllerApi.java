package com.ruoyi.phone.controller.api;

import com.alibaba.fastjson2.JSON;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.phone.domain.PhoneMemberCard;
import com.ruoyi.phone.domain.PhoneMemberCardLog;
import com.ruoyi.phone.service.IPhoneMemberCardLogService;
import com.ruoyi.phone.service.IPhoneMemberCardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author yangxuemin
 * @ClassName MemberCardControllerApi
 * @Description
 * @date 2024/3/5 6:56 PM
 */
@Api("会员卡管理")
@RestController
@RequestMapping("/api/phone/memberCard")
public class MemberCardControllerApi extends BaseController {
    private final IPhoneMemberCardService phoneMemberCardService;
    private final IPhoneMemberCardLogService phoneMemberCardLogService;

    public MemberCardControllerApi(IPhoneMemberCardService phoneMemberCardService, IPhoneMemberCardLogService phoneMemberCardLogService) {
        this.phoneMemberCardService = phoneMemberCardService;
        this.phoneMemberCardLogService = phoneMemberCardLogService;
    }

    /**
     * 查询会员卡管理列表
     */
    @ApiOperation("查询会员卡管理列表")
    @GetMapping("/list")
    public TableDataInfo list(PhoneMemberCard phoneMemberCard) {
        startPage();
        List<PhoneMemberCard> list = phoneMemberCardService.selectPhoneMemberCardList(phoneMemberCard);
        return getDataTable(list);
    }


    /**
     * 获取会员卡管理详细信息
     */
    @ApiOperation("获取会员卡管理详细信息")
    @ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "Long", paramType = "path", dataTypeClass = Long.class)
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(phoneMemberCardService.selectPhoneMemberCardById(id));
    }

    /**
     * 创建会员卡订单
     */
    @ApiOperation("创建会员卡订单")
    @PostMapping("/create")
    public AjaxResult create(@RequestBody PhoneMemberCardLog phoneMemberCardLog) {
        logger.info("接收到参数：{}", JSON.toJSONString(phoneMemberCardLog));
        return success(phoneMemberCardLogService.insertPhoneMemberCardLog(phoneMemberCardLog));
    }

}

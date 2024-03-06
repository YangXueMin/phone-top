package com.ruoyi.phone.controller.api;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.phone.domain.PhonePrice;
import com.ruoyi.phone.service.IPhonePriceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author yangxuemin
 * @ClassName PriceControllerApi
 * @Description
 * @date 2024/3/5 6:58 PM
 */
@Api("价格管理")
@RestController
@RequestMapping("/api/phone/price")
public class PriceControllerApi extends BaseController {

    private final IPhonePriceService phonePriceService;


    public PriceControllerApi(IPhonePriceService phonePriceService) {
        this.phonePriceService = phonePriceService;
    }

    /**
     * 查询价格配置列表
     */
    @ApiOperation("查询价格配置列表")
    @GetMapping("/list")
    public TableDataInfo list(PhonePrice phonePrice) {
        startPage();
        List<PhonePrice> list = phonePriceService.selectPhonePriceList(phonePrice);
        return getDataTable(list);
    }

    /**
     * 获取价格配置详细信息
     */
    @ApiOperation("获取价格配置详细信息")
    @ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "Long", paramType = "path", dataTypeClass = Long.class)
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(phonePriceService.selectPhonePriceById(id));
    }
}

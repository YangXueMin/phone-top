package com.ruoyi.phone.controller.api;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.phone.domain.PhonePrice;
import com.ruoyi.phone.domain.PhonePriceType;
import com.ruoyi.phone.service.IPhonePriceService;
import com.ruoyi.phone.service.IPhonePriceTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

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
    private final IPhonePriceTypeService phonePriceTypeService;


    public PriceControllerApi(IPhonePriceService phonePriceService, IPhonePriceTypeService phonePriceTypeService) {
        this.phonePriceService = phonePriceService;
        this.phonePriceTypeService = phonePriceTypeService;
    }

    /**
     * 查询价格类型列表
     */
    @ApiOperation("查询价格类型列表")
    @GetMapping("/typeList")
    public AjaxResult typeList(PhonePriceType phonePriceType) {
        List<PhonePriceType> list = phonePriceTypeService.selectPhonePriceTypeList(phonePriceType);
        return success(list);
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
    @GetMapping(value = "getInfo")
    public AjaxResult getInfo(@RequestParam("id") Long id) {
        return success(phonePriceService.selectPhonePriceById(id));
    }
}

package com.ruoyi.shop.controller.api;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.shop.domain.ContentType;
import com.ruoyi.shop.domain.DayActivity;
import com.ruoyi.shop.domain.ShopTreeSelect;
import com.ruoyi.shop.service.IDayActivityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author yangxuemin
 * @ClassName DayActivityControllerApi
 * @Description
 * @date 2024/1/14 10:45 AM
 */
@Api("会员日管理")
@RestController
@RequestMapping("/api/shop/dayActivity")
public class DayActivityControllerApi extends BaseController {
    @Autowired
    private IDayActivityService dayActivityService;

    /**
     * 查询会员日数据
     */
    @ApiOperation("查询会员日数据")
    @PostMapping("/findList")
    public AjaxResult findList(@RequestBody DayActivity dayActivity) {
        List<DayActivity> list = dayActivityService.selectDayActivityList(dayActivity);
        return success(list);
    }

    /**
     * 根据ID查询会员日数据
     */
    @ApiOperation("根据ID查询会员日数据")
    @GetMapping("/get")
    public AjaxResult get(@RequestParam("id")Long id) {
        return success(dayActivityService.deleteDayActivityById(id));
    }


    @ApiOperation("是否会员日")
    @GetMapping("/isHoliday")
    public AjaxResult isHoliday(@RequestParam("shopId")Long shopId){
        return success(dayActivityService.isHoliday(shopId));
    }
}

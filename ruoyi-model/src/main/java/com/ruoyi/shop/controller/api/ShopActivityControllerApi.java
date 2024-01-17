package com.ruoyi.shop.controller.api;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.shop.domain.ShopActivity;
import com.ruoyi.shop.service.IShopActivityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author yangxuemin
 * @ClassName ShopActivityControllerApi
 * @Description
 * @date 2024/1/14 10:51 AM
 */
@Api("活动管理")
@RestController
@RequestMapping("/api/shop/activity")
public class ShopActivityControllerApi extends BaseController {
    @Autowired
    private IShopActivityService shopActivityService;

    @ApiOperation("获取活动管理")
    @PostMapping("/findList")
    public AjaxResult findList(@RequestBody ShopActivity shopActivity) {
        return success(shopActivityService.selectShopActivityListApi(shopActivity));
    }

    @ApiOperation("获取活动管理")
    @PostMapping("/findListGroup")
    public AjaxResult findListGroup(@RequestBody ShopActivity shopActivity) {
        return success(shopActivityService.selectShopActivityListGroup(shopActivity));
    }

    @ApiOperation("根据主键获取活动管理")
    @GetMapping("/get")
    public AjaxResult getCard(@RequestParam("id") Long id) {
        return success(shopActivityService.selectShopActivityById(id));
    }
}

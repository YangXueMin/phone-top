package com.ruoyi.shop.controller.api;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.shop.domain.ShopInfoActivity;
import com.ruoyi.shop.service.IShopInfoActivityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author yangxuemin
 * @ClassName ShopInfoActivityControllerApi
 * @Description
 * @date 2024/1/17 6:27 PM
 */
@Api("店铺活动管理")
@RestController
@RequestMapping("/api/shop/infoActivity")
public class ShopInfoActivityControllerApi extends BaseController {
    @Autowired
    private IShopInfoActivityService shopInfoActivityService;

    @ApiOperation("获取店铺活动集合")
    @PostMapping("/findList")
    public AjaxResult findCardList(@RequestBody ShopInfoActivity shopInfoActivity) {
        final List<ShopInfoActivity> cardList = shopInfoActivityService.selectShopInfoActivityList(shopInfoActivity);
        return success(cardList);
    }

    @ApiOperation("根据主键获取店铺活动集合")
    @GetMapping("/get")
    public AjaxResult get(@RequestParam("id") Long id) {
        return success(shopInfoActivityService.selectShopInfoActivityById(id));
    }
}

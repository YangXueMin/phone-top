package com.ruoyi.shop.controller.api;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.shop.domain.RechargeOrderCoupon;
import com.ruoyi.shop.domain.ShopCard;
import com.ruoyi.shop.service.IShopCardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author yangxuemin
 * @ClassName ShopCardContrllerApi
 * @Description
 * @date 2024/1/14 10:20 AM
 */
@Api("卡券管理")
@RestController
@RequestMapping("/api/shop/card")
public class ShopCardControllerApi extends BaseController {
    @Autowired
    private IShopCardService shopCardService;

    @ApiOperation("获取卡券集合")
    @PostMapping("/findCardList")
    public AjaxResult findCardList(@RequestBody ShopCard shopCard){
        final List<ShopCard> cardList = shopCardService.selectShopCardList(shopCard);
        return success(cardList);
    }

    @ApiOperation("获取卡券详情")
    @GetMapping("/getCard")
    public AjaxResult getCard(@RequestParam("id") Long id){
        return success(shopCardService.selectShopCardById(id));
    }
}

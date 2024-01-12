package com.ruoyi.shop.controller.api;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.shop.domain.Banner;
import com.ruoyi.shop.domain.ShopInfo;
import com.ruoyi.shop.service.IShopInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author yangxuemin
 * @ClassName ShopInfoControllerApi
 * @Description
 * @date 2024/1/11 6:57 PM
 */
@Api("店铺管理")
@RestController
@RequestMapping("/api/shop/info")
public class ShopInfoControllerApi extends BaseController {
    @Autowired
    private IShopInfoService shopInfoService;

    /**
     * 查询banner配置列表
     */
    @ApiOperation("查询banner列表")
    @GetMapping("/list")
    public AjaxResult list() {
        ShopInfo shopInfo = new ShopInfo();
        shopInfo.setStatus("1");
        List<ShopInfo> list = shopInfoService.selectShopInfoListApi(shopInfo);
        return success(list);
    }
}

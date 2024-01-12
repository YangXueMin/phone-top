package com.ruoyi.shop.controller.api;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.shop.domain.Goods;
import com.ruoyi.shop.service.IGoodsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author yangxuemin
 * @ClassName GoodsControllerApi
 * @Description
 * @date 2024/1/11 6:59 PM
 */
@Api("商品管理")
@RestController
@RequestMapping("/api/shop/goods")
public class GoodsControllerApi extends BaseController {
    @Autowired
    private IGoodsService goodsService;

    /**
     * 查询商品管理列表
     */
    @ApiOperation("查询商品管理列表")
    @PostMapping("/list")
    public AjaxResult list() {
        List<Goods> list = goodsService.selectGoodsList(new Goods());
        return success(list);
    }

    /**
     * 根据ID商品管理
     */
    @ApiOperation("根据ID商品管理")
    @GetMapping("/get")
    public AjaxResult get(@RequestParam("id") Long id) {
        return success(goodsService.selectGoodsById(id));
    }
}

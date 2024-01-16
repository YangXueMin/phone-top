package com.ruoyi.shop.controller.api;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.shop.domain.Goods;
import com.ruoyi.shop.domain.GoodsClassify;
import com.ruoyi.shop.mapper.GoodsClassifyMapper;
import com.ruoyi.shop.service.IGoodsClassifyService;
import com.ruoyi.shop.service.IGoodsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @Autowired
    private IGoodsClassifyService goodsClassifyService;

    @ApiOperation("查询商品分类列表")
    @PostMapping("/classifyList")
    public AjaxResult classifyList(GoodsClassify goodsClassify) {
        List<GoodsClassify> list = goodsClassifyService.selectGoodsClassifyList(goodsClassify);
        return success(list);
    }

    /**
     * 获取商品分类树列表
     */
    @ApiOperation("获取商品分类树列表")
    @GetMapping("/classifyTree")
    public AjaxResult classifyTree(GoodsClassify goodsClassify) {
        return success(goodsClassifyService.selectShopTreeList(goodsClassify));
    }

    @ApiOperation("根据ID获取商品分类详情")
    @GetMapping(value = "/getClassify")
    public AjaxResult getClassify(@RequestParam("classId") Long classId) {
        return success(goodsClassifyService.selectGoodsClassifyByClassId(classId));
    }


    /**
     * 查询商品管理列表
     */
    @ApiOperation("查询商品管理列表")
    @PostMapping("/list")
    public AjaxResult list(@RequestBody Goods goods) {
        List<Goods> list = goodsService.selectGoodsListApi(goods);
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

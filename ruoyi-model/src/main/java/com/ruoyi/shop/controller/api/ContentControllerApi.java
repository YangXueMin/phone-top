package com.ruoyi.shop.controller.api;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.shop.domain.ContentType;
import com.ruoyi.shop.domain.ShopContent;
import com.ruoyi.shop.domain.ShopTreeSelect;
import com.ruoyi.shop.service.IContentTypeService;
import com.ruoyi.shop.service.IShopContentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author yangxuemin
 * @ClassName ContentControllerApi
 * @Description
 * @date 2024/1/10 10:28 AM
 */
@Api("内容管理")
@RestController
@RequestMapping("/api/shop/content")
public class ContentControllerApi extends BaseController {
    @Autowired
    private IContentTypeService contentTypeService;
    @Autowired
    private IShopContentService shopContentService;

    /**
     * 查询内容类型树形结构
     */
    @ApiOperation("查询内容类型树形结构")
    @GetMapping("/treeList")
    public AjaxResult treeList(@RequestBody ContentType contentType) {
        List<ShopTreeSelect> list = contentTypeService.selectTreeList(contentType);
        return success(list);
    }

    /**
     * 查询内容类型列表
     */
    @ApiOperation("查询内容类型列表")
    @GetMapping("/typeList")
    public AjaxResult typeList(@RequestBody ContentType contentType) {
        List<ContentType> list = contentTypeService.selectContentTypeList(contentType);
        return success(list);
    }

    /**
     * 查询内容列表
     */
    @ApiOperation("查询内容列表")
    @GetMapping("/list")
    public AjaxResult list(@RequestBody ShopContent shopContent) {
        List<ShopContent> list = shopContentService.selectShopContentList(shopContent);
        return success(list);
    }
}

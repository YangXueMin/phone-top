package com.ruoyi.shop.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.shop.domain.GoodsClassify;
import com.ruoyi.shop.service.IGoodsClassifyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 商品分类Controller
 *
 * @author ruoyi
 * @date 2024-01-05
 */
@Api("商品分类")
@RestController
@RequestMapping("/shop/classify")
public class GoodsClassifyController extends BaseController {
    @Autowired
    private IGoodsClassifyService goodsClassifyService;

    /**
     * 查询商品分类列表
     */
    @ApiOperation("查询商品分类列表")
    @PreAuthorize("@ss.hasPermi('shop:classify:list')")
    @GetMapping("/list")
    public TableDataInfo list(GoodsClassify goodsClassify) {
        List<GoodsClassify> list = goodsClassifyService.selectGoodsClassifyList(goodsClassify);
        return getDataTable(list);
    }

    /**
     * 导出商品分类列表
     */
    @PreAuthorize("@ss.hasPermi('shop:classify:export')")
    @Log(title = "商品分类", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, GoodsClassify goodsClassify) {
        List<GoodsClassify> list = goodsClassifyService.selectGoodsClassifyList(goodsClassify);
        ExcelUtil<GoodsClassify> util = new ExcelUtil<GoodsClassify>(GoodsClassify.class);
        util.exportExcel(response, list, "商品分类数据");
    }

    /**
     * 获取商品分类详细信息
     */
    @ApiOperation("获取商品分类详细信息")
    @ApiImplicitParam(name = "classId", value = "ID", required = true, dataType = "Long", paramType = "path", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermi('shop:classify:query')")
    @GetMapping(value = "/{classId}")
    public AjaxResult getInfo(@PathVariable("classId") Long classId) {
        return success(goodsClassifyService.selectGoodsClassifyByClassId(classId));
    }

    /**
     * 新增商品分类
     */
    @ApiOperation("新增商品分类")
    @PreAuthorize("@ss.hasPermi('shop:classify:add')")
    @Log(title = "商品分类", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody GoodsClassify goodsClassify) {
        if (UserConstants.NOT_UNIQUE.equals(goodsClassifyService.checkNameUnique(goodsClassify))) {
            return error("新增分类'" + goodsClassify.getName() + "'失败，分类名称已存在");
        }
        return toAjax(goodsClassifyService.insertGoodsClassify(goodsClassify));
    }

    /**
     * 修改商品分类
     */
    @ApiOperation("修改商品分类")
    @PreAuthorize("@ss.hasPermi('shop:classify:edit')")
    @Log(title = "商品分类", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody GoodsClassify goodsClassify) {
        if (UserConstants.NOT_UNIQUE.equals(goodsClassifyService.checkNameUnique(goodsClassify))) {
            return error("修改分类'" + goodsClassify.getName() + "'失败，分类名称已存在");
        }
        return toAjax(goodsClassifyService.updateGoodsClassify(goodsClassify));
    }

    /**
     * 删除商品分类
     */
    @ApiOperation("删除商品分类")
    @ApiImplicitParam(name = "classId", value = "ID", required = true, dataType = "Long", paramType = "path", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermi('shop:classify:remove')")
    @Log(title = "商品分类", businessType = BusinessType.DELETE)
    @DeleteMapping("/{classId}")
    public AjaxResult remove(@PathVariable Long classId) {
        if (goodsClassifyService.hasChildByClassId(classId)) {
            return warn("存在下级分类,不允许删除");
        }
        if (goodsClassifyService.checkClassifyExistGoods(classId)) {
            return warn("分类下存在商品,不允许删除");
        }
        if (goodsClassifyService.checkClassifyExistCompanyGoods(classId)) {
            return warn("分类下存在企业商品,不允许删除");
        }
        return toAjax(goodsClassifyService.deleteGoodsClassifyByClassId(classId));
    }

    /**
     * 获取商品分类树列表
     */
    @ApiOperation("获取商品分类树列表")
    @PreAuthorize("@ss.hasPermi('shop:classify:list')")
    @GetMapping("/classifyTree")
    public AjaxResult classifyTree(GoodsClassify goodsClassify) {
        return success(goodsClassifyService.selectShopTreeList(goodsClassify));
    }
}

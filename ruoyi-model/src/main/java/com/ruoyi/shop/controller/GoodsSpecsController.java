package com.ruoyi.shop.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import com.ruoyi.shop.domain.GoodsSpecs;
import com.ruoyi.shop.service.IGoodsSpecsService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 商品规格Controller
 *
 * @author ruoyi
 * @date 2024-01-06
 */
@Api("商品规格")
@RestController
@RequestMapping("/shop/specs")
public class GoodsSpecsController extends BaseController {
    @Autowired
    private IGoodsSpecsService goodsSpecsService;

    /**
     * 查询商品规格列表
     */
    @ApiOperation("查询商品规格列表")
    @PreAuthorize("@ss.hasPermi('shop:specs:list')")
    @GetMapping("/list")
    public TableDataInfo list(GoodsSpecs goodsSpecs)
    {
        startPage();
        List<GoodsSpecs> list = goodsSpecsService.selectGoodsSpecsList(goodsSpecs);
        return getDataTable(list);
    }

    /**
     * 导出商品规格列表
     */
    @PreAuthorize("@ss.hasPermi('shop:specs:export')")
    @Log(title = "商品规格", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, GoodsSpecs goodsSpecs)
    {
        List<GoodsSpecs> list = goodsSpecsService.selectGoodsSpecsList(goodsSpecs);
        ExcelUtil<GoodsSpecs> util = new ExcelUtil<GoodsSpecs>(GoodsSpecs.class);
        util.exportExcel(response, list, "商品规格数据");
    }

    /**
     * 获取商品规格详细信息
     */
    @ApiOperation("获取商品规格详细信息")
    @ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "Long", paramType = "path", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermi('shop:specs:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(goodsSpecsService.selectGoodsSpecsById(id));
    }

    /**
     * 新增商品规格
     */
    @ApiOperation("新增商品规格")
    @PreAuthorize("@ss.hasPermi('shop:specs:add')")
    @Log(title = "商品规格", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody GoodsSpecs goodsSpecs)
    {
        return toAjax(goodsSpecsService.insertGoodsSpecs(goodsSpecs));
    }

    /**
     * 修改商品规格
     */
    @ApiOperation("修改商品规格")
    @PreAuthorize("@ss.hasPermi('shop:specs:edit')")
    @Log(title = "商品规格", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody GoodsSpecs goodsSpecs)
    {
        return toAjax(goodsSpecsService.updateGoodsSpecs(goodsSpecs));
    }

    /**
     * 删除商品规格
     */
    @ApiOperation("删除商品规格")
    @ApiImplicitParam(name = "ids", value = "ID数组", required = true, dataType = "Long[]", paramType = "path", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermi('shop:specs:remove')")
    @Log(title = "商品规格", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(goodsSpecsService.deleteGoodsSpecsByIds(ids));
    }
}

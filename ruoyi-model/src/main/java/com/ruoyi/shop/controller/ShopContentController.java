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
import com.ruoyi.shop.domain.ShopContent;
import com.ruoyi.shop.service.IShopContentService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 内容配置Controller
 *
 * @author ruoyi
 * @date 2024-01-06
 */
@Api("内容配置")
@RestController
@RequestMapping("/shop/content")
public class ShopContentController extends BaseController {
    @Autowired
    private IShopContentService shopContentService;

    /**
     * 查询内容配置列表
     */
    @ApiOperation("查询内容配置列表")
    @PreAuthorize("@ss.hasPermi('shop:content:list')")
    @GetMapping("/list")
    public TableDataInfo list(ShopContent shopContent)
    {
        startPage();
        List<ShopContent> list = shopContentService.selectShopContentList(shopContent);
        return getDataTable(list);
    }

    /**
     * 导出内容配置列表
     */
    @PreAuthorize("@ss.hasPermi('shop:content:export')")
    @Log(title = "内容配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ShopContent shopContent)
    {
        List<ShopContent> list = shopContentService.selectShopContentList(shopContent);
        ExcelUtil<ShopContent> util = new ExcelUtil<ShopContent>(ShopContent.class);
        util.exportExcel(response, list, "内容配置数据");
    }

    /**
     * 获取内容配置详细信息
     */
    @ApiOperation("获取内容配置详细信息")
    @ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "Long", paramType = "path", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermi('shop:content:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(shopContentService.selectShopContentById(id));
    }

    /**
     * 新增内容配置
     */
    @ApiOperation("新增内容配置")
    @PreAuthorize("@ss.hasPermi('shop:content:add')")
    @Log(title = "内容配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ShopContent shopContent)
    {
        return toAjax(shopContentService.insertShopContent(shopContent));
    }

    /**
     * 修改内容配置
     */
    @ApiOperation("修改内容配置")
    @PreAuthorize("@ss.hasPermi('shop:content:edit')")
    @Log(title = "内容配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ShopContent shopContent)
    {
        return toAjax(shopContentService.updateShopContent(shopContent));
    }

    /**
     * 删除内容配置
     */
    @ApiOperation("删除内容配置")
    @ApiImplicitParam(name = "ids", value = "ID数组", required = true, dataType = "Long[]", paramType = "path", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermi('shop:content:remove')")
    @Log(title = "内容配置", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(shopContentService.deleteShopContentByIds(ids));
    }
}

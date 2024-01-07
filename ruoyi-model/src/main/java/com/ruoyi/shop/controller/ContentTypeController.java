package com.ruoyi.shop.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.shop.domain.ContentType;
import com.ruoyi.shop.service.IContentTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 内容分类Controller
 *
 * @author ruoyi
 * @date 2024-01-07
 */
@Api("内容分类")
@RestController
@RequestMapping("/shop/contentType")
public class ContentTypeController extends BaseController {
    @Autowired
    private IContentTypeService contentTypeService;

    /**
     * 查询内容分类列表
     */
    @ApiOperation("查询内容分类列表")
    @PreAuthorize("@ss.hasPermi('shop:contentType:list')")
    @GetMapping("/list")
    public TableDataInfo list(ContentType contentType) {
        startPage();
        List<ContentType> list = contentTypeService.selectContentTypeList(contentType);
        return getDataTable(list);
    }

    /**
     * 导出内容分类列表
     */
    @PreAuthorize("@ss.hasPermi('shop:contentType:export')")
    @Log(title = "内容分类", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ContentType contentType) {
        List<ContentType> list = contentTypeService.selectContentTypeList(contentType);
        ExcelUtil<ContentType> util = new ExcelUtil<ContentType>(ContentType.class);
        util.exportExcel(response, list, "内容分类数据");
    }

    /**
     * 获取内容分类详细信息
     */
    @ApiOperation("获取内容分类详细信息")
    @ApiImplicitParam(name = "typeId", value = "ID", required = true, dataType = "Long", paramType = "path", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermi('shop:contentType:query')")
    @GetMapping(value = "/{typeId}")
    public AjaxResult getInfo(@PathVariable("typeId") Long typeId) {
        return success(contentTypeService.selectContentTypeByTypeId(typeId));
    }

    /**
     * 新增内容分类
     */
    @ApiOperation("新增内容分类")
    @PreAuthorize("@ss.hasPermi('shop:contentType:add')")
    @Log(title = "内容分类", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ContentType contentType) {
        return toAjax(contentTypeService.insertContentType(contentType));
    }

    /**
     * 修改内容分类
     */
    @ApiOperation("修改内容分类")
    @PreAuthorize("@ss.hasPermi('shop:contentType:edit')")
    @Log(title = "内容分类", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ContentType contentType) {
        return toAjax(contentTypeService.updateContentType(contentType));
    }

    /**
     * 删除内容分类
     */
    @ApiOperation("删除内容分类")
    @ApiImplicitParam(name = "typeIds", value = "ID数组", required = true, dataType = "Long[]", paramType = "path", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermi('shop:contentType:remove')")
    @Log(title = "内容分类", businessType = BusinessType.DELETE)
    @DeleteMapping("/{typeIds}")
    public AjaxResult remove(@PathVariable Long[] typeIds) {
        return toAjax(contentTypeService.deleteContentTypeByTypeIds(typeIds));
    }

    /**
     * 获取内容分类树列表
     */
    @ApiOperation("获取内容分类树列表")
    @PreAuthorize("@ss.hasPermi('shop:contentType:list')")
    @GetMapping("/typeTree")
    public AjaxResult typeTree(ContentType contentType) {
        return success(contentTypeService.selectTreeList(contentType));
    }
}

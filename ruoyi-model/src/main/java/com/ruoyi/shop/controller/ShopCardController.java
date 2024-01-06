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
import com.ruoyi.shop.domain.ShopCard;
import com.ruoyi.shop.service.IShopCardService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 储值卡Controller
 *
 * @author ruoyi
 * @date 2024-01-06
 */
@Api("储值卡")
@RestController
@RequestMapping("/shop/card")
public class ShopCardController extends BaseController {
    @Autowired
    private IShopCardService shopCardService;

    /**
     * 查询储值卡列表
     */
    @ApiOperation("查询储值卡列表")
    @PreAuthorize("@ss.hasPermi('shop:card:list')")
    @GetMapping("/list")
    public TableDataInfo list(ShopCard shopCard)
    {
        startPage();
        List<ShopCard> list = shopCardService.selectShopCardList(shopCard);
        return getDataTable(list);
    }

    /**
     * 导出储值卡列表
     */
    @PreAuthorize("@ss.hasPermi('shop:card:export')")
    @Log(title = "储值卡", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ShopCard shopCard)
    {
        List<ShopCard> list = shopCardService.selectShopCardList(shopCard);
        ExcelUtil<ShopCard> util = new ExcelUtil<ShopCard>(ShopCard.class);
        util.exportExcel(response, list, "储值卡数据");
    }

    /**
     * 获取储值卡详细信息
     */
    @ApiOperation("获取储值卡详细信息")
    @ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "Long", paramType = "path", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermi('shop:card:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(shopCardService.selectShopCardById(id));
    }

    /**
     * 新增储值卡
     */
    @ApiOperation("新增储值卡")
    @PreAuthorize("@ss.hasPermi('shop:card:add')")
    @Log(title = "储值卡", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ShopCard shopCard)
    {
        return toAjax(shopCardService.insertShopCard(shopCard));
    }

    /**
     * 修改储值卡
     */
    @ApiOperation("修改储值卡")
    @PreAuthorize("@ss.hasPermi('shop:card:edit')")
    @Log(title = "储值卡", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ShopCard shopCard)
    {
        return toAjax(shopCardService.updateShopCard(shopCard));
    }

    /**
     * 删除储值卡
     */
    @ApiOperation("删除储值卡")
    @ApiImplicitParam(name = "ids", value = "ID数组", required = true, dataType = "Long[]", paramType = "path", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermi('shop:card:remove')")
    @Log(title = "储值卡", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(shopCardService.deleteShopCardByIds(ids));
    }
}

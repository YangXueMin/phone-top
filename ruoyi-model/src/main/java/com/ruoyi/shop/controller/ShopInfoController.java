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
import com.ruoyi.shop.domain.ShopInfo;
import com.ruoyi.shop.service.IShopInfoService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 店铺信息Controller
 *
 * @author ruoyi
 * @date 2024-01-04
 */
@RestController
@RequestMapping("/shop/info")
public class ShopInfoController extends BaseController {
    @Autowired
    private IShopInfoService shopInfoService;

    /**
     * 查询店铺信息列表
     */
    @PreAuthorize("@ss.hasPermi('shop:info:list')")
    @GetMapping("/list")
    public TableDataInfo list(ShopInfo shopInfo) {
        startPage();
        List<ShopInfo> list = shopInfoService.selectShopInfoList(shopInfo);
        return getDataTable(list);
    }

    /**
     * 导出店铺信息列表
     */
    @PreAuthorize("@ss.hasPermi('shop:info:export')")
    @Log(title = "店铺信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ShopInfo shopInfo) {
        List<ShopInfo> list = shopInfoService.selectShopInfoList(shopInfo);
        ExcelUtil<ShopInfo> util = new ExcelUtil<ShopInfo>(ShopInfo.class);
        util.exportExcel(response, list, "店铺信息数据");
    }

    /**
     * 获取店铺信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('shop:info:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(shopInfoService.selectShopInfoById(id));
    }

    /**
     * 新增店铺信息
     */
    @PreAuthorize("@ss.hasPermi('shop:info:add')")
    @Log(title = "店铺信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ShopInfo shopInfo) {
        return toAjax(shopInfoService.insertShopInfo(shopInfo));
    }

    /**
     * 修改店铺信息
     */
    @PreAuthorize("@ss.hasPermi('shop:info:edit')")
    @Log(title = "店铺信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ShopInfo shopInfo) {
        return toAjax(shopInfoService.updateShopInfo(shopInfo));
    }

    /**
     * 删除店铺信息
     */
    @PreAuthorize("@ss.hasPermi('shop:info:remove')")
    @Log(title = "店铺信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(shopInfoService.deleteShopInfoByIds(ids));
    }
}

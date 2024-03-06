package com.ruoyi.phone.controller;

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
import com.ruoyi.phone.domain.PhoneBanner;
import com.ruoyi.phone.service.IPhoneBannerService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * banner轮播配置Controller
 *
 * @author ruoyi
 * @date 2024-03-04
 */
@Api("banner轮播配置")
@RestController
@RequestMapping("/phone/banner")
public class PhoneBannerController extends BaseController {
    @Autowired
    private IPhoneBannerService phoneBannerService;

    /**
     * 查询banner轮播配置列表
     */
    @ApiOperation("查询banner轮播配置列表")
    @PreAuthorize("@ss.hasPermi('phone:banner:list')")
    @GetMapping("/list")
    public TableDataInfo list(PhoneBanner phoneBanner) {
        startPage();
        List<PhoneBanner> list = phoneBannerService.selectPhoneBannerList(phoneBanner);
        return getDataTable(list);
    }

    /**
     * 导出banner轮播配置列表
     */
    @PreAuthorize("@ss.hasPermi('phone:banner:export')")
    @Log(title = "banner轮播配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PhoneBanner phoneBanner) {
        List<PhoneBanner> list = phoneBannerService.selectPhoneBannerList(phoneBanner);
        ExcelUtil<PhoneBanner> util = new ExcelUtil<PhoneBanner>(PhoneBanner.class);
        util.exportExcel(response, list, "banner轮播配置数据");
    }

    /**
     * 获取banner轮播配置详细信息
     */
    @ApiOperation("获取banner轮播配置详细信息")
    @ApiImplicitParam(name = "id", value = "ID", required = true, dataType = "Long", paramType = "path", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermi('phone:banner:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(phoneBannerService.selectPhoneBannerById(id));
    }

    /**
     * 新增banner轮播配置
     */
    @ApiOperation("新增banner轮播配置")
    @PreAuthorize("@ss.hasPermi('phone:banner:add')")
    @Log(title = "banner轮播配置", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PhoneBanner phoneBanner) {
        return toAjax(phoneBannerService.insertPhoneBanner(phoneBanner));
    }

    /**
     * 修改banner轮播配置
     */
    @ApiOperation("修改banner轮播配置")
    @PreAuthorize("@ss.hasPermi('phone:banner:edit')")
    @Log(title = "banner轮播配置", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PhoneBanner phoneBanner) {
        return toAjax(phoneBannerService.updatePhoneBanner(phoneBanner));
    }

    /**
     * 删除banner轮播配置
     */
    @ApiOperation("删除banner轮播配置")
    @ApiImplicitParam(name = "ids", value = "ID数组", required = true, dataType = "Long[]", paramType = "path", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermi('phone:banner:remove')")
    @Log(title = "banner轮播配置", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(phoneBannerService.deletePhoneBannerByIds(ids));
    }
}

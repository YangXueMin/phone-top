package com.ruoyi.shop.controller.api;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.shop.domain.Banner;
import com.ruoyi.shop.service.IBannerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author yangxuemin
 * @ClassName BannerControllerApi
 * @Description
 * @date 2024/1/9 9:24 AM
 */
@Api("banner管理")
@RestController
@RequestMapping("/api/shop/banner")
public class BannerControllerApi extends BaseController {
    @Autowired
    private IBannerService bannerService;

    /**
     * 查询banner配置列表
     */
    @ApiOperation("查询banner列表")
    @GetMapping("/list")
    public AjaxResult list(@RequestBody Banner banner) {
        List<Banner> list = bannerService.selectBannerList(banner);
        return success(list);
    }
}

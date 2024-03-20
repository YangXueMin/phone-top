package com.ruoyi.phone.controller.api;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.phone.domain.PhoneMenuConfig;
import com.ruoyi.phone.service.IPhoneMenuConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author ruoyi
 * @ClassName MenuControllerApi
 * @Description
 * @date 2024/3/11 9:20 PM
 */
@Api("菜单管理")
@RestController
@RequestMapping("/api/phone/menu")
public class MenuControllerApi extends BaseController {
    private final IPhoneMenuConfigService phoneMenuConfigService;

    public MenuControllerApi(IPhoneMenuConfigService phoneMenuConfigService) {
        this.phoneMenuConfigService = phoneMenuConfigService;
    }

    /**
     * 查询小程序菜单配置列表
     */
    @ApiOperation("查询小程序菜单配置列表")
    @GetMapping("/list")
    public AjaxResult list(PhoneMenuConfig phoneMenuConfig) {
        List<PhoneMenuConfig> list = phoneMenuConfigService.selectPhoneMenuConfigList(phoneMenuConfig);
        return success(list);
    }
}

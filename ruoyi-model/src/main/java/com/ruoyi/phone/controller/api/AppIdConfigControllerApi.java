package com.ruoyi.phone.controller.api;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.WechatConfig;
import com.ruoyi.phone.service.IPhoneBannerService;
import com.ruoyi.phone.service.IPhoneCompanyConfigService;
import com.ruoyi.phone.service.IPhoneCustomerService;
import com.ruoyi.phone.service.IPhoneMemberNowService;
import com.ruoyi.system.service.IWechatConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ruoyi
 * @ClassName AppIdConfigControllerApi
 * @Description
 * @date 2024/3/5 1:47 PM
 */
@Api("基础数据获取")
@RestController
@RequestMapping("/api/phone/config")
public class AppIdConfigControllerApi extends BaseController {
    private final IPhoneBannerService phoneBannerService;
    private final IPhoneCompanyConfigService phoneCompanyConfigService;
    private final IPhoneCustomerService phoneCustomerService;
    private final IWechatConfigService wechatConfigService;
    private final IPhoneMemberNowService phoneMemberNowService;

    public AppIdConfigControllerApi(IPhoneBannerService phoneBannerService, IPhoneCompanyConfigService phoneCompanyConfigService, IPhoneCustomerService phoneCustomerService, IWechatConfigService wechatConfigService, IPhoneMemberNowService phoneMemberNowService) {
        this.phoneBannerService = phoneBannerService;
        this.phoneCompanyConfigService = phoneCompanyConfigService;
        this.phoneCustomerService = phoneCustomerService;
        this.wechatConfigService = wechatConfigService;
        this.phoneMemberNowService = phoneMemberNowService;
    }

    /**
     * 查询banner配置列表
     */
    @ApiOperation("查询banner列表")
    @GetMapping("/banner")
    public AjaxResult banner(@RequestParam("appId") String appId) {
        return success(phoneBannerService.selectPhoneBannerByAppId(appId));
    }

    /**
     * 查询公司配置信息
     */
    @ApiOperation("查询公司配置信息")
    @GetMapping("/companyConfig")
    public AjaxResult companyConfig(@RequestParam("appId") String appId) {
        return success(phoneCompanyConfigService.selectPhoneCompanyConfigByAppId(appId));
    }

    /**
     * 获取客服配置
     */
    @ApiOperation("获取客服配置")
    @GetMapping("/customer")
    public AjaxResult customer(@RequestParam("appId") String appId) {
        return success(phoneCustomerService.selectPhoneCustomerByAppId(appId));
    }

    /**
     * 获取微信配置
     */
    @ApiOperation("获取微信配置")
    @GetMapping("/wechatConfig")
    public AjaxResult wechatConfig(@RequestParam("appId") String appId) {
        final WechatConfig wechatConfig = wechatConfigService.selectWechatConfigByAppId(appId);
        if (wechatConfig != null) {
            wechatConfig.setAppSecret(null);
            wechatConfig.setMchId(null);
            wechatConfig.setMchKey(null);
            wechatConfig.setAesKey(null);
            wechatConfig.setKeyPath(null);
        }
        return success(wechatConfig);
    }

    /**
     * 获取用户须知信息
     */
    @ApiOperation("获取用户须知信息")
    @GetMapping("/phoneMemberNow")
    public AjaxResult phoneMemberNow(@RequestParam("appId") String appId) {
        return success(phoneMemberNowService.selectPhoneMemberNowByAppId(appId));
    }
}

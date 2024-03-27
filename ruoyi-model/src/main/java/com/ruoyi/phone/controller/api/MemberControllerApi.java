package com.ruoyi.phone.controller.api;

import com.alibaba.fastjson2.JSON;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.Member;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.web.service.TokenService;
import com.ruoyi.phone.domain.PhoneCompanyConfig;
import com.ruoyi.phone.service.IPhoneCompanyConfigService;
import com.ruoyi.system.service.IMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Base64;

/**
 * @author ruoyi
 * @ClassName MemberControllerApi
 * @Description
 * @date 2024/1/10 10:33 AM
 */
@Api("会员管理")
@RestController
@RequestMapping("/api/phone/member")
@Slf4j
public class MemberControllerApi extends BaseController {
    @Autowired
    private IMemberService memberService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private IPhoneCompanyConfigService companyConfigService;

    /**
     * 更新会员信息
     */
    @ApiOperation("更新会员信息")
    @PostMapping("/update")
    public AjaxResult update(@RequestBody Member member) {
        if (StringUtils.isNotEmpty(member.getMobile())
                && UserConstants.NOT_UNIQUE.equals(memberService.checkMobileUnique(member))) {
            return error("修改用户'" + member.getName() + "'失败，手机号码已存在");
        }
        if (StringUtils.isNotBlank(member.getPassword())) {
            member.setPassword(SecurityUtils.encryptPassword(member.getPassword()));
        }
        final int i = memberService.updateMember(member);
        return success(i);
    }

    /**
     * 会员修改密码
     */
    @Log(title = "会员修改密码", businessType = BusinessType.UPDATE)
    @GetMapping("/updateMemberPwd")
    public AjaxResult updatePwd(String oldPassword, String newPassword) {
        LoginUser loginUser = getLoginUser();
        Long userId = loginUser.getUserId();
        String password = loginUser.getMember().getPassword();
        if (!SecurityUtils.matchesPassword(oldPassword, password)) {
            return error("修改密码失败，旧密码错误");
        }
        if (SecurityUtils.matchesPassword(newPassword, password)) {
            return error("新密码不能与旧密码相同");
        }
        if (memberService.resetMemberPwd(userId, SecurityUtils.encryptPassword(newPassword)) > 0) {
            // 更新缓存用户密码
            loginUser.getMember().setPassword(SecurityUtils.encryptPassword(newPassword));
            tokenService.setLoginUser(loginUser);
            return success();
        }
        return error("修改密码异常，请联系管理员");
    }

    /**
     * 获取会员信息
     */
    @ApiOperation("获取会员信息")
    @PostMapping("/getMemberInfo")
    public AjaxResult getMemberInfo() {
        return success(memberService.getMemberInfo());
    }

    /**
     * 获取会员下级
     */
    @ApiOperation("获取会员下级")
    @GetMapping("/findSubordinateList")
    public AjaxResult findSubordinateList(@RequestParam("type") String type) {
        return success(memberService.findSubordinateList(type));
    }

    /**
     * 生成推广二维码
     */
    @ApiOperation("生成推广二维码")
    @PostMapping("/getQrCode")
    public AjaxResult getQrCode() {
        return success(memberService.getQrCode());
    }

    /**
     * 合并图片
     */
    @ApiOperation("生成合并后的推广二维码")
    @PostMapping("/getInviteQrCode")
    public AjaxResult invitePicture() {
        try {
            Member member = SecurityUtils.getLoginUser().getMember();
            if (member != null) {
                PhoneCompanyConfig phoneCompanyConfig = companyConfigService.selectPhoneCompanyConfigByAppId(member.getAppId());
                if (phoneCompanyConfig != null) {
                    BufferedImage baseImage = ImageIO.read(new URL(phoneCompanyConfig.getPromotionPoster()));

                    //获取二维码
                    String qrCode = memberService.getQrCode();
                    //设置二维码宽和高
                    BufferedImage originalImage = ImageIO.read(new URL(qrCode));
                    BufferedImage topImage = new BufferedImage(phoneCompanyConfig.getCodeWidth(), phoneCompanyConfig.getCodeHeight(), originalImage.getType());
                    topImage.getGraphics().drawImage(originalImage.getScaledInstance(phoneCompanyConfig.getCodeWidth(), phoneCompanyConfig.getCodeHeight(), Image.SCALE_SMOOTH), 0, 0, null);


                    //创建一个新的图像，大小与地图相同
                    BufferedImage resultImage = new BufferedImage(baseImage.getWidth(), baseImage.getHeight(), BufferedImage.TYPE_INT_RGB);
                    //获取Graphics2D对象，用于绘制图像
                    Graphics2D g2d = resultImage.createGraphics();
                    //绘制地图
                    g2d.drawImage(baseImage, 0, 0, null);
                    //绘制顶图  170 700
                    g2d.drawImage(topImage, phoneCompanyConfig.getxAxis(), phoneCompanyConfig.getyAxis(), null);

                    // 将 BufferedImage 对象写入 ByteArrayOutputStream
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    ImageIO.write(resultImage, "jpg", baos);

                    byte[] imageBytes = baos.toByteArray();
                    String base64 = Base64.getEncoder().encodeToString(imageBytes);
                    //释放资源
                    g2d.dispose();
                    return AjaxResult.success("合并图片成功", base64);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return AjaxResult.error("图片合并失败");
    }

}

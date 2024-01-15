package com.ruoyi.common.utils.qrCode;

import cn.hutool.extra.qrcode.QrCodeUtil;
import cn.hutool.extra.qrcode.QrConfig;
import com.ruoyi.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author yangxuemin
 * @ClassName EwmUtils
 * @Description 二维码工具类
 * @date 2024/1/15 10:50 PM
 */
@Component
public class EwmUtils {
    @Autowired
    private QrConfig qrConfig;

    /**
     * 二维码生成base64字符串
     * @param content 跳转地址(即扫码后跳转的地址,可以是后端项目地址,用户扫码后填写数据提交到后台)
     * @param imageType 图片类型
     */
    public String generateBase64(String content,String imageType) {
        if(StringUtils.isBlank(content)){
            return "生成二维码数据不能为空";
        }
        return QrCodeUtil.generateAsBase64(content, qrConfig, imageType);
    }
}

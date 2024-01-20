package com.ruoyi.common.utils.qrCode;

import cn.hutool.extra.qrcode.QrConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.awt.*;

/**
 * @author yangxuemin
 * @ClassName EwmCode
 * @Description 二维码配置类
 * @date 2024/1/15 10:49 PM
 */
@Configuration
public class EwmCode {
    @Bean
    public QrConfig qrConfig(){
        QrConfig qrConfig=new QrConfig();
        qrConfig.setBackColor(Color.white.getRGB());
        qrConfig.setForeColor(Color.black.getRGB());
        qrConfig.setWidth(200);
        qrConfig.setHeight(200);
        return qrConfig;
    }
}

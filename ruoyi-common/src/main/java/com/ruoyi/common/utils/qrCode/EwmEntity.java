package com.ruoyi.common.utils.qrCode;

import lombok.ToString;

/**
 * @author yangxuemin
 * @ClassName EwmEntity
 * @Description
 * @date 2024/1/15 10:56 PM
 */
@ToString
public class EwmEntity {
    /**
     * 跳转地址(即扫码后跳转的地址,可以是后端项目地址,用户扫码后填写数据提交到后台)
     */
    private String content;

    /**
     * 图片类型
     */
    private String imageType;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }
}

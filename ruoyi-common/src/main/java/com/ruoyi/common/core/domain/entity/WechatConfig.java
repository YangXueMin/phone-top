package com.ruoyi.common.core.domain.entity;

import com.ruoyi.common.annotation.Excel;
import lombok.ToString;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 微信配置对象 phone_wechat_config
 *
 * @author ruoyi
 * @date 2024-03-01
 */
@ToString
public class WechatConfig extends BaseEntity{
    private static final long serialVersionUID=1L;

    /** 主键 */
    private Long id;

    /** 公司ID */
    @Excel(name = "公司ID")
    private Long companyId;

    /** 公众号名称 */
    @Excel(name = "公众号名称")
    private String title;

    /** 公众号APPID */
    @Excel(name = "公众号APPID")
    private String appId;

    /** 公众号appsecret */
    @Excel(name = "公众号appsecret")
    private String appSecret;

    /** 公众号token */
    @Excel(name = "公众号token")
    private String token;

    /** 信公众号的EncodingAESKey */
    @Excel(name = "信公众号的EncodingAESKey")
    private String aesKey;

    /** 微信支付商户号 */
    @Excel(name = "微信支付商户号")
    private String mchId;

    /** 微信支付商户密钥 */
    @Excel(name = "微信支付商户密钥")
    private String mchKey;

    /** p12证书的位置 */
    @Excel(name = "p12证书的位置")
    private String keyPath;

    public void setId(Long id){
        this.id = id;
    }

    public Long getId(){
        return id;
    }
    public void setCompanyId(Long companyId){
        this.companyId = companyId;
    }

    public Long getCompanyId(){
        return companyId;
    }
    public void setAppId(String appId){
        this.appId = appId;
    }

    public String getAppId(){
        return appId;
    }
    public void setAppSecret(String appSecret){
        this.appSecret = appSecret;
    }

    public String getAppSecret(){
        return appSecret;
    }
    public void setAesKey(String aesKey){
        this.aesKey = aesKey;
    }

    public String getAesKey(){
        return aesKey;
    }
    public void setMchId(String mchId){
        this.mchId = mchId;
    }

    public String getMchId(){
        return mchId;
    }
    public void setMchKey(String mchKey){
        this.mchKey = mchKey;
    }

    public String getMchKey(){
        return mchKey;
    }
    public void setKeyPath(String keyPath){
        this.keyPath = keyPath;
    }

    public String getKeyPath(){
        return keyPath;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

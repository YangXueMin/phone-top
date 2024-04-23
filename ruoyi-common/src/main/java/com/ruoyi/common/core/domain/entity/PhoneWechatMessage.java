package com.ruoyi.common.core.domain.entity;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.utils.DictUtils;
import lombok.ToString;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.StringUtils;

/**
 * 微信公众号消息类型配置对象 phone_wechat_message
 *
 * @author ruoyi
 * @date 2024-03-25
 */
@ToString
public class PhoneWechatMessage extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 公司ID
     */
    @Excel(name = "公司ID")
    private Long deptId;

    /**
     * 公众号APPID
     */
    @Excel(name = "公众号APPID")
    private String appId;

    /**
     * 触发方式（关注公众号、关键字回复）字典：phone_wechat_touch_type
     */
    @Excel(name = "触发方式", readConverterExp = "关注公众号、关键字回复")
    private String touchType;

    /**
     * 触发方式（关注公众号、关键字回复）字典：phone_wechat_touch_type
     */
    @Excel(name = "触发方式", readConverterExp = "关注公众号、关键字回复")
    private String touchTypeLabel;


    /**
     * 匹配类型（模糊匹配、精准匹配）字典：phone_wechat_key_word_type
     */
    @Excel(name = "匹配类型", readConverterExp = "模糊匹配、精准匹配")
    private String matchingType;

    /**
     * 匹配类型（模糊匹配、精准匹配）
     */
    @Excel(name = "匹配类型", readConverterExp = "模糊匹配、精准匹配")
    private String matchingTypeLabel;

    /**
     * 关键字
     */
    @Excel(name = "关键字")
    private String keyWord;

    /**
     * 消息类型（文本消息、图片消息、语音消息、视频消息、音乐消息、图文消息） 字典：phone_wechat_msg_type
     */
    @Excel(name = "消息类型", readConverterExp = "文本消息、图片消息、语音消息、视频消息、音乐消息、图文消息")
    private String msgType;

    /**
     * 消息类型（文本消息、图片消息、语音消息、视频消息、音乐消息、图文消息） 字典：phone_wechat_msg_type
     */
    @Excel(name = "消息类型", readConverterExp = "文=本消息、图片消息、语音消息、视频消息、音乐消息、图文消息")
    private String msgTypeLabel;

    /**
     * 标题
     */
    @Excel(name = "标题")
    private String title;

    /**
     * 描述
     */
    @Excel(name = "描述")
    private String description;

    /**
     * 文件链接
     */
    @Excel(name = "文件链接")
    private String picUrl;

    /**
     * 高清路径
     */
    @Excel(name = "高清路径")
    private String hqPicUrl;

    /**
     * 点击图文消息跳转链接
     */
    @Excel(name = "点击图文消息跳转链接")
    private String url;

    /**
     * 回复内容
     */
    @Excel(name = "回复内容")
    private String content;

    /**
     * 素材ID
     */
    @Excel(name = "素材ID（图片、语音、视频、音乐必填）")
    private String mediaId;

    /**
     * 是否启用 字典：phone_status
     */
    @Excel(name = "是否启用 字典：phone_status")
    private String status;

    /**
     * 是否启用 字典：phone_status
     */
    @Excel(name = "是否启用 字典：phone_status")
    private String statusLabel;

    private WechatConfig wechatConfig;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppId() {
        return appId;
    }

    public void setTouchType(String touchType) {
        this.touchType = touchType;
    }

    public String getTouchType() {
        return touchType;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMatchingType(String matchingType) {
        this.matchingType = matchingType;
    }

    public String getMatchingType() {
        return matchingType;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setHqPicUrl(String hqPicUrl) {
        this.hqPicUrl = hqPicUrl;
    }

    public String getHqPicUrl() {
        return hqPicUrl;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public String getMediaId() {
        return mediaId;
    }

    public WechatConfig getWechatConfig() {
        return wechatConfig;
    }

    public void setWechatConfig(WechatConfig wechatConfig) {
        this.wechatConfig = wechatConfig;
    }

    public String getTouchTypeLabel() {
        if (StringUtils.isNotBlank(touchType)) {
            return DictUtils.getDictLabel("phone_wechat_touch_type", touchType);
        }
        return touchTypeLabel;
    }

    public void setTouchTypeLabel(String touchTypeLabel) {
        this.touchTypeLabel = touchTypeLabel;
    }

    public String getMsgTypeLabel() {
        if (StringUtils.isNotBlank(msgType)) {
            return DictUtils.getDictLabel("phone_wechat_msg_type", msgType);
        }
        return msgTypeLabel;
    }

    public void setMsgTypeLabel(String msgTypeLabel) {
        this.msgTypeLabel = msgTypeLabel;
    }

    public String getMatchingTypeLabel() {
        if (StringUtils.isNotBlank(matchingType)) {
            return DictUtils.getDictLabel("phone_wechat_key_word_type", matchingType);
        }
        return matchingTypeLabel;
    }

    public void setMatchingTypeLabel(String matchingTypeLabel) {
        this.matchingTypeLabel = matchingTypeLabel;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusLabel() {
        if (StringUtils.isNotBlank(status)) {
            return DictUtils.getDictLabel("phone_status", status);
        }
        return statusLabel;
    }

    public void setStatusLabel(String statusLabel) {
        this.statusLabel = statusLabel;
    }
}

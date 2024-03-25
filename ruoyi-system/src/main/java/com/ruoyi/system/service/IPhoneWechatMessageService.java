package com.ruoyi.system.service;

import com.ruoyi.common.core.domain.entity.PhoneWechatMessage;

import java.util.List;

/**
 * 微信公众号消息类型配置Service接口
 *
 * @author ruoyi
 * @date 2024-03-25
 */
public interface IPhoneWechatMessageService {
    /**
     * 查询微信公众号消息类型配置
     *
     * @param id 微信公众号消息类型配置主键
     * @return 微信公众号消息类型配置
     */
    public PhoneWechatMessage selectPhoneWechatMessageById(Long id);

    /**
     * 查询微信公众号消息类型配置列表
     *
     * @param phoneWechatMessage 微信公众号消息类型配置
     * @return 微信公众号消息类型配置集合
     */
    public List<PhoneWechatMessage> selectPhoneWechatMessageList(PhoneWechatMessage phoneWechatMessage);

    /**
     * 新增微信公众号消息类型配置
     *
     * @param phoneWechatMessage 微信公众号消息类型配置
     * @return 结果
     */
    public int insertPhoneWechatMessage(PhoneWechatMessage phoneWechatMessage);

    /**
     * 修改微信公众号消息类型配置
     *
     * @param phoneWechatMessage 微信公众号消息类型配置
     * @return 结果
     */
    public int updatePhoneWechatMessage(PhoneWechatMessage phoneWechatMessage);

    /**
     * 批量删除微信公众号消息类型配置
     *
     * @param ids 需要删除的微信公众号消息类型配置主键集合
     * @return 结果
     */
    public int deletePhoneWechatMessageByIds(Long[] ids);

    /**
     * 删除微信公众号消息类型配置信息
     *
     * @param id 微信公众号消息类型配置主键
     * @return 结果
     */
    public int deletePhoneWechatMessageById(Long id);
}

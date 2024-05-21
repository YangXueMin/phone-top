package com.ruoyi.system.service.impl;

import java.util.List;

import com.ruoyi.common.config.WechatConfiguration;
import com.ruoyi.common.config.WechatTestConfiguration;
import com.ruoyi.common.core.domain.entity.PhoneWechatMessage;
import com.ruoyi.common.core.domain.entity.WechatConfig;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.service.IWechatConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.PhoneWechatMessageMapper;
import com.ruoyi.system.service.IPhoneWechatMessageService;

/**
 * 微信公众号消息类型配置Service业务层处理
 *
 * @author ruoyi
 * @date 2024-03-25
 */
@Service
public class PhoneWechatMessageServiceImpl implements IPhoneWechatMessageService {
    @Autowired
    private PhoneWechatMessageMapper phoneWechatMessageMapper;
    @Autowired
    private WechatTestConfiguration wechatTestConfiguration;
    @Autowired
    private IWechatConfigService wechatConfigService;

    /**
     * 查询微信公众号消息类型配置
     *
     * @param id 微信公众号消息类型配置主键
     * @return 微信公众号消息类型配置
     */
    @Override
    public PhoneWechatMessage selectPhoneWechatMessageById(Long id) {
        return phoneWechatMessageMapper.selectPhoneWechatMessageById(id);
    }

    /**
     * 查询微信公众号消息类型配置列表
     *
     * @param phoneWechatMessage 微信公众号消息类型配置
     * @return 微信公众号消息类型配置
     */
    @Override
    public List<PhoneWechatMessage> selectPhoneWechatMessageList(PhoneWechatMessage phoneWechatMessage) {
        return phoneWechatMessageMapper.selectPhoneWechatMessageList(phoneWechatMessage);
    }

    /**
     * 新增微信公众号消息类型配置
     *
     * @param phoneWechatMessage 微信公众号消息类型配置
     * @return 结果
     */
    @Override
    public int insertPhoneWechatMessage(PhoneWechatMessage phoneWechatMessage) {
        phoneWechatMessage.setCreateTime(DateUtils.getNowDate());
        final WechatConfig wechatConfig = wechatConfigService.selectWechatConfigByAppId(phoneWechatMessage.getAppId());
        phoneWechatMessage.setDeptId(wechatConfig.getDeptId());
        return phoneWechatMessageMapper.insertPhoneWechatMessage(phoneWechatMessage);
    }

    /**
     * 修改微信公众号消息类型配置
     *
     * @param phoneWechatMessage 微信公众号消息类型配置
     * @return 结果
     */
    @Override
    public int updatePhoneWechatMessage(PhoneWechatMessage phoneWechatMessage) {
        phoneWechatMessage.setUpdateTime(DateUtils.getNowDate());
        return phoneWechatMessageMapper.updatePhoneWechatMessage(phoneWechatMessage);
    }

    /**
     * 批量删除微信公众号消息类型配置
     *
     * @param ids 需要删除的微信公众号消息类型配置主键
     * @return 结果
     */
    @Override
    public int deletePhoneWechatMessageByIds(Long[] ids) {
        return phoneWechatMessageMapper.deletePhoneWechatMessageByIds(ids);
    }

    /**
     * 删除微信公众号消息类型配置信息
     *
     * @param id 微信公众号消息类型配置主键
     * @return 结果
     */
    @Override
    public int deletePhoneWechatMessageById(Long id) {
        return phoneWechatMessageMapper.deletePhoneWechatMessageById(id);
    }

    @Override
    public void findMaterialList(String appId) {
        wechatTestConfiguration.wxMpService().switchoverTo(appId).getMaterialService();
    }
}

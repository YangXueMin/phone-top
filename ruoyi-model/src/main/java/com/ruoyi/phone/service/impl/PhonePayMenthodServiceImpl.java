package com.ruoyi.phone.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.ruoyi.common.annotation.DataScope;
import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.common.core.domain.entity.WechatConfig;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.DictUtils;
import com.ruoyi.phone.domain.PhoneMenu;
import com.ruoyi.phone.domain.PhoneMenuApp;
import com.ruoyi.system.service.IWechatConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.phone.mapper.PhonePayMenthodMapper;
import com.ruoyi.phone.domain.PhonePayMenthod;
import com.ruoyi.phone.service.IPhonePayMenthodService;

/**
 * 充值方式配置Service业务层处理
 *
 * @author ruoyi
 * @date 2024-04-11
 */
@Service
public class PhonePayMenthodServiceImpl implements IPhonePayMenthodService {
    @Autowired
    private PhonePayMenthodMapper phonePayMenthodMapper;

    @Autowired
    private IWechatConfigService wechatConfigService;

    /**
     * 查询充值方式配置
     *
     * @param id 充值方式配置主键
     * @return 充值方式配置
     */
    @Override
    public PhonePayMenthod selectPhonePayMenthodById(Long id) {
        return phonePayMenthodMapper.selectPhonePayMenthodById(id);
    }

    @Override
    public List<PhonePayMenthod> selectPhonePayMenthodByAppId(String appId) {
        PhonePayMenthod phonePayMenthod = new PhonePayMenthod();
        phonePayMenthod.setAppId(appId);
        List<PhonePayMenthod> phonePayMenthodList = phonePayMenthodMapper.selectPhonePayMenthodList(phonePayMenthod);
        if (phonePayMenthodList != null && !phonePayMenthodList.isEmpty()) {
            return phonePayMenthodList;
        }
        WechatConfig wechatConfig = wechatConfigService.selectWechatConfigByAppId(appId);
        if (wechatConfig != null) {
            //如果没有，默认插入全部数据
            phonePayMenthodList = new ArrayList<>();
            List<SysDictData> phonePayMethod = DictUtils.getDictCache("phone_pay_method");
            for (SysDictData sysDictData : phonePayMethod) {
                PhonePayMenthod payMenthod = new PhonePayMenthod();
                payMenthod.setAppId(appId);
                payMenthod.setDeptId(wechatConfig.getDeptId());
                payMenthod.setValue(sysDictData.getDictValue());
                payMenthod.setWechatConfig(wechatConfig);
                phonePayMenthodList.add(payMenthod);
            }
            return phonePayMenthodList;
        }
        return null;
    }

    /**
     * 查询充值方式配置列表
     *
     * @param phonePayMenthod 充值方式配置
     * @return 充值方式配置
     */
    @Override
    @DataScope(deptAlias = "d", userAlias = "a")
    public List<PhonePayMenthod> selectPhonePayMenthodList(PhonePayMenthod phonePayMenthod) {
        return phonePayMenthodMapper.selectPhonePayMenthodList(phonePayMenthod);
    }

    /**
     * 新增充值方式配置
     *
     * @param phonePayMenthod 充值方式配置
     * @return 结果
     */
    @Override
    public int insertPhonePayMenthod(PhonePayMenthod phonePayMenthod) {
        WechatConfig wechatConfig = wechatConfigService.selectWechatConfigByAppId(phonePayMenthod.getAppId());
        phonePayMenthod.setDeptId(wechatConfig.getDeptId());
        phonePayMenthod.setCreateTime(DateUtils.getNowDate());
        return phonePayMenthodMapper.insertPhonePayMenthod(phonePayMenthod);
    }

    @Override
    public int saveBach(List<PhonePayMenthod> list) {
        int i = 0;
        if (list != null) {
            String appId = list.get(0).getAppId();
            WechatConfig wechatConfig = wechatConfigService.selectWechatConfigByAppId(appId);
            phonePayMenthodMapper.deletePhonePayMenthodByAppId(appId);
            for (PhonePayMenthod phonePayMenthod : list) {
                phonePayMenthod.setAppId(appId);
                phonePayMenthod.setDeptId(wechatConfig.getDeptId());
                phonePayMenthodMapper.insertPhonePayMenthod(phonePayMenthod);
                i++;
            }
        }
        return i;
    }

    /**
     * 修改充值方式配置
     *
     * @param phonePayMenthod 充值方式配置
     * @return 结果
     */
    @Override
    public int updatePhonePayMenthod(PhonePayMenthod phonePayMenthod) {
        WechatConfig wechatConfig = wechatConfigService.selectWechatConfigByAppId(phonePayMenthod.getAppId());
        phonePayMenthod.setDeptId(wechatConfig.getDeptId());
        phonePayMenthod.setUpdateTime(DateUtils.getNowDate());
        return phonePayMenthodMapper.updatePhonePayMenthod(phonePayMenthod);
    }

    /**
     * 批量删除充值方式配置
     *
     * @param ids 需要删除的充值方式配置主键
     * @return 结果
     */
    @Override
    public int deletePhonePayMenthodByIds(Long[] ids) {
        return phonePayMenthodMapper.deletePhonePayMenthodByIds(ids);
    }

    /**
     * 删除充值方式配置信息
     *
     * @param id 充值方式配置主键
     * @return 结果
     */
    @Override
    public int deletePhonePayMenthodById(Long id) {
        return phonePayMenthodMapper.deletePhonePayMenthodById(id);
    }
}

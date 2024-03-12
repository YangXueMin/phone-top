package com.ruoyi.phone.service.impl;

import java.util.List;

import com.ruoyi.common.annotation.DataScope;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.phone.mapper.PhoneCommissionConfigMapper;
import com.ruoyi.phone.domain.PhoneCommissionConfig;
import com.ruoyi.phone.service.IPhoneCommissionConfigService;

/**
 * 佣金生成记录Service业务层处理
 *
 * @author ruoyi
 * @date 2024-03-05
 */
@Service
public class PhoneCommissionConfigServiceImpl implements IPhoneCommissionConfigService {
    @Autowired
    private PhoneCommissionConfigMapper phoneCommissionConfigMapper;

    /**
     * 查询佣金生成记录
     *
     * @param id 佣金生成记录主键
     * @return 佣金生成记录
     */
    @Override
    public PhoneCommissionConfig selectPhoneCommissionConfigById(Long id) {
        return phoneCommissionConfigMapper.selectPhoneCommissionConfigById(id);
    }

    /**
     * 查询佣金生成记录列表
     *
     * @param phoneCommissionConfig 佣金生成记录
     * @return 佣金生成记录
     */
    @Override
    @DataScope(deptAlias = "d", userAlias = "a")
    public List<PhoneCommissionConfig> selectPhoneCommissionConfigList(PhoneCommissionConfig phoneCommissionConfig) {
        return phoneCommissionConfigMapper.selectPhoneCommissionConfigList(phoneCommissionConfig);
    }

    /**
     * 新增佣金生成记录
     *
     * @param phoneCommissionConfig 佣金生成记录
     * @return 结果
     */
    @Override
    public int insertPhoneCommissionConfig(PhoneCommissionConfig phoneCommissionConfig) {
        phoneCommissionConfig.setCreateTime(DateUtils.getNowDate());
        return phoneCommissionConfigMapper.insertPhoneCommissionConfig(phoneCommissionConfig);
    }

    /**
     * 修改佣金生成记录
     *
     * @param phoneCommissionConfig 佣金生成记录
     * @return 结果
     */
    @Override
    public int updatePhoneCommissionConfig(PhoneCommissionConfig phoneCommissionConfig) {
        phoneCommissionConfig.setUpdateTime(DateUtils.getNowDate());
        return phoneCommissionConfigMapper.updatePhoneCommissionConfig(phoneCommissionConfig);
    }

    /**
     * 批量删除佣金生成记录
     *
     * @param ids 需要删除的佣金生成记录主键
     * @return 结果
     */
    @Override
    public int deletePhoneCommissionConfigByIds(Long[] ids) {
        return phoneCommissionConfigMapper.deletePhoneCommissionConfigByIds(ids);
    }

    /**
     * 删除佣金生成记录信息
     *
     * @param id 佣金生成记录主键
     * @return 结果
     */
    @Override
    public int deletePhoneCommissionConfigById(Long id) {
        return phoneCommissionConfigMapper.deletePhoneCommissionConfigById(id);
    }
}

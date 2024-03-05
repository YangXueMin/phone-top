package com.ruoyi.phone.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.phone.mapper.PhoneCommissionLogMapper;
import com.ruoyi.phone.domain.PhoneCommissionLog;
import com.ruoyi.phone.service.IPhoneCommissionLogService;

/**
 * 佣金提现记录Service业务层处理
 *
 * @author ruoyi
 * @date 2024-03-05
 */
@Service
public class PhoneCommissionLogServiceImpl implements IPhoneCommissionLogService
{
    @Autowired
    private PhoneCommissionLogMapper phoneCommissionLogMapper;

    /**
     * 查询佣金提现记录
     *
     * @param id 佣金提现记录主键
     * @return 佣金提现记录
     */
    @Override
    public PhoneCommissionLog selectPhoneCommissionLogById(Long id)
    {
        return phoneCommissionLogMapper.selectPhoneCommissionLogById(id);
    }

    /**
     * 查询佣金提现记录列表
     *
     * @param phoneCommissionLog 佣金提现记录
     * @return 佣金提现记录
     */
    @Override
    public List<PhoneCommissionLog> selectPhoneCommissionLogList(PhoneCommissionLog phoneCommissionLog)
    {
        return phoneCommissionLogMapper.selectPhoneCommissionLogList(phoneCommissionLog);
    }

    /**
     * 新增佣金提现记录
     *
     * @param phoneCommissionLog 佣金提现记录
     * @return 结果
     */
    @Override
    public int insertPhoneCommissionLog(PhoneCommissionLog phoneCommissionLog)
    {
        phoneCommissionLog.setCreateTime(DateUtils.getNowDate());
        return phoneCommissionLogMapper.insertPhoneCommissionLog(phoneCommissionLog);
    }

    /**
     * 修改佣金提现记录
     *
     * @param phoneCommissionLog 佣金提现记录
     * @return 结果
     */
    @Override
    public int updatePhoneCommissionLog(PhoneCommissionLog phoneCommissionLog)
    {
        phoneCommissionLog.setUpdateTime(DateUtils.getNowDate());
        return phoneCommissionLogMapper.updatePhoneCommissionLog(phoneCommissionLog);
    }

    /**
     * 批量删除佣金提现记录
     *
     * @param ids 需要删除的佣金提现记录主键
     * @return 结果
     */
    @Override
    public int deletePhoneCommissionLogByIds(Long[] ids)
    {
        return phoneCommissionLogMapper.deletePhoneCommissionLogByIds(ids);
    }

    /**
     * 删除佣金提现记录信息
     *
     * @param id 佣金提现记录主键
     * @return 结果
     */
    @Override
    public int deletePhoneCommissionLogById(Long id)
    {
        return phoneCommissionLogMapper.deletePhoneCommissionLogById(id);
    }
}

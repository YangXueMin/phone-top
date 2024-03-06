package com.ruoyi.phone.service.impl;

import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.phone.mapper.PhoneBalanceLogMapper;
import com.ruoyi.phone.domain.PhoneBalanceLog;
import com.ruoyi.phone.service.IPhoneBalanceLogService;

/**
 * 余额充值记录Service业务层处理
 *
 * @author ruoyi
 * @date 2024-03-05
 */
@Service
public class PhoneBalanceLogServiceImpl implements IPhoneBalanceLogService {
    @Autowired
    private PhoneBalanceLogMapper phoneBalanceLogMapper;

    /**
     * 查询余额充值记录
     *
     * @param id 余额充值记录主键
     * @return 余额充值记录
     */
    @Override
    public PhoneBalanceLog selectPhoneBalanceLogById(Long id) {
        return phoneBalanceLogMapper.selectPhoneBalanceLogById(id);
    }

    /**
     * 查询余额充值记录列表
     *
     * @param phoneBalanceLog 余额充值记录
     * @return 余额充值记录
     */
    @Override
    public List<PhoneBalanceLog> selectPhoneBalanceLogList(PhoneBalanceLog phoneBalanceLog) {
        return phoneBalanceLogMapper.selectPhoneBalanceLogList(phoneBalanceLog);
    }

    /**
     * 新增余额充值记录
     *
     * @param phoneBalanceLog 余额充值记录
     * @return 结果
     */
    @Override
    public int insertPhoneBalanceLog(PhoneBalanceLog phoneBalanceLog) {
        phoneBalanceLog.setCreateTime(DateUtils.getNowDate());
        return phoneBalanceLogMapper.insertPhoneBalanceLog(phoneBalanceLog);
    }

    /**
     * 修改余额充值记录
     *
     * @param phoneBalanceLog 余额充值记录
     * @return 结果
     */
    @Override
    public int updatePhoneBalanceLog(PhoneBalanceLog phoneBalanceLog) {
        phoneBalanceLog.setUpdateTime(DateUtils.getNowDate());
        return phoneBalanceLogMapper.updatePhoneBalanceLog(phoneBalanceLog);
    }

    /**
     * 批量删除余额充值记录
     *
     * @param ids 需要删除的余额充值记录主键
     * @return 结果
     */
    @Override
    public int deletePhoneBalanceLogByIds(Long[] ids) {
        return phoneBalanceLogMapper.deletePhoneBalanceLogByIds(ids);
    }

    /**
     * 删除余额充值记录信息
     *
     * @param id 余额充值记录主键
     * @return 结果
     */
    @Override
    public int deletePhoneBalanceLogById(Long id) {
        return phoneBalanceLogMapper.deletePhoneBalanceLogById(id);
    }
}

package com.ruoyi.phone.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.phone.mapper.PhoneMemberCardLogMapper;
import com.ruoyi.phone.domain.PhoneMemberCardLog;
import com.ruoyi.phone.service.IPhoneMemberCardLogService;

/**
 * 会员卡充值记录Service业务层处理
 *
 * @author ruoyi
 * @date 2024-03-05
 */
@Service
public class PhoneMemberCardLogServiceImpl implements IPhoneMemberCardLogService
{
    @Autowired
    private PhoneMemberCardLogMapper phoneMemberCardLogMapper;

    /**
     * 查询会员卡充值记录
     *
     * @param id 会员卡充值记录主键
     * @return 会员卡充值记录
     */
    @Override
    public PhoneMemberCardLog selectPhoneMemberCardLogById(Long id)
    {
        return phoneMemberCardLogMapper.selectPhoneMemberCardLogById(id);
    }

    /**
     * 查询会员卡充值记录列表
     *
     * @param phoneMemberCardLog 会员卡充值记录
     * @return 会员卡充值记录
     */
    @Override
    public List<PhoneMemberCardLog> selectPhoneMemberCardLogList(PhoneMemberCardLog phoneMemberCardLog)
    {
        return phoneMemberCardLogMapper.selectPhoneMemberCardLogList(phoneMemberCardLog);
    }

    /**
     * 新增会员卡充值记录
     *
     * @param phoneMemberCardLog 会员卡充值记录
     * @return 结果
     */
    @Override
    public int insertPhoneMemberCardLog(PhoneMemberCardLog phoneMemberCardLog)
    {
        phoneMemberCardLog.setCreateTime(DateUtils.getNowDate());
        return phoneMemberCardLogMapper.insertPhoneMemberCardLog(phoneMemberCardLog);
    }

    /**
     * 修改会员卡充值记录
     *
     * @param phoneMemberCardLog 会员卡充值记录
     * @return 结果
     */
    @Override
    public int updatePhoneMemberCardLog(PhoneMemberCardLog phoneMemberCardLog)
    {
        phoneMemberCardLog.setUpdateTime(DateUtils.getNowDate());
        return phoneMemberCardLogMapper.updatePhoneMemberCardLog(phoneMemberCardLog);
    }

    /**
     * 批量删除会员卡充值记录
     *
     * @param ids 需要删除的会员卡充值记录主键
     * @return 结果
     */
    @Override
    public int deletePhoneMemberCardLogByIds(Long[] ids)
    {
        return phoneMemberCardLogMapper.deletePhoneMemberCardLogByIds(ids);
    }

    /**
     * 删除会员卡充值记录信息
     *
     * @param id 会员卡充值记录主键
     * @return 结果
     */
    @Override
    public int deletePhoneMemberCardLogById(Long id)
    {
        return phoneMemberCardLogMapper.deletePhoneMemberCardLogById(id);
    }
}

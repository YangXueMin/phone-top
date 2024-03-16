package com.ruoyi.phone.service.impl;

import java.util.List;

import com.ruoyi.common.annotation.DataScope;
import com.ruoyi.common.core.domain.entity.Member;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.mapper.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.phone.mapper.PhoneCommissionLogMapper;
import com.ruoyi.phone.domain.PhoneCommissionLog;
import com.ruoyi.phone.service.IPhoneCommissionLogService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 佣金提现记录Service业务层处理
 *
 * @author ruoyi
 * @date 2024-03-05
 */
@Service
public class PhoneCommissionLogServiceImpl implements IPhoneCommissionLogService {
    @Autowired
    private PhoneCommissionLogMapper phoneCommissionLogMapper;
    @Autowired
    private MemberMapper memberMapper;

    /**
     * 查询佣金提现记录
     *
     * @param id 佣金提现记录主键
     * @return 佣金提现记录
     */
    @Override
    public PhoneCommissionLog selectPhoneCommissionLogById(Long id) {
        return phoneCommissionLogMapper.selectPhoneCommissionLogById(id);
    }

    /**
     * 查询佣金提现记录列表
     *
     * @param phoneCommissionLog 佣金提现记录
     * @return 佣金提现记录
     */
    @Override
    public List<PhoneCommissionLog> selectPhoneCommissionLogList(PhoneCommissionLog phoneCommissionLog) {
        return phoneCommissionLogMapper.selectPhoneCommissionLogList(phoneCommissionLog);
    }

    /**
     * 新增佣金提现记录
     *
     * @param phoneCommissionLog 佣金提现记录
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public synchronized PhoneCommissionLog insertPhoneCommissionLog(PhoneCommissionLog phoneCommissionLog) {
        phoneCommissionLog.setCreateTime(DateUtils.getNowDate());
        phoneCommissionLog.setAuditStatus("1");
        Member member = memberMapper.selectMemberById(phoneCommissionLog.getMemberId());
        if (member != null) {
            phoneCommissionLog.setCommissionBefore(member.getCommissionBalance());
            phoneCommissionLog.setCommissionAfter(member.getCommissionBalance().subtract(phoneCommissionLog.getMoney()));
            if(StringUtils.equals("1",phoneCommissionLog.getType())){
                phoneCommissionLog.setAuditStatus("2");
                member.setBalance(member.getBalance().add(phoneCommissionLog.getMoney()));
                member.setCommissionBalance(member.getCommissionBalance().subtract(phoneCommissionLog.getMoney()));
                member.setWithdrawalAmount(member.getWithdrawalAmount().add(phoneCommissionLog.getMoney()));
                memberMapper.updateMember(member);
            }
        }
        final int i = phoneCommissionLogMapper.insertPhoneCommissionLog(phoneCommissionLog);
        return phoneCommissionLog;
    }

    /**
     * 修改佣金提现记录
     *
     * @param phoneCommissionLog 佣金提现记录
     * @return 结果
     */
    @Override
    public int updatePhoneCommissionLog(PhoneCommissionLog phoneCommissionLog) {
        phoneCommissionLog.setUpdateTime(DateUtils.getNowDate());
        return phoneCommissionLogMapper.updatePhoneCommissionLog(phoneCommissionLog);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int auditPhoneCommissionLog(PhoneCommissionLog phoneCommissionLog) {
        phoneCommissionLog.setUpdateTime(DateUtils.getNowDate());
        phoneCommissionLog.setAuditId(SecurityUtils.getUserId());
        phoneCommissionLog.setAuditTime(DateUtils.getNowDate());
        if (StringUtils.equals("2", phoneCommissionLog.getAuditStatus())) {
            //修改会员的佣金，并修改会员的体现佣金
            Member member = memberMapper.selectMemberById(phoneCommissionLog.getMemberId());
            member.setCommissionBalance(member.getCommissionBalance().subtract(phoneCommissionLog.getMoney()));
            member.setWithdrawalAmount(member.getWithdrawalAmount().add(phoneCommissionLog.getMoney()));
            memberMapper.updateMember(member);
        }
        return phoneCommissionLogMapper.updatePhoneCommissionLog(phoneCommissionLog);
    }

    /**
     * 批量删除佣金提现记录
     *
     * @param ids 需要删除的佣金提现记录主键
     * @return 结果
     */
    @Override
    public int deletePhoneCommissionLogByIds(Long[] ids) {
        return phoneCommissionLogMapper.deletePhoneCommissionLogByIds(ids);
    }

    /**
     * 删除佣金提现记录信息
     *
     * @param id 佣金提现记录主键
     * @return 结果
     */
    @Override
    public int deletePhoneCommissionLogById(Long id) {
        return phoneCommissionLogMapper.deletePhoneCommissionLogById(id);
    }
}

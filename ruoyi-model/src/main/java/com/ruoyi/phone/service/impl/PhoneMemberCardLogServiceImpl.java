package com.ruoyi.phone.service.impl;

import java.math.BigDecimal;
import java.util.List;

import com.ruoyi.common.config.WechatConfiguration;
import com.ruoyi.common.core.domain.entity.Member;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.phone.domain.PhoneBalanceLog;
import com.ruoyi.phone.mapper.PhoneBalanceLogMapper;
import com.ruoyi.system.mapper.MemberMapper;
import com.ruoyi.system.service.IMemberService;
import com.ruoyi.system.service.impl.MemberServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.phone.mapper.PhoneMemberCardLogMapper;
import com.ruoyi.phone.domain.PhoneMemberCardLog;
import com.ruoyi.phone.service.IPhoneMemberCardLogService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 会员卡充值记录Service业务层处理
 *
 * @author ruoyi
 * @date 2024-03-05
 */
@Service
public class PhoneMemberCardLogServiceImpl implements IPhoneMemberCardLogService {
    @Autowired
    private PhoneMemberCardLogMapper phoneMemberCardLogMapper;
    @Autowired
    private WechatConfiguration wechatConfiguration;
    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private PhoneBalanceLogMapper phoneBalanceLogMapper;

    /**
     * 查询会员卡充值记录
     *
     * @param id 会员卡充值记录主键
     * @return 会员卡充值记录
     */
    @Override
    public PhoneMemberCardLog selectPhoneMemberCardLogById(Long id) {
        return phoneMemberCardLogMapper.selectPhoneMemberCardLogById(id);
    }

    /**
     * 查询会员卡充值记录列表
     *
     * @param phoneMemberCardLog 会员卡充值记录
     * @return 会员卡充值记录
     */
    @Override
    public List<PhoneMemberCardLog> selectPhoneMemberCardLogList(PhoneMemberCardLog phoneMemberCardLog) {
        return phoneMemberCardLogMapper.selectPhoneMemberCardLogList(phoneMemberCardLog);
    }

    /**
     * 新增会员卡充值记录
     *
     * @param phoneMemberCardLog 会员卡充值记录
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public PhoneMemberCardLog insertPhoneMemberCardLog(PhoneMemberCardLog phoneMemberCardLog) {
        phoneMemberCardLog.setCreateTime(DateUtils.getNowDate());
        Member member = memberMapper.selectMemberById(phoneMemberCardLog.getMemberId());
        BigDecimal balance = member.getBalance();
        //如果不是在线支付，需要判断余额是否充足
        if(!StringUtils.equals("1",phoneMemberCardLog.getPayType())){
            if(phoneMemberCardLog.getTotalMoney().compareTo(member.getBalance()) > 0){
                member.setBalance(BigDecimal.ZERO);
                phoneMemberCardLog.setPayStatus("1");
                phoneMemberCardLog.setMoney(phoneMemberCardLog.getTotalMoney().subtract(balance));
                phoneMemberCardLog.setBalanceMoney(balance);
            }else{
                member.setBalance(balance.subtract(phoneMemberCardLog.getTotalMoney()));
                phoneMemberCardLog.setBalanceMoney(phoneMemberCardLog.getTotalMoney());
                phoneMemberCardLog.setPayStatus("2");
                phoneMemberCardLog.setMoney(BigDecimal.ZERO);
            }
            memberMapper.updateMember(member);
            //添加余额变更记录
            PhoneBalanceLog phoneBalanceLog = new PhoneBalanceLog();
            phoneBalanceLog.setCompanyId(phoneMemberCardLog.getCompanyId());
            phoneBalanceLog.setAppId(phoneMemberCardLog.getAppId());
            phoneBalanceLog.setMemberId(member.getMemberId());
            phoneBalanceLog.setType("2");
            phoneBalanceLog.setBalanceAfter(balance);
            phoneBalanceLog.setMoney(phoneMemberCardLog.getMoney());
            phoneBalanceLog.setBalanceBefore(member.getBalance());
            phoneBalanceLog.setCreateTime(DateUtils.getNowDate());
            phoneBalanceLogMapper.insertPhoneBalanceLog(phoneBalanceLog);
        }else{
            phoneMemberCardLog.setPayStatus("1");
            phoneMemberCardLog.setBalanceMoney(BigDecimal.ZERO);
            phoneMemberCardLog.setMoney(phoneMemberCardLog.getTotalMoney());
        }
        phoneMemberCardLogMapper.insertPhoneMemberCardLog(phoneMemberCardLog);
        return phoneMemberCardLog;
    }

    /**
     * 修改会员卡充值记录
     *
     * @param phoneMemberCardLog 会员卡充值记录
     * @return 结果
     */
    @Override
    public int updatePhoneMemberCardLog(PhoneMemberCardLog phoneMemberCardLog) {
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
    public int deletePhoneMemberCardLogByIds(Long[] ids) {
        return phoneMemberCardLogMapper.deletePhoneMemberCardLogByIds(ids);
    }

    /**
     * 删除会员卡充值记录信息
     *
     * @param id 会员卡充值记录主键
     * @return 结果
     */
    @Override
    public int deletePhoneMemberCardLogById(Long id) {
        return phoneMemberCardLogMapper.deletePhoneMemberCardLogById(id);
    }
}

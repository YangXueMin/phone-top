package com.ruoyi.phone.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.ruoyi.common.core.domain.entity.Member;
import com.ruoyi.common.utils.CardGenerator;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SnowflakeGenerator;
import com.ruoyi.phone.domain.PhoneBalanceLog;
import com.ruoyi.phone.mapper.PhoneBalanceLogMapper;
import com.ruoyi.system.mapper.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.phone.mapper.PhoneCardMapper;
import com.ruoyi.phone.domain.PhoneCard;
import com.ruoyi.phone.service.IPhoneCardService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 卡密管理Service业务层处理
 *
 * @author ruoyi
 * @date 2024-03-04
 */
@Service
public class PhoneCardServiceImpl implements IPhoneCardService {
    @Autowired
    private PhoneCardMapper phoneCardMapper;
    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private PhoneBalanceLogMapper phoneBalanceLogMapper;

    /**
     * 查询卡密管理
     *
     * @param id 卡密管理主键
     * @return 卡密管理
     */
    @Override
    public PhoneCard selectPhoneCardById(Long id) {
        return phoneCardMapper.selectPhoneCardById(id);
    }

    /**
     * 查询卡密管理列表
     *
     * @param phoneCard 卡密管理
     * @return 卡密管理
     */
    @Override
    public List<PhoneCard> selectPhoneCardList(PhoneCard phoneCard) {
        return phoneCardMapper.selectPhoneCardList(phoneCard);
    }

    /**
     * 新增卡密管理
     *
     * @param phoneCard 卡密管理
     * @return 结果
     */
    @Override
    public int insertPhoneCard(PhoneCard phoneCard) {
        phoneCard.setCreateTime(DateUtils.getNowDate());
        String cardNo = CardGenerator.generateCard(8);
        phoneCard.setCardNo(cardNo);
        phoneCard.setCancelStatus("1");
        return phoneCardMapper.insertPhoneCard(phoneCard);
    }

    /**
     * 修改卡密管理
     *
     * @param phoneCard 卡密管理
     * @return 结果
     */
    @Override
    public int updatePhoneCard(PhoneCard phoneCard) {
        phoneCard.setUpdateTime(DateUtils.getNowDate());
        return phoneCardMapper.updatePhoneCard(phoneCard);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public synchronized int cancel(PhoneCard phoneCard) {
        phoneCard.setCancelStatus("1");
        phoneCard.setCancelTime(DateUtils.getNowDate());
        final int i = phoneCardMapper.updatePhoneCard(phoneCard);
        if (i > 0) {
            final Member member = memberMapper.selectMemberById(phoneCard.getMemberId());
            BigDecimal balance = member.getBalance();
            member.setBalance(member.getBalance().add(phoneCard.getPrice()));
            member.setUpdateTime(DateUtils.getNowDate());
            memberMapper.updateMember(member);
            //添加余额变更记录
            PhoneBalanceLog phoneBalanceLog = new PhoneBalanceLog();
            phoneBalanceLog.setCompanyId(phoneCard.getCompanyId());
            phoneBalanceLog.setAppId(phoneCard.getAppId());
            phoneBalanceLog.setMemberId(member.getMemberId());
            phoneBalanceLog.setType("1");
            phoneBalanceLog.setBalanceAfter(balance);
            phoneBalanceLog.setMoney(phoneCard.getPrice());
            phoneBalanceLog.setBalanceBefore(member.getBalance());
            phoneBalanceLog.setCreateTime(DateUtils.getNowDate());
            phoneBalanceLogMapper.insertPhoneBalanceLog(phoneBalanceLog);
        }
        return i;
    }

    /**
     * 批量删除卡密管理
     *
     * @param ids 需要删除的卡密管理主键
     * @return 结果
     */
    @Override
    public int deletePhoneCardByIds(Long[] ids) {
        return phoneCardMapper.deletePhoneCardByIds(ids);
    }

    /**
     * 删除卡密管理信息
     *
     * @param id 卡密管理主键
     * @return 结果
     */
    @Override
    public int deletePhoneCardById(Long id) {
        return phoneCardMapper.deletePhoneCardById(id);
    }
}

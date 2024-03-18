package com.ruoyi.phone.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.ruoyi.common.annotation.DataScope;
import com.ruoyi.common.core.domain.entity.Member;
import com.ruoyi.common.core.domain.entity.WechatConfig;
import com.ruoyi.common.utils.CardGenerator;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SnowflakeGenerator;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.time.DateUtil;
import com.ruoyi.phone.domain.PhoneBalanceLog;
import com.ruoyi.phone.mapper.PhoneBalanceLogMapper;
import com.ruoyi.system.mapper.MemberMapper;
import com.ruoyi.system.service.IWechatConfigService;
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
    private IWechatConfigService wechatConfigService;

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
    @DataScope(deptAlias = "d", userAlias = "a")
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
    @Transactional(rollbackFor = Exception.class)
    public int insertPhoneCard(PhoneCard phoneCard) {
        WechatConfig wechatConfig = wechatConfigService.selectWechatConfigByAppId(phoneCard.getAppId());
        phoneCard.setDeptId(wechatConfig.getDeptId());
        phoneCard.setCreateTime(DateUtils.getNowDate());
        phoneCard.setCancelStatus("1");
        int num = 0;
        if(phoneCard.getParams().get("number") != null){
            num = Integer.parseInt(phoneCard.getParams().get("number").toString());
        }
        for (int i = 0; i < num; i++) {
            String cardNo = CardGenerator.generateCard(8);
            phoneCard.setCardNo(cardNo);
            phoneCardMapper.insertPhoneCard(phoneCard);
        }
        return num;
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
            Date date = new Date();
            Member member = memberMapper.selectMemberById(phoneCard.getMemberId());
            if(StringUtils.equals("1",phoneCard.getType())){
                //普通会员
                if(member.getExpirationTime() != null){
                    date = member.getExpirationTime();
                }
                member.setExpirationTime(DateUtil.endOfDate(DateUtils.addDays(date,phoneCard.getDuration())));
                member.setIsMember("1");
            }else if(StringUtils.equals("2",phoneCard.getType())){
                //超级会员
                if(member.getSuperExpirationTime() != null){
                    date = member.getSuperExpirationTime();
                }
                member.setSuperExpirationTime(DateUtil.endOfDate(DateUtils.addDays(date,phoneCard.getDuration())));
                member.setIsSuperMember("1");
            }
            member.setUpdateTime(DateUtils.getNowDate());
            memberMapper.updateMember(member);
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

package com.ruoyi.phone.service.impl;

import java.util.List;

import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.service.ISysDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.phone.mapper.PhoneMemberCardMapper;
import com.ruoyi.phone.domain.PhoneMemberCard;
import com.ruoyi.phone.service.IPhoneMemberCardService;

/**
 * 会员卡管理Service业务层处理
 *
 * @author ruoyi
 * @date 2024-03-04
 */
@Service
public class PhoneMemberCardServiceImpl implements IPhoneMemberCardService {
    @Autowired
    private PhoneMemberCardMapper phoneMemberCardMapper;
    @Autowired
    private ISysDeptService sysDeptService;

    /**
     * 查询会员卡管理
     *
     * @param id 会员卡管理主键
     * @return 会员卡管理
     */
    @Override
    public PhoneMemberCard selectPhoneMemberCardById(Long id) {
        return phoneMemberCardMapper.selectPhoneMemberCardById(id);
    }

    /**
     * 查询会员卡管理列表
     *
     * @param phoneMemberCard 会员卡管理
     * @return 会员卡管理
     */
    @Override
    public List<PhoneMemberCard> selectPhoneMemberCardList(PhoneMemberCard phoneMemberCard) {
        return phoneMemberCardMapper.selectPhoneMemberCardList(phoneMemberCard);
    }

    /**
     * 新增会员卡管理
     *
     * @param phoneMemberCard 会员卡管理
     * @return 结果
     */
    @Override
    public int insertPhoneMemberCard(PhoneMemberCard phoneMemberCard) {
        SysDept company = sysDeptService.selectCompany(SecurityUtils.getLoginUser().getDeptId());
        if(company != null && !company.getDeptId().equals(100L)){
            phoneMemberCard.setCompanyId(company.getDeptId());
        }
        phoneMemberCard.setCreateTime(DateUtils.getNowDate());
        return phoneMemberCardMapper.insertPhoneMemberCard(phoneMemberCard);
    }

    /**
     * 修改会员卡管理
     *
     * @param phoneMemberCard 会员卡管理
     * @return 结果
     */
    @Override
    public int updatePhoneMemberCard(PhoneMemberCard phoneMemberCard) {
        phoneMemberCard.setUpdateTime(DateUtils.getNowDate());
        return phoneMemberCardMapper.updatePhoneMemberCard(phoneMemberCard);
    }

    /**
     * 批量删除会员卡管理
     *
     * @param ids 需要删除的会员卡管理主键
     * @return 结果
     */
    @Override
    public int deletePhoneMemberCardByIds(Long[] ids) {
        return phoneMemberCardMapper.deletePhoneMemberCardByIds(ids);
    }

    /**
     * 删除会员卡管理信息
     *
     * @param id 会员卡管理主键
     * @return 结果
     */
    @Override
    public int deletePhoneMemberCardById(Long id) {
        return phoneMemberCardMapper.deletePhoneMemberCardById(id);
    }
}

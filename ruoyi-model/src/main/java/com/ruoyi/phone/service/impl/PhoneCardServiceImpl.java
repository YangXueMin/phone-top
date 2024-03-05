package com.ruoyi.phone.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.phone.mapper.PhoneCardMapper;
import com.ruoyi.phone.domain.PhoneCard;
import com.ruoyi.phone.service.IPhoneCardService;

/**
 * 卡密管理Service业务层处理
 *
 * @author ruoyi
 * @date 2024-03-04
 */
@Service
public class PhoneCardServiceImpl implements IPhoneCardService
{
    @Autowired
    private PhoneCardMapper phoneCardMapper;

    /**
     * 查询卡密管理
     *
     * @param id 卡密管理主键
     * @return 卡密管理
     */
    @Override
    public PhoneCard selectPhoneCardById(Long id)
    {
        return phoneCardMapper.selectPhoneCardById(id);
    }

    /**
     * 查询卡密管理列表
     *
     * @param phoneCard 卡密管理
     * @return 卡密管理
     */
    @Override
    public List<PhoneCard> selectPhoneCardList(PhoneCard phoneCard)
    {
        return phoneCardMapper.selectPhoneCardList(phoneCard);
    }

    /**
     * 新增卡密管理
     *
     * @param phoneCard 卡密管理
     * @return 结果
     */
    @Override
    public int insertPhoneCard(PhoneCard phoneCard)
    {
        phoneCard.setCreateTime(DateUtils.getNowDate());
        return phoneCardMapper.insertPhoneCard(phoneCard);
    }

    /**
     * 修改卡密管理
     *
     * @param phoneCard 卡密管理
     * @return 结果
     */
    @Override
    public int updatePhoneCard(PhoneCard phoneCard)
    {
        phoneCard.setUpdateTime(DateUtils.getNowDate());
        return phoneCardMapper.updatePhoneCard(phoneCard);
    }

    /**
     * 批量删除卡密管理
     *
     * @param ids 需要删除的卡密管理主键
     * @return 结果
     */
    @Override
    public int deletePhoneCardByIds(Long[] ids)
    {
        return phoneCardMapper.deletePhoneCardByIds(ids);
    }

    /**
     * 删除卡密管理信息
     *
     * @param id 卡密管理主键
     * @return 结果
     */
    @Override
    public int deletePhoneCardById(Long id)
    {
        return phoneCardMapper.deletePhoneCardById(id);
    }
}

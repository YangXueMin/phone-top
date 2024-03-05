package com.ruoyi.phone.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.phone.mapper.PhoneMemberNowMapper;
import com.ruoyi.phone.domain.PhoneMemberNow;
import com.ruoyi.phone.service.IPhoneMemberNowService;

/**
 * 用户须知配置Service业务层处理
 *
 * @author ruoyi
 * @date 2024-03-04
 */
@Service
public class PhoneMemberNowServiceImpl implements IPhoneMemberNowService
{
    @Autowired
    private PhoneMemberNowMapper phoneMemberNowMapper;

    /**
     * 查询用户须知配置
     *
     * @param id 用户须知配置主键
     * @return 用户须知配置
     */
    @Override
    public PhoneMemberNow selectPhoneMemberNowById(Long id)
    {
        return phoneMemberNowMapper.selectPhoneMemberNowById(id);
    }

    /**
     * 查询用户须知配置列表
     *
     * @param phoneMemberNow 用户须知配置
     * @return 用户须知配置
     */
    @Override
    public List<PhoneMemberNow> selectPhoneMemberNowList(PhoneMemberNow phoneMemberNow)
    {
        return phoneMemberNowMapper.selectPhoneMemberNowList(phoneMemberNow);
    }

    /**
     * 新增用户须知配置
     *
     * @param phoneMemberNow 用户须知配置
     * @return 结果
     */
    @Override
    public int insertPhoneMemberNow(PhoneMemberNow phoneMemberNow)
    {
        phoneMemberNow.setCreateTime(DateUtils.getNowDate());
        return phoneMemberNowMapper.insertPhoneMemberNow(phoneMemberNow);
    }

    /**
     * 修改用户须知配置
     *
     * @param phoneMemberNow 用户须知配置
     * @return 结果
     */
    @Override
    public int updatePhoneMemberNow(PhoneMemberNow phoneMemberNow)
    {
        phoneMemberNow.setUpdateTime(DateUtils.getNowDate());
        return phoneMemberNowMapper.updatePhoneMemberNow(phoneMemberNow);
    }

    /**
     * 批量删除用户须知配置
     *
     * @param ids 需要删除的用户须知配置主键
     * @return 结果
     */
    @Override
    public int deletePhoneMemberNowByIds(Long[] ids)
    {
        return phoneMemberNowMapper.deletePhoneMemberNowByIds(ids);
    }

    /**
     * 删除用户须知配置信息
     *
     * @param id 用户须知配置主键
     * @return 结果
     */
    @Override
    public int deletePhoneMemberNowById(Long id)
    {
        return phoneMemberNowMapper.deletePhoneMemberNowById(id);
    }
}

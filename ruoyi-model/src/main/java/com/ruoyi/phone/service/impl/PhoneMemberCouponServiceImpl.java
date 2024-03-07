package com.ruoyi.phone.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.phone.mapper.PhoneMemberCouponMapper;
import com.ruoyi.phone.domain.PhoneMemberCoupon;
import com.ruoyi.phone.service.IPhoneMemberCouponService;

/**
 * 会员卡券管理关系Service业务层处理
 *
 * @author ruoyi
 * @date 2024-03-07
 */
@Service
public class PhoneMemberCouponServiceImpl implements IPhoneMemberCouponService
{
    @Autowired
    private PhoneMemberCouponMapper phoneMemberCouponMapper;

    /**
     * 查询会员卡券管理关系
     *
     * @param id 会员卡券管理关系主键
     * @return 会员卡券管理关系
     */
    @Override
    public PhoneMemberCoupon selectPhoneMemberCouponById(Long id)
    {
        return phoneMemberCouponMapper.selectPhoneMemberCouponById(id);
    }

    /**
     * 查询会员卡券管理关系列表
     *
     * @param phoneMemberCoupon 会员卡券管理关系
     * @return 会员卡券管理关系
     */
    @Override
    public List<PhoneMemberCoupon> selectPhoneMemberCouponList(PhoneMemberCoupon phoneMemberCoupon)
    {
        return phoneMemberCouponMapper.selectPhoneMemberCouponList(phoneMemberCoupon);
    }

    /**
     * 新增会员卡券管理关系
     *
     * @param phoneMemberCoupon 会员卡券管理关系
     * @return 结果
     */
    @Override
    public int insertPhoneMemberCoupon(PhoneMemberCoupon phoneMemberCoupon)
    {
        phoneMemberCoupon.setCreateTime(DateUtils.getNowDate());
        return phoneMemberCouponMapper.insertPhoneMemberCoupon(phoneMemberCoupon);
    }

    /**
     * 修改会员卡券管理关系
     *
     * @param phoneMemberCoupon 会员卡券管理关系
     * @return 结果
     */
    @Override
    public int updatePhoneMemberCoupon(PhoneMemberCoupon phoneMemberCoupon)
    {
        phoneMemberCoupon.setUpdateTime(DateUtils.getNowDate());
        return phoneMemberCouponMapper.updatePhoneMemberCoupon(phoneMemberCoupon);
    }

    /**
     * 批量删除会员卡券管理关系
     *
     * @param ids 需要删除的会员卡券管理关系主键
     * @return 结果
     */
    @Override
    public int deletePhoneMemberCouponByIds(Long[] ids)
    {
        return phoneMemberCouponMapper.deletePhoneMemberCouponByIds(ids);
    }

    /**
     * 删除会员卡券管理关系信息
     *
     * @param id 会员卡券管理关系主键
     * @return 结果
     */
    @Override
    public int deletePhoneMemberCouponById(Long id)
    {
        return phoneMemberCouponMapper.deletePhoneMemberCouponById(id);
    }
}

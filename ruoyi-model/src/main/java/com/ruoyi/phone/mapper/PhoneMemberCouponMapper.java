package com.ruoyi.phone.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.ruoyi.phone.domain.PhoneMemberCoupon;

/**
 * 会员卡券管理关系Mapper接口
 *
 * @author ruoyi
 * @date 2024-03-07
 */
@Mapper
public interface PhoneMemberCouponMapper
{
    /**
     * 查询会员卡券管理关系
     *
     * @param id 会员卡券管理关系主键
     * @return 会员卡券管理关系
     */
    public PhoneMemberCoupon selectPhoneMemberCouponById(Long id);

    /**
     * 查询会员卡券管理关系列表
     *
     * @param phoneMemberCoupon 会员卡券管理关系
     * @return 会员卡券管理关系集合
     */
    public List<PhoneMemberCoupon> selectPhoneMemberCouponList(PhoneMemberCoupon phoneMemberCoupon);

    /**
     * 新增会员卡券管理关系
     *
     * @param phoneMemberCoupon 会员卡券管理关系
     * @return 结果
     */
    public int insertPhoneMemberCoupon(PhoneMemberCoupon phoneMemberCoupon);

    /**
     * 修改会员卡券管理关系
     *
     * @param phoneMemberCoupon 会员卡券管理关系
     * @return 结果
     */
    public int updatePhoneMemberCoupon(PhoneMemberCoupon phoneMemberCoupon);

    /**
     * 删除会员卡券管理关系
     *
     * @param id 会员卡券管理关系主键
     * @return 结果
     */
    public int deletePhoneMemberCouponById(Long id);

    /**
     * 批量删除会员卡券管理关系
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePhoneMemberCouponByIds(Long[] ids);
}

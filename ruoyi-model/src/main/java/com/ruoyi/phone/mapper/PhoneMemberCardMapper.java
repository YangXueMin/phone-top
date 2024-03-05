package com.ruoyi.phone.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.ruoyi.phone.domain.PhoneMemberCard;

/**
 * 会员卡管理Mapper接口
 *
 * @author ruoyi
 * @date 2024-03-04
 */
@Mapper
public interface PhoneMemberCardMapper
{
    /**
     * 查询会员卡管理
     *
     * @param id 会员卡管理主键
     * @return 会员卡管理
     */
    public PhoneMemberCard selectPhoneMemberCardById(Long id);

    /**
     * 查询会员卡管理列表
     *
     * @param phoneMemberCard 会员卡管理
     * @return 会员卡管理集合
     */
    public List<PhoneMemberCard> selectPhoneMemberCardList(PhoneMemberCard phoneMemberCard);

    /**
     * 新增会员卡管理
     *
     * @param phoneMemberCard 会员卡管理
     * @return 结果
     */
    public int insertPhoneMemberCard(PhoneMemberCard phoneMemberCard);

    /**
     * 修改会员卡管理
     *
     * @param phoneMemberCard 会员卡管理
     * @return 结果
     */
    public int updatePhoneMemberCard(PhoneMemberCard phoneMemberCard);

    /**
     * 删除会员卡管理
     *
     * @param id 会员卡管理主键
     * @return 结果
     */
    public int deletePhoneMemberCardById(Long id);

    /**
     * 批量删除会员卡管理
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePhoneMemberCardByIds(Long[] ids);
}

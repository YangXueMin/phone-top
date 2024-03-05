package com.ruoyi.phone.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.ruoyi.phone.domain.PhoneMemberCardLog;

/**
 * 会员卡充值记录Mapper接口
 *
 * @author ruoyi
 * @date 2024-03-05
 */
@Mapper
public interface PhoneMemberCardLogMapper
{
    /**
     * 查询会员卡充值记录
     *
     * @param id 会员卡充值记录主键
     * @return 会员卡充值记录
     */
    public PhoneMemberCardLog selectPhoneMemberCardLogById(Long id);

    /**
     * 查询会员卡充值记录列表
     *
     * @param phoneMemberCardLog 会员卡充值记录
     * @return 会员卡充值记录集合
     */
    public List<PhoneMemberCardLog> selectPhoneMemberCardLogList(PhoneMemberCardLog phoneMemberCardLog);

    /**
     * 新增会员卡充值记录
     *
     * @param phoneMemberCardLog 会员卡充值记录
     * @return 结果
     */
    public int insertPhoneMemberCardLog(PhoneMemberCardLog phoneMemberCardLog);

    /**
     * 修改会员卡充值记录
     *
     * @param phoneMemberCardLog 会员卡充值记录
     * @return 结果
     */
    public int updatePhoneMemberCardLog(PhoneMemberCardLog phoneMemberCardLog);

    /**
     * 删除会员卡充值记录
     *
     * @param id 会员卡充值记录主键
     * @return 结果
     */
    public int deletePhoneMemberCardLogById(Long id);

    /**
     * 批量删除会员卡充值记录
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePhoneMemberCardLogByIds(Long[] ids);
}

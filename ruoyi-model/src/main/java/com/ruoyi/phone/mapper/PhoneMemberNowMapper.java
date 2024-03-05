package com.ruoyi.phone.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.ruoyi.phone.domain.PhoneMemberNow;

/**
 * 用户须知配置Mapper接口
 *
 * @author ruoyi
 * @date 2024-03-04
 */
@Mapper
public interface PhoneMemberNowMapper
{
    /**
     * 查询用户须知配置
     *
     * @param id 用户须知配置主键
     * @return 用户须知配置
     */
    public PhoneMemberNow selectPhoneMemberNowById(Long id);

    /**
     * 查询用户须知配置列表
     *
     * @param phoneMemberNow 用户须知配置
     * @return 用户须知配置集合
     */
    public List<PhoneMemberNow> selectPhoneMemberNowList(PhoneMemberNow phoneMemberNow);

    /**
     * 新增用户须知配置
     *
     * @param phoneMemberNow 用户须知配置
     * @return 结果
     */
    public int insertPhoneMemberNow(PhoneMemberNow phoneMemberNow);

    /**
     * 修改用户须知配置
     *
     * @param phoneMemberNow 用户须知配置
     * @return 结果
     */
    public int updatePhoneMemberNow(PhoneMemberNow phoneMemberNow);

    /**
     * 删除用户须知配置
     *
     * @param id 用户须知配置主键
     * @return 结果
     */
    public int deletePhoneMemberNowById(Long id);

    /**
     * 批量删除用户须知配置
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePhoneMemberNowByIds(Long[] ids);
}

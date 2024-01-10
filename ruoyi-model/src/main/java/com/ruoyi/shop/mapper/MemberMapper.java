package com.ruoyi.shop.mapper;

import java.util.List;

import com.ruoyi.common.core.domain.entity.Member;
import com.ruoyi.common.core.domain.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员管理Mapper接口
 *
 * @author ruoyi
 * @date 2024-01-04
 */
@Mapper
public interface MemberMapper
{
    /**
     * 查询会员管理
     *
     * @param id 会员管理主键
     * @return 会员管理
     */
    public Member selectMemberById(Long id);

    /**
     * 查询会员管理列表
     *
     * @param member 会员管理
     * @return 会员管理集合
     */
    public List<Member> selectMemberList(Member member);

    /**
     * 查询会员管理列表
     *
     * @param member 会员管理
     * @return 会员管理集合
     */
    public List<Member> selectMemberListByOpenId(Member member);

    /**
     * 校验手机号码是否唯一
     *
     * @param mobile 手机号码
     * @return 结果
     */
    public Member checkMobileUnique(String mobile);

    /**
     * 新增会员管理
     *
     * @param member 会员管理
     * @return 结果
     */
    public int insertMember(Member member);

    /**
     * 修改会员管理
     *
     * @param member 会员管理
     * @return 结果
     */
    public int updateMember(Member member);

    /**
     * 删除会员管理
     *
     * @param id 会员管理主键
     * @return 结果
     */
    public int deleteMemberById(Long id);

    /**
     * 批量删除会员管理
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMemberByIds(Long[] ids);
}

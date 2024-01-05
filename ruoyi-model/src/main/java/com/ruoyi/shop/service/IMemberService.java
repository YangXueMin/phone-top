package com.ruoyi.shop.service;

import com.ruoyi.common.core.domain.entity.Member;

import java.util.List;

/**
 * 会员管理Service接口
 *
 * @author ruoyi
 * @date 2024-01-04
 */
public interface IMemberService {
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
     * 根据openID获取用户
     * @param openId
     * @return
     */
    Member getMemberByOpenId(String openId);

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
     * 批量删除会员管理
     *
     * @param ids 需要删除的会员管理主键集合
     * @return 结果
     */
    public int deleteMemberByIds(Long[] ids);

    /**
     * 删除会员管理信息
     *
     * @param id 会员管理主键
     * @return 结果
     */
    public int deleteMemberById(Long id);
}

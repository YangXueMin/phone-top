package com.ruoyi.shop.service.impl;

import java.util.List;

import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.domain.entity.Member;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.shop.mapper.MemberMapper;
import com.ruoyi.shop.service.IMemberService;

/**
 * 会员管理Service业务层处理
 *
 * @author ruoyi
 * @date 2024-01-04
 */
@Service
public class MemberServiceImpl implements IMemberService {
    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private SysUserMapper sysUserMapper;

    /**
     * 查询会员管理
     *
     * @param id 会员管理主键
     * @return 会员管理
     */
    @Override
    public Member selectMemberById(Long id) {
        return memberMapper.selectMemberById(id);
    }

    /**
     * 查询会员管理列表
     *
     * @param member 会员管理
     * @return 会员管理
     */
    @Override
    public List<Member> selectMemberList(Member member) {
        return memberMapper.selectMemberList(member);
    }

    @Override
    public Member getMemberByOpenId(String openId) {
        Member member = new Member();
        member.setOpenId(openId);
        List<Member> memberList = memberMapper.selectMemberListByOpenId(member);
        if (memberList != null && memberList.size() > 0) {
            return memberList.get(0);
        }
        return null;
    }

    @Override
    public Member getMemberByMobile(String mobile) {
        Member member = new Member();
        member.setMobile(mobile);
        List<Member> memberList = memberMapper.selectMemberListByOpenId(member);
        if (memberList != null && memberList.size() > 0) {
            return memberList.get(0);
        }
        return null;
    }

    @Override
    public String checkMobileUnique(Member member) {
        Long id = StringUtils.isNull(member.getId()) ? -1L : member.getId();
        Member info = memberMapper.checkMobileUnique(member.getMobile());
        if (StringUtils.isNotNull(info) && info.getId().longValue() != id.longValue()) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 新增会员管理
     *
     * @param member 会员管理
     * @return 结果
     */
    @Override
    public int insertMember(Member member) {
        member.setCreateTime(DateUtils.getNowDate());
        return memberMapper.insertMember(member);
    }

    /**
     * 修改会员管理
     *
     * @param member 会员管理
     * @return 结果
     */
    @Override
    public int updateMember(Member member) {
        member.setUpdateTime(DateUtils.getNowDate());
        return memberMapper.updateMember(member);
    }

    /**
     * 批量删除会员管理
     *
     * @param ids 需要删除的会员管理主键
     * @return 结果
     */
    @Override
    public int deleteMemberByIds(Long[] ids) {
        return memberMapper.deleteMemberByIds(ids);
    }

    /**
     * 删除会员管理信息
     *
     * @param id 会员管理主键
     * @return 结果
     */
    @Override
    public int deleteMemberById(Long id) {
        return memberMapper.deleteMemberById(id);
    }

    @Override
    public Member getMemberInfo() {
        Long id = SecurityUtils.getLoginUser().getUserId();
        Member member = memberMapper.selectMemberById(id);
        if (member != null && StringUtils.isNotBlank(member.getMobile())) {
            SysUser sysUser = new SysUser();
            sysUser.setPhonenumber(member.getMobile());
            List<SysUser> userList = sysUserMapper.selectUserList(sysUser);
            if (userList.size() > 0) {
                member.setUserId(userList.get(0).getUserId());
                member.setSysUser(userList.get(0));
            }
        }
        return null;
    }
}

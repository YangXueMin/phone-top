package com.ruoyi.system.mapper;

import com.ruoyi.common.core.domain.entity.DataRequest;
import com.ruoyi.common.core.domain.entity.Member;
import com.ruoyi.system.domain.vo.LevelVo;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author ruoyi
 * @ClassName MemberMapper
 * @Description
 * @date 2024/3/1 10:01 AM
 */
@Mapper
public interface MemberMapper {
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
     * 查询下级会员管理列表
     *
     * @param member 会员管理
     * @return 会员管理集合
     */
    public List<LevelVo>selectSubordinateMemberCommissionList(Member member);

    /**
     * 查询下级会员管理列表
     *
     * @param member 会员管理
     * @return 会员管理集合
     */
    public List<LevelVo>selectSubordinateMemberOrderList(Member member);

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
     * 修改会员管理
     *
     * @return 结果
     */
    public int updateMemberExpiration();

    /**
     * 修改会员管理
     *
     * @return 结果
     */
    public int updateSuperMemberExpiration();

    /**
     * 重置用户密码
     *
     * @param id 会员ID
     * @param password 密码
     * @return 结果
     */
    public int resetMemberPwd(@Param("id") Long id, @Param("password") String password);

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

    /**
     * 统计用户数据
     * @param member
     * @return
     */
    public int getDayCount(Member member);


    /**
     * 统计订单总数
     * @param member
     * @return
     */
    public List<Map<String,Object>> getDayChartCount(Member member);

    /**
     * 统计订单总数
     * @param member
     * @return
     */
    public List<Map<String,Object>> getMonthChartCount(Member member);

    /**
     * 统计会员数据
     * @param dataRequest
     * @return
     */
    public List<Map<String,Object>> getMemberCount(DataRequest dataRequest);

    /**
     * 统计会员时间数据
     * @param dataRequest
     * @return
     */
    public List<Map<String,Object>> getMemberDateCount(DataRequest dataRequest);
}

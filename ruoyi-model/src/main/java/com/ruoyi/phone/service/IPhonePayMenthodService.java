package com.ruoyi.phone.service;

import java.util.List;

import com.ruoyi.phone.domain.PhonePayMenthod;

/**
 * 充值方式配置Service接口
 *
 * @author ruoyi
 * @date 2024-04-11
 */
public interface IPhonePayMenthodService {
    /**
     * 查询充值方式配置
     *
     * @param id 充值方式配置主键
     * @return 充值方式配置
     */
    public PhonePayMenthod selectPhonePayMenthodById(Long id);
    /**
     * 查询充值方式配置
     *
     * @param appId 充值方式配置主键
     * @return 充值方式配置
     */
    public List<PhonePayMenthod> selectPhonePayMenthodByAppId(String appId);

    /**
     * 查询充值方式配置列表
     *
     * @param phonePayMenthod 充值方式配置
     * @return 充值方式配置集合
     */
    public List<PhonePayMenthod> selectPhonePayMenthodList(PhonePayMenthod phonePayMenthod);

    /**
     * 新增充值方式配置
     *
     * @param phonePayMenthod 充值方式配置
     * @return 结果
     */
    public int insertPhonePayMenthod(PhonePayMenthod phonePayMenthod);

    /**
     * 新增充值方式配置
     *
     * @param list 充值方式配置
     * @return 结果
     */
    public int saveBach(List<PhonePayMenthod> list);

    /**
     * 修改充值方式配置
     *
     * @param phonePayMenthod 充值方式配置
     * @return 结果
     */
    public int updatePhonePayMenthod(PhonePayMenthod phonePayMenthod);

    /**
     * 批量删除充值方式配置
     *
     * @param ids 需要删除的充值方式配置主键集合
     * @return 结果
     */
    public int deletePhonePayMenthodByIds(Long[] ids);

    /**
     * 删除充值方式配置信息
     *
     * @param id 充值方式配置主键
     * @return 结果
     */
    public int deletePhonePayMenthodById(Long id);
}

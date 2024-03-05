package com.ruoyi.phone.service;

import java.util.List;
import com.ruoyi.phone.domain.PhoneCommissionLog;

/**
 * 佣金提现记录Service接口
 *
 * @author ruoyi
 * @date 2024-03-05
 */
public interface IPhoneCommissionLogService
{
    /**
     * 查询佣金提现记录
     *
     * @param id 佣金提现记录主键
     * @return 佣金提现记录
     */
    public PhoneCommissionLog selectPhoneCommissionLogById(Long id);

    /**
     * 查询佣金提现记录列表
     *
     * @param phoneCommissionLog 佣金提现记录
     * @return 佣金提现记录集合
     */
    public List<PhoneCommissionLog> selectPhoneCommissionLogList(PhoneCommissionLog phoneCommissionLog);

    /**
     * 新增佣金提现记录
     *
     * @param phoneCommissionLog 佣金提现记录
     * @return 结果
     */
    public int insertPhoneCommissionLog(PhoneCommissionLog phoneCommissionLog);

    /**
     * 修改佣金提现记录
     *
     * @param phoneCommissionLog 佣金提现记录
     * @return 结果
     */
    public int updatePhoneCommissionLog(PhoneCommissionLog phoneCommissionLog);

    /**
     * 批量删除佣金提现记录
     *
     * @param ids 需要删除的佣金提现记录主键集合
     * @return 结果
     */
    public int deletePhoneCommissionLogByIds(Long[] ids);

    /**
     * 删除佣金提现记录信息
     *
     * @param id 佣金提现记录主键
     * @return 结果
     */
    public int deletePhoneCommissionLogById(Long id);
}

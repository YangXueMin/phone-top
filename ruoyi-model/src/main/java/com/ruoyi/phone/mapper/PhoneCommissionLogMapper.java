package com.ruoyi.phone.mapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.ruoyi.phone.domain.PhoneOrder;
import org.apache.ibatis.annotations.Mapper;
import com.ruoyi.phone.domain.PhoneCommissionLog;

/**
 * 佣金提现记录Mapper接口
 *
 * @author ruoyi
 * @date 2024-03-05
 */
@Mapper
public interface PhoneCommissionLogMapper
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
     * 删除佣金提现记录
     *
     * @param id 佣金提现记录主键
     * @return 结果
     */
    public int deletePhoneCommissionLogById(Long id);

    /**
     * 批量删除佣金提现记录
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePhoneCommissionLogByIds(Long[] ids);

    /**
     * 统计基础数据
     * @param phoneCommissionLog
     * @return
     */
    public BigDecimal getCommissionCountMoney(PhoneCommissionLog phoneCommissionLog);

    /**
     * 统计支出金额
     * @param phoneCommissionLog
     * @return
     */
    public List<Map<String,Object>> getMonthCountMoney(PhoneCommissionLog phoneCommissionLog);
}

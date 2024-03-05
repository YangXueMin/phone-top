package com.ruoyi.phone.service;

import java.util.List;
import com.ruoyi.phone.domain.PhoneCommissionConfig;

/**
 * 佣金生成记录Service接口
 *
 * @author ruoyi
 * @date 2024-03-05
 */
public interface IPhoneCommissionConfigService
{
    /**
     * 查询佣金生成记录
     *
     * @param id 佣金生成记录主键
     * @return 佣金生成记录
     */
    public PhoneCommissionConfig selectPhoneCommissionConfigById(Long id);

    /**
     * 查询佣金生成记录列表
     *
     * @param phoneCommissionConfig 佣金生成记录
     * @return 佣金生成记录集合
     */
    public List<PhoneCommissionConfig> selectPhoneCommissionConfigList(PhoneCommissionConfig phoneCommissionConfig);

    /**
     * 新增佣金生成记录
     *
     * @param phoneCommissionConfig 佣金生成记录
     * @return 结果
     */
    public int insertPhoneCommissionConfig(PhoneCommissionConfig phoneCommissionConfig);

    /**
     * 修改佣金生成记录
     *
     * @param phoneCommissionConfig 佣金生成记录
     * @return 结果
     */
    public int updatePhoneCommissionConfig(PhoneCommissionConfig phoneCommissionConfig);

    /**
     * 批量删除佣金生成记录
     *
     * @param ids 需要删除的佣金生成记录主键集合
     * @return 结果
     */
    public int deletePhoneCommissionConfigByIds(Long[] ids);

    /**
     * 删除佣金生成记录信息
     *
     * @param id 佣金生成记录主键
     * @return 结果
     */
    public int deletePhoneCommissionConfigById(Long id);
}

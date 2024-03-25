package com.ruoyi.phone.mapper;

import java.util.List;
import java.util.Map;

import com.ruoyi.common.core.domain.entity.DataRequest;
import org.apache.ibatis.annotations.Mapper;
import com.ruoyi.phone.domain.PhoneCommissionConfig;

/**
 * 佣金生成记录Mapper接口
 *
 * @author ruoyi
 * @date 2024-03-05
 */
@Mapper
public interface PhoneCommissionConfigMapper
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
     * 删除佣金生成记录
     *
     * @param id 佣金生成记录主键
     * @return 结果
     */
    public int deletePhoneCommissionConfigById(Long id);

    /**
     * 批量删除佣金生成记录
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePhoneCommissionConfigByIds(Long[] ids);

    /**
     * 统计列表数据
     * @param dataRequest
     * @return
     */
    public List<Map<String,Object>> getListCount(DataRequest dataRequest);
}

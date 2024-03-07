package com.ruoyi.phone.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.ruoyi.phone.domain.PhoneBalanceLog;

/**
 * 余额充值记录Mapper接口
 *
 * @author ruoyi
 * @date 2024-03-05
 */
@Mapper
public interface PhoneBalanceLogMapper
{
    /**
     * 查询余额充值记录
     *
     * @param id 余额充值记录主键
     * @return 余额充值记录
     */
    public PhoneBalanceLog selectPhoneBalanceLogById(Long id);

    /**
     * 查询余额充值记录列表
     *
     * @param phoneBalanceLog 余额充值记录
     * @return 余额充值记录集合
     */
    public List<PhoneBalanceLog> selectPhoneBalanceLogList(PhoneBalanceLog phoneBalanceLog);

    /**
     * 查询余额充值记录列表
     *
     * @param phoneBalanceLog 余额充值记录
     * @return 余额充值记录集合
     */
    public List<PhoneBalanceLog> selectPhoneBalanceLogByOrderNo(String orderNo);

    /**
     * 新增余额充值记录
     *
     * @param phoneBalanceLog 余额充值记录
     * @return 结果
     */
    public int insertPhoneBalanceLog(PhoneBalanceLog phoneBalanceLog);

    /**
     * 修改余额充值记录
     *
     * @param phoneBalanceLog 余额充值记录
     * @return 结果
     */
    public int updatePhoneBalanceLog(PhoneBalanceLog phoneBalanceLog);

    /**
     * 删除余额充值记录
     *
     * @param id 余额充值记录主键
     * @return 结果
     */
    public int deletePhoneBalanceLogById(Long id);

    /**
     * 批量删除余额充值记录
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePhoneBalanceLogByIds(Long[] ids);
}

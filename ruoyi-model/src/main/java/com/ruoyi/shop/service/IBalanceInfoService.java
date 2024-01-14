package com.ruoyi.shop.service;

import com.ruoyi.shop.domain.BalanceInfo;

import java.util.List;

/**
 * 余额消费记录Service接口
 *
 * @author ruoyi
 * @date 2024-01-14
 */
public interface IBalanceInfoService {
    /**
     * 查询余额消费记录
     *
     * @param id 余额消费记录主键
     * @return 余额消费记录
     */
    public BalanceInfo selectBalanceInfoById(Long id);

    /**
     * 查询余额消费记录列表
     *
     * @param balanceInfo 余额消费记录
     * @return 余额消费记录集合
     */
    public List<BalanceInfo> selectBalanceInfoList(BalanceInfo balanceInfo);

    /**
     * 新增余额消费记录
     *
     * @param balanceInfo 余额消费记录
     * @return 结果
     */
    public int insertBalanceInfo(BalanceInfo balanceInfo);

    /**
     * 修改余额消费记录
     *
     * @param balanceInfo 余额消费记录
     * @return 结果
     */
    public int updateBalanceInfo(BalanceInfo balanceInfo);

    /**
     * 批量删除余额消费记录
     *
     * @param ids 需要删除的余额消费记录主键集合
     * @return 结果
     */
    public int deleteBalanceInfoByIds(Long[] ids);

    /**
     * 删除余额消费记录信息
     *
     * @param id 余额消费记录主键
     * @return 结果
     */
    public int deleteBalanceInfoById(Long id);
}

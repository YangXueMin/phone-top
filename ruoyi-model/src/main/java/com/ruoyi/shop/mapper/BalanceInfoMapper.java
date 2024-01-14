package com.ruoyi.shop.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.ruoyi.shop.domain.BalanceInfo;
import org.apache.ibatis.annotations.Param;

/**
 * 余额消费记录Mapper接口
 *
 * @author ruoyi
 * @date 2024-01-14
 */
@Mapper
public interface BalanceInfoMapper
{
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
     * 删除余额消费记录
     *
     * @param id 余额消费记录主键
     * @return 结果
     */
    public int deleteBalanceInfoById(Long id);

    /**
     * 批量删除余额消费记录
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBalanceInfoByIds(Long[] ids);

    /**
     * 删除余额消费记录
     *
     * @param orderId 需要删除的数据主键集合
     * @param orderType 订单类型
     * @return 结果
     */
    public int deleteBalanceInfoByOrderIdAndOrderType(@Param("orderId") Long orderId, @Param("orderType") String orderType);


}

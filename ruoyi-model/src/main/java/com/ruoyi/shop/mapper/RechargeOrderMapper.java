package com.ruoyi.shop.mapper;

import com.ruoyi.shop.domain.RechargeOrder;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 充值记录Mapper接口
 *
 * @author ruoyi
 * @date 2024-01-11
 */
@Mapper
public interface RechargeOrderMapper {
    /**
     * 查询充值记录
     *
     * @param id 充值记录主键
     * @return 充值记录
     */
    public RechargeOrder selectRechargeOrderById(Long id);

    /**
     * 查询充值记录列表
     *
     * @param rechargeOrder 充值记录
     * @return 充值记录集合
     */
    public List<RechargeOrder> selectRechargeOrderList(RechargeOrder rechargeOrder);

    /**
     * 新增充值记录
     *
     * @param rechargeOrder 充值记录
     * @return 结果
     */
    public int insertRechargeOrder(RechargeOrder rechargeOrder);

    /**
     * 修改充值记录
     *
     * @param rechargeOrder 充值记录
     * @return 结果
     */
    public int updateRechargeOrder(RechargeOrder rechargeOrder);

    /**
     * 删除充值记录
     *
     * @param id 充值记录主键
     * @return 结果
     */
    public int deleteRechargeOrderById(Long id);

    /**
     * 批量删除充值记录
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteRechargeOrderByIds(Long[] ids);
}

package com.ruoyi.shop.mapper;

import com.ruoyi.shop.domain.OfflineOrder;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 线下订单Mapper接口
 *
 * @author ruoyi
 * @date 2024-01-14
 */
@Mapper
public interface OfflineOrderMapper {
    /**
     * 查询线下订单
     *
     * @param id 线下订单主键
     * @return 线下订单
     */
    public OfflineOrder selectOfflineOrderById(Long id);

    /**
     * 查询线下订单列表
     *
     * @param offlineOrder 线下订单
     * @return 线下订单集合
     */
    public List<OfflineOrder> selectOfflineOrderList(OfflineOrder offlineOrder);

    /**
     * 根据订单编号查询订单
     * @param orderNumber
     * @return
     */
    public List<OfflineOrder> selectOfflineOrderByOrderNumber(String orderNumber);

    /**
     * 新增线下订单
     *
     * @param offlineOrder 线下订单
     * @return 结果
     */
    public int insertOfflineOrder(OfflineOrder offlineOrder);

    /**
     * 修改线下订单
     *
     * @param offlineOrder 线下订单
     * @return 结果
     */
    public int updateOfflineOrder(OfflineOrder offlineOrder);

    /**
     * 删除线下订单
     *
     * @param id 线下订单主键
     * @return 结果
     */
    public int deleteOfflineOrderById(Long id);

    /**
     * 批量删除线下订单
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteOfflineOrderByIds(Long[] ids);
}

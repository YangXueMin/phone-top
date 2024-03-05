package com.ruoyi.phone.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.ruoyi.phone.domain.PhoneOrder;

/**
 * 订单记录Mapper接口
 *
 * @author ruoyi
 * @date 2024-03-05
 */
@Mapper
public interface PhoneOrderMapper
{
    /**
     * 查询订单记录
     *
     * @param id 订单记录主键
     * @return 订单记录
     */
    public PhoneOrder selectPhoneOrderById(Long id);

    /**
     * 查询订单记录列表
     *
     * @param phoneOrder 订单记录
     * @return 订单记录集合
     */
    public List<PhoneOrder> selectPhoneOrderList(PhoneOrder phoneOrder);

    /**
     * 新增订单记录
     *
     * @param phoneOrder 订单记录
     * @return 结果
     */
    public int insertPhoneOrder(PhoneOrder phoneOrder);

    /**
     * 修改订单记录
     *
     * @param phoneOrder 订单记录
     * @return 结果
     */
    public int updatePhoneOrder(PhoneOrder phoneOrder);

    /**
     * 删除订单记录
     *
     * @param id 订单记录主键
     * @return 结果
     */
    public int deletePhoneOrderById(Long id);

    /**
     * 批量删除订单记录
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePhoneOrderByIds(Long[] ids);
}

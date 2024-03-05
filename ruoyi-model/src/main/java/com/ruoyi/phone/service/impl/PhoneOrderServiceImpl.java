package com.ruoyi.phone.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.phone.mapper.PhoneOrderMapper;
import com.ruoyi.phone.domain.PhoneOrder;
import com.ruoyi.phone.service.IPhoneOrderService;

/**
 * 订单记录Service业务层处理
 *
 * @author ruoyi
 * @date 2024-03-05
 */
@Service
public class PhoneOrderServiceImpl implements IPhoneOrderService
{
    @Autowired
    private PhoneOrderMapper phoneOrderMapper;

    /**
     * 查询订单记录
     *
     * @param id 订单记录主键
     * @return 订单记录
     */
    @Override
    public PhoneOrder selectPhoneOrderById(Long id)
    {
        return phoneOrderMapper.selectPhoneOrderById(id);
    }

    /**
     * 查询订单记录列表
     *
     * @param phoneOrder 订单记录
     * @return 订单记录
     */
    @Override
    public List<PhoneOrder> selectPhoneOrderList(PhoneOrder phoneOrder)
    {
        return phoneOrderMapper.selectPhoneOrderList(phoneOrder);
    }

    /**
     * 新增订单记录
     *
     * @param phoneOrder 订单记录
     * @return 结果
     */
    @Override
    public int insertPhoneOrder(PhoneOrder phoneOrder)
    {
        phoneOrder.setCreateTime(DateUtils.getNowDate());
        return phoneOrderMapper.insertPhoneOrder(phoneOrder);
    }

    /**
     * 修改订单记录
     *
     * @param phoneOrder 订单记录
     * @return 结果
     */
    @Override
    public int updatePhoneOrder(PhoneOrder phoneOrder)
    {
        phoneOrder.setUpdateTime(DateUtils.getNowDate());
        return phoneOrderMapper.updatePhoneOrder(phoneOrder);
    }

    /**
     * 批量删除订单记录
     *
     * @param ids 需要删除的订单记录主键
     * @return 结果
     */
    @Override
    public int deletePhoneOrderByIds(Long[] ids)
    {
        return phoneOrderMapper.deletePhoneOrderByIds(ids);
    }

    /**
     * 删除订单记录信息
     *
     * @param id 订单记录主键
     * @return 结果
     */
    @Override
    public int deletePhoneOrderById(Long id)
    {
        return phoneOrderMapper.deletePhoneOrderById(id);
    }
}

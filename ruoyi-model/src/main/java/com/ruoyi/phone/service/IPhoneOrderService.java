package com.ruoyi.phone.service;

import java.util.List;

import com.github.binarywang.wxpay.bean.order.WxPayMpOrderResult;
import com.ruoyi.phone.domain.PhoneMemberCardLog;
import com.ruoyi.phone.domain.PhoneOrder;

/**
 * 订单记录Service接口
 *
 * @author ruoyi
 * @date 2024-03-05
 */
public interface IPhoneOrderService {
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
     * 查询订单记录列表
     *
     * @param phoneOrder 订单记录
     * @return 订单记录集合
     */
    public Integer selectPhoneOrderCount(PhoneOrder phoneOrder);

    /**
     * 新增订单记录
     *
     * @param phoneOrder 订单记录
     * @return 结果
     */
    public PhoneOrder insertPhoneOrder(PhoneOrder phoneOrder);

    /**
     * 修改订单记录
     *
     * @param phoneOrder 订单记录
     * @return 结果
     */
    public int updatePhoneOrder(PhoneOrder phoneOrder);

    /**
     * 批量删除订单记录
     *
     * @param ids 需要删除的订单记录主键集合
     * @return 结果
     */
    public int deletePhoneOrderByIds(Long[] ids);

    /**
     * 删除订单记录信息
     *
     * @param id 订单记录主键
     * @return 结果
     */
    public int deletePhoneOrderById(Long id);

    /**
     * 发起支付
     *
     * @param phoneOrder 发起支付
     * @return 结果
     */
    public WxPayMpOrderResult pay(PhoneOrder phoneOrder);

    /**
     * 支付通知
     *
     * @param xmlData 支付通知
     * @return 结果
     */
    public String payNotify(String xmlData);
}

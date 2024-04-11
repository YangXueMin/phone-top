package com.ruoyi.phone.mapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.ruoyi.common.core.domain.entity.DataRequest;
import org.apache.ibatis.annotations.Mapper;
import com.ruoyi.phone.domain.PhoneOrder;
import org.apache.ibatis.annotations.Param;

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
     * 查询订单记录列表
     *
     * @param phoneOrder 订单记录
     * @return 订单记录集合
     */
    public Integer selectPhoneOrderCount(PhoneOrder phoneOrder);

    /**
     * 查询订单记录列表
     *
     * @param orderNo 订单编号
     * @return 订单记录集合
     */
    public List<PhoneOrder> selectPhoneOrderListByOrderNo(String orderNo);

    /**
     * 查询订单记录列表
     *
     * @param accountNumber 充值号
     * @return 订单记录集合
     */
    public List<PhoneOrder> selectPhoneOrderListByAccountNumber(String accountNumber);

    /**
     * 查询订单记录列表
     *
     * @param appId 微信小程序ID
     * @return 订单记录集合
     */
    public List<String> selectNewsflash(@Param("appId")String appId);

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

    /**
     * 统计订单笔数
     * @param phoneOrder
     * @return
     */
    public int getOrderCount(PhoneOrder phoneOrder);

    /**
     * 统计订单金额
     * @param phoneOrder
     * @return
     */
    public BigDecimal getOrderCountMoney(PhoneOrder phoneOrder);

    /**
     * 统计订单金额
     * @param phoneOrder
     * @return
     */
    public List<Map<String,Object>> getDayOrderCountMoney(PhoneOrder phoneOrder);

    /**
     * 统计订单金额
     * @param phoneOrder
     * @return
     */
    public List<Map<String,Object>> getMonthOrderCountMoney(PhoneOrder phoneOrder);

    /**
     * 统计订单总数
     * @param phoneOrder
     * @return
     */
    public List<Map<String,Object>> getDayOrderCount(PhoneOrder phoneOrder);

    /**
     * 统计订单总数
     * @param phoneOrder
     * @return
     */
    public List<Map<String,Object>> getMonthOrderCount(PhoneOrder phoneOrder);

    /**
     * 统计列表订单总数
     * @param dataRequest
     * @return
     */
    public List<Map<String,Object>> getOrderListCount(DataRequest dataRequest);

    /**
     * 统计列表充值成功订单总数
     * @param dataRequest
     * @return
     */
    public List<Map<String,Object>> getArrivalOrderListCount(DataRequest dataRequest);
}

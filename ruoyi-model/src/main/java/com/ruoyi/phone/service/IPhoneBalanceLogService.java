package com.ruoyi.phone.service;

import java.util.List;

import com.github.binarywang.wxpay.bean.order.WxPayMpOrderResult;
import com.ruoyi.phone.domain.PhoneBalanceLog;
import com.ruoyi.phone.domain.PhoneOrder;

/**
 * 余额充值记录Service接口
 *
 * @author ruoyi
 * @date 2024-03-05
 */
public interface IPhoneBalanceLogService
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
     * 新增余额充值记录
     *
     * @param phoneBalanceLog 余额充值记录
     * @return 结果
     */
    public PhoneBalanceLog insertPhoneBalanceLog(PhoneBalanceLog phoneBalanceLog);

    /**
     * 修改余额充值记录
     *
     * @param phoneBalanceLog 余额充值记录
     * @return 结果
     */
    public int updatePhoneBalanceLog(PhoneBalanceLog phoneBalanceLog);

    /**
     * 批量删除余额充值记录
     *
     * @param ids 需要删除的余额充值记录主键集合
     * @return 结果
     */
    public int deletePhoneBalanceLogByIds(Long[] ids);

    /**
     * 删除余额充值记录信息
     *
     * @param id 余额充值记录主键
     * @return 结果
     */
    public int deletePhoneBalanceLogById(Long id);

    /**
     * 发起支付
     *
     * @param phoneBalanceLog 发起支付
     * @return 结果
     */
    public WxPayMpOrderResult pay(PhoneBalanceLog phoneBalanceLog);

    /**
     * 支付通知
     *
     * @param xmlData 支付通知
     * @return 结果
     */
    public String payNotify(String xmlData);
}

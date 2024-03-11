package com.ruoyi.phone.service;

import java.util.List;

import com.github.binarywang.wxpay.bean.order.WxPayMpOrderResult;
import com.ruoyi.phone.domain.PhoneMemberCardLog;

/**
 * 会员卡充值记录Service接口
 *
 * @author ruoyi
 * @date 2024-03-05
 */
public interface IPhoneMemberCardLogService
{
    /**
     * 查询会员卡充值记录
     *
     * @param id 会员卡充值记录主键
     * @return 会员卡充值记录
     */
    public PhoneMemberCardLog selectPhoneMemberCardLogById(Long id);

    /**
     * 查询会员卡充值记录列表
     *
     * @param phoneMemberCardLog 会员卡充值记录
     * @return 会员卡充值记录集合
     */
    public List<PhoneMemberCardLog> selectPhoneMemberCardLogList(PhoneMemberCardLog phoneMemberCardLog);

    /**
     * 新增会员卡充值记录
     *
     * @param phoneMemberCardLog 会员卡充值记录
     * @return 结果
     */
    public PhoneMemberCardLog insertPhoneMemberCardLog(PhoneMemberCardLog phoneMemberCardLog);

    /**
     * 修改会员卡充值记录
     *
     * @param phoneMemberCardLog 会员卡充值记录
     * @return 结果
     */
    public int updatePhoneMemberCardLog(PhoneMemberCardLog phoneMemberCardLog);

    /**
     * 批量删除会员卡充值记录
     *
     * @param ids 需要删除的会员卡充值记录主键集合
     * @return 结果
     */
    public int deletePhoneMemberCardLogByIds(Long[] ids);

    /**
     * 删除会员卡充值记录信息
     *
     * @param id 会员卡充值记录主键
     * @return 结果
     */
    public int deletePhoneMemberCardLogById(Long id);

    /**
     * 发起支付
     *
     * @param phoneMemberCardLog 发起支付
     * @return 结果
     */
    public WxPayMpOrderResult pay(PhoneMemberCardLog phoneMemberCardLog);

    /**
     * 支付通知
     *
     * @param xmlData 支付通知
     * @return 结果
     */
    public String payNotify(String xmlData);
}

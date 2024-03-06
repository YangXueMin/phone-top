package com.ruoyi.phone.service.impl;

import java.math.BigDecimal;
import java.util.List;

import com.github.binarywang.wxpay.bean.order.WxPayMpOrderResult;
import com.ruoyi.common.config.WechatConfiguration;
import com.ruoyi.common.core.domain.entity.Member;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.phone.domain.PhoneBalanceLog;
import com.ruoyi.phone.mapper.PhoneBalanceLogMapper;
import com.ruoyi.system.mapper.MemberMapper;
import com.ruoyi.system.service.IWechatConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.phone.mapper.PhoneOrderMapper;
import com.ruoyi.phone.domain.PhoneOrder;
import com.ruoyi.phone.service.IPhoneOrderService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 订单记录Service业务层处理
 *
 * @author ruoyi
 * @date 2024-03-05
 */
@Service
public class PhoneOrderServiceImpl implements IPhoneOrderService {
    @Autowired
    private PhoneOrderMapper phoneOrderMapper;
    @Autowired
    private WechatConfiguration wechatConfiguration;
    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private PhoneBalanceLogMapper phoneBalanceLogMapper;
    @Autowired
    private IWechatConfigService wechatConfigService;

    /**
     * 查询订单记录
     *
     * @param id 订单记录主键
     * @return 订单记录
     */
    @Override
    public PhoneOrder selectPhoneOrderById(Long id) {
        return phoneOrderMapper.selectPhoneOrderById(id);
    }

    /**
     * 查询订单记录列表
     *
     * @param phoneOrder 订单记录
     * @return 订单记录
     */
    @Override
    public List<PhoneOrder> selectPhoneOrderList(PhoneOrder phoneOrder) {
        return phoneOrderMapper.selectPhoneOrderList(phoneOrder);
    }

    /**
     * 新增订单记录
     *
     * @param phoneOrder 订单记录
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public PhoneOrder insertPhoneOrder(PhoneOrder phoneOrder) {
        Member member = memberMapper.selectMemberById(phoneOrder.getMemberId());
        BigDecimal balance = member.getBalance();
        phoneOrder.setStatus("1");
        if(StringUtils.equals("1",phoneOrder.getPayType())){
            phoneOrder.setPayMoney(phoneOrder.getMoney());
            phoneOrder.setPayBalance(BigDecimal.ZERO);
            phoneOrder.setPayStatus("1");
        }else{
            BigDecimal money = BigDecimal.ZERO;
            if (phoneOrder.getMoney().compareTo(member.getBalance()) > 0) {
                member.setBalance(BigDecimal.ZERO);
                phoneOrder.setPayStatus("1");
                phoneOrder.setPayMoney(phoneOrder.getMoney().subtract(balance));
                money = balance;
                phoneOrder.setPayBalance(balance);
            } else {
                member.setBalance(balance.subtract(phoneOrder.getMoney()));
                phoneOrder.setPayBalance(phoneOrder.getMoney());
                money = phoneOrder.getMoney();
                phoneOrder.setPayStatus("2");
                phoneOrder.setPayMoney(BigDecimal.ZERO);
            }
            memberMapper.updateMember(member);
            //添加余额变更记录
            PhoneBalanceLog phoneBalanceLog = new PhoneBalanceLog();
            phoneBalanceLog.setCompanyId(phoneOrder.getCompanyId());
            phoneBalanceLog.setAppId(phoneOrder.getAppId());
            phoneBalanceLog.setMemberId(member.getMemberId());
            phoneBalanceLog.setType("2");
            phoneBalanceLog.setBalanceAfter(balance);
            phoneBalanceLog.setMoney(money);
            phoneBalanceLog.setBalanceBefore(member.getBalance());
            phoneBalanceLog.setCreateTime(DateUtils.getNowDate());
            phoneBalanceLogMapper.insertPhoneBalanceLog(phoneBalanceLog);
        }


        phoneOrder.setCreateTime(DateUtils.getNowDate());
        phoneOrderMapper.insertPhoneOrder(phoneOrder);
        return phoneOrder;
    }

    /**
     * 修改订单记录
     *
     * @param phoneOrder 订单记录
     * @return 结果
     */
    @Override
    public int updatePhoneOrder(PhoneOrder phoneOrder) {
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
    public int deletePhoneOrderByIds(Long[] ids) {
        return phoneOrderMapper.deletePhoneOrderByIds(ids);
    }

    /**
     * 删除订单记录信息
     *
     * @param id 订单记录主键
     * @return 结果
     */
    @Override
    public int deletePhoneOrderById(Long id) {
        return phoneOrderMapper.deletePhoneOrderById(id);
    }

    @Override
    public WxPayMpOrderResult pay(PhoneOrder phoneOrder) {
        return null;
    }

    @Override
    public String payNotify(String appid, String xmlData) {
        return null;
    }
}

package com.ruoyi.phone.task;

import com.ruoyi.common.core.domain.entity.Member;
import com.ruoyi.common.core.domain.entity.WechatConfig;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.time.DateUtil;
import com.ruoyi.phone.domain.PhoneBalanceLog;
import com.ruoyi.phone.domain.PhoneMemberCoupon;
import com.ruoyi.phone.domain.PhoneOrder;
import com.ruoyi.phone.mapper.PhoneBalanceLogMapper;
import com.ruoyi.phone.mapper.PhoneMemberCardMapper;
import com.ruoyi.phone.mapper.PhoneMemberCouponMapper;
import com.ruoyi.phone.mapper.PhoneOrderMapper;
import com.ruoyi.system.mapper.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author yangxuemin
 * @ClassName OrderTask
 * @Description
 * @date 2024/3/16 10:25 AM
 */
@Component("overtimeTask")
public class OrderTask {
    @Autowired
    private PhoneOrderMapper phoneOrderMapper;
    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private PhoneMemberCouponMapper phoneMemberCouponMapper;
    @Autowired
    private PhoneBalanceLogMapper phoneBalanceLogMapper;

    public void overtimeTask() {
        Date date = new Date();
        PhoneOrder phoneOrder = new PhoneOrder();
        phoneOrder.setPayStatus("1");
        phoneOrder.setCreateTime(DateUtil.subHours(date, 24));
        List<PhoneOrder> orderList = phoneOrderMapper.selectPhoneOrderList(phoneOrder);
        if (orderList.size() > 0) {
            for (PhoneOrder order : orderList) {
                order.setPayStatus("4");
                //取消后给用户退款
                if(order.getPayBalance().compareTo(BigDecimal.ZERO) > 0){
                    Member member = memberMapper.selectMemberById(order.getMemberId());
                    if(member != null){
                        BigDecimal balance = member.getBalance();
                        member.setBalance(balance.add(order.getPayBalance()));
                        memberMapper.updateMember(member);
                        //添加余额变更记录
                        PhoneBalanceLog phoneBalanceLog = new PhoneBalanceLog();
                        phoneBalanceLog.setDeptId(phoneOrder.getDeptId());
                        phoneBalanceLog.setAppId(phoneOrder.getAppId());
                        phoneBalanceLog.setMemberId(member.getMemberId());
                        phoneBalanceLog.setType("1");
                        phoneBalanceLog.setBalanceAfter(balance);
                        phoneBalanceLog.setMoney(order.getPayBalance());
                        phoneBalanceLog.setBalanceBefore(member.getBalance());
                        phoneBalanceLog.setCreateTime(DateUtils.getNowDate());
                        phoneBalanceLogMapper.insertPhoneBalanceLog(phoneBalanceLog);
                    }
                }
                if(order.getCouponId() != null){
                    PhoneMemberCoupon phoneMemberCoupon = phoneMemberCouponMapper.selectPhoneMemberCouponById(order.getCouponId());
                    if(phoneMemberCoupon != null){
                        phoneMemberCoupon.setStatus("1");
                        phoneMemberCouponMapper.updatePhoneMemberCoupon(phoneMemberCoupon);
                    }
                }
            }
        }
    }

}

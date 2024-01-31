package com.ruoyi.shop.task;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.shop.domain.RechargeOrderCoupon;
import com.ruoyi.shop.mapper.RechargeOrderCouponMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author yangxuemin
 * @ClassName RechargeOrderCouponTask
 * @Description
 * @date 2024/1/31 9:45 AM
 */
@Component("rechargeOrderCouponTask")
public class RechargeOrderCouponTask {
    @Autowired
    private RechargeOrderCouponMapper rechargeOrderCouponMapper;

    public void updateStatus() {
        System.out.println("更新优惠券状态");
        RechargeOrderCoupon rechargeOrderCoupon = new RechargeOrderCoupon();
        rechargeOrderCoupon.setStatus("1");
        List<RechargeOrderCoupon> rechargeOrderCouponList = rechargeOrderCouponMapper.selectRechargeOrderCouponList(rechargeOrderCoupon);
        if(rechargeOrderCouponList != null && rechargeOrderCouponList.size() > 0){
            List<Long> idList = new ArrayList<>();
            for (RechargeOrderCoupon orderCoupon : rechargeOrderCouponList) {
                if(orderCoupon.getCoupon() != null && orderCoupon.getCreateTime() != null){
                    Date date = DateUtils.addDays(orderCoupon.getCreateTime(), orderCoupon.getCoupon().getTermValidity());
                    if(date.getTime() < DateUtils.getNowDate().getTime()){
                        idList.add(orderCoupon.getId());
                    }
                }
            }
            if(idList.size() > 0){
                rechargeOrderCouponMapper.updateRechargeOrderCouponStatusByIds(idList.toArray(new Long[0]));
            }
        }
    }
}

package com.ruoyi.shop.task;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.time.DateUtil;
import com.ruoyi.shop.domain.Order;
import com.ruoyi.shop.domain.RechargeOrderCoupon;
import com.ruoyi.shop.mapper.OrderMapper;
import com.ruoyi.shop.mapper.RechargeOrderCouponMapper;
import com.ruoyi.shop.mapper.RechargeOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * @author yangxuemin
 * @ClassName OrderTask
 * @Description
 * @date 2024/1/22 11:14 PM
 */
@Component("orderTask")
public class OrderTask {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private RechargeOrderMapper rechargeOrderMapper;
    @Autowired
    private RechargeOrderCouponMapper rechargeOrderCouponMapper;

    public void updateTimeOrderStatus() {
        System.out.println("更新订单状态");
        Date date = new Date();
        Order queryOrder = new Order();
        queryOrder.setOrderStatus("1");
        queryOrder.setPayType("2");
        List<Order> orderList = orderMapper.selectOrderList(queryOrder);
        if (orderList.size() > 0) {
            for (Order order : orderList) {
                if (order.getCreateTime().getTime() < DateUtil.subMinutes(date, 10).getTime()) {
                    if(StringUtils.isNotBlank(order.getCouponList())){
                        //还原用户优惠券
                        String[] couponList = order.getCouponList().split(",");
                        for (String couponId : couponList) {
                            RechargeOrderCoupon rechargeOrderCoupon = rechargeOrderCouponMapper.selectRechargeOrderCouponById(Long.parseLong(couponId));
                            if (rechargeOrderCoupon.getId() != null) {
                                rechargeOrderCoupon.setStatus("1");
                                rechargeOrderCoupon.setUpdateTime(DateUtils.getNowDate());
                                rechargeOrderCouponMapper.updateRechargeOrderCoupon(rechargeOrderCoupon);
                            }
                        }
                    }
                }
            }
        }
        orderMapper.updateTimeOrderStatus();
        rechargeOrderMapper.updateTimeOrderStatus();
    }
}

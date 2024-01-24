package com.ruoyi.shop.task;

import com.ruoyi.shop.mapper.OrderMapper;
import com.ruoyi.shop.mapper.RechargeOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

    public void updateTimeOrderStatus() {
        System.out.println("更新订单状态");
        orderMapper.updateTimeOrderStatus();
        rechargeOrderMapper.updateTimeOrderStatus();
    }
}

package com.ruoyi.phone.service;

import com.alibaba.fastjson2.JSONArray;
import com.ruoyi.phone.domain.PhoneOrder;

/**
 * @author yangxuemin
 * @ClassName PhoneCountService
 * @Description
 * @date 2024/3/17 10:27 AM
 */
public interface PhoneCountService {

    /**
     * 统计月数据
     *
     * @param phoneOrder
     * @return
     */
    JSONArray getMonthCount(PhoneOrder phoneOrder);
}

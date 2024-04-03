package com.ruoyi.phone.service;

import com.alibaba.fastjson2.JSONArray;
import com.ruoyi.common.core.domain.entity.DataRequest;
import com.ruoyi.phone.domain.PhoneOrder;

/**
 * @author ruoyi
 * @ClassName PhoneCountService
 * @Description
 * @date 2024/3/17 10:27 AM
 */
public interface PhoneCountService {

    /**
     * 统计日数据
     *
     * @param phoneOrder
     * @return
     */
    JSONArray getDayChartCount(PhoneOrder phoneOrder);

    /**
     * 统计月数据
     *
     * @param phoneOrder
     * @return
     */
    JSONArray getMonthCount(PhoneOrder phoneOrder);

    /**
     * 统计列表数据
     *
     * @param dataRequest
     * @return
     */
    JSONArray getListCount(DataRequest dataRequest);
}

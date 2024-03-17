package com.ruoyi.phone.service.impl;

import com.alibaba.fastjson2.JSONArray;
import com.ruoyi.common.core.domain.entity.Member;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.time.DateFormatUtil;
import com.ruoyi.common.utils.time.DateUtil;
import com.ruoyi.phone.domain.PhoneCommissionLog;
import com.ruoyi.phone.domain.PhoneOrder;
import com.ruoyi.phone.mapper.PhoneCommissionLogMapper;
import com.ruoyi.phone.mapper.PhoneOrderMapper;
import com.ruoyi.phone.service.PhoneCountService;
import com.ruoyi.system.mapper.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author yangxuemin
 * @ClassName PhoneCountServiceImpl
 * @Description
 * @date 2024/3/17 10:27 AM
 */
@Service
public class PhoneCountServiceImpl implements PhoneCountService {
    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private PhoneOrderMapper phoneOrderMapper;
    @Autowired
    private PhoneCommissionLogMapper phoneCommissionLogMapper;


    @Override
    public JSONArray getMonthCount(PhoneOrder phoneOrder) {
        JSONArray result = new JSONArray();
        Map<String, Object> businessMap = new HashMap<>();
        businessMap.put("name", "成交额");
        businessMap.put("data", getOrderMoneyMonthCount(phoneOrder));
        result.add(businessMap);

        Map<String, Object> orderMap = new HashMap<>();
        orderMap.put("name", "订单");
        orderMap.put("data", getOrderMonthCount(phoneOrder));
        result.add(orderMap);

        Map<String, Object> memberMap = new HashMap<>();
        memberMap.put("name", "新增会员");
        memberMap.put("data", getMemberMonthCount(phoneOrder));
        result.add(memberMap);

        Map<String, Object> expensesMap = new HashMap<>();
        expensesMap.put("name", "支出");
        expensesMap.put("data", getCommissionMonthCount(phoneOrder));
        result.add(expensesMap);

        return result;
    }

    /**
     * 获取成交额
     *
     * @param phoneOrder
     * @return
     */
    public List<Object> getOrderMoneyMonthCount(PhoneOrder phoneOrder) {
        //初始化数据
        Map<String, Object> map = new LinkedHashMap<>();
        Date date = DateUtil.beginOfYear(new Date());
        for (int i = 0; i < 12; i++) {
            map.put(DateFormatUtil.formatDate(DateFormatUtil.PATTERN_ISO_ON_MONTH_DATE, DateUtil.addMonths(date, i)), 0);
        }
        List<Object> orderList = new ArrayList<>();
        List<Map<String, Object>> monthOrderCountMoney = phoneOrderMapper.getMonthOrderCountMoney(phoneOrder);
        map.forEach((k, v) -> {
            for (Map<String, Object> stringObjectMap : monthOrderCountMoney) {
                if (StringUtils.equals(k, stringObjectMap.get("months").toString())) {
                    map.put(k, stringObjectMap.get("money"));
                }
            }
        });
        map.forEach((k, v) -> {
            orderList.add(v);
        });
        return orderList;
    }

    public List<Object> getOrderMonthCount(PhoneOrder phoneOrder) {
        //初始化数据
        Map<String, Object> map = new LinkedHashMap<>();
        Date date = DateUtil.beginOfYear(new Date());
        for (int i = 0; i < 12; i++) {
            map.put(DateFormatUtil.formatDate(DateFormatUtil.PATTERN_ISO_ON_MONTH_DATE, DateUtil.addMonths(date, i)), 0);
        }
        List<Object> orderList = new ArrayList<>();
        List<Map<String, Object>> monthOrderCountMoney = phoneOrderMapper.getMonthOrderCount(phoneOrder);
        map.forEach((k, v) -> {
            for (Map<String, Object> stringObjectMap : monthOrderCountMoney) {
                if (StringUtils.equals(k, stringObjectMap.get("months").toString())) {
                    map.put(k, stringObjectMap.get("number"));
                }
            }
        });
        map.forEach((k, v) -> {
            orderList.add(v);
        });
        return orderList;
    }

    /**
     * 获取会员
     *
     * @param phoneOrder
     * @return
     */
    public List<Object> getMemberMonthCount(PhoneOrder phoneOrder) {
        //初始化数据
        Map<String, Object> map = new LinkedHashMap<>();
        Date date = DateUtil.beginOfYear(new Date());
        for (int i = 0; i < 12; i++) {
            map.put(DateFormatUtil.formatDate(DateFormatUtil.PATTERN_ISO_ON_MONTH_DATE, DateUtil.addMonths(date, i)), 0);
        }
        List<Object> orderList = new ArrayList<>();
        Member member = new Member();
        member.setParams(phoneOrder.getParams());
        member.setAppId(phoneOrder.getAppId());
        List<Map<String, Object>> monthOrderCountMoney = memberMapper.getMonthCount(member);
        map.forEach((k, v) -> {
            for (Map<String, Object> stringObjectMap : monthOrderCountMoney) {
                if (StringUtils.equals(k, stringObjectMap.get("months").toString())) {
                    map.put(k, stringObjectMap.get("number"));
                }
            }
        });
        map.forEach((k, v) -> {
            orderList.add(v);
        });
        return orderList;
    }

    /**
     * 获取支出
     *
     * @param phoneOrder
     * @return
     */
    public List<Object> getCommissionMonthCount(PhoneOrder phoneOrder) {
        //初始化数据
        Map<String, Object> map = new LinkedHashMap<>();
        Date date = DateUtil.beginOfYear(new Date());
        for (int i = 0; i < 12; i++) {
            map.put(DateFormatUtil.formatDate(DateFormatUtil.PATTERN_ISO_ON_MONTH_DATE, DateUtil.addMonths(date, i)), 0);
        }
        List<Object> orderList = new ArrayList<>();
        PhoneCommissionLog phoneCommissionLog = new PhoneCommissionLog();
        phoneCommissionLog.setParams(phoneOrder.getParams());
        phoneCommissionLog.setAppId(phoneOrder.getAppId());
        List<Map<String, Object>> monthOrderCountMoney = phoneCommissionLogMapper.getMonthCountMoney(phoneCommissionLog);
        map.forEach((k, v) -> {
            for (Map<String, Object> stringObjectMap : monthOrderCountMoney) {
                if (StringUtils.equals(k, stringObjectMap.get("months").toString())) {
                    map.put(k, stringObjectMap.get("money"));
                }
            }
        });
        map.forEach((k, v) -> {
            orderList.add(v);
        });
        return orderList;
    }
}

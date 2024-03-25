package com.ruoyi.phone.service.impl;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.core.domain.entity.DataRequest;
import com.ruoyi.common.core.domain.entity.Member;
import com.ruoyi.common.core.domain.entity.WechatConfig;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.time.DateFormatUtil;
import com.ruoyi.common.utils.time.DateUtil;
import com.ruoyi.phone.domain.PhoneCommissionLog;
import com.ruoyi.phone.domain.PhoneOrder;
import com.ruoyi.phone.mapper.PhoneCommissionConfigMapper;
import com.ruoyi.phone.mapper.PhoneCommissionLogMapper;
import com.ruoyi.phone.mapper.PhoneOrderMapper;
import com.ruoyi.phone.service.PhoneCountService;
import com.ruoyi.system.mapper.MemberMapper;
import com.ruoyi.system.mapper.WechatConfigMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author ruoyi
 * @ClassName PhoneCountServiceImpl
 * @Description
 * @date 2024/3/17 10:27 AM
 */
@Service
public class PhoneCountServiceImpl implements PhoneCountService {
    @Autowired
    private WechatConfigMapper wechatConfigMapper;
    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private PhoneOrderMapper phoneOrderMapper;
    @Autowired
    private PhoneCommissionLogMapper phoneCommissionLogMapper;
    @Autowired
    private PhoneCommissionConfigMapper phoneCommissionConfigMapper;


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

    @Override
    public JSONArray getListCount(DataRequest dataRequest) {
        JSONArray result = new JSONArray();
        WechatConfig query = new WechatConfig();
        query.setParams(dataRequest.getParams());
        List<WechatConfig> list = wechatConfigMapper.selectWechatConfigList(query);
        List<Map<String, Object>> memberList = memberMapper.getMemberCount(dataRequest);
        List<Map<String, Object>> memberDateList = memberMapper.getMemberDateCount(dataRequest);
        List<Map<String, Object>> orderList = phoneOrderMapper.getOrderListCount(dataRequest);
        List<Map<String, Object>> orderArrivalList = phoneOrderMapper.getArrivalOrderListCount(dataRequest);
        List<Map<String, Object>> logList = phoneCommissionLogMapper.getListCount(dataRequest);
        List<Map<String, Object>> commissionLogList = phoneCommissionConfigMapper.getListCount(dataRequest);
        for (WechatConfig wechatConfig : list) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("title", wechatConfig.getTitle());
            jsonObject.put("appId", wechatConfig.getAppId());
            if (memberList.size() > 0) {
                for (Map<String, Object> map : memberList) {
                    if (map.get("appId") != null && StringUtils.equals(wechatConfig.getAppId(), map.get("appId").toString())) {
                        jsonObject.put("memberCount", map.get("memberCount"));
                    }
                }
                for (Map<String, Object> map : memberDateList) {
                    if (map.get("appId") != null && StringUtils.equals(wechatConfig.getAppId(), map.get("appId").toString())) {
                        jsonObject.put("memberDateCount", map.get("memberDateCount"));
                    }
                }
                for (Map<String, Object> map : orderList) {
                    if (map.get("appId") != null && StringUtils.equals(wechatConfig.getAppId(), map.get("appId").toString())) {
                        jsonObject.put("orderMoney", map.get("orderMoney"));
                        jsonObject.put("orderCount", map.get("orderCount"));
                    }
                }
                for (Map<String, Object> map : orderArrivalList) {
                    if (map.get("appId") != null && StringUtils.equals(wechatConfig.getAppId(), map.get("appId").toString())) {
                        jsonObject.put("successMoney", map.get("successMoney"));
                        jsonObject.put("orderSuccessCount", map.get("orderSuccessCount"));
                    }
                }
                for (Map<String, Object> map : logList) {
                    if (map.get("appId") != null && StringUtils.equals(wechatConfig.getAppId(), map.get("appId").toString())) {
                        jsonObject.put("cashMoney", map.get("money"));
                    }
                }
                for (Map<String, Object> map : commissionLogList) {
                    if (map.get("appId") != null && StringUtils.equals(wechatConfig.getAppId(), map.get("appId").toString())) {
                        jsonObject.put("commissionMoney", map.get("money"));
                    }
                }
            }
            result.add(jsonObject);
        }
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

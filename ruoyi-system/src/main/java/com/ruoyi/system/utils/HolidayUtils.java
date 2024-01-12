package com.ruoyi.system.utils;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.common.utils.time.DateFormatUtil;
import com.ruoyi.system.domain.Holiday;
import com.ruoyi.system.mapper.HolidayMapper;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Administrator
 */
@Component
public class HolidayUtils {
    private static String EASY_BOT_URL = "https://timor.tech/api/holiday/year/";
    private static HolidayMapper holidayMapper = SpringUtils.getBean(HolidayMapper.class);

    /**
     * 获取当年的节假日, 休息日
     *
     * @throws Exception
     */
    public static List<Holiday> getHolidays() throws Exception {
        //创建httpclient
        HttpClient httpClient = new DefaultHttpClient();
        //创建httpget对象
        HttpGet httpGet = new HttpGet();
        //构建url, 先从easyBot获取所有节假日, 包括周六日
        String nowYear = new SimpleDateFormat("yyyy").format(new Date());
        httpGet.setURI(URI.create(EASY_BOT_URL + nowYear));
        try {
            //发送get请求
            HttpResponse response = httpClient.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                throw new Exception("error");
            } else {
                String rstStr = EntityUtils.toString(response.getEntity());
                JSONObject json = JSONObject.parseObject(rstStr);
                if (json.getInteger("code") != 0) {
                    throw new Exception("error");
                } else {
                    List<Holiday> holidays = new ArrayList<>();
                    Map<String, Object> map = JSON.parseObject(json.get("holiday").toString(), Map.class);
                    for (Map.Entry<String, Object> entry : map.entrySet()) {
                        Object value = entry.getValue();
                        // 在此处使用key和value进行操作
                        Holiday holiday = JSON.parseObject(value.toString(), Holiday.class);
                        holidays.add(holiday);
                    }
                    return holidays;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 判断当前日期是否为休息日
     *
     * @param date
     * @return 返回true表示是，返回false表示不是
     */
    public static boolean check(Date date) {
        Holiday holiday = holidayMapper.selectHolidayById(DateFormatUtil.formatDate(DateFormatUtil.PATTERN_ISO_ON_DATE, date));
        if (holiday != null) {
            return holiday.isHoliday();
        }
        return false;
    }

}

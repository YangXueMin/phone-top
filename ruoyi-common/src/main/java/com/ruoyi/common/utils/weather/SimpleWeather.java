package com.ruoyi.common.utils.weather;

import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.utils.http.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ruoyi
 * @ClassName WeatherUtils
 * @Description
 * @date 2023/6/1 2:19 PM
 */
public class SimpleWeather {
    private static final Logger log = LoggerFactory.getLogger(SimpleWeather.class);

    /**
     * 聚合天气查询
     */
    public static final String URL = "http://apis.juhe.cn/simpleWeather/query";

    /**
     * key
     */
    public static final String KEY = "149496b567237d2dd28a562b9af67b96";

    public static JSONObject getSimpleWeatherByCity(String city) {
        String rspStr = HttpUtils.sendGet(URL, "key=" + KEY + "&city=" + city, Constants.UTF8);
        return JSONObject.parseObject(rspStr);
    }

}

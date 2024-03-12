package com.ruoyi.common.utils;

import com.alibaba.fastjson2.JSON;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author yangxuemin
 * @ClassName JsonUtils
 * @Description
 * @date 2024/3/1 10:47 AM
 */
public class JsonUtils {
    public static String toJson(Object obj) {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .disableHtmlEscaping()
                .create();
        return gson.toJson(obj);
    }

    public static boolean isJson2(String string) {
        boolean result = false;
        try {
            JSON.parse(string);
            result = true;
        } catch (Exception e) {
            result = false;
        }
        return result;
    }
}

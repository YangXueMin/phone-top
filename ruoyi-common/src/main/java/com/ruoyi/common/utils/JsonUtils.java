package com.ruoyi.common.utils;

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
}

package com.ruoyi.common.utils.great;

import cn.hutool.crypto.SmUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson2.JSON;

import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author yangxuemin
 * @ClassName SignUtils
 * @Description
 * @date 2024/3/12 5:20 PM
 */
public class SignUtils {

    public static String unionSign(TreeMap<String, String> params,String apikey) throws Exception {
        params.remove("sign");
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (entry.getValue() != null && entry.getValue().length() > 0) {
                sb.append(entry.getKey()).append("=").append(entry.getValue())
                        .append("&");
            }
        }
        sb.append("apikey=" + apikey + "&");
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        // 记得是md5编码的加签
        String sign = md5(sb.toString().getBytes("UTF-8"));
        params.remove("key");
        return sign.toUpperCase(Locale.ROOT);
    }

    public static void main(String[] args) throws Exception {
        String url = "http://101.33.209.99/";
        String userId = "74";
        String apikey = "A8E20FB194D86087AD24B3172461C5AE";
        TreeMap<String, String> params = new TreeMap<>();
        params.put("userid",userId);
        params.put("type","1");

        params.put("sign",unionSign(params,apikey));

        final String post = HttpUtil.post(url + GreatUrlConstants.QUERY_PRODUCT, JSON.toJSONString(params));

        System.out.println(post);
    }

    /**
     * md5
     *
     * @param b
     * @return
     */
    public static String md5(byte[] b) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.reset();
            md.update(b);
            byte[] hash = md.digest();
            StringBuilder outStrBuf = new StringBuilder(32);
            for (byte value : hash) {
                int v = value & 0xFF;
                if (v < 16) {
                    outStrBuf.append('0');
                }
                outStrBuf.append(Integer.toString(v, 16).toLowerCase());
            }
            return outStrBuf.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return new String(b);
        }
    }
}

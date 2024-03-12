package com.ruoyi.common.constant;

/**
 * 缓存的key 常量
 * 
 * @author ruoyi
 */
public class CacheConstants
{
    /**
     * 登录用户 redis key
     */
    public static final String LOGIN_TOKEN_KEY = "login_tokens:";

    /**
     * 验证码 redis key
     */
    public static final String CAPTCHA_CODE_KEY = "captcha_codes:";

    /**
     * 参数管理 cache key
     */
    public static final String SYS_CONFIG_KEY = "sys_config:";

    /**
     * 参数管理 cache key
     */
    public static final String WECHAT_JSAPI_KEY = "wechat_jsapi:";

    /**
     * 参数管理 cache key
     */
    public static final String WECHAT_CONFIG_KEY = "wechat_config:";

    /**
     * 参数管理 cache key
     */
    public static final String WECHAT_BANNER_CONFIG_KEY = "wechat_banner_config:";

    /**
     * 参数管理 cache key
     */
    public static final String MEMBER_QR_CODE_KEY = "member_qr_code:";

    /**
     * 参数管理 cache key
     */
    public static final String WECHAT_ACCESS_TOKEN_KEY = "wechat_accessToken:";

    /**
     * 参数管理 cache key
     */
    public static final String WECHAT_CUSTOMER_CONFIG_KEY = "wechat_customer_config:";

    /**
     * 参数管理 cache key
     */
    public static final String WECHAT_MEMBER_NOW_KEY = "wechat_member_now:";

    /**
     * 参数管理 cache key
     */
    public static final String WECHAT_COMPANY_CONFIG_KEY = "wechat_company_config:";

    /**
     * 字典管理 cache key
     */
    public static final String SYS_DICT_KEY = "sys_dict:";

    /**
     * 防重提交 redis key
     */
    public static final String REPEAT_SUBMIT_KEY = "repeat_submit:";

    /**
     * 限流 redis key
     */
    public static final String RATE_LIMIT_KEY = "rate_limit:";

    /**
     * 登录账户密码错误次数 redis key
     */
    public static final String PWD_ERR_CNT_KEY = "pwd_err_cnt:";

    /**
     * 支付密码错误次数 redis key
     */
    public static final String PWD_ERR_PAY_KEY = "pwd_err_pay:";

    /**
     * 城市天气 redis key
     */
    public static final String CITY_WEATHER_KEY = "city_weather:";
}

package com.ruoyi.common.utils.great;

/**
 * @author ruoyi
 * @ClassName UrlEnum
 * @Description
 * @date 2024/3/12 6:53 PM
 */
public class GreatUrlConstants {
    /**
     * 充值接口
     */
    public final static String CREATE_ORDER = "/yrapi.php/index/recharge";

    /**
     * 查询用户接口
     */
    public final static String QUERY_USER = "/yrapi.php/index/user";

    /**
     * 获取产品类型和产品分类
     */
    public final static String QUERY_PRODUCT_TYPE = "/yrapi.php/index/typecate";

    /**
     * 获取产品
     */
    public final static String QUERY_PRODUCT = "/yrapi.php/index/product";

    /**
     * 自发查询订单状态
     */
    public final static String QUERY_ORDER_STATUS = "/yrapi.php/index/check";

    /**
     * 电费支持地区查询
     */
    public final static String QUERY_ELECTRICITY_AREA = "/yrapi.php/index/elecity";

    /**
     * 退单申请
     */
    public final static String CANCEL_ORDER = "/yrapi.php/index/cancel";
}

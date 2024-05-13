package com.ruoyi.phone.service.impl;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.github.binarywang.wxpay.bean.notify.WxPayNotifyResponse;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.bean.notify.WxPayRefundNotifyResult;
import com.github.binarywang.wxpay.bean.order.WxPayMpOrderResult;
import com.github.binarywang.wxpay.bean.request.WxPayRefundRequest;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.bean.result.WxPayRefundResult;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.ruoyi.common.config.WechatConfiguration;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.entity.Member;
import com.ruoyi.common.core.domain.entity.WechatConfig;
import com.ruoyi.common.utils.*;
import com.ruoyi.common.utils.great.GreatUrlConstants;
import com.ruoyi.common.utils.great.SignUtils;
import com.ruoyi.common.utils.time.DateFormatUtil;
import com.ruoyi.common.utils.time.DateUtil;
import com.ruoyi.common.utils.uuid.IdUtils;
import com.ruoyi.phone.domain.*;
import com.ruoyi.phone.mapper.*;
import com.ruoyi.phone.service.IPhoneOrderService;
import com.ruoyi.system.mapper.MemberMapper;
import com.ruoyi.system.service.IWechatConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.util.*;

/**
 * 订单记录Service业务层处理
 *
 * @author ruoyi
 * @date 2024-03-05
 */
@Service
@Slf4j
public class PhoneOrderServiceImpl implements IPhoneOrderService {

    private static List<String> directManageCityList = Arrays.asList("北京", "天津", "上海", "重庆");


    @Autowired
    private PhoneOrderMapper phoneOrderMapper;
    @Autowired
    private WechatConfiguration wechatConfiguration;
    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private PhoneBalanceLogMapper phoneBalanceLogMapper;
    @Autowired
    private IWechatConfigService wechatConfigService;
    @Autowired
    private PhoneInterfaceConfigMapper phoneInterfaceConfigMapper;
    @Autowired
    private PhonePriceMapper phonePriceMapper;
    @Autowired
    private PhoneCommissionConfigMapper phoneCommissionConfigMapper;
    @Autowired
    private PhoneCouponMapper phoneCouponMapper;
    @Autowired
    private PhoneMemberCouponMapper phoneMemberCouponMapper;

    /**
     * 查询订单记录
     *
     * @param id 订单记录主键
     * @return 订单记录
     */
    @Override
    public PhoneOrder selectPhoneOrderById(Long id) {
        return phoneOrderMapper.selectPhoneOrderById(id);
    }

    /**
     * 查询订单记录列表
     *
     * @param phoneOrder 订单记录
     * @return 订单记录
     */
    @Override
    public List<PhoneOrder> selectPhoneOrderList(PhoneOrder phoneOrder) {
        return phoneOrderMapper.selectPhoneOrderList(phoneOrder);
    }

    @Override
    public JSONObject getElecityArea(String appId) {
        //如果是直充
        PhoneInterfaceConfig phoneInterfaceConfig = new PhoneInterfaceConfig();
        phoneInterfaceConfig.setAppId(appId);
        phoneInterfaceConfig.setSwitchType("1");
        List<PhoneInterfaceConfig> phoneInterfaceConfigList = phoneInterfaceConfigMapper.selectPhoneInterfaceConfigList(phoneInterfaceConfig);
        if (phoneInterfaceConfigList != null && phoneInterfaceConfigList.size() > 0) {
            phoneInterfaceConfig = phoneInterfaceConfigList.get(0);

            TreeMap<String, String> params = new TreeMap<>();
            params.put("userid", phoneInterfaceConfig.getMchId());
            try {
                params.put("sign", SignUtils.unionSign(params, phoneInterfaceConfig.getApiKey()));
                String post = HttpUtil.post(phoneInterfaceConfig.getInterfaceUrl() + GreatUrlConstants.QUERY_ELECTRICITY_AREA, JSON.toJSONString(params));
                if (StringUtils.isNotBlank(post) && JsonUtils.isJson2(post)) {
                    JSONObject jsonObject = JSON.parseObject(post);
                    if (jsonObject != null && jsonObject.get("errno") != null && jsonObject.getInteger("errno") == 0) {
                        return jsonObject;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public Integer selectPhoneOrderCount(PhoneOrder phoneOrder) {
        return phoneOrderMapper.selectPhoneOrderCount(phoneOrder);
    }

    /**
     * 新增订单记录
     *
     * @param phoneOrder 订单记录
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public PhoneOrder insertPhoneOrder(PhoneOrder phoneOrder) {
        final WechatConfig wechatConfig = wechatConfigService.selectWechatConfigByAppId(phoneOrder.getAppId());
        phoneOrder.setDeptId(wechatConfig.getDeptId());
        phoneOrder.setOrderNo(SnowflakeGenerator.generateOrderNumber());
        phoneOrder.setCreateTime(DateUtils.getNowDate());
        phoneOrder.setRefundMoney(BigDecimal.ZERO);
        phoneOrder.setTopStatus("1");
        PhonePrice phonePrice = phonePriceMapper.selectPhonePriceById(phoneOrder.getPriceId());
        phoneOrder.setTopUpMoney(phonePrice.getOriginalPrice());
        Member member = memberMapper.selectMemberById(phoneOrder.getMemberId());
        if (StringUtils.equals("1", member.getIsSuperMember())) {
            phoneOrder.setMoney(phonePrice.getOriginalPrice().multiply(phonePrice.getSuperMemberPrice()).setScale(2, RoundingMode.HALF_UP));
        } else if (StringUtils.equals("1", member.getIsMember())) {
            phoneOrder.setMoney(phonePrice.getOriginalPrice().multiply(phonePrice.getMemberPrice()).setScale(2, RoundingMode.HALF_UP));
        } else {
            phoneOrder.setMoney(phonePrice.getOriginalPrice().multiply(phonePrice.getDiscount()).setScale(2, RoundingMode.HALF_UP));
        }
        if (phoneOrder.getCouponId() != null && phoneOrder.getCouponId() != 0L) {
            PhoneMemberCoupon phoneMemberCoupon = phoneMemberCouponMapper.selectPhoneMemberCouponById(phoneOrder.getCouponId());
            if (phoneMemberCoupon != null) {
                final PhoneCoupon phoneCoupon = phoneCouponMapper.selectPhoneCouponById(phoneMemberCoupon.getCouponId());
                if (phoneCoupon != null) {
                    phoneOrder.setMoney(phoneOrder.getMoney().subtract(phoneCoupon.getMinusMoney()));
                }
                phoneMemberCoupon.setStatus("2");
                phoneMemberCouponMapper.updatePhoneMemberCoupon(phoneMemberCoupon);
            }
        }

        BigDecimal balance = member.getBalance();
        phoneOrder.setArrivalStatus("0");
        if (StringUtils.equals("1", phoneOrder.getPayType())) {
            phoneOrder.setPayMoney(phoneOrder.getMoney());
            phoneOrder.setPayBalance(BigDecimal.ZERO);
            phoneOrder.setPayStatus("1");
        } else {
            BigDecimal money;
            if (phoneOrder.getMoney().compareTo(member.getBalance()) > 0) {
                member.setBalance(BigDecimal.ZERO);
                phoneOrder.setPayStatus("1");
                phoneOrder.setPayMoney(phoneOrder.getMoney().subtract(balance));
                money = balance;
                phoneOrder.setPayBalance(balance);
            } else {
                member.setBalance(balance.subtract(phoneOrder.getMoney()));
                phoneOrder.setPayBalance(phoneOrder.getMoney());
                money = phoneOrder.getMoney();
                phoneOrder.setPayStatus("2");
                phoneOrder.setPayTime(DateUtils.getNowDate());
                phoneOrder.setPayMoney(BigDecimal.ZERO);
            }
            if (money.compareTo(BigDecimal.ZERO) > 0) {
                memberMapper.updateMember(member);
                //添加余额变更记录
                PhoneBalanceLog phoneBalanceLog = new PhoneBalanceLog();
                phoneBalanceLog.setDeptId(phoneOrder.getDeptId());
                phoneBalanceLog.setAppId(phoneOrder.getAppId());
                phoneBalanceLog.setMemberId(member.getId());
                phoneBalanceLog.setType("2");
                phoneBalanceLog.setBalanceBefore(balance);
                phoneBalanceLog.setMoney(money);
                phoneBalanceLog.setBalanceAfter(member.getBalance());
                phoneBalanceLog.setCreateTime(DateUtils.getNowDate());
                phoneBalanceLogMapper.insertPhoneBalanceLog(phoneBalanceLog);
            }
        }
        phoneOrderMapper.insertPhoneOrder(phoneOrder);
        topOrder(phoneOrder, phonePrice);
        return phoneOrder;
    }

    /**
     * 修改订单记录
     *
     * @param phoneOrder 订单记录
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updatePhoneOrder(PhoneOrder phoneOrder) throws WxPayException {
        phoneOrder.setUpdateTime(DateUtils.getNowDate());
        Member member = memberMapper.selectMemberById(phoneOrder.getMemberId());
        PhoneOrder old = phoneOrderMapper.selectPhoneOrderById(phoneOrder.getId());
        if (StringUtils.equals("4", phoneOrder.getArrivalStatus()) || StringUtils.equals("5", phoneOrder.getArrivalStatus())) {
            if (!StringUtils.equals("4", old.getArrivalStatus()) && !StringUtils.equals("5", old.getArrivalStatus())) {
                if (phoneOrder.getPayBalance().compareTo(BigDecimal.ZERO) > 0) {
                    //回退余额给用户
                    BigDecimal balance = member.getBalance();
                    member.setBalance(balance.add(phoneOrder.getPayBalance()));
                    memberMapper.updateMember(member);
                    //添加余额变更记录
                    PhoneBalanceLog phoneBalanceLog = new PhoneBalanceLog();
                    phoneBalanceLog.setDeptId(phoneOrder.getDeptId());
                    phoneBalanceLog.setAppId(phoneOrder.getAppId());
                    phoneBalanceLog.setMemberId(member.getId());
                    phoneBalanceLog.setType("3");
                    phoneBalanceLog.setBalanceBefore(balance);
                    phoneBalanceLog.setMoney(phoneOrder.getPayBalance());
                    phoneBalanceLog.setBalanceAfter(member.getBalance());
                    phoneBalanceLog.setCreateTime(DateUtils.getNowDate());
                    phoneBalanceLogMapper.insertPhoneBalanceLog(phoneBalanceLog);
                    phoneOrder.setRefundMoney(phoneOrder.getPayBalance());
                }
                log.info("判断退款逻辑：{}", JSON.toJSONString(old));
                log.info("判断退款逻辑：{}", old.getPayMoney().compareTo(BigDecimal.ZERO) > 0);
                if (old.getPayMoney().compareTo(BigDecimal.ZERO) > 0) {
                    //调用退款接口
                    old.setRefundMoney(old.getPayMoney().subtract(old.getRefundMoney()));
                    this.refund(old);
                } else {
                    if (phoneOrder.getPayBalance().compareTo(BigDecimal.ZERO) > 0) {
                        phoneOrder.setPayStatus("3");
                    }
                }
            }
            //调用取消接口
            PhoneInterfaceConfig phoneInterfaceConfig = new PhoneInterfaceConfig();
            phoneInterfaceConfig.setAppId(phoneOrder.getAppId());
            phoneInterfaceConfig.setSwitchType("1");
            List<PhoneInterfaceConfig> phoneInterfaceConfigList = phoneInterfaceConfigMapper.selectPhoneInterfaceConfigList(phoneInterfaceConfig);
            if (phoneInterfaceConfigList != null && phoneInterfaceConfigList.size() > 0) {
                phoneInterfaceConfig = phoneInterfaceConfigList.get(0);
                TreeMap<String, String> params = new TreeMap<>();
                params.put("userid", phoneInterfaceConfig.getMchId());
                params.put("out_trade_nums", phoneOrder.getOrderNo());
                try {
                    params.put("sign", SignUtils.unionSign(params, phoneInterfaceConfig.getApiKey()));
                    String post = HttpUtil.post(phoneInterfaceConfig.getInterfaceUrl() + GreatUrlConstants.CANCEL_ORDER, JSON.toJSONString(params));
                    log.info("发送请求到第三方返回：" + post);
                    if (StringUtils.isNotBlank(post) && JsonUtils.isJson2(post)) {
                        JSONObject jsonObject = JSON.parseObject(post);
                        if (jsonObject != null && jsonObject.get("errno") != null && jsonObject.getInteger("errno") == 0) {
                            //表示下单成功
                            phoneOrder.setArrivalStatus("5");
                            phoneOrderMapper.updatePhoneOrder(phoneOrder);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else if (StringUtils.equals("2", phoneOrder.getArrivalStatus())) {
            phoneOrder.setTopTime(DateUtils.getNowDate());
        }
        int i = phoneOrderMapper.updatePhoneOrder(phoneOrder);
        if (i > 0) {
            phoneOrder = phoneOrderMapper.selectPhoneOrderById(phoneOrder.getId());
            PhonePrice phonePrice = phonePriceMapper.selectPhonePriceById(phoneOrder.getPriceId());
            updateMemberInfoMoney(phoneOrder, phonePrice, member);
        }
        return i;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int cancel(PhoneOrder phoneOrder) throws WxPayException {
        PhoneOrder old = phoneOrderMapper.selectPhoneOrderById(phoneOrder.getId());
        Member member = memberMapper.selectMemberById(old.getMemberId());
        if (!StringUtils.equals("4", old.getArrivalStatus()) && !StringUtils.equals("5", old.getArrivalStatus())) {
            phoneOrder.setArrivalStatus("5");
            if (old.getPayBalance().compareTo(BigDecimal.ZERO) > 0) {
                //回退余额给用户
                BigDecimal balance = member.getBalance();
                member.setBalance(balance.add(old.getPayBalance()));
                memberMapper.updateMember(member);
                //添加余额变更记录
                PhoneBalanceLog phoneBalanceLog = new PhoneBalanceLog();
                phoneBalanceLog.setDeptId(old.getDeptId());
                phoneBalanceLog.setAppId(old.getAppId());
                phoneBalanceLog.setMemberId(member.getId());
                phoneBalanceLog.setType("3");
                phoneBalanceLog.setBalanceBefore(balance);
                phoneBalanceLog.setMoney(old.getPayBalance());
                phoneBalanceLog.setBalanceAfter(member.getBalance());
                phoneBalanceLog.setCreateTime(DateUtils.getNowDate());
                phoneBalanceLogMapper.insertPhoneBalanceLog(phoneBalanceLog);
            }
            log.info("判断退款逻辑：{}", JSON.toJSONString(old));
            log.info("判断退款逻辑：{}", old.getPayMoney().compareTo(BigDecimal.ZERO) > 0);
            if (old.getPayMoney().compareTo(BigDecimal.ZERO) > 0) {
                //调用退款接口
                old.setRefundMoney(old.getPayMoney().subtract(old.getRefundMoney()));
                this.refund(old);
            }else{
                if (phoneOrder.getPayBalance().compareTo(BigDecimal.ZERO) > 0) {
                    phoneOrder.setPayStatus("3");
                }
            }
        }
        //调用取消接口
        PhoneInterfaceConfig phoneInterfaceConfig = new PhoneInterfaceConfig();
        phoneInterfaceConfig.setAppId(old.getAppId());
        phoneInterfaceConfig.setSwitchType("1");
        List<PhoneInterfaceConfig> phoneInterfaceConfigList = phoneInterfaceConfigMapper.selectPhoneInterfaceConfigList(phoneInterfaceConfig);
        if (phoneInterfaceConfigList != null && phoneInterfaceConfigList.size() > 0) {
            phoneInterfaceConfig = phoneInterfaceConfigList.get(0);
            TreeMap<String, String> params = new TreeMap<>();
            params.put("userid", phoneInterfaceConfig.getMchId());
            params.put("out_trade_nums", old.getOrderNo());
            try {
                params.put("sign", SignUtils.unionSign(params, phoneInterfaceConfig.getApiKey()));
                String post = HttpUtil.post(phoneInterfaceConfig.getInterfaceUrl() + GreatUrlConstants.CANCEL_ORDER, JSON.toJSONString(params));
                log.info("发送请求到第三方返回：" + post);
                if (StringUtils.isNotBlank(post) && JsonUtils.isJson2(post)) {
                    JSONObject jsonObject = JSON.parseObject(post);
                    if (jsonObject != null && jsonObject.get("errno") != null && jsonObject.getInteger("errno") == 0) {
                        //表示下单成功
                        phoneOrder.setArrivalStatus("5");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return phoneOrderMapper.updatePhoneOrder(phoneOrder);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public JSONObject cancelBatch(PhoneOrder phoneOrder) {
        JSONObject jsonObject = new JSONObject();
        int success = 0;
        int error = 0;
        if (phoneOrder.getAccountNumberList() != null && !phoneOrder.getAccountNumberList().isEmpty()) {
            for (String account : phoneOrder.getAccountNumberList()) {
                List<PhoneOrder> phoneOrderList = phoneOrderMapper.selectPhoneOrderListByAccountNumber(account);
                if (!phoneOrderList.isEmpty()) {
                    try {
                        PhoneOrder order = phoneOrderList.get(0);
                        if (!StringUtils.equals("2", order.getArrivalStatus())) {
                            success = success + this.cancel(order);
                        } else {
                            error++;
                        }
                    } catch (WxPayException e) {
                        error++;
                    }
                }
            }
        }
        if (phoneOrder.getOrderNoList() != null && !phoneOrder.getOrderNoList().isEmpty()) {
            for (String orderNo : phoneOrder.getOrderNoList()) {
                List<PhoneOrder> phoneOrderList = phoneOrderMapper.selectPhoneOrderListByOrderNo(orderNo);
                if (!phoneOrderList.isEmpty()) {
                    try {
                        PhoneOrder order = phoneOrderList.get(0);
                        if (!StringUtils.equals("2", order.getArrivalStatus())) {
                            success = success + this.cancel(order);
                        } else {
                            error++;
                        }
                    } catch (WxPayException e) {
                        error++;
                    }
                }
            }
        }
        jsonObject.put("success", success);
        jsonObject.put("error", error);
        return jsonObject;
    }

    /**
     * 批量删除订单记录
     *
     * @param ids 需要删除的订单记录主键
     * @return 结果
     */
    @Override
    public int deletePhoneOrderByIds(Long[] ids) {
        return phoneOrderMapper.deletePhoneOrderByIds(ids);
    }

    /**
     * 删除订单记录信息
     *
     * @param id 订单记录主键
     * @return 结果
     */
    @Override
    public int deletePhoneOrderById(Long id) {
        return phoneOrderMapper.deletePhoneOrderById(id);
    }

    @Override
    public WxPayMpOrderResult pay(PhoneOrder phoneOrder) {
        Member member = SecurityUtils.getLoginUser().getMember();
        WxPayUnifiedOrderRequest request = new WxPayUnifiedOrderRequest();
        //随机字符串
        request.setNonceStr(IdUtils.generateNonceStr());
        //加密方式
        request.setSignType("MD5");
        //订单号
        request.setOutTradeNo(phoneOrder.getOrderNo());
        //金额，以分为单位
        request.setTotalFee(phoneOrder.getPayMoney().multiply(BigDecimal.valueOf(100L)).intValue());
        // 用户ip
        request.setSpbillCreateIp("127.0.0.1");
        //回调通知地址（必须外网能访问的地址）
        request.setNotifyUrl(Constants.URL + "/api/phone/order/payNotify");
        //公众号支付
        request.setTradeType("JSAPI");
        //微信公众号用户openid
        request.setOpenid(member.getOpenId());
        StringBuilder sb = new StringBuilder();
        if (StringUtils.equals("0", phoneOrder.getMethod())) {
            if (StringUtils.equals("4", phoneOrder.getType())) {
                sb.append("国家电网电费缴存");
            } else {
                sb.append("南方电网电费缴存");
            }
        } else {
            if (StringUtils.equals("1", phoneOrder.getType())) {
                sb.append("移动话费缴存");
            } else if (StringUtils.equals("2", phoneOrder.getType())) {
                sb.append("联通话费缴存");
            } else {
                sb.append("电信话费缴存");
            }
        }
        request.setBody(sb.toString());
        try {
            final WechatConfig wechatConfig = wechatConfigService.selectWechatConfigByAppId(phoneOrder.getAppId());
            return wechatConfiguration.wxPayService(wechatConfig).createOrder(request);
        } catch (WxPayException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String payNotify(String xmlData) {
        WxPayOrderNotifyResult notifyResult = WxPayOrderNotifyResult.fromXML(xmlData);
        log.info("支付回调返回：{}", notifyResult);
        if (StringUtils.equals("SUCCESS", notifyResult.getReturnCode())) {
            List<PhoneOrder> phoneOrderList = phoneOrderMapper.selectPhoneOrderListByOrderNo(notifyResult.getOutTradeNo());
            if (phoneOrderList != null && phoneOrderList.size() > 0) {
                PhoneOrder phoneOrder = phoneOrderList.get(0);
                if (!StringUtils.equals("2", phoneOrder.getPayStatus())) {
                    phoneOrder.setPayStatus("2");
                    try {
                        phoneOrder.setPayTime(DateFormatUtil.pareDate(DateFormatUtil.PATTERN_DEFAULT_SECOND, notifyResult.getTimeEnd()));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    phoneOrder.setPayResult(JSON.toJSONString(notifyResult));
                    phoneOrder.setUpdateTime(DateUtils.getNowDate());
                    phoneOrderMapper.updatePhoneOrder(phoneOrder);
                    PhonePrice phonePrice = phonePriceMapper.selectPhonePriceById(phoneOrder.getPriceId());
                    topOrder(phoneOrder, phonePrice);
                }
            }
            return WxPayNotifyResponse.success("成功");
        }
        return WxPayNotifyResponse.fail("失败");
    }

    @Override
    public WxPayRefundResult refund(PhoneOrder phoneOrder) throws WxPayException {
        WxPayRefundRequest request = new WxPayRefundRequest();
        //订单号
        request.setOutTradeNo(phoneOrder.getOrderNo());
        //退款单号
        request.setOutRefundNo(SnowflakeGenerator.generateOrderNumber());
        //订单金额
        request.setTotalFee(phoneOrder.getPayMoney().multiply(BigDecimal.valueOf(100L)).intValue());
        //退款金额
        request.setRefundFee(phoneOrder.getRefundMoney().multiply(BigDecimal.valueOf(100L)).intValue());
        //加密方式
        request.setSignType("MD5");
        //回调通知地址（必须外网能访问的地址）
        request.setNotifyUrl(Constants.URL + "/api/phone/order/refundNotify");
        WechatConfig wechatConfig = wechatConfigService.selectWechatConfigByAppId(phoneOrder.getAppId());
        final WxPayService wxPayService = wechatConfiguration.wxPayService(wechatConfig);
        final WxPayRefundResult refund = wxPayService.refund(request);
        log.info("调用退款接口：订单号：{},响应：{}", phoneOrder.getOrderNo(), JSON.toJSONString(refund));
        if (StringUtils.equals("SUCCESS", refund.getResultCode())) {
            return refund;
        } else {
            throw new WxPayException(refund.getErrCodeDes());
        }
    }

    public static void main(String[] args) {
        //TreeMap<String, String> params = new TreeMap<>();
        //params.put("userid", "175");
        //try {
        //    params.put("sign", SignUtils.unionSign(params, "DB346B5FA26FD55DBE7068DAD6DFE43B"));
        //    String post = HttpUtil.post("http://8.218.193.88/" + GreatUrlConstants.QUERY_ELECTRICITY_AREA, JSON.toJSONString(params));
        //    log.info("发送请求到第三方返回：" + post);
        //} catch (Exception e) {
        //    e.printStackTrace();
        //}
        TreeMap<String, String> params = new TreeMap<>();
        params.put("userid", "166");
        params.put("out_trade_nums", "2024040323392046629");
        try {
            params.put("sign", SignUtils.unionSign(params, "ltVkJU28epDISRuZHxGQ4WTvojPA6NYb"));
            String post = HttpUtil.post("http://8.218.193.88/yrapi.php/index/cancel", JSON.toJSONString(params));
            System.out.println(post);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String refundNotify(String xmlData) {
        WxPayRefundNotifyResult wxPayRefundNotifyResult = WxPayRefundNotifyResult.fromXML(xmlData, WxPayRefundNotifyResult.class);
        log.info("退款返回信息：{}", JSON.toJSONString(wxPayRefundNotifyResult));
        if (StringUtils.equals("SUCCESS", wxPayRefundNotifyResult.getReturnCode())) {
            WechatConfig wechatConfig = wechatConfigService.selectWechatConfigByAppId(wxPayRefundNotifyResult.getAppid());
            try {
                WxPayRefundNotifyResult result = wechatConfiguration.wxPayService(wechatConfig).parseRefundNotifyResult(xmlData);
                log.info("退款返回信息解密：{}", JSON.toJSONString(result.getReqInfo()));
                List<PhoneOrder> orderList = phoneOrderMapper.selectPhoneOrderListByOrderNo(result.getReqInfo().getOutTradeNo());
                if (orderList != null && orderList.size() > 0) {
                    PhoneOrder order = orderList.get(0);
                    order.setPayStatus("3");
                    order.setPayResult(JSON.toJSONString(result));
                    order.setUpdateTime(DateUtils.getNowDate());
                    BigDecimal refundFee = new BigDecimal(result.getReqInfo().getRefundFee()).divide(BigDecimal.valueOf(100L)).setScale(2, RoundingMode.HALF_UP);
                    if (result.getReqInfo().getRefundFee().equals(result.getReqInfo().getTotalFee())) {
                        order.setRefundMoney(refundFee);
                    } else {
                        order.setRefundMoney(order.getRefundMoney().add(refundFee));
                    }
                    final int i = phoneOrderMapper.updatePhoneOrder(order);
                }
                return WxPayNotifyResponse.success("成功");
            } catch (WxPayException e) {
                e.printStackTrace();
            }
        }
        return WxPayNotifyResponse.fail("失败");
    }

    @Override
    public String topNotify(TopNotifyRequest requestBody) {
        log.info(JSON.toJSONString(requestBody));
        List<PhoneOrder> phoneOrderList = phoneOrderMapper.selectPhoneOrderListByOrderNo(requestBody.getOut_trade_num());
        if (phoneOrderList != null && phoneOrderList.size() > 0) {
            PhoneOrder phoneOrder = phoneOrderList.get(0);
            PhoneOrder old = phoneOrderList.get(0);

            boolean flag = false;
            PhoneInterfaceConfig phoneInterfaceConfig = new PhoneInterfaceConfig();
            phoneInterfaceConfig.setAppId(phoneOrder.getAppId());
            phoneInterfaceConfig.setSwitchType("1");
            List<PhoneInterfaceConfig> phoneInterfaceConfigList = phoneInterfaceConfigMapper.selectPhoneInterfaceConfigList(phoneInterfaceConfig);

            if (requestBody.getState() == 1) {
                phoneOrder.setArrivalStatus("2");
            } else {
                if (phoneInterfaceConfigList != null && !phoneInterfaceConfigList.isEmpty()) {
                    phoneInterfaceConfig = phoneInterfaceConfigList.get(0);
                    if (StringUtils.isNotBlank(phoneInterfaceConfig.getIsSync()) && StringUtils.equals("1", phoneInterfaceConfig.getIsSync())) {
                        if (requestBody.getState() == -1) {
                            phoneOrder.setArrivalStatus("5");
                            flag = true;
                        } else if (requestBody.getState() == 2) {
                            phoneOrder.setArrivalStatus("3");
                            flag = true;
                        }
                    }
                }
            }
            phoneOrder.setTopTime(new Date(requestBody.getOtime() * 1000L));
            phoneOrder.setTopNotifyResult(JSON.toJSONString(requestBody));
            phoneOrder.setUpdateTime(DateUtils.getNowDate());
            phoneOrderMapper.updatePhoneOrder(phoneOrder);
            PhonePrice phonePrice = phonePriceMapper.selectPhonePriceById(phoneOrder.getPriceId());
            Member member = memberMapper.selectMemberById(phoneOrder.getMemberId());
            updateMemberInfoMoney(phoneOrder, phonePrice, member);
            if (flag) {
                //判断是否需要退款
                if (!phoneInterfaceConfigList.isEmpty()) {
                    phoneInterfaceConfig = phoneInterfaceConfigList.get(0);
                    if (StringUtils.isNotBlank(phoneInterfaceConfig.getIsRefund()) && StringUtils.equals("1", phoneInterfaceConfig.getIsRefund())) {
                        if (old.getPayBalance().compareTo(BigDecimal.ZERO) > 0) {
                            //回退余额给用户
                            BigDecimal balance = member.getBalance();
                            member.setBalance(balance.add(old.getPayBalance()));
                            memberMapper.updateMember(member);
                            //添加余额变更记录
                            PhoneBalanceLog phoneBalanceLog = new PhoneBalanceLog();
                            phoneBalanceLog.setDeptId(old.getDeptId());
                            phoneBalanceLog.setAppId(old.getAppId());
                            phoneBalanceLog.setMemberId(member.getId());
                            phoneBalanceLog.setType("3");
                            phoneBalanceLog.setBalanceBefore(balance);
                            phoneBalanceLog.setMoney(old.getPayBalance());
                            phoneBalanceLog.setBalanceAfter(member.getBalance());
                            phoneBalanceLog.setCreateTime(DateUtils.getNowDate());
                            phoneBalanceLogMapper.insertPhoneBalanceLog(phoneBalanceLog);
                        }
                        if (phoneOrder.getPayMoney().compareTo(BigDecimal.ZERO) > 0) {
                            //调用退款接口
                            try {
                                phoneOrder.setRefundMoney(phoneOrder.getPayMoney().subtract(phoneOrder.getRefundMoney()));
                                this.refund(phoneOrder);
                            } catch (WxPayException e) {
                                log.error(e.getMessage());
                            }
                        }
                        phoneOrder.setPayStatus("3");
                        phoneOrderMapper.updatePhoneOrder(phoneOrder);
                    }
                }
            }

        }
        return "success";
    }

    @Override
    public List<String> findNewsflash(String appId) {
        return phoneOrderMapper.selectNewsflash(appId);
    }

    @Override
    public Map<String, Object> getOrderDayCount(PhoneOrder phoneOrder) {
        Map<String, Object> map = new HashMap<>();
        //今日数据
        phoneOrder.getParams().put("type", 1);
        int todayOrderCount = phoneOrderMapper.getOrderCount(phoneOrder);
        map.put("todayOrderCount", todayOrderCount);
        //昨日数据
        phoneOrder.getParams().put("type", 2);
        int yesterdayOrderCount = phoneOrderMapper.getOrderCount(phoneOrder);
        map.put("yesterdayOrderCount", yesterdayOrderCount);
        //本月数据
        phoneOrder.getParams().put("type", 3);
        int monthOrderCount = phoneOrderMapper.getOrderCount(phoneOrder);
        map.put("monthOrderCount", monthOrderCount);
        return map;
    }

    @Override
    public Map<String, Object> getOrderDayCountMoney(PhoneOrder phoneOrder) {
        Map<String, Object> map = new HashMap<>();
        //今日数据
        phoneOrder.getParams().put("type", 1);
        BigDecimal todayOrderMoney = phoneOrderMapper.getOrderCountMoney(phoneOrder);
        map.put("todayOrderMoney", todayOrderMoney);
        //昨日数据
        phoneOrder.getParams().put("type", 2);
        BigDecimal yesterdayOrderMoney = phoneOrderMapper.getOrderCountMoney(phoneOrder);
        map.put("yesterdayOrderMoney", yesterdayOrderMoney);
        //本月数据
        phoneOrder.getParams().put("type", 3);
        BigDecimal monthOrderMoney = phoneOrderMapper.getOrderCountMoney(phoneOrder);
        map.put("monthOrderMoney", monthOrderMoney);
        return map;
    }

    /**
     * 充值
     *
     * @param phoneOrder
     * @param phonePrice
     */
    public void topOrder(PhoneOrder phoneOrder, PhonePrice phonePrice) {
        if (StringUtils.equals("2", phoneOrder.getPayStatus())) {
            log.info("价格类型数据：{}", JSON.toJSONString(phonePrice));
            log.info("调用第三方充值判断：{}", phonePrice != null && !StringUtils.equals("1", phonePrice.getRechargeType()));
            if (phonePrice != null && !StringUtils.equals("1", phonePrice.getRechargeType())) {
                log.info("开始调用充值第三方接口");
                //如果是直充
                PhoneInterfaceConfig phoneInterfaceConfig = new PhoneInterfaceConfig();
                phoneInterfaceConfig.setAppId(phoneOrder.getAppId());
                phoneInterfaceConfig.setSwitchType("1");
                List<PhoneInterfaceConfig> phoneInterfaceConfigList = phoneInterfaceConfigMapper.selectPhoneInterfaceConfigList(phoneInterfaceConfig);
                if (phoneInterfaceConfigList != null && phoneInterfaceConfigList.size() > 0) {
                    phoneInterfaceConfig = phoneInterfaceConfigList.get(0);

                    TreeMap<String, String> params = new TreeMap<>();
                    params.put("out_trade_num", phoneOrder.getOrderNo());
                    params.put("mobile", phoneOrder.getAccountNumber());
                    params.put("notify_url", Constants.URL + "/api/phone/order/topNotify");
                    params.put("userid", phoneInterfaceConfig.getMchId());
                    params.put("product_id", phonePrice.getProductId() + "");
                    if (StringUtils.equals("0", phonePrice.getMethod())) {
                        String[] areas = phoneOrder.getArea().split("-");
                        params.put("area", areas[0]);
                        if (!directManageCityList.contains(areas[0])) {
                            //不是直辖市传地市
                            params.put("city", areas[1]);
                        }
                        if (StringUtils.equals("5", phonePrice.getType())) {
                            params.put("ytype", "1");
                            params.put("id_card_no", phoneOrder.getCardNo());
                        }
                    }
                    try {
                        params.put("sign", SignUtils.unionSign(params, phoneInterfaceConfig.getApiKey()));
                        String post = HttpUtil.post(phoneInterfaceConfig.getInterfaceUrl() + GreatUrlConstants.CREATE_ORDER, JSON.toJSONString(params));
                        log.info("发送请求到第三方返回：" + post);
                        phoneOrder.setTopStatusRemark(post);
                        if (StringUtils.isNotBlank(post) && JsonUtils.isJson2(post)) {
                            JSONObject jsonObject = JSON.parseObject(post);
                            if (jsonObject != null && jsonObject.get("errno") != null && jsonObject.getInteger("errno") == 0) {
                                //表示下单成功
                                phoneOrder.setTopStatus("2");
                                phoneOrder.setArrivalStatus("1");
                                phoneOrder.setTopResult(post);
                            }
                        }
                        phoneOrderMapper.updatePhoneOrder(phoneOrder);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void updateMemberInfoMoney(PhoneOrder phoneOrder, PhonePrice phonePrice, Member member) {
        if (StringUtils.equals("2", phoneOrder.getArrivalStatus())) {
            BigDecimal expenditureTotal = BigDecimal.ZERO;
            BigDecimal expenditureFirst = BigDecimal.ZERO;
            BigDecimal expenditureSecond = BigDecimal.ZERO;
            //如果是被推荐用户获取上级
            if (member.getMemberId() != null) {
                //获取佣金
                if (phonePrice != null) {
                    Member agency = memberMapper.selectMemberById(member.getMemberId());
                    if (agency != null) {
                        BigDecimal agencyBalance = BigDecimal.ZERO;
                        if (agency.getCommissionBalance() != null) {
                            agencyBalance = agency.getCommissionBalance();
                        }
                        expenditureFirst = agencyBalance;
                        expenditureTotal = expenditureTotal.add(agencyBalance);
                        BigDecimal directCommission = phonePrice.getDirectCommission();
                        if (StringUtils.equals("1", agency.getIsSuperMember())) {
                            directCommission = phonePrice.getSuperMemberDirectCommission();
                        } else if (StringUtils.equals("1", agency.getIsMember())) {
                            directCommission = phonePrice.getMemberDirectCommission();
                        }
                        agency.setCommissionBalance(agencyBalance.add(directCommission));
                        memberMapper.updateMember(agency);
                        //添加佣金记录
                        PhoneCommissionConfig phoneCommissionConfig = new PhoneCommissionConfig();
                        phoneCommissionConfig.setDeptId(phoneOrder.getDeptId());
                        phoneCommissionConfig.setAppId(phoneOrder.getAppId());
                        phoneCommissionConfig.setCommissionBefore(agencyBalance);
                        phoneCommissionConfig.setMoney(directCommission);
                        phoneCommissionConfig.setMemberId(agency.getId());
                        phoneCommissionConfig.setCommissionMemberId(member.getId());
                        phoneCommissionConfig.setCommissionAfter(agency.getCommissionBalance());
                        phoneCommissionConfig.setCreateTime(DateUtils.getNowDate());
                        phoneCommissionConfig.setOrderId(phoneOrder.getId());
                        phoneCommissionConfigMapper.insertPhoneCommissionConfig(phoneCommissionConfig);
                        if (agency.getMemberId() != null) {
                            Member secondary = memberMapper.selectMemberById(agency.getMemberId());
                            if (secondary != null) {
                                BigDecimal secondaryBalance = BigDecimal.ZERO;
                                if (secondary.getCommissionBalance() != null) {
                                    secondaryBalance = secondary.getCommissionBalance();
                                }
                                expenditureSecond = secondaryBalance;
                                expenditureTotal = expenditureTotal.add(secondaryBalance);
                                BigDecimal secondaryDirectCommission = phonePrice.getIndirectCommission();
                                if (StringUtils.equals("1", secondary.getIsSuperMember())) {
                                    secondaryDirectCommission = phonePrice.getSuperMemberIndirectCommission();
                                } else if (StringUtils.equals("1", secondary.getIsMember())) {
                                    secondaryDirectCommission = phonePrice.getMemberIndirectCommission();
                                }
                                secondary.setCommissionBalance(secondaryBalance.add(secondaryDirectCommission));
                                memberMapper.updateMember(secondary);
                                //添加佣金记录
                                PhoneCommissionConfig secondaryPhoneCommissionConfig = new PhoneCommissionConfig();
                                secondaryPhoneCommissionConfig.setDeptId(phoneOrder.getDeptId());
                                secondaryPhoneCommissionConfig.setAppId(phoneOrder.getAppId());
                                secondaryPhoneCommissionConfig.setMemberId(secondary.getId());
                                secondaryPhoneCommissionConfig.setCommissionMemberId(member.getId());
                                secondaryPhoneCommissionConfig.setCommissionBefore(secondaryBalance);
                                secondaryPhoneCommissionConfig.setMoney(secondaryDirectCommission);
                                secondaryPhoneCommissionConfig.setCommissionAfter(secondary.getCommissionBalance());
                                secondaryPhoneCommissionConfig.setCreateTime(DateUtils.getNowDate());
                                secondaryPhoneCommissionConfig.setOrderId(phoneOrder.getId());
                                phoneCommissionConfigMapper.insertPhoneCommissionConfig(secondaryPhoneCommissionConfig);
                            }
                        }
                    }
                }
                //送优惠券
                PhoneCoupon phoneCoupon = new PhoneCoupon();
                phoneCoupon.setAppId(phoneOrder.getAppId());
                phoneCoupon.setStatus("1");
                List<PhoneCoupon> phoneCouponList = phoneCouponMapper.selectPhoneCouponList(phoneCoupon);
                if (phoneCouponList.size() > 0) {
                    List<PhoneMemberCoupon> memberCouponList = new ArrayList<>();
                    for (PhoneCoupon coupon : phoneCouponList) {
                        switch (coupon.getDistributionMode()) {
                            case "2":
                                //充值送
                                if (phoneOrder.getTopUpMoney().compareTo(coupon.getRechargeAmount()) > -1) {
                                    int num = 1;
                                    if (coupon.getNumber() != null) {
                                        num = coupon.getNumber().intValue();
                                    }
                                    for (int i = 0; i < num; i++) {
                                        PhoneMemberCoupon phoneMemberCoupon = new PhoneMemberCoupon();
                                        phoneMemberCoupon.setDeptId(phoneOrder.getDeptId());
                                        phoneMemberCoupon.setAppId(phoneOrder.getAppId());
                                        phoneMemberCoupon.setMemberId(member.getMemberId());
                                        phoneMemberCoupon.setCouponId(coupon.getId());
                                        phoneMemberCoupon.setExpirationTime(DateUtil.endOfDate(DateUtil.addDays(DateUtils.getNowDate(), coupon.getTermValidity().intValue())));
                                        phoneMemberCoupon.setStatus("1");
                                        phoneMemberCoupon.setCreateTime(DateUtils.getNowDate());
                                        memberCouponList.add(phoneMemberCoupon);
                                    }
                                }
                                break;
                            case "3":
                                //首单送
                                PhoneOrder queryOrder = new PhoneOrder();
                                queryOrder.setMemberId(member.getId());
                                queryOrder.setPayStatus("2");
                                queryOrder.setArrivalStatus("2");
                                List<PhoneOrder> phoneOrderList = phoneOrderMapper.selectPhoneOrderList(queryOrder);
                                if (phoneOrderList.size() == 1) {
                                    if (phoneOrder.getTopUpMoney().compareTo(coupon.getRechargeAmount()) > -1) {
                                        int num = 1;
                                        if (coupon.getNumber() != null) {
                                            num = coupon.getNumber().intValue();
                                        }
                                        for (int i = 0; i < num; i++) {
                                            PhoneMemberCoupon phoneMemberCoupon = new PhoneMemberCoupon();
                                            phoneMemberCoupon.setDeptId(phoneOrder.getDeptId());
                                            phoneMemberCoupon.setAppId(phoneOrder.getAppId());
                                            phoneMemberCoupon.setMemberId(member.getMemberId());
                                            phoneMemberCoupon.setCouponId(coupon.getId());
                                            phoneMemberCoupon.setExpirationTime(DateUtil.endOfDate(DateUtil.addDays(DateUtils.getNowDate(), coupon.getTermValidity().intValue())));
                                            phoneMemberCoupon.setStatus("1");
                                            phoneMemberCoupon.setCreateTime(DateUtils.getNowDate());
                                            memberCouponList.add(phoneMemberCoupon);
                                        }
                                    }
                                }
                                break;
                            default:
                                break;
                        }
                    }
                    if (memberCouponList.size() > 0) {
                        for (PhoneMemberCoupon phoneMemberCoupon : memberCouponList) {
                            phoneMemberCouponMapper.insertPhoneMemberCoupon(phoneMemberCoupon);
                        }
                    }
                }
            }
            //更新订单支出金额
            phoneOrder.setExpenditureFirst(expenditureFirst);
            phoneOrder.setExpenditureSecond(expenditureSecond);
            phoneOrder.setExpenditureTotal(expenditureTotal);
            phoneOrderMapper.updatePhoneOrder(phoneOrder);
        }
    }
}

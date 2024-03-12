package com.ruoyi.phone.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.github.binarywang.wxpay.bean.notify.WxPayNotifyResponse;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.bean.order.WxPayMpOrderResult;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.ruoyi.common.annotation.DataScope;
import com.ruoyi.common.config.WechatConfiguration;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.entity.Member;
import com.ruoyi.common.core.domain.entity.WechatConfig;
import com.ruoyi.common.utils.*;
import com.ruoyi.common.utils.great.GreatUrlConstants;
import com.ruoyi.common.utils.great.SignUtils;
import com.ruoyi.common.utils.time.DateUtil;
import com.ruoyi.common.utils.uuid.IdUtils;
import com.ruoyi.phone.domain.*;
import com.ruoyi.phone.mapper.*;
import com.ruoyi.phone.service.*;
import com.ruoyi.system.mapper.MemberMapper;
import com.ruoyi.system.service.IWechatConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 订单记录Service业务层处理
 *
 * @author ruoyi
 * @date 2024-03-05
 */
@Service
public class PhoneOrderServiceImpl implements IPhoneOrderService {
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

        PhonePrice phonePrice = phonePriceMapper.selectPhonePriceById(phoneOrder.getPriceId());

        Member member = memberMapper.selectMemberById(phoneOrder.getMemberId());
        if (StringUtils.equals("1", member.getIsSuperMember())) {
            phoneOrder.setMoney(phonePrice.getOriginalPrice().multiply(phonePrice.getSuperMemberPrice()).setScale(2, RoundingMode.HALF_UP));
        } else if (StringUtils.equals("2", member.getIsMember())) {
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
                phoneOrder.setPayMoney(BigDecimal.ZERO);
            }
            memberMapper.updateMember(member);
            //添加余额变更记录
            PhoneBalanceLog phoneBalanceLog = new PhoneBalanceLog();
            phoneBalanceLog.setDeptId(phoneOrder.getDeptId());
            phoneBalanceLog.setAppId(phoneOrder.getAppId());
            phoneBalanceLog.setMemberId(member.getMemberId());
            phoneBalanceLog.setType("2");
            phoneBalanceLog.setBalanceAfter(balance);
            phoneBalanceLog.setMoney(money);
            phoneBalanceLog.setBalanceBefore(member.getBalance());
            phoneBalanceLog.setCreateTime(DateUtils.getNowDate());
            phoneBalanceLogMapper.insertPhoneBalanceLog(phoneBalanceLog);
        }
        phoneOrderMapper.insertPhoneOrder(phoneOrder);
        updateMemberInfo(phoneOrder, member);
        return phoneOrder;
    }

    /**
     * 修改订单记录
     *
     * @param phoneOrder 订单记录
     * @return 结果
     */
    @Override
    public int updatePhoneOrder(PhoneOrder phoneOrder) {
        phoneOrder.setUpdateTime(DateUtils.getNowDate());
        return phoneOrderMapper.updatePhoneOrder(phoneOrder);
    }

    @Override
    public int cancel(PhoneOrder phoneOrder) {
        phoneOrder = phoneOrderMapper.selectPhoneOrderById(phoneOrder.getId());
        PhoneInterfaceConfig phoneInterfaceConfig = new PhoneInterfaceConfig();
        phoneInterfaceConfig.setAppId(phoneOrder.getAppId());
        phoneInterfaceConfig.setSwitchType("1");
        List<PhoneInterfaceConfig> phoneInterfaceConfigList = phoneInterfaceConfigMapper.selectPhoneInterfaceConfigList(phoneInterfaceConfig);
        if (phoneInterfaceConfigList != null && phoneInterfaceConfigList.size() > 0) {
            phoneInterfaceConfig = phoneInterfaceConfigList.get(0);
            if (StringUtils.equals("2", phoneInterfaceConfig.getInterfaceType())) {
                TreeMap<String, String> params = new TreeMap<>();
                params.put("userid", phoneInterfaceConfig.getMchId());
                params.put("out_trade_nums", phoneOrder.getOrderNo());
                try {
                    params.put("sign", SignUtils.unionSign(params, phoneInterfaceConfig.getApiKey()));
                    String post = HttpUtil.post(phoneInterfaceConfig.getInterfaceUrl() + GreatUrlConstants.QUERY_PRODUCT, JSON.toJSONString(params));
                    if (StringUtils.isNotBlank(post) && JsonUtils.isJson2(post)) {
                        JSONObject jsonObject = JSON.parseObject(post);
                        if (jsonObject != null && jsonObject.get("errno") != null && jsonObject.getInteger("errno") == 0) {
                            phoneOrder.setArrivalStatus("4");
                            phoneOrderMapper.updatePhoneOrder(phoneOrder);
                            return 1;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return 0;
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
        request.setNotifyUrl(Constants.URL + "/api/phone/memberCard/payNotify");
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
        if (StringUtils.equals("SUCCESS", notifyResult.getReturnCode())) {
            List<PhoneOrder> phoneOrderList = phoneOrderMapper.selectPhoneOrderListByOrderNo(notifyResult.getOutTradeNo());
            if (phoneOrderList != null && phoneOrderList.size() > 0) {
                PhoneOrder phoneOrder = phoneOrderList.get(0);
                if (!StringUtils.equals("2", phoneOrder.getPayStatus())) {
                    Member member = memberMapper.selectMemberById(phoneOrder.getMemberId());
                    phoneOrder.setPayStatus("2");
                    phoneOrder.setPayTime(notifyResult.getTimeEnd());
                    phoneOrder.setPayResult(JSON.toJSONString(notifyResult));
                    phoneOrder.setUpdateTime(DateUtils.getNowDate());
                    phoneOrderMapper.updatePhoneOrder(phoneOrder);
                    updateMemberInfo(phoneOrder, member);
                }
            }
            return WxPayNotifyResponse.success("成功");
        }
        return WxPayNotifyResponse.fail("失败");
    }

    @Override
    public String topNotify(TopNotifyRequest requestBody) {
        System.out.println(JSON.toJSONString(requestBody));
        List<PhoneOrder> phoneOrderList = phoneOrderMapper.selectPhoneOrderListByOrderNo(requestBody.getOrder_number());
        if (phoneOrderList != null && phoneOrderList.size() > 0) {
            PhoneOrder phoneOrder = phoneOrderList.get(0);
            if (requestBody.getState() == -1) {
                phoneOrder.setArrivalStatus("5");
            }else if (requestBody.getState() == 1) {
                phoneOrder.setArrivalStatus("2");
            }else if(requestBody.getState() == 2){
                phoneOrder.setArrivalStatus("3");
            }
            phoneOrder.setTopTime(requestBody.getOtime() + "");
            phoneOrder.setTopNotifyResult(JSON.toJSONString(requestBody));
            phoneOrder.setUpdateTime(DateUtils.getNowDate());
            phoneOrderMapper.updatePhoneOrder(phoneOrder);
        }
        return "success";
    }

    /**
     * 更新会员相关数据
     *
     * @param phoneOrder
     * @param member
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateMemberInfo(PhoneOrder phoneOrder, Member member) {
        if (StringUtils.equals("2", phoneOrder.getPayStatus())) {
            //如果是直充
            PhoneInterfaceConfig phoneInterfaceConfig = new PhoneInterfaceConfig();
            phoneInterfaceConfig.setAppId(phoneOrder.getAppId());
            phoneInterfaceConfig.setSwitchType("1");
            List<PhoneInterfaceConfig> phoneInterfaceConfigList = phoneInterfaceConfigMapper.selectPhoneInterfaceConfigList(phoneInterfaceConfig);
            if (phoneInterfaceConfigList != null && phoneInterfaceConfigList.size() > 0) {
                phoneInterfaceConfig = phoneInterfaceConfigList.get(0);
                if (StringUtils.equals("2", phoneInterfaceConfig.getInterfaceType())) {
                    //TODO 调用第三方接口
                    //组装数据
                    //判断是话费还是电费
                    TreeMap<String, String> params = new TreeMap<>();
                    params.put("out_trade_num", phoneOrder.getOrderNo());
                    params.put("mobile", phoneOrder.getAccountNumber());
                    params.put("notify_url", Constants.URL + "/api/phone/memberCard/topNotify");
                    params.put("userid", phoneInterfaceConfig.getMchId());
                    int product_id = 0;
                    if (StringUtils.equals("4", phoneOrder.getType())) {
                        //国家电网
                        product_id = 247;
                        params.put("area", phoneOrder.getArea());
                    } else if (StringUtils.equals("5", phoneOrder.getType())) {
                        //南方电网
                        product_id = 0;
                        params.put("area", phoneOrder.getArea());
                        params.put("ytype", "1");
                        params.put("id_card_no", phoneOrder.getCardNo());
                    } else {
                        //话费
                        //判断金额
                        if (phoneOrder.getMoney().compareTo(new BigDecimal(50)) < 1) {
                            product_id = 238;
                        } else if (phoneOrder.getMoney().compareTo(new BigDecimal(100)) < 1) {
                            product_id = 239;
                        } else if (phoneOrder.getMoney().compareTo(new BigDecimal(200)) < 1) {
                            product_id = 240;
                        }
                    }
                    if (product_id != 0) {
                        params.put("product_id", product_id + "");
                        try {
                            params.put("sign", SignUtils.unionSign(params, phoneInterfaceConfig.getApiKey()));
                            String post = HttpUtil.post(phoneInterfaceConfig.getInterfaceUrl() + GreatUrlConstants.QUERY_PRODUCT, JSON.toJSONString(params));
                            if (StringUtils.isNotBlank(post) && JsonUtils.isJson2(post)) {
                                JSONObject jsonObject = JSON.parseObject(post);
                                if (jsonObject != null && jsonObject.get("errno") != null && jsonObject.getInteger("errno") == 0) {
                                    //表示下单成功
                                    phoneOrder.setArrivalStatus("1");
                                    phoneOrder.setTopResult(post);
                                    phoneOrderMapper.updatePhoneOrder(phoneOrder);
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
            //如果是被推荐用户获取上级
            if (member.getMemberId() != null) {
                //获取佣金
                final PhonePrice phonePrice = phonePriceMapper.selectPhonePriceById(phoneOrder.getPriceId());
                if (phonePrice != null) {
                    Member agency = memberMapper.selectMemberById(member.getMemberId());
                    if (agency != null) {
                        BigDecimal agencyBalance = agency.getCommissionBalance();
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
                        phoneCommissionConfig.setCommissionAfter(agency.getCommissionBalance());
                        phoneCommissionConfig.setCreateTime(DateUtils.getNowDate());
                        phoneCommissionConfigMapper.insertPhoneCommissionConfig(phoneCommissionConfig);
                        if (agency.getMemberId() != null) {
                            Member secondary = memberMapper.selectMemberById(member.getMemberId());
                            if (secondary != null) {
                                BigDecimal secondaryBalance = secondary.getCommissionBalance();
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
                                secondaryPhoneCommissionConfig.setCommissionBefore(secondaryBalance);
                                secondaryPhoneCommissionConfig.setMoney(secondaryDirectCommission);
                                secondaryPhoneCommissionConfig.setCommissionAfter(secondary.getCommissionBalance());
                                secondaryPhoneCommissionConfig.setCreateTime(DateUtils.getNowDate());
                                phoneCommissionConfigMapper.insertPhoneCommissionConfig(secondaryPhoneCommissionConfig);
                            }
                        }
                    }
                }
            }
            //送优惠券
            PhoneCoupon phoneCoupon = new PhoneCoupon();
            phoneCoupon.setAppId(phoneOrder.getAppId());
            List<PhoneCoupon> phoneCouponList = phoneCouponMapper.selectPhoneCouponList(phoneCoupon);
            if (phoneCouponList.size() > 0) {
                List<PhoneMemberCoupon> memberCouponList = new ArrayList<>();
                for (PhoneCoupon coupon : phoneCouponList) {
                    switch (coupon.getDistributionMode()) {
                        case "1":
                            //充值送
                            if (phoneOrder.getMoney().compareTo(coupon.getRechargeAmount()) > -1) {
                                PhoneMemberCoupon phoneMemberCoupon = new PhoneMemberCoupon();
                                phoneMemberCoupon.setDeptId(phoneOrder.getDeptId());
                                phoneMemberCoupon.setAppId(phoneOrder.getAppId());
                                phoneMemberCoupon.setMemberId(member.getId());
                                phoneMemberCoupon.setCouponId(coupon.getId());
                                phoneMemberCoupon.setExpirationTime(DateUtil.endOfDate(DateUtil.addDays(DateUtils.getNowDate(), coupon.getTermValidity().intValue())));
                                phoneMemberCoupon.setStatus("1");
                                phoneMemberCoupon.setCreateTime(DateUtils.getNowDate());
                                memberCouponList.add(phoneMemberCoupon);
                            }
                            break;
                        case "3":
                            //首单送
                            PhoneOrder queryOrder = new PhoneOrder();
                            queryOrder.setMemberId(member.getId());
                            List<PhoneOrder> phoneOrderList = phoneOrderMapper.selectPhoneOrderList(queryOrder);
                            if (phoneOrderList.size() == 1) {
                                if (phoneOrder.getMoney().compareTo(coupon.getRechargeAmount()) > -1) {
                                    PhoneMemberCoupon phoneMemberCoupon = new PhoneMemberCoupon();
                                    phoneMemberCoupon.setDeptId(phoneOrder.getDeptId());
                                    phoneMemberCoupon.setAppId(phoneOrder.getAppId());
                                    phoneMemberCoupon.setMemberId(member.getId());
                                    phoneMemberCoupon.setCouponId(coupon.getId());
                                    phoneMemberCoupon.setExpirationTime(DateUtil.endOfDate(DateUtil.addDays(DateUtils.getNowDate(), coupon.getTermValidity().intValue())));
                                    phoneMemberCoupon.setStatus("1");
                                    phoneMemberCoupon.setCreateTime(DateUtils.getNowDate());
                                    memberCouponList.add(phoneMemberCoupon);
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
    }
}

package com.ruoyi.phone.service.impl;

import com.alibaba.fastjson2.JSON;
import com.github.binarywang.wxpay.bean.notify.WxPayNotifyResponse;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.bean.order.WxPayMpOrderResult;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.ruoyi.common.annotation.DataScope;
import com.ruoyi.common.config.WechatConfiguration;
import com.ruoyi.common.config.WechatTestConfiguration;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.entity.Member;
import com.ruoyi.common.core.domain.entity.WechatConfig;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.SnowflakeGenerator;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.time.DateUtil;
import com.ruoyi.common.utils.uuid.IdUtils;
import com.ruoyi.phone.domain.PhoneCommissionConfig;
import com.ruoyi.phone.domain.PhoneMemberCard;
import com.ruoyi.phone.domain.PhoneMemberCardLog;
import com.ruoyi.phone.mapper.PhoneCommissionConfigMapper;
import com.ruoyi.phone.mapper.PhoneMemberCardLogMapper;
import com.ruoyi.phone.mapper.PhoneMemberCardMapper;
import com.ruoyi.phone.service.IPhoneMemberCardLogService;
import com.ruoyi.system.mapper.MemberMapper;
import com.ruoyi.system.service.IWechatConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 会员卡充值记录Service业务层处理
 *
 * @author ruoyi
 * @date 2024-03-05
 */
@Service
public class PhoneMemberCardLogServiceImpl implements IPhoneMemberCardLogService {
    @Autowired
    private PhoneMemberCardLogMapper phoneMemberCardLogMapper;
    @Autowired
    private WechatTestConfiguration wechatTestConfiguration;
    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private IWechatConfigService wechatConfigService;
    @Autowired
    private PhoneMemberCardMapper phoneMemberCardMapper;
    @Autowired
    private PhoneCommissionConfigMapper phoneCommissionConfigMapper;

    /**
     * 查询会员卡充值记录
     *
     * @param id 会员卡充值记录主键
     * @return 会员卡充值记录
     */
    @Override
    public PhoneMemberCardLog selectPhoneMemberCardLogById(Long id) {
        return phoneMemberCardLogMapper.selectPhoneMemberCardLogById(id);
    }

    /**
     * 查询会员卡充值记录列表
     *
     * @param phoneMemberCardLog 会员卡充值记录
     * @return 会员卡充值记录
     */
    @Override
    @DataScope(deptAlias = "d", userAlias = "a")
    public List<PhoneMemberCardLog> selectPhoneMemberCardLogList(PhoneMemberCardLog phoneMemberCardLog) {
        return phoneMemberCardLogMapper.selectPhoneMemberCardLogList(phoneMemberCardLog);
    }

    /**
     * 新增会员卡充值记录
     *
     * @param phoneMemberCardLog 会员卡充值记录
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public PhoneMemberCardLog insertPhoneMemberCardLog(PhoneMemberCardLog phoneMemberCardLog) {
        final WechatConfig wechatConfig = wechatConfigService.selectWechatConfigByAppId(phoneMemberCardLog.getAppId());
        phoneMemberCardLog.setDeptId(wechatConfig.getDeptId());
        phoneMemberCardLog.setCreateTime(DateUtils.getNowDate());
        phoneMemberCardLog.setOrderNo(SnowflakeGenerator.generateOrderNumber());
        PhoneMemberCard phoneMemberCard = phoneMemberCardMapper.selectPhoneMemberCardById(phoneMemberCardLog.getCardId());
        phoneMemberCardLog.setTotalMoney(phoneMemberCard.getBuyingPrice());
        phoneMemberCardLog.setCardName(phoneMemberCard.getTitle());
        phoneMemberCardLog.setBuyDay(phoneMemberCard.getMemberDay());
        phoneMemberCardLog.setPayStatus("1");
        phoneMemberCardLog.setPayType("1");
        phoneMemberCardLog.setMemberId(SecurityUtils.getUserId());
        phoneMemberCardLogMapper.insertPhoneMemberCardLog(phoneMemberCardLog);
        return phoneMemberCardLog;
    }

    /**
     * 修改会员卡充值记录
     *
     * @param phoneMemberCardLog 会员卡充值记录
     * @return 结果
     */
    @Override
    public int updatePhoneMemberCardLog(PhoneMemberCardLog phoneMemberCardLog) {
        phoneMemberCardLog.setUpdateTime(DateUtils.getNowDate());
        return phoneMemberCardLogMapper.updatePhoneMemberCardLog(phoneMemberCardLog);
    }

    /**
     * 批量删除会员卡充值记录
     *
     * @param ids 需要删除的会员卡充值记录主键
     * @return 结果
     */
    @Override
    public int deletePhoneMemberCardLogByIds(Long[] ids) {
        return phoneMemberCardLogMapper.deletePhoneMemberCardLogByIds(ids);
    }

    /**
     * 删除会员卡充值记录信息
     *
     * @param id 会员卡充值记录主键
     * @return 结果
     */
    @Override
    public int deletePhoneMemberCardLogById(Long id) {
        return phoneMemberCardLogMapper.deletePhoneMemberCardLogById(id);
    }

    @Override
    public WxPayMpOrderResult pay(PhoneMemberCardLog phoneMemberCardLog) {
        Member member = SecurityUtils.getLoginUser().getMember();
        WxPayUnifiedOrderRequest request = new WxPayUnifiedOrderRequest();
        //随机字符串
        request.setNonceStr(IdUtils.generateNonceStr());
        //加密方式
        request.setSignType("MD5");
        //订单号
        request.setOutTradeNo(phoneMemberCardLog.getOrderNo());
        //金额，以分为单位
        request.setTotalFee(phoneMemberCardLog.getTotalMoney().multiply(BigDecimal.valueOf(100L)).intValue());
        // 用户ip
        request.setSpbillCreateIp("127.0.0.1");
        //回调通知地址（必须外网能访问的地址）
        request.setNotifyUrl(Constants.URL + "/api/phone/memberCard/payNotify");
        //公众号支付
        request.setTradeType("JSAPI");
        //小程序用户openid
        request.setOpenid(member.getOpenId());
        request.setBody(phoneMemberCardLog.getCardName());
        try {
            return wechatTestConfiguration.wxPayService().switchoverTo(phoneMemberCardLog.getAppId()).createOrder(request);
        } catch (WxPayException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String payNotify(String xmlData) {
        WxPayOrderNotifyResult notifyResult = WxPayOrderNotifyResult.fromXML(xmlData);
        if (StringUtils.equals("SUCCESS", notifyResult.getReturnCode())) {
            List<PhoneMemberCardLog> cardLogList = phoneMemberCardLogMapper.selectPhoneMemberCardLogByOrderNo(notifyResult.getOutTradeNo());
            if (cardLogList != null && cardLogList.size() > 0) {
                PhoneMemberCardLog phoneMemberCardLog = cardLogList.get(0);
                PhoneMemberCard phoneMemberCard = phoneMemberCardMapper.selectPhoneMemberCardById(phoneMemberCardLog.getCardId());
                Member member = memberMapper.selectMemberById(phoneMemberCardLog.getMemberId());
                if (StringUtils.equals("1", phoneMemberCardLog.getPayStatus())) {
                    phoneMemberCardLog.setPayStatus("2");
                    phoneMemberCardLog.setPayTime(notifyResult.getTimeEnd());
                    phoneMemberCardLog.setPayResult(JSON.toJSONString(notifyResult));
                    phoneMemberCardLog.setUpdateTime(DateUtils.getNowDate());
                    phoneMemberCardLogMapper.updatePhoneMemberCardLog(phoneMemberCardLog);
                    Date endDate = DateUtils.getNowDate();
                    if (StringUtils.equals("2", phoneMemberCard.getMemberType())) {
                        //如果是高级会员
                        member.setIsSuperMember("1");
                        if (member.getSuperExpirationTime() != null) {
                            endDate = member.getSuperExpirationTime();
                        }
                        member.setSuperExpirationTime(DateUtil.endOfDate(DateUtil.addDays(endDate, phoneMemberCardLog.getBuyDay().intValue())));
                    } else {
                        member.setIsMember("1");
                        if (member.getExpirationTime() != null) {
                            endDate = member.getExpirationTime();
                        }
                        member.setExpirationTime(DateUtil.endOfDate(DateUtil.addDays(endDate, phoneMemberCardLog.getBuyDay().intValue())));
                    }
                    memberMapper.updateMember(member);
                    updateUserInfo(phoneMemberCardLog, phoneMemberCard, member);
                }
            }
            return WxPayNotifyResponse.success("成功");
        }
        return WxPayNotifyResponse.fail("失败");
    }

    public void updateUserInfo(PhoneMemberCardLog phoneMemberCardLog, PhoneMemberCard phoneMemberCard, Member member) {
        if (StringUtils.equals("2", phoneMemberCardLog.getPayStatus())) {
            //如果是被推荐用户获取上级
            if (member.getMemberId() != null) {
                Member agency = memberMapper.selectMemberById(member.getMemberId());
                if (agency != null) {
                    BigDecimal agencyBalance = agency.getCommissionBalance();
                    BigDecimal directCommission = phoneMemberCard.getDirectCommission();
                    if (StringUtils.equals("1", agency.getIsSuperMember())) {
                        directCommission = phoneMemberCard.getSuperMemberDirectCommission();
                    }else if(StringUtils.equals("1",agency.getIsMember())){
                        directCommission = phoneMemberCard.getMemberDirectCommission();
                    }
                    agency.setCommissionBalance(agencyBalance.add(directCommission));
                    memberMapper.updateMember(agency);
                    //添加佣金记录
                    PhoneCommissionConfig phoneCommissionConfig = new PhoneCommissionConfig();
                    phoneCommissionConfig.setDeptId(phoneMemberCardLog.getDeptId());
                    phoneCommissionConfig.setAppId(phoneMemberCardLog.getAppId());
                    phoneCommissionConfig.setCommissionBefore(agencyBalance);
                    phoneCommissionConfig.setMoney(directCommission);
                    phoneCommissionConfig.setCommissionAfter(agency.getCommissionBalance());
                    phoneCommissionConfig.setCreateTime(DateUtils.getNowDate());
                    phoneCommissionConfigMapper.insertPhoneCommissionConfig(phoneCommissionConfig);
                    if (agency.getMemberId() != null) {
                        Member secondary = memberMapper.selectMemberById(member.getMemberId());
                        if (secondary != null) {
                            BigDecimal secondaryBalance = secondary.getCommissionBalance();
                            BigDecimal secondaryDirectCommission = phoneMemberCard.getIndirectCommission();
                            if (StringUtils.equals("1", secondary.getIsSuperMember())) {
                                secondaryDirectCommission = phoneMemberCard.getSuperMemberIndirectCommission();
                            }else if(StringUtils.equals("1",secondary.getIsMember())){
                                secondaryDirectCommission = phoneMemberCard.getMemberIndirectCommission();
                            }
                            secondary.setCommissionBalance(secondaryBalance.add(secondaryDirectCommission));
                            memberMapper.updateMember(secondary);
                            //添加佣金记录
                            PhoneCommissionConfig secondaryPhoneCommissionConfig = new PhoneCommissionConfig();
                            secondaryPhoneCommissionConfig.setDeptId(phoneMemberCardLog.getDeptId());
                            secondaryPhoneCommissionConfig.setAppId(phoneMemberCardLog.getAppId());
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
    }
}

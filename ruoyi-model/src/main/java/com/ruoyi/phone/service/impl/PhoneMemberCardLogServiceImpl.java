package com.ruoyi.phone.service.impl;

import com.alibaba.fastjson2.JSON;
import com.github.binarywang.wxpay.bean.notify.WxPayNotifyResponse;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.bean.order.WxPayMpOrderResult;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.ruoyi.common.config.WechatConfiguration;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.entity.Member;
import com.ruoyi.common.core.domain.entity.WechatConfig;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.SnowflakeGenerator;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.uuid.IdUtils;
import com.ruoyi.phone.domain.PhoneBalanceLog;
import com.ruoyi.phone.domain.PhoneMemberCardLog;
import com.ruoyi.phone.mapper.PhoneBalanceLogMapper;
import com.ruoyi.phone.mapper.PhoneMemberCardLogMapper;
import com.ruoyi.phone.service.IPhoneMemberCardLogService;
import com.ruoyi.system.mapper.MemberMapper;
import com.ruoyi.system.service.IWechatConfigService;
import com.ruoyi.system.service.impl.WechatConfigServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
    private WechatConfiguration wechatConfiguration;
    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private PhoneBalanceLogMapper phoneBalanceLogMapper;
    @Autowired
    private IWechatConfigService wechatConfigService;

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
        phoneMemberCardLog.setCreateTime(DateUtils.getNowDate());
        phoneMemberCardLog.setOrderNo(SnowflakeGenerator.generateOrderNumber());
        Member member = memberMapper.selectMemberById(phoneMemberCardLog.getMemberId());
        BigDecimal balance = member.getBalance();
        //如果不是在线支付，需要判断余额是否充足
        if (StringUtils.equals("1", phoneMemberCardLog.getPayType())) {
            phoneMemberCardLog.setPayStatus("1");
            phoneMemberCardLog.setBalanceMoney(BigDecimal.ZERO);
            phoneMemberCardLog.setMoney(phoneMemberCardLog.getTotalMoney());
        } else {
            BigDecimal money;
            if (phoneMemberCardLog.getTotalMoney().compareTo(member.getBalance()) > 0) {
                member.setBalance(BigDecimal.ZERO);
                phoneMemberCardLog.setPayStatus("1");
                phoneMemberCardLog.setMoney(phoneMemberCardLog.getTotalMoney().subtract(balance));
                phoneMemberCardLog.setBalanceMoney(balance);
                money = balance;
            } else {
                member.setBalance(balance.subtract(phoneMemberCardLog.getTotalMoney()));
                phoneMemberCardLog.setBalanceMoney(phoneMemberCardLog.getTotalMoney());
                phoneMemberCardLog.setPayStatus("2");
                phoneMemberCardLog.setMoney(BigDecimal.ZERO);
                money = phoneMemberCardLog.getTotalMoney();
            }
            memberMapper.updateMember(member);
            //添加余额变更记录
            PhoneBalanceLog phoneBalanceLog = new PhoneBalanceLog();
            phoneBalanceLog.setCompanyId(phoneMemberCardLog.getCompanyId());
            phoneBalanceLog.setAppId(phoneMemberCardLog.getAppId());
            phoneBalanceLog.setMemberId(member.getMemberId());
            phoneBalanceLog.setType("2");
            phoneBalanceLog.setBalanceAfter(balance);
            phoneBalanceLog.setMoney(money);
            phoneBalanceLog.setBalanceBefore(member.getBalance());
            phoneBalanceLog.setCreateTime(DateUtils.getNowDate());
            phoneBalanceLogMapper.insertPhoneBalanceLog(phoneBalanceLog);
        }
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
        request.setTotalFee(phoneMemberCardLog.getMoney().multiply(BigDecimal.valueOf(100L)).intValue());
        // 用户ip
        request.setSpbillCreateIp("127.0.0.1");
        //回调通知地址（必须外网能访问的地址）
        request.setNotifyUrl(Constants.URL + "/api/phone/memberCard/payNotify?appid=" + phoneMemberCardLog.getAppId());
        //公众号支付
        request.setTradeType("JSAPI");
        //小程序用户openid
        request.setOpenid(member.getOpenId());
        request.setBody(phoneMemberCardLog.getCardName());
        try {
            final WechatConfig wechatConfig = wechatConfigService.selectWechatConfigByAppId(phoneMemberCardLog.getAppId());
            return wechatConfiguration.wxPayService(wechatConfig).createOrder(request);
        } catch (WxPayException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String payNotify(String appid, String xmlData) {
        try {
            final WechatConfig wechatConfig = wechatConfigService.selectWechatConfigByAppId(appid);
            WxPayOrderNotifyResult notifyResult = wechatConfiguration.wxPayService(wechatConfig).parseOrderNotifyResult(xmlData);
            if (StringUtils.equals("SUCCESS", notifyResult.getReturnCode())) {
                List<PhoneMemberCardLog> cardLogList = phoneMemberCardLogMapper.selectPhoneMemberCardLogByOrderNo(notifyResult.getOutTradeNo());
                if (cardLogList != null && cardLogList.size() > 0) {
                    PhoneMemberCardLog phoneMemberCardLog = cardLogList.get(0);
                    if (StringUtils.equals("1", phoneMemberCardLog.getPayStatus())) {
                        phoneMemberCardLog.setPayStatus("2");
                        phoneMemberCardLog.setPayTime(notifyResult.getTimeEnd());
                        phoneMemberCardLog.setPayResult(JSON.toJSONString(notifyResult));
                        phoneMemberCardLog.setUpdateTime(DateUtils.getNowDate());
                        phoneMemberCardLogMapper.updatePhoneMemberCardLog(phoneMemberCardLog);
                    }
                }
                return WxPayNotifyResponse.success("成功");
            }
        } catch (WxPayException e) {
            e.printStackTrace();
        }
        return WxPayNotifyResponse.fail("失败");
    }
}

package com.ruoyi.phone.service.impl;

import java.math.BigDecimal;
import java.util.List;

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
import com.ruoyi.phone.domain.PhoneOrder;
import com.ruoyi.system.mapper.MemberMapper;
import com.ruoyi.system.service.IWechatConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.phone.mapper.PhoneBalanceLogMapper;
import com.ruoyi.phone.domain.PhoneBalanceLog;
import com.ruoyi.phone.service.IPhoneBalanceLogService;

/**
 * 余额充值记录Service业务层处理
 *
 * @author ruoyi
 * @date 2024-03-05
 */
@Service
public class PhoneBalanceLogServiceImpl implements IPhoneBalanceLogService {
    @Autowired
    private PhoneBalanceLogMapper phoneBalanceLogMapper;
    @Autowired
    private IWechatConfigService wechatConfigService;
    @Autowired
    private WechatConfiguration wechatConfiguration;
    @Autowired
    private MemberMapper memberMapper;

    /**
     * 查询余额充值记录
     *
     * @param id 余额充值记录主键
     * @return 余额充值记录
     */
    @Override
    public PhoneBalanceLog selectPhoneBalanceLogById(Long id) {
        return phoneBalanceLogMapper.selectPhoneBalanceLogById(id);
    }

    /**
     * 查询余额充值记录列表
     *
     * @param phoneBalanceLog 余额充值记录
     * @return 余额充值记录
     */
    @Override
    public List<PhoneBalanceLog> selectPhoneBalanceLogList(PhoneBalanceLog phoneBalanceLog) {
        return phoneBalanceLogMapper.selectPhoneBalanceLogList(phoneBalanceLog);
    }

    /**
     * 新增余额充值记录
     *
     * @param phoneBalanceLog 余额充值记录
     * @return 结果
     */
    @Override
    public PhoneBalanceLog insertPhoneBalanceLog(PhoneBalanceLog phoneBalanceLog) {
        final WechatConfig wechatConfig = wechatConfigService.selectWechatConfigByAppId(phoneBalanceLog.getAppId());
        phoneBalanceLog.setCompanyId(wechatConfig.getCompanyId());
        phoneBalanceLog.setCreateTime(DateUtils.getNowDate());
        phoneBalanceLog.setPayStatus("1");
        phoneBalanceLog.setOrderNo(SnowflakeGenerator.generateOrderNumber());
        phoneBalanceLogMapper.insertPhoneBalanceLog(phoneBalanceLog);
        return phoneBalanceLog;
    }

    /**
     * 修改余额充值记录
     *
     * @param phoneBalanceLog 余额充值记录
     * @return 结果
     */
    @Override
    public int updatePhoneBalanceLog(PhoneBalanceLog phoneBalanceLog) {
        phoneBalanceLog.setUpdateTime(DateUtils.getNowDate());
        return phoneBalanceLogMapper.updatePhoneBalanceLog(phoneBalanceLog);
    }

    /**
     * 批量删除余额充值记录
     *
     * @param ids 需要删除的余额充值记录主键
     * @return 结果
     */
    @Override
    public int deletePhoneBalanceLogByIds(Long[] ids) {
        return phoneBalanceLogMapper.deletePhoneBalanceLogByIds(ids);
    }

    /**
     * 删除余额充值记录信息
     *
     * @param id 余额充值记录主键
     * @return 结果
     */
    @Override
    public int deletePhoneBalanceLogById(Long id) {
        return phoneBalanceLogMapper.deletePhoneBalanceLogById(id);
    }

    @Override
    public WxPayMpOrderResult pay(PhoneBalanceLog phoneBalanceLog) {
        Member member = SecurityUtils.getLoginUser().getMember();
        WxPayUnifiedOrderRequest request = new WxPayUnifiedOrderRequest();
        //随机字符串
        request.setNonceStr(IdUtils.generateNonceStr());
        //加密方式
        request.setSignType("MD5");
        //订单号
        request.setOutTradeNo(phoneBalanceLog.getOrderNo());
        //金额，以分为单位
        request.setTotalFee(phoneBalanceLog.getMoney().multiply(BigDecimal.valueOf(100L)).intValue());
        // 用户ip
        request.setSpbillCreateIp("127.0.0.1");
        //回调通知地址（必须外网能访问的地址）
        request.setNotifyUrl(Constants.URL + "/api/phone/memberCard/payNotify?appid=" + phoneBalanceLog.getAppId());
        //公众号支付
        request.setTradeType("JSAPI");
        //小程序用户openid
        request.setOpenid(member.getOpenId());
        request.setBody("余额充值");
        try {
            final WechatConfig wechatConfig = wechatConfigService.selectWechatConfigByAppId(phoneBalanceLog.getAppId());
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
                List<PhoneBalanceLog> phoneBalanceLogList = phoneBalanceLogMapper.selectPhoneBalanceLogByOrderNo(notifyResult.getOutTradeNo());
                if (phoneBalanceLogList != null && phoneBalanceLogList.size() > 0) {
                    PhoneBalanceLog phoneBalanceLog = phoneBalanceLogList.get(0);
                    if (!StringUtils.equals("2", phoneBalanceLog.getPayStatus())) {
                        Member member = memberMapper.selectMemberById(phoneBalanceLog.getMemberId());
                        BigDecimal balance = member.getBalance();
                        phoneBalanceLog.setBalanceBefore(balance);
                        phoneBalanceLog.setBalanceAfter(balance.add(phoneBalanceLog.getMoney()));
                        phoneBalanceLog.setPayStatus("2");
                        phoneBalanceLog.setPayTime(notifyResult.getTimeEnd());
                        phoneBalanceLog.setPayResult(JSON.toJSONString(notifyResult));
                        phoneBalanceLog.setUpdateTime(DateUtils.getNowDate());
                        phoneBalanceLogMapper.updatePhoneBalanceLog(phoneBalanceLog);
                        //更新用户余额
                        member.setBalance(balance.add(phoneBalanceLog.getMoney()));
                        memberMapper.updateMember(member);
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

package com.ruoyi.system.service.impl;

import com.alibaba.fastjson2.JSON;
import com.ruoyi.common.config.WechatConfiguration;
import com.ruoyi.common.constant.CacheConstants;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.domain.entity.Member;
import com.ruoyi.common.core.domain.entity.WechatConfig;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.mapper.MemberMapper;
import com.ruoyi.system.service.IMemberService;
import com.ruoyi.system.service.IWechatConfigService;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpQrCodeTicket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 会员管理Service业务层处理
 *
 * @author ruoyi
 * @date 2024-01-04
 */
@Service
public class MemberServiceImpl implements IMemberService {
    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private WechatConfiguration wechatConfiguration;
    @Autowired
    private IWechatConfigService wechatConfigService;
    @Autowired
    private RedisCache redisCache;

    /**
     * 查询会员管理
     *
     * @param id 会员管理主键
     * @return 会员管理
     */
    @Override
    public Member selectMemberById(Long id) {
        return memberMapper.selectMemberById(id);
    }

    /**
     * 查询会员管理列表
     *
     * @param member 会员管理
     * @return 会员管理
     */
    @Override
    public List<Member> selectMemberList(Member member) {
        return memberMapper.selectMemberList(member);
    }

    @Override
    public Member getMemberByOpenId(String openId) {
        Member member = new Member();
        member.setOpenId(openId);
        List<Member> memberList = memberMapper.selectMemberListByOpenId(member);
        if (memberList != null && memberList.size() > 0) {
            return memberList.get(0);
        }
        return null;
    }

    @Override
    public Member getMemberByMobile(String mobile) {
        Member member = new Member();
        member.setMobile(mobile);
        List<Member> memberList = memberMapper.selectMemberListByOpenId(member);
        if (memberList != null && memberList.size() > 0) {
            return memberList.get(0);
        }
        return null;
    }

    @Override
    public String checkMobileUnique(Member member) {
        Long id = StringUtils.isNull(member.getId()) ? -1L : member.getId();
        Member info = memberMapper.checkMobileUnique(member.getMobile());
        if (StringUtils.isNotNull(info) && info.getId().longValue() != id.longValue()) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 新增会员管理
     *
     * @param member 会员管理
     * @return 结果
     */
    @Override
    public int insertMember(Member member) {
        member.setCreateTime(DateUtils.getNowDate());
        return memberMapper.insertMember(member);
    }

    /**
     * 修改会员管理
     *
     * @param member 会员管理
     * @return 结果
     */
    @Override
    public int updateMember(Member member) {
        member.setUpdateTime(DateUtils.getNowDate());
        return memberMapper.updateMember(member);
    }

    @Override
    public int resetMemberPwd(Long id, String password) {
        return memberMapper.resetMemberPwd(id, password);
    }

    /**
     * 批量删除会员管理
     *
     * @param ids 需要删除的会员管理主键
     * @return 结果
     */
    @Override
    public int deleteMemberByIds(Long[] ids) {
        return memberMapper.deleteMemberByIds(ids);
    }

    /**
     * 删除会员管理信息
     *
     * @param id 会员管理主键
     * @return 结果
     */
    @Override
    public int deleteMemberById(Long id) {
        return memberMapper.deleteMemberById(id);
    }

    @Override
    public Member getMemberInfo() {
        Long id = SecurityUtils.getLoginUser().getUserId();
        return memberMapper.selectMemberById(id);
    }

    @Override
    public List<Map<String, Object>> findSubordinateList(String type) {
        Member member = new Member();
        Long id = SecurityUtils.getLoginUser().getUserId();

        if (StringUtils.equals("1", type)) {
            member.setMemberId(id);
        } else if (StringUtils.equals("2", type)) {
            member.setMemberId(id);
            member.setAncestors(id.toString());
        } else {
            member.setAncestors(id.toString());
        }
        return memberMapper.selectSubordinateMemberList(member);
    }

    @Override
    public String getQrCode() {
        try {
            Member member = SecurityUtils.getLoginUser().getMember();
            WechatConfig wechatConfig = wechatConfigService.selectWechatConfigByAppId(member.getAppId());
            WxMpService wxMpService = wechatConfiguration.wxMpService(wechatConfig);
            WxMpQrCodeTicket wxMpQrCodeTicket;
            if (redisCache.hasKey(getCacheKey(member.getOpenId()))) {
                wxMpQrCodeTicket = JSON.parseObject(redisCache.getCacheObject(getCacheKey(member.getOpenId())).toString(), WxMpQrCodeTicket.class);
            } else {
                wxMpQrCodeTicket = wxMpService.getQrcodeService().qrCodeCreateTmpTicket(member.getOpenId(), 30 * 24 * 60 * 60);
                redisCache.setCacheObject(getCacheKey(member.getOpenId()), JSON.toJSONString(wxMpQrCodeTicket), 20, TimeUnit.DAYS);
            }
            return wxMpService.getQrcodeService().qrCodePictureUrl(wxMpQrCodeTicket.getTicket());
        } catch (WxErrorException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Map<String, Integer> getMemberDayCount(Member member) {
        Map<String, Integer> map = new HashMap<>();
        //今日数据
        member.getParams().put("type", 1);
        int todayCount = memberMapper.getDayCount(member);
        map.put("todayCount", todayCount);
        //昨日数据
        member.getParams().put("type", 2);
        int yesterdayCount = memberMapper.getDayCount(member);
        map.put("yesterdayCount", yesterdayCount);
        //本月数据
        member.getParams().put("type", 3);
        int monthCount = memberMapper.getDayCount(member);
        map.put("monthCount", monthCount);
        return map;
    }

    /**
     * 设置cache key
     *
     * @param configKey 参数键
     * @return 缓存键key
     */
    private String getCacheKey(String configKey) {
        return CacheConstants.MEMBER_QR_CODE_KEY + configKey;
    }
}

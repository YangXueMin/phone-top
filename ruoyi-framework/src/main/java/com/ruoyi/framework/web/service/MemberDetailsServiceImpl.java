package com.ruoyi.framework.web.service;

import com.ruoyi.common.core.domain.entity.Member;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.service.IMemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author yangxuemin
 * @ClassName MemberDetailsServiceImpl
 * @Description
 * @date 2024/1/4 1:39 PM
 */

@Slf4j
@Service
public class MemberDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private IMemberService memberService;

    @Override
    public UserDetails loadUserByUsername(String openId) throws UsernameNotFoundException {
        //因为是微信公众号登录，上面形参其实是openId
        Member member = memberService.getMemberByOpenId(openId);
        if (StringUtils.isNull(member)) {
            throw new ServiceException("登录用户不存在");
        } else if (StringUtils.equals("2", member.getDelFlag())) {
            log.info("登录用户：{} 已被删除.", member.getName());
            throw new ServiceException("对不起，您的账号：" + member.getName() + " 已被删除");
        }
        //返回UserDetails用户对象
        return createLoginUser(new Member());
    }

    public UserDetails createLoginUser(Member member) {
        /**
         * 参数一：第一个是用户的ID，用户后期使用SecurityUtils.getUserId()时获取上下文中存储的用户ID数据，若未设置，SecurityUtils.getUserId()获取数据位null
         * 参数二：整个用户数据传入，后面会保存在Redis中，登陆校验时会用到
         */
        return new LoginUser(member.getId(), member);
    }
}

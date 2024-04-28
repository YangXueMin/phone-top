package com.ruoyi.system.task;

import com.ruoyi.system.mapper.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 会员过期定时器
 */

@Component("memberExpirationTask")
public class MemberExpirationTask {
    @Autowired
    private MemberMapper memberMapper;

    public void MemberExpiration(){
        memberMapper.updateMemberExpiration();
        memberMapper.updateSuperMemberExpiration();
    }
}

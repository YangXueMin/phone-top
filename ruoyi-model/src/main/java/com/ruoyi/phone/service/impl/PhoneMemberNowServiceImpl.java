package com.ruoyi.phone.service.impl;

import java.util.List;

import com.alibaba.fastjson2.JSON;
import com.ruoyi.common.constant.CacheConstants;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.phone.domain.PhoneCustomer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.phone.mapper.PhoneMemberNowMapper;
import com.ruoyi.phone.domain.PhoneMemberNow;
import com.ruoyi.phone.service.IPhoneMemberNowService;

/**
 * 用户须知配置Service业务层处理
 *
 * @author ruoyi
 * @date 2024-03-04
 */
@Service
public class PhoneMemberNowServiceImpl implements IPhoneMemberNowService {
    @Autowired
    private PhoneMemberNowMapper phoneMemberNowMapper;
    @Autowired
    private RedisCache redisCache;

    /**
     * 查询用户须知配置
     *
     * @param id 用户须知配置主键
     * @return 用户须知配置
     */
    @Override
    public PhoneMemberNow selectPhoneMemberNowById(Long id) {
        return phoneMemberNowMapper.selectPhoneMemberNowById(id);
    }

    @Override
    public PhoneMemberNow selectPhoneMemberNowByAppId(String appId) {
        PhoneMemberNow phoneMemberNow;
        if (redisCache.hasKey(getCacheKey(appId))) {
            phoneMemberNow = JSON.parseObject(redisCache.getCacheObject(getCacheKey(appId)).toString(), PhoneMemberNow.class);
        } else {
            phoneMemberNow = new PhoneMemberNow();
            phoneMemberNow.setAppId(appId);
            List<PhoneMemberNow> phoneMemberNowList = phoneMemberNowMapper.selectPhoneMemberNowList(phoneMemberNow);
            if (phoneMemberNowList.size() > 0) {
                phoneMemberNow = phoneMemberNowList.get(0);
                redisCache.setCacheObject(getCacheKey(appId), JSON.toJSONString(phoneMemberNow));
            }
        }
        return phoneMemberNow;
    }

    /**
     * 查询用户须知配置列表
     *
     * @param phoneMemberNow 用户须知配置
     * @return 用户须知配置
     */
    @Override
    public List<PhoneMemberNow> selectPhoneMemberNowList(PhoneMemberNow phoneMemberNow) {
        return phoneMemberNowMapper.selectPhoneMemberNowList(phoneMemberNow);
    }

    /**
     * 新增用户须知配置
     *
     * @param phoneMemberNow 用户须知配置
     * @return 结果
     */
    @Override
    public int insertPhoneMemberNow(PhoneMemberNow phoneMemberNow) {
        phoneMemberNow.setCreateTime(DateUtils.getNowDate());
        final int i = phoneMemberNowMapper.insertPhoneMemberNow(phoneMemberNow);
        if (i > 0) {
            redisCache.setCacheObject(getCacheKey(phoneMemberNow.getAppId()), JSON.toJSONString(phoneMemberNow));
        }
        return i;
    }

    /**
     * 修改用户须知配置
     *
     * @param phoneMemberNow 用户须知配置
     * @return 结果
     */
    @Override
    public int updatePhoneMemberNow(PhoneMemberNow phoneMemberNow) {
        phoneMemberNow.setUpdateTime(DateUtils.getNowDate());
        final int i = phoneMemberNowMapper.updatePhoneMemberNow(phoneMemberNow);
        if (i > 0) {
            redisCache.setCacheObject(getCacheKey(phoneMemberNow.getAppId()), JSON.toJSONString(phoneMemberNow));
        }
        return i;
    }

    /**
     * 批量删除用户须知配置
     *
     * @param ids 需要删除的用户须知配置主键
     * @return 结果
     */
    @Override
    public int deletePhoneMemberNowByIds(Long[] ids) {
        return phoneMemberNowMapper.deletePhoneMemberNowByIds(ids);
    }

    /**
     * 删除用户须知配置信息
     *
     * @param id 用户须知配置主键
     * @return 结果
     */
    @Override
    public int deletePhoneMemberNowById(Long id) {
        return phoneMemberNowMapper.deletePhoneMemberNowById(id);
    }

    /**
     * 设置cache key
     *
     * @param configKey 参数键
     * @return 缓存键key
     */
    private String getCacheKey(String configKey) {
        return CacheConstants.WECHAT_MEMBER_NOW_KEY + configKey;
    }
}

package com.ruoyi.phone.service.impl;

import com.alibaba.fastjson2.JSON;
import com.ruoyi.common.constant.CacheConstants;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.phone.domain.PhoneBanner;
import com.ruoyi.phone.mapper.PhoneBannerMapper;
import com.ruoyi.phone.service.IPhoneBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.List;

/**
 * banner轮播配置Service业务层处理
 *
 * @author ruoyi
 * @date 2024-03-04
 */
@Service
public class PhoneBannerServiceImpl implements IPhoneBannerService {
    @Autowired
    private PhoneBannerMapper phoneBannerMapper;
    @Autowired
    private RedisCache redisCache;

    /**
     * 项目启动时，初始化参数到缓存
     */
    @PostConstruct
    public void init() {
        loadingConfigCache();
    }

    /**
     * 查询banner轮播配置
     *
     * @param id banner轮播配置主键
     * @return banner轮播配置
     */
    @Override
    public PhoneBanner selectPhoneBannerById(Long id) {
        return phoneBannerMapper.selectPhoneBannerById(id);
    }

    @Override
    public PhoneBanner selectPhoneBannerByAppId(String appId) {
        PhoneBanner phoneBanner;
        if (redisCache.hasKey(getCacheKey(appId))) {
            phoneBanner = redisCache.getCacheObject(getCacheKey(appId));
        } else {
            phoneBanner = new PhoneBanner();
            phoneBanner.setAppId(appId);
            List<PhoneBanner> phoneBannerList = phoneBannerMapper.selectPhoneBannerList(phoneBanner);
            if (phoneBannerList.size() > 0) {
                phoneBanner = phoneBannerList.get(0);
                redisCache.setCacheObject(getCacheKey(appId), JSON.toJSONString(phoneBanner));
            }
        }
        return phoneBanner;
    }

    /**
     * 查询banner轮播配置列表
     *
     * @param phoneBanner banner轮播配置
     * @return banner轮播配置
     */
    @Override
    public List<PhoneBanner> selectPhoneBannerList(PhoneBanner phoneBanner) {
        return phoneBannerMapper.selectPhoneBannerList(phoneBanner);
    }

    /**
     * 新增banner轮播配置
     *
     * @param phoneBanner banner轮播配置
     * @return 结果
     */
    @Override
    public int insertPhoneBanner(PhoneBanner phoneBanner) {
        phoneBanner.setCreateTime(DateUtils.getNowDate());
        final int i = phoneBannerMapper.insertPhoneBanner(phoneBanner);
        if (i > 0) {
            redisCache.setCacheObject(getCacheKey(phoneBanner.getAppId()), JSON.toJSONString(phoneBanner));
        }
        return i;
    }

    /**
     * 修改banner轮播配置
     *
     * @param phoneBanner banner轮播配置
     * @return 结果
     */
    @Override
    public int updatePhoneBanner(PhoneBanner phoneBanner) {
        phoneBanner.setUpdateTime(DateUtils.getNowDate());
        final int i = phoneBannerMapper.updatePhoneBanner(phoneBanner);
        if (i > 0) {
            redisCache.setCacheObject(getCacheKey(phoneBanner.getAppId()), JSON.toJSONString(phoneBanner));
        }
        return i;
    }

    /**
     * 批量删除banner轮播配置
     *
     * @param ids 需要删除的banner轮播配置主键
     * @return 结果
     */
    @Override
    public int deletePhoneBannerByIds(Long[] ids) {
        clearConfigCache();
        return phoneBannerMapper.deletePhoneBannerByIds(ids);
    }

    /**
     * 删除banner轮播配置信息
     *
     * @param id banner轮播配置主键
     * @return 结果
     */
    @Override
    public int deletePhoneBannerById(Long id) {
        final PhoneBanner phoneBanner = phoneBannerMapper.selectPhoneBannerById(id);
        final int i = phoneBannerMapper.deletePhoneBannerById(id);
        if (i > 0) {
            redisCache.deleteObject(getCacheKey(phoneBanner.getAppId()));
        }
        return i;
    }

    @Override
    public void loadingConfigCache() {
        List<PhoneBanner> phoneBannerList = phoneBannerMapper.selectPhoneBannerList(new PhoneBanner());
        for (PhoneBanner banner : phoneBannerList) {
            redisCache.setCacheObject(getCacheKey(banner.getAppId()), JSON.toJSONString(banner));
        }
    }

    @Override
    public void clearConfigCache() {
        Collection<String> keys = redisCache.keys(CacheConstants.WECHAT_BANNER_CONFIG_KEY + "*");
        redisCache.deleteObject(keys);
    }

    @Override
    public void resetConfigCache() {
        clearConfigCache();
        loadingConfigCache();
    }

    /**
     * 设置cache key
     *
     * @param configKey 参数键
     * @return 缓存键key
     */
    private String getCacheKey(String configKey) {
        return CacheConstants.WECHAT_BANNER_CONFIG_KEY + configKey;
    }
}

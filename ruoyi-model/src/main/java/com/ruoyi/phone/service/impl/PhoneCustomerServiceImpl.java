package com.ruoyi.phone.service.impl;

import java.util.Collection;
import java.util.List;

import com.alibaba.fastjson2.JSON;
import com.ruoyi.common.constant.CacheConstants;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.phone.domain.PhoneBanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.phone.mapper.PhoneCustomerMapper;
import com.ruoyi.phone.domain.PhoneCustomer;
import com.ruoyi.phone.service.IPhoneCustomerService;

import javax.annotation.PostConstruct;

/**
 * 客服配置Service业务层处理
 *
 * @author ruoyi
 * @date 2024-03-04
 */
@Service
public class PhoneCustomerServiceImpl implements IPhoneCustomerService {
    @Autowired
    private PhoneCustomerMapper phoneCustomerMapper;
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
     * 查询客服配置
     *
     * @param id 客服配置主键
     * @return 客服配置
     */
    @Override
    public PhoneCustomer selectPhoneCustomerById(Long id) {
        return phoneCustomerMapper.selectPhoneCustomerById(id);
    }

    @Override
    public PhoneCustomer selectPhoneCustomerByAppId(String appId) {
        PhoneCustomer phoneCustomer;
        if (redisCache.hasKey(getCacheKey(appId))) {
            phoneCustomer = redisCache.getCacheObject(getCacheKey(appId));
        } else {
            phoneCustomer = new PhoneCustomer();
            phoneCustomer.setAppId(appId);
            List<PhoneCustomer> phoneCustomerList = phoneCustomerMapper.selectPhoneCustomerList(phoneCustomer);
            if (phoneCustomerList.size() > 0) {
                phoneCustomer = phoneCustomerList.get(0);
                redisCache.setCacheObject(getCacheKey(appId), JSON.toJSONString(phoneCustomer));
            }
        }
        return phoneCustomer;
    }

    /**
     * 查询客服配置列表
     *
     * @param phoneCustomer 客服配置
     * @return 客服配置
     */
    @Override
    public List<PhoneCustomer> selectPhoneCustomerList(PhoneCustomer phoneCustomer) {
        return phoneCustomerMapper.selectPhoneCustomerList(phoneCustomer);
    }

    /**
     * 新增客服配置
     *
     * @param phoneCustomer 客服配置
     * @return 结果
     */
    @Override
    public int insertPhoneCustomer(PhoneCustomer phoneCustomer) {
        phoneCustomer.setCreateTime(DateUtils.getNowDate());
        final int i = phoneCustomerMapper.insertPhoneCustomer(phoneCustomer);
        if (i > 0) {
            redisCache.setCacheObject(getCacheKey(phoneCustomer.getAppId()), JSON.toJSONString(phoneCustomer));
        }
        return i;
    }

    /**
     * 修改客服配置
     *
     * @param phoneCustomer 客服配置
     * @return 结果
     */
    @Override
    public int updatePhoneCustomer(PhoneCustomer phoneCustomer) {
        phoneCustomer.setUpdateTime(DateUtils.getNowDate());
        final int i = phoneCustomerMapper.updatePhoneCustomer(phoneCustomer);
        if (i > 0) {
            redisCache.setCacheObject(getCacheKey(phoneCustomer.getAppId()), JSON.toJSONString(phoneCustomer));
        }
        return i;
    }

    /**
     * 批量删除客服配置
     *
     * @param ids 需要删除的客服配置主键
     * @return 结果
     */
    @Override
    public int deletePhoneCustomerByIds(Long[] ids) {
        clearConfigCache();
        return phoneCustomerMapper.deletePhoneCustomerByIds(ids);
    }

    /**
     * 删除客服配置信息
     *
     * @param id 客服配置主键
     * @return 结果
     */
    @Override
    public int deletePhoneCustomerById(Long id) {
        final PhoneCustomer phoneCustomer = phoneCustomerMapper.selectPhoneCustomerById(id);
        final int i = phoneCustomerMapper.deletePhoneCustomerById(id);
        if(i > 0){
            redisCache.deleteObject(getCacheKey(phoneCustomer.getAppId()));
        }
        return i;
    }

    @Override
    public void loadingConfigCache() {
        List<PhoneCustomer> phoneCustomerList = phoneCustomerMapper.selectPhoneCustomerList(new PhoneCustomer());
        for (PhoneCustomer phoneCustomer : phoneCustomerList) {
            redisCache.setCacheObject(getCacheKey(phoneCustomer.getAppId()), JSON.toJSONString(phoneCustomer));
        }
    }

    @Override
    public void clearConfigCache() {
        Collection<String> keys = redisCache.keys(CacheConstants.WECHAT_CUSTOMER_CONFIG_KEY + "*");
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
        return CacheConstants.WECHAT_CUSTOMER_CONFIG_KEY + configKey;
    }
}

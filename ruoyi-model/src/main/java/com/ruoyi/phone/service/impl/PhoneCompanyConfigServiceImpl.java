package com.ruoyi.phone.service.impl;

import com.alibaba.fastjson2.JSON;
import com.ruoyi.common.constant.CacheConstants;
import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.phone.domain.PhoneCompanyConfig;
import com.ruoyi.phone.domain.PhoneCustomer;
import com.ruoyi.phone.mapper.PhoneCompanyConfigMapper;
import com.ruoyi.phone.service.IPhoneCompanyConfigService;
import com.ruoyi.system.service.ISysDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.List;

/**
 * 企业配置Service业务层处理
 *
 * @author ruoyi
 * @date 2024-03-04
 */
@Service
public class PhoneCompanyConfigServiceImpl implements IPhoneCompanyConfigService {
    @Autowired
    private PhoneCompanyConfigMapper phoneCompanyConfigMapper;
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private ISysDeptService sysDeptService;

    /**
     * 项目启动时，初始化参数到缓存
     */
    @PostConstruct
    public void init() {
        loadingConfigCache();
    }

    /**
     * 查询企业配置
     *
     * @param id 企业配置主键
     * @return 企业配置
     */
    @Override
    public PhoneCompanyConfig selectPhoneCompanyConfigById(Long id) {
        return phoneCompanyConfigMapper.selectPhoneCompanyConfigById(id);
    }

    @Override
    public PhoneCompanyConfig selectPhoneCompanyConfigByAppId(String appId) {
        PhoneCompanyConfig phoneCompanyConfig;
        if (redisCache.hasKey(getCacheKey(appId))) {
            phoneCompanyConfig = JSON.parseObject(redisCache.getCacheObject(getCacheKey(appId)).toString(),PhoneCompanyConfig.class);
        } else {
            phoneCompanyConfig = new PhoneCompanyConfig();
            phoneCompanyConfig.setAppId(appId);
            List<PhoneCompanyConfig> phoneCustomerList = phoneCompanyConfigMapper.selectPhoneCompanyConfigList(phoneCompanyConfig);
            if (phoneCustomerList.size() > 0) {
                phoneCompanyConfig = phoneCustomerList.get(0);
                redisCache.setCacheObject(getCacheKey(appId), JSON.toJSONString(phoneCompanyConfig));
            }
        }
        return phoneCompanyConfig;
    }

    /**
     * 查询企业配置列表
     *
     * @param phoneCompanyConfig 企业配置
     * @return 企业配置
     */
    @Override
    public List<PhoneCompanyConfig> selectPhoneCompanyConfigList(PhoneCompanyConfig phoneCompanyConfig) {
        return phoneCompanyConfigMapper.selectPhoneCompanyConfigList(phoneCompanyConfig);
    }

    /**
     * 新增企业配置
     *
     * @param phoneCompanyConfig 企业配置
     * @return 结果
     */
    @Override
    public int insertPhoneCompanyConfig(PhoneCompanyConfig phoneCompanyConfig) {
        SysDept company = sysDeptService.selectCompany(SecurityUtils.getLoginUser().getDeptId());
        if(company != null && !company.getDeptId().equals(100L)){
            phoneCompanyConfig.setCompanyId(company.getDeptId());
        }
        phoneCompanyConfig.setCreateTime(DateUtils.getNowDate());
        final int i = phoneCompanyConfigMapper.insertPhoneCompanyConfig(phoneCompanyConfig);
        if (i > 0) {
            redisCache.setCacheObject(getCacheKey(phoneCompanyConfig.getAppId()), JSON.toJSONString(phoneCompanyConfig));
        }
        return i;
    }

    /**
     * 修改企业配置
     *
     * @param phoneCompanyConfig 企业配置
     * @return 结果
     */
    @Override
    public int updatePhoneCompanyConfig(PhoneCompanyConfig phoneCompanyConfig) {
        phoneCompanyConfig.setUpdateTime(DateUtils.getNowDate());
        final int i = phoneCompanyConfigMapper.updatePhoneCompanyConfig(phoneCompanyConfig);
        if (i > 0) {
            redisCache.setCacheObject(getCacheKey(phoneCompanyConfig.getAppId()), JSON.toJSONString(phoneCompanyConfig));
        }
        return i;
    }

    /**
     * 批量删除企业配置
     *
     * @param ids 需要删除的企业配置主键
     * @return 结果
     */
    @Override
    public int deletePhoneCompanyConfigByIds(Long[] ids) {
        clearConfigCache();
        return phoneCompanyConfigMapper.deletePhoneCompanyConfigByIds(ids);
    }

    /**
     * 删除企业配置信息
     *
     * @param id 企业配置主键
     * @return 结果
     */
    @Override
    public int deletePhoneCompanyConfigById(Long id) {
        final PhoneCompanyConfig phoneCompanyConfig = phoneCompanyConfigMapper.selectPhoneCompanyConfigById(id);
        final int i = phoneCompanyConfigMapper.deletePhoneCompanyConfigById(id);
        if(i > 0){
            redisCache.deleteObject(getCacheKey(phoneCompanyConfig.getAppId()));
        }
        return i;
    }

    @Override
    public void loadingConfigCache() {
        List<PhoneCompanyConfig> phoneCompanyConfigList = phoneCompanyConfigMapper.selectPhoneCompanyConfigList(new PhoneCompanyConfig());
        for (PhoneCompanyConfig phoneCompanyConfig : phoneCompanyConfigList) {
            redisCache.setCacheObject(getCacheKey(phoneCompanyConfig.getAppId()), JSON.toJSONString(phoneCompanyConfig));
        }
    }

    @Override
    public void clearConfigCache() {
        Collection<String> keys = redisCache.keys(CacheConstants.WECHAT_COMPANY_CONFIG_KEY + "*");
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
        return CacheConstants.WECHAT_COMPANY_CONFIG_KEY + configKey;
    }
}

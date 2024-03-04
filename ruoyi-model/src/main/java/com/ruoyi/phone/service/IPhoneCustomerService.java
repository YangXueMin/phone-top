package com.ruoyi.phone.service;

import com.ruoyi.phone.domain.PhoneCustomer;

import java.util.List;

/**
 * 客服配置Service接口
 *
 * @author ruoyi
 * @date 2024-03-04
 */
public interface IPhoneCustomerService {
    /**
     * 查询客服配置
     *
     * @param id 客服配置主键
     * @return 客服配置
     */
    public PhoneCustomer selectPhoneCustomerById(Long id);

    /**
     * 查询微信配置
     *
     * @param appId 微信APPId
     * @return 微信配置
     */
    public PhoneCustomer selectPhoneCustomerByAppId(String appId);

    /**
     * 查询客服配置列表
     *
     * @param phoneCustomer 客服配置
     * @return 客服配置集合
     */
    public List<PhoneCustomer> selectPhoneCustomerList(PhoneCustomer phoneCustomer);

    /**
     * 新增客服配置
     *
     * @param phoneCustomer 客服配置
     * @return 结果
     */
    public int insertPhoneCustomer(PhoneCustomer phoneCustomer);

    /**
     * 修改客服配置
     *
     * @param phoneCustomer 客服配置
     * @return 结果
     */
    public int updatePhoneCustomer(PhoneCustomer phoneCustomer);

    /**
     * 批量删除客服配置
     *
     * @param ids 需要删除的客服配置主键集合
     * @return 结果
     */
    public int deletePhoneCustomerByIds(Long[] ids);

    /**
     * 删除客服配置信息
     *
     * @param id 客服配置主键
     * @return 结果
     */
    public int deletePhoneCustomerById(Long id);

    /**
     * 加载参数缓存数据
     */
    public void loadingConfigCache();

    /**
     * 清空参数缓存数据
     */
    public void clearConfigCache();

    /**
     * 重置参数缓存数据
     */
    public void resetConfigCache();
}

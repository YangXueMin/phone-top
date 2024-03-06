package com.ruoyi.phone.service;

import com.ruoyi.phone.domain.PhoneBanner;

import java.util.List;

/**
 * banner轮播配置Service接口
 *
 * @author ruoyi
 * @date 2024-03-04
 */
public interface IPhoneBannerService
{
    /**
     * 查询banner轮播配置
     *
     * @param id banner轮播配置主键
     * @return banner轮播配置
     */
    public PhoneBanner selectPhoneBannerById(Long id);


    /**
     * 查询微信配置
     *
     * @param appId 微信APPId
     * @return 微信配置
     */
    public List<PhoneBanner> selectPhoneBannerByAppId(String appId);

    /**
     * 查询banner轮播配置列表
     *
     * @param phoneBanner banner轮播配置
     * @return banner轮播配置集合
     */
    public List<PhoneBanner> selectPhoneBannerList(PhoneBanner phoneBanner);

    /**
     * 新增banner轮播配置
     *
     * @param phoneBanner banner轮播配置
     * @return 结果
     */
    public int insertPhoneBanner(PhoneBanner phoneBanner);

    /**
     * 修改banner轮播配置
     *
     * @param phoneBanner banner轮播配置
     * @return 结果
     */
    public int updatePhoneBanner(PhoneBanner phoneBanner);

    /**
     * 批量删除banner轮播配置
     *
     * @param ids 需要删除的banner轮播配置主键集合
     * @return 结果
     */
    public int deletePhoneBannerByIds(Long[] ids);

    /**
     * 删除banner轮播配置信息
     *
     * @param id banner轮播配置主键
     * @return 结果
     */
    public int deletePhoneBannerById(Long id);

    /**
     * 清空参数缓存数据
     */
    public void clearConfigCache();
}

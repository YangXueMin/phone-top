package com.ruoyi.phone.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.ruoyi.phone.domain.PhoneBanner;

/**
 * banner轮播配置Mapper接口
 *
 * @author ruoyi
 * @date 2024-03-04
 */
@Mapper
public interface PhoneBannerMapper
{
    /**
     * 查询banner轮播配置
     *
     * @param id banner轮播配置主键
     * @return banner轮播配置
     */
    public PhoneBanner selectPhoneBannerById(Long id);

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
     * 删除banner轮播配置
     *
     * @param id banner轮播配置主键
     * @return 结果
     */
    public int deletePhoneBannerById(Long id);

    /**
     * 批量删除banner轮播配置
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePhoneBannerByIds(Long[] ids);
}

package com.ruoyi.shop.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.ruoyi.shop.domain.Banner;

/**
 * banner配置Mapper接口
 *
 * @author ruoyi
 * @date 2024-01-06
 */
@Mapper
public interface BannerMapper
{
    /**
     * 查询banner配置
     *
     * @param id banner配置主键
     * @return banner配置
     */
    public Banner selectBannerById(Long id);

    /**
     * 查询banner配置列表
     *
     * @param banner banner配置
     * @return banner配置集合
     */
    public List<Banner> selectBannerList(Banner banner);

    /**
     * 新增banner配置
     *
     * @param banner banner配置
     * @return 结果
     */
    public int insertBanner(Banner banner);

    /**
     * 修改banner配置
     *
     * @param banner banner配置
     * @return 结果
     */
    public int updateBanner(Banner banner);

    /**
     * 删除banner配置
     *
     * @param id banner配置主键
     * @return 结果
     */
    public int deleteBannerById(Long id);

    /**
     * 批量删除banner配置
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBannerByIds(Long[] ids);
}

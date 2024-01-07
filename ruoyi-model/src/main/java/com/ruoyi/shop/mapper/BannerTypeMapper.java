package com.ruoyi.shop.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.ruoyi.shop.domain.BannerType;

/**
 * banner类型Mapper接口
 *
 * @author ruoyi
 * @date 2024-01-07
 */
@Mapper
public interface BannerTypeMapper
{
    /**
     * 查询banner类型
     *
     * @param typeId banner类型主键
     * @return banner类型
     */
    public BannerType selectBannerTypeByTypeId(Long typeId);

    /**
     * 查询banner类型列表
     *
     * @param bannerType banner类型
     * @return banner类型集合
     */
    public List<BannerType> selectBannerTypeList(BannerType bannerType);

    /**
     * 新增banner类型
     *
     * @param bannerType banner类型
     * @return 结果
     */
    public int insertBannerType(BannerType bannerType);

    /**
     * 修改banner类型
     *
     * @param bannerType banner类型
     * @return 结果
     */
    public int updateBannerType(BannerType bannerType);

    /**
     * 删除banner类型
     *
     * @param typeId banner类型主键
     * @return 结果
     */
    public int deleteBannerTypeByTypeId(Long typeId);

    /**
     * 批量删除banner类型
     *
     * @param typeIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBannerTypeByTypeIds(Long[] typeIds);
}

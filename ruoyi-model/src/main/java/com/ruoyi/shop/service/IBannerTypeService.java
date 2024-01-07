package com.ruoyi.shop.service;

import com.ruoyi.shop.domain.BannerType;
import com.ruoyi.shop.domain.ShopTreeSelect;

import java.util.List;

/**
 * banner类型Service接口
 *
 * @author ruoyi
 * @date 2024-01-07
 */
public interface IBannerTypeService {
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
     * 查询banner类型树结构信息
     *
     * @param bannerType banner类型
     * @return 内容分类信息集合
     */
    public List<ShopTreeSelect> selectTreeList(BannerType bannerType);

    /**
     * 构建前端所需要下拉树结构
     *
     * @param typeList banner类型
     * @return 下拉树结构列表
     */
    public List<ShopTreeSelect> buildTreeSelect(List<BannerType> typeList);

    /**
     * 构建前端所需要树结构
     *
     * @param typeList banner类型
     * @return 树结构列表
     */
    public List<BannerType> buildTree(List<BannerType> typeList);

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
     * 批量删除banner类型
     *
     * @param typeIds 需要删除的banner类型主键集合
     * @return 结果
     */
    public int deleteBannerTypeByTypeIds(Long[] typeIds);

    /**
     * 删除banner类型信息
     *
     * @param typeId banner类型主键
     * @return 结果
     */
    public int deleteBannerTypeByTypeId(Long typeId);
}

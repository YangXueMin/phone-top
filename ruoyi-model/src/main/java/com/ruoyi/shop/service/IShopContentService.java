package com.ruoyi.shop.service;

import java.util.List;
import com.ruoyi.shop.domain.ShopContent;

/**
 * 内容配置Service接口
 *
 * @author ruoyi
 * @date 2024-01-06
 */
public interface IShopContentService
{
    /**
     * 查询内容配置
     *
     * @param id 内容配置主键
     * @return 内容配置
     */
    public ShopContent selectShopContentById(Long id);

    /**
     * 查询内容配置列表
     *
     * @param shopContent 内容配置
     * @return 内容配置集合
     */
    public List<ShopContent> selectShopContentList(ShopContent shopContent);

    /**
     * 新增内容配置
     *
     * @param shopContent 内容配置
     * @return 结果
     */
    public int insertShopContent(ShopContent shopContent);

    /**
     * 修改内容配置
     *
     * @param shopContent 内容配置
     * @return 结果
     */
    public int updateShopContent(ShopContent shopContent);

    /**
     * 批量删除内容配置
     *
     * @param ids 需要删除的内容配置主键集合
     * @return 结果
     */
    public int deleteShopContentByIds(Long[] ids);

    /**
     * 删除内容配置信息
     *
     * @param id 内容配置主键
     * @return 结果
     */
    public int deleteShopContentById(Long id);
}

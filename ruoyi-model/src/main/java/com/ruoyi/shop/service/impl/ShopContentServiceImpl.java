package com.ruoyi.shop.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.shop.mapper.ShopContentMapper;
import com.ruoyi.shop.domain.ShopContent;
import com.ruoyi.shop.service.IShopContentService;

/**
 * 内容配置Service业务层处理
 *
 * @author ruoyi
 * @date 2024-01-06
 */
@Service
public class ShopContentServiceImpl implements IShopContentService
{
    @Autowired
    private ShopContentMapper shopContentMapper;

    /**
     * 查询内容配置
     *
     * @param id 内容配置主键
     * @return 内容配置
     */
    @Override
    public ShopContent selectShopContentById(Long id)
    {
        return shopContentMapper.selectShopContentById(id);
    }

    /**
     * 查询内容配置列表
     *
     * @param shopContent 内容配置
     * @return 内容配置
     */
    @Override
    public List<ShopContent> selectShopContentList(ShopContent shopContent)
    {
        return shopContentMapper.selectShopContentList(shopContent);
    }

    /**
     * 新增内容配置
     *
     * @param shopContent 内容配置
     * @return 结果
     */
    @Override
    public int insertShopContent(ShopContent shopContent)
    {
        shopContent.setCreateTime(DateUtils.getNowDate());
        return shopContentMapper.insertShopContent(shopContent);
    }

    /**
     * 修改内容配置
     *
     * @param shopContent 内容配置
     * @return 结果
     */
    @Override
    public int updateShopContent(ShopContent shopContent)
    {
        shopContent.setUpdateTime(DateUtils.getNowDate());
        return shopContentMapper.updateShopContent(shopContent);
    }

    /**
     * 批量删除内容配置
     *
     * @param ids 需要删除的内容配置主键
     * @return 结果
     */
    @Override
    public int deleteShopContentByIds(Long[] ids)
    {
        return shopContentMapper.deleteShopContentByIds(ids);
    }

    /**
     * 删除内容配置信息
     *
     * @param id 内容配置主键
     * @return 结果
     */
    @Override
    public int deleteShopContentById(Long id)
    {
        return shopContentMapper.deleteShopContentById(id);
    }
}

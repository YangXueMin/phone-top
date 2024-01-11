package com.ruoyi.shop.mapper;

import com.ruoyi.shop.domain.ShopActivity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 活动管理Mapper接口
 *
 * @author ruoyi
 * @date 2024-01-11
 */
@Mapper
public interface ShopActivityMapper {
    /**
     * 查询活动管理
     *
     * @param id 活动管理主键
     * @return 活动管理
     */
    public ShopActivity selectShopActivityById(Long id);

    /**
     * 查询活动管理列表
     *
     * @param shopActivity 活动管理
     * @return 活动管理集合
     */
    public List<ShopActivity> selectShopActivityList(ShopActivity shopActivity);

    /**
     * 新增活动管理
     *
     * @param shopActivity 活动管理
     * @return 结果
     */
    public int insertShopActivity(ShopActivity shopActivity);

    /**
     * 修改活动管理
     *
     * @param shopActivity 活动管理
     * @return 结果
     */
    public int updateShopActivity(ShopActivity shopActivity);

    /**
     * 删除活动管理
     *
     * @param id 活动管理主键
     * @return 结果
     */
    public int deleteShopActivityById(Long id);

    /**
     * 批量删除活动管理
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteShopActivityByIds(Long[] ids);
}

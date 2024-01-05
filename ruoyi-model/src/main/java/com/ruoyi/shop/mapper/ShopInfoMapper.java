package com.ruoyi.shop.mapper;

import java.util.List;

import com.ruoyi.system.domain.SysPost;
import org.apache.ibatis.annotations.Mapper;
import com.ruoyi.shop.domain.ShopInfo;

/**
 * 店铺信息Mapper接口
 *
 * @author ruoyi
 * @date 2024-01-04
 */
@Mapper
public interface ShopInfoMapper
{
    /**
     * 查询店铺信息
     *
     * @param id 店铺信息主键
     * @return 店铺信息
     */
    public ShopInfo selectShopInfoById(Long id);

    /**
     * 查询店铺信息列表
     *
     * @param shopInfo 店铺信息
     * @return 店铺信息集合
     */
    public List<ShopInfo> selectShopInfoList(ShopInfo shopInfo);

    /**
     * 查询所有岗位
     *
     * @return 岗位列表
     */
    public List<ShopInfo> selectShopAll();

    /**
     * 根据用户ID获取店铺选择框列表
     * @param userId
     * @return
     */
    List<Long> selectShopListByUserId(Long userId);

    /**
     * 新增店铺信息
     *
     * @param shopInfo 店铺信息
     * @return 结果
     */
    public int insertShopInfo(ShopInfo shopInfo);

    /**
     * 修改店铺信息
     *
     * @param shopInfo 店铺信息
     * @return 结果
     */
    public int updateShopInfo(ShopInfo shopInfo);

    /**
     * 删除店铺信息
     *
     * @param id 店铺信息主键
     * @return 结果
     */
    public int deleteShopInfoById(Long id);

    /**
     * 批量删除店铺信息
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteShopInfoByIds(Long[] ids);
}

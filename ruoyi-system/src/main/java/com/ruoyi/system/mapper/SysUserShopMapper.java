package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.SysUserShop;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 用户与岗位关联表 数据层
 *
 * @author ruoyi
 */
@Mapper
public interface SysUserShopMapper {
    /**
     * 通过用户ID删除用户和店铺关联
     *
     * @param userId 用户ID
     * @return 结果
     */
    public int deleteUserShopByUserId(Long userId);

    /**
     * 通过岗位ID查询店铺使用数量
     *
     * @param shopId 店铺ID
     * @return 结果
     */
    public int countUserShopById(Long shopId);

    /**
     * 通过用户ID获取店铺ID集合
     *
     * @param userId 用户ID
     * @return 结果
     */
    public List<Long> findShopIdsByUserId(Long userId);

    /**
     * 批量删除用户和店铺关联
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteUserShop(Long[] ids);

    /**
     * 批量新增用户店铺信息
     *
     * @param userShopList
     * @return 结果
     */
    public int batchUserShop(List<SysUserShop> userShopList);
}

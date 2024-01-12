package com.ruoyi.shop.service.impl;

import java.util.List;

import com.ruoyi.common.annotation.ShopScope;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.shop.mapper.ShopInfoMapper;
import com.ruoyi.shop.domain.ShopInfo;
import com.ruoyi.shop.service.IShopInfoService;

/**
 * 店铺信息Service业务层处理
 *
 * @author ruoyi
 * @date 2024-01-04
 */
@Service
public class ShopInfoServiceImpl implements IShopInfoService {
    @Autowired
    private ShopInfoMapper shopInfoMapper;

    /**
     * 查询店铺信息
     *
     * @param id 店铺信息主键
     * @return 店铺信息
     */
    @Override
    public ShopInfo selectShopInfoById(Long id) {
        return shopInfoMapper.selectShopInfoById(id);
    }

    /**
     * 查询店铺信息列表
     *
     * @param shopInfo 店铺信息
     * @return 店铺信息
     */
    @Override
    @ShopScope(shopAlias = "a")
    public List<ShopInfo> selectShopInfoList(ShopInfo shopInfo) {
        return shopInfoMapper.selectShopInfoList(shopInfo);
    }

    /**
     * 查询店铺信息列表
     *
     * @param shopInfo 店铺信息
     * @return 店铺信息
     */
    @Override
    public List<ShopInfo> selectShopInfoListApi(ShopInfo shopInfo) {
        return shopInfoMapper.selectShopInfoList(shopInfo);
    }

    @Override
    public List<ShopInfo> selectShopInfoAll() {
        return shopInfoMapper.selectShopAll();
    }

    @Override
    public List<Long> selectShopListByUserId(Long userId) {
        return shopInfoMapper.selectShopListByUserId(userId);
    }

    /**
     * 新增店铺信息
     *
     * @param shopInfo 店铺信息
     * @return 结果
     */
    @Override
    public int insertShopInfo(ShopInfo shopInfo) {
        shopInfo.setCreateTime(DateUtils.getNowDate());
        return shopInfoMapper.insertShopInfo(shopInfo);
    }

    /**
     * 修改店铺信息
     *
     * @param shopInfo 店铺信息
     * @return 结果
     */
    @Override
    public int updateShopInfo(ShopInfo shopInfo) {
        shopInfo.setUpdateTime(DateUtils.getNowDate());
        return shopInfoMapper.updateShopInfo(shopInfo);
    }

    /**
     * 批量删除店铺信息
     *
     * @param ids 需要删除的店铺信息主键
     * @return 结果
     */
    @Override
    public int deleteShopInfoByIds(Long[] ids) {
        return shopInfoMapper.deleteShopInfoByIds(ids);
    }

    /**
     * 删除店铺信息信息
     *
     * @param id 店铺信息主键
     * @return 结果
     */
    @Override
    public int deleteShopInfoById(Long id) {
        return shopInfoMapper.deleteShopInfoById(id);
    }
}

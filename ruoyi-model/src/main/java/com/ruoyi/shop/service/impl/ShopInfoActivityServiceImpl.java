package com.ruoyi.shop.service.impl;

import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.shop.mapper.ShopInfoActivityMapper;
import com.ruoyi.shop.domain.ShopInfoActivity;
import com.ruoyi.shop.service.IShopInfoActivityService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 店铺信息活动配置Service业务层处理
 *
 * @author ruoyi
 * @date 2024-01-17
 */
@Service
public class ShopInfoActivityServiceImpl implements IShopInfoActivityService {
    @Autowired
    private ShopInfoActivityMapper shopInfoActivityMapper;

    /**
     * 查询店铺信息活动配置
     *
     * @param id 店铺信息活动配置主键
     * @return 店铺信息活动配置
     */
    @Override
    public ShopInfoActivity selectShopInfoActivityById(Long id) {
        return shopInfoActivityMapper.selectShopInfoActivityById(id);
    }

    /**
     * 查询店铺信息活动配置列表
     *
     * @param shopInfoActivity 店铺信息活动配置
     * @return 店铺信息活动配置
     */
    @Override
    public List<ShopInfoActivity> selectShopInfoActivityList(ShopInfoActivity shopInfoActivity) {
        return shopInfoActivityMapper.selectShopInfoActivityList(shopInfoActivity);
    }

    /**
     * 新增店铺信息活动配置
     *
     * @param shopInfoActivityList 店铺信息活动配置
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertShopInfoActivity(List<ShopInfoActivity> shopInfoActivityList) {
        int i = 0;
        if(shopInfoActivityList.size() > 0){
            shopInfoActivityMapper.deleteShopInfoActivityByShopId(shopInfoActivityList.get(0).getShopId());
            for (ShopInfoActivity shopInfoActivity : shopInfoActivityList) {
                shopInfoActivity.setCreateTime(DateUtils.getNowDate());
                shopInfoActivityMapper.insertShopInfoActivity(shopInfoActivity);
                i ++;
            }
        }
        return i;
    }

    /**
     * 修改店铺信息活动配置
     *
     * @param shopInfoActivity 店铺信息活动配置
     * @return 结果
     */
    @Override
    public int updateShopInfoActivity(ShopInfoActivity shopInfoActivity) {
        shopInfoActivity.setUpdateTime(DateUtils.getNowDate());
        return shopInfoActivityMapper.updateShopInfoActivity(shopInfoActivity);
    }

    /**
     * 批量删除店铺信息活动配置
     *
     * @param ids 需要删除的店铺信息活动配置主键
     * @return 结果
     */
    @Override
    public int deleteShopInfoActivityByIds(Long[] ids) {
        return shopInfoActivityMapper.deleteShopInfoActivityByIds(ids);
    }

    /**
     * 删除店铺信息活动配置信息
     *
     * @param id 店铺信息活动配置主键
     * @return 结果
     */
    @Override
    public int deleteShopInfoActivityById(Long id) {
        return shopInfoActivityMapper.deleteShopInfoActivityById(id);
    }
}

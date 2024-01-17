package com.ruoyi.shop.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.time.DateFormatUtil;
import com.ruoyi.shop.domain.Goods;
import com.ruoyi.shop.domain.ShopActivityGoods;
import com.ruoyi.shop.mapper.GoodsSpecsMapper;
import com.ruoyi.shop.mapper.ShopActivityGoodsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.shop.mapper.ShopActivityMapper;
import com.ruoyi.shop.domain.ShopActivity;
import com.ruoyi.shop.service.IShopActivityService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 活动管理Service业务层处理
 *
 * @author ruoyi
 * @date 2024-01-11
 */
@Service
public class ShopActivityServiceImpl implements IShopActivityService {
    @Autowired
    private ShopActivityMapper shopActivityMapper;
    @Autowired
    private ShopActivityGoodsMapper shopActivityGoodsMapper;
    @Autowired
    private GoodsSpecsMapper goodsSpecsMapper;

    /**
     * 查询活动管理
     *
     * @param id 活动管理主键
     * @return 活动管理
     */
    @Override
    public ShopActivity selectShopActivityById(Long id) {
        ShopActivity shopActivity = shopActivityMapper.selectShopActivityById(id);
        if (shopActivity != null) {
            ShopActivityGoods shopActivityGoods = new ShopActivityGoods();
            shopActivityGoods.setActivityId(id);
            List<ShopActivityGoods> activityGoodsList = shopActivityGoodsMapper.selectShopActivityGoodsList(shopActivityGoods);
            if (activityGoodsList.size() > 0) {
                for (ShopActivityGoods activityGoods : activityGoodsList) {
                    if (activityGoods.getGoods() != null) {
                        activityGoods.getGoods().setSpecsList(goodsSpecsMapper.selectGoodsSpecsByGoodId(activityGoods.getGoodsId()));
                    }
                }
            }
            shopActivity.setActivityGoodsList(activityGoodsList);
        }
        return shopActivity;
    }

    /**
     * 查询活动管理列表
     *
     * @param shopActivity 活动管理
     * @return 活动管理
     */
    @Override
    public List<ShopActivity> selectShopActivityList(ShopActivity shopActivity) {
        return selectShopActivityListApi(shopActivity);
    }

    /**
     * 查询活动管理列表
     *
     * @param shopActivity 活动管理
     * @return 活动管理
     */
    @Override
    public List<ShopActivity> selectShopActivityListApi(ShopActivity shopActivity) {
        List<ShopActivity> list = shopActivityMapper.selectShopActivityList(shopActivity);
        if (list.size() > 0) {
            for (ShopActivity activity : list) {
                ShopActivityGoods shopActivityGoods = new ShopActivityGoods();
                shopActivityGoods.setActivityId(activity.getId());
                List<ShopActivityGoods> activityGoodsList = shopActivityGoodsMapper.selectShopActivityGoodsList(shopActivityGoods);
                if (activityGoodsList.size() > 0) {
                    for (ShopActivityGoods activityGoods : activityGoodsList) {
                        if (activityGoods.getGoods() != null) {
                            activityGoods.getGoods().setSpecsList(goodsSpecsMapper.selectGoodsSpecsByGoodId(activityGoods.getGoodsId()));
                        }
                    }
                }
                activity.setActivityGoodsList(activityGoodsList);
            }
        }
        return list;
    }

    @Override
    public JSONObject selectShopActivityListGroup(ShopActivity shopActivity) {
        List<ShopActivity> list = selectShopActivityListApi(shopActivity);
        Map<String, Map<String, List<ShopActivity>>> map = list.stream()
                .collect(Collectors.groupingBy(ShopActivity::getBeginDateString, Collectors.groupingBy(ShopActivity::getShopName)));
        JSONObject jsonObject = new JSONObject();
        for (Map.Entry<String, Map<String, List<ShopActivity>>> entry : map.entrySet()) {
            JSONArray jsonArray = new JSONArray();
            for (Map.Entry<String, List<ShopActivity>> entry2 : entry.getValue().entrySet()) {
                Map<String, Object> data = new HashMap<>(2);
                data.put("name", entry2.getKey());
                data.put("list", entry2.getValue());
                jsonArray.add(data);
            }
            jsonObject.put(entry.getKey(), jsonArray);
        }
        return jsonObject;
    }

    /**
     * 新增活动管理
     *
     * @param shopActivity 活动管理
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertShopActivity(ShopActivity shopActivity) {
        shopActivity.setCreateTime(DateUtils.getNowDate());
        int i = shopActivityMapper.insertShopActivity(shopActivity);
        if (i > 0) {
            if (shopActivity.getActivityGoodsList().size() > 0) {
                for (ShopActivityGoods shopActivityGoods : shopActivity.getActivityGoodsList()) {
                    shopActivityGoods.setActivityId(shopActivity.getId());
                    shopActivityGoods.setCreateTime(DateUtils.getNowDate());
                    shopActivityGoodsMapper.insertShopActivityGoods(shopActivityGoods);
                }
            }
        }
        return i;
    }

    /**
     * 修改活动管理
     *
     * @param shopActivity 活动管理
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateShopActivity(ShopActivity shopActivity) {
        shopActivity.setUpdateTime(DateUtils.getNowDate());
        int i = shopActivityMapper.updateShopActivity(shopActivity);
        if (i > 0) {
            shopActivityGoodsMapper.deleteShopActivityGoodsByActivityId(shopActivity.getId());
            if (shopActivity.getActivityGoodsList().size() > 0) {
                for (ShopActivityGoods shopActivityGoods : shopActivity.getActivityGoodsList()) {
                    shopActivityGoods.setActivityId(shopActivity.getId());
                    shopActivityGoods.setCreateTime(DateUtils.getNowDate());
                    shopActivityGoodsMapper.insertShopActivityGoods(shopActivityGoods);
                }
            }
        }
        return i;
    }

    /**
     * 批量删除活动管理
     *
     * @param ids 需要删除的活动管理主键
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteShopActivityByIds(Long[] ids) {
        shopActivityGoodsMapper.deleteShopActivityGoodsByActivityIds(ids);
        return shopActivityMapper.deleteShopActivityByIds(ids);
    }

    /**
     * 删除活动管理信息
     *
     * @param id 活动管理主键
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteShopActivityById(Long id) {
        shopActivityGoodsMapper.deleteShopActivityGoodsByActivityId(id);
        return shopActivityMapper.deleteShopActivityById(id);
    }
}

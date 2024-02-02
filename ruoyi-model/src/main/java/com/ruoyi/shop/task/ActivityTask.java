package com.ruoyi.shop.task;

import cn.hutool.json.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.time.DateUtil;
import com.ruoyi.framework.websocket.WebSocketServerMessage;
import com.ruoyi.shop.domain.ShopActivity;
import com.ruoyi.shop.domain.ShopActivityGoods;
import com.ruoyi.shop.mapper.ShopActivityGoodsMapper;
import com.ruoyi.shop.mapper.ShopActivityMapper;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author yangxuemin
 * @ClassName ActivityTask
 * @Description
 * @date 2024/2/1 5:22 PM
 */
@Component("activityTask")
public class ActivityTask {
    @Autowired
    private ShopActivityMapper shopActivityMapper;
    @Autowired
    private ShopActivityGoodsMapper shopActivityGoodsMapper;

    public void findActivityMap() {
        ShopActivity shopActivity = new ShopActivity();
        shopActivity.setStatus("1");
        List<ShopActivity> list = shopActivityMapper.selectShopActivityList(shopActivity);
        if (list.size() > 0) {
            JSONArray jsonArray = new JSONArray();
            for (ShopActivity activity : list) {
                Date date = DateUtil.addMinutes(activity.getBeginDate(), activity.getActivityTime().intValue());
                Date now = DateUtils.getNowDate();
                String status;
                if (now.getTime() < activity.getBeginDate().getTime()) {
                    //未开始
                    status = "-1";
                } else if (now.getTime() >= activity.getBeginDate().getTime() && now.getTime() <= date.getTime()) {
                    //进行中
                    status = "0";
                } else {
                    //已结束
                    status = "1";
                }
                if(StringUtils.isBlank(activity.getActivityStatus()) || !StringUtils.equals(activity.getActivityStatus(),status)){
                    activity.setActivityStatus(status);
                    shopActivityMapper.updateShopActivity(activity);
                    ShopActivityGoods shopActivityGoods = new ShopActivityGoods();
                    shopActivityGoods.setActivityId(activity.getId());
                    final List<ShopActivityGoods> shopActivityGoodsList = shopActivityGoodsMapper.selectShopActivityGoodsList(shopActivityGoods);
                    if(shopActivityGoodsList != null && shopActivityGoodsList.size() > 0){
                        for (ShopActivityGoods activityGoods : shopActivityGoodsList) {
                            JSONObject json = new JSONObject();
                            json.put("id", activityGoods.getId());
                            json.put("status", status);
                            jsonArray.add(json);
                        }
                    }
                }
            }
            if(jsonArray.size() > 0){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("name", "shopActivity");
                jsonObject.put("data", jsonArray);
                try {
                    WebSocketServerMessage.sendInfo(jsonObject.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}

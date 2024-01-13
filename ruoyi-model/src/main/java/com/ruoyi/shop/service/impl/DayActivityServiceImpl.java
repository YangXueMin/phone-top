package com.ruoyi.shop.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.shop.domain.DayActivity;
import com.ruoyi.shop.domain.DayActivityGoods;
import com.ruoyi.shop.domain.Goods;
import com.ruoyi.shop.mapper.DayActivityGoodsMapper;
import com.ruoyi.shop.mapper.DayActivityMapper;
import com.ruoyi.shop.mapper.GoodsMapper;
import com.ruoyi.shop.mapper.GoodsSpecsMapper;
import com.ruoyi.shop.service.IDayActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 会员日活动Service业务层处理
 *
 * @author ruoyi
 * @date 2024-01-13
 */
@Service
public class DayActivityServiceImpl implements IDayActivityService {
    @Autowired
    private DayActivityMapper dayActivityMapper;
    @Autowired
    private DayActivityGoodsMapper dayActivityGoodsMapper;
    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private GoodsSpecsMapper goodsSpecsMapper;

    /**
     * 查询会员日活动
     *
     * @param id 会员日活动主键
     * @return 会员日活动
     */
    @Override
    public DayActivity selectDayActivityById(Long id) {
        DayActivity dayActivity = dayActivityMapper.selectDayActivityById(id);
        getGoodsList(dayActivity);
        return dayActivity;
    }

    /**
     * 查询会员日活动列表
     *
     * @param dayActivity 会员日活动
     * @return 会员日活动
     */
    @Override
    public List<DayActivity> selectDayActivityList(DayActivity dayActivity) {
        List<DayActivity> dayActivityList = dayActivityMapper.selectDayActivityList(dayActivity);
        for (DayActivity activity : dayActivityList) {
            getGoodsList(activity);
        }
        return dayActivityList;
    }

    public void getGoodsList(DayActivity dayActivity) {
        List<Long> list = dayActivityGoodsMapper.selectDayActivityGoodsByDayActivityId(dayActivity.getId());
        if (list.size() > 0) {
            List<Goods> goodsList = goodsMapper.selectGoodsListByIdIn(list.toArray(new Long[0]));
            if (goodsList.size() > 0) {
                for (Goods goodsData : goodsList) {
                    goodsData.setSpecsList(goodsSpecsMapper.selectGoodsSpecsByGoodId(goodsData.getId()));
                }
            }
            dayActivity.setGoodsList(goodsList);
        }
    }

    /**
     * 新增会员日活动
     *
     * @param dayActivity 会员日活动
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertDayActivity(DayActivity dayActivity) {
        dayActivity.setCreateTime(DateUtils.getNowDate());
        final int i = dayActivityMapper.insertDayActivity(dayActivity);
        insertActivityGoods(dayActivity);
        return i;
    }

    /**
     * 修改会员日活动
     *
     * @param dayActivity 会员日活动
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateDayActivity(DayActivity dayActivity) {
        dayActivity.setUpdateTime(DateUtils.getNowDate());
        final int i = dayActivityMapper.updateDayActivity(dayActivity);
        dayActivityGoodsMapper.deleteDayActivityGoodsByDayActivityId(dayActivity.getId());
        insertActivityGoods(dayActivity);
        return i;
    }

    /**
     * 新增会员日和商品信息
     *
     * @param dayActivity 会员日对象
     */
    public void insertActivityGoods(DayActivity dayActivity) {
        Long[] goodsIds = dayActivity.getGoodsIds();
        if (StringUtils.isNotEmpty(goodsIds)) {
            // 新增用户与岗位管理
            List<DayActivityGoods> list = new ArrayList<>(goodsIds.length);
            for (Long goodsId : goodsIds) {
                DayActivityGoods dayActivityGoods = new DayActivityGoods();
                dayActivityGoods.setDayActivityId(dayActivity.getId());
                dayActivityGoods.setGoodsId(goodsId);
                list.add(dayActivityGoods);
            }
            dayActivityGoodsMapper.batchDayActivityGoods(list);
        }
    }

    /**
     * 批量删除会员日活动
     *
     * @param ids 需要删除的会员日活动主键
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteDayActivityByIds(Long[] ids) {
        final int i = dayActivityMapper.deleteDayActivityByIds(ids);
        if (i > 0) {
            dayActivityGoodsMapper.deleteDayActivityGoods(ids);
        }
        return i;
    }

    /**
     * 删除会员日活动信息
     *
     * @param id 会员日活动主键
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteDayActivityById(Long id) {
        final int i = dayActivityMapper.deleteDayActivityById(id);
        if (i > 0) {
            dayActivityGoodsMapper.deleteDayActivityGoodsByDayActivityId(id);
        }
        return i;
    }
}

package com.ruoyi.shop.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.shop.domain.DayActivity;
import com.ruoyi.shop.mapper.DayActivityMapper;
import com.ruoyi.shop.service.IDayActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 会员日活动Service业务层处理
 *
 * @author ruoyi
 * @date 2024-01-12
 */
@Service
public class DayActivityServiceImpl implements IDayActivityService {
    @Autowired
    private DayActivityMapper dayActivityMapper;

    /**
     * 查询会员日活动
     *
     * @param id 会员日活动主键
     * @return 会员日活动
     */
    @Override
    public DayActivity selectDayActivityById(Long id) {
        return dayActivityMapper.selectDayActivityById(id);
    }

    /**
     * 查询会员日活动列表
     *
     * @param dayActivity 会员日活动
     * @return 会员日活动
     */
    @Override
    public List<DayActivity> selectDayActivityList(DayActivity dayActivity) {
        return dayActivityMapper.selectDayActivityList(dayActivity);
    }

    /**
     * 新增会员日活动
     *
     * @param dayActivity 会员日活动
     * @return 结果
     */
    @Override
    public int insertDayActivity(DayActivity dayActivity) {
        dayActivity.setCreateTime(DateUtils.getNowDate());
        return dayActivityMapper.insertDayActivity(dayActivity);
    }

    /**
     * 修改会员日活动
     *
     * @param dayActivity 会员日活动
     * @return 结果
     */
    @Override
    public int updateDayActivity(DayActivity dayActivity) {
        dayActivity.setUpdateTime(DateUtils.getNowDate());
        return dayActivityMapper.updateDayActivity(dayActivity);
    }

    /**
     * 批量删除会员日活动
     *
     * @param ids 需要删除的会员日活动主键
     * @return 结果
     */
    @Override
    public int deleteDayActivityByIds(Long[] ids) {
        return dayActivityMapper.deleteDayActivityByIds(ids);
    }

    /**
     * 删除会员日活动信息
     *
     * @param id 会员日活动主键
     * @return 结果
     */
    @Override
    public int deleteDayActivityById(Long id) {
        return dayActivityMapper.deleteDayActivityById(id);
    }
}

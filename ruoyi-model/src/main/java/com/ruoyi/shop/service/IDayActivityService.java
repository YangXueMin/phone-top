package com.ruoyi.shop.service;

import java.util.List;

import com.ruoyi.shop.domain.DayActivity;

/**
 * 会员日活动Service接口
 *
 * @author ruoyi
 * @date 2024-01-13
 */
public interface IDayActivityService {
    /**
     * 查询会员日活动
     *
     * @param id 会员日活动主键
     * @return 会员日活动
     */
    public DayActivity selectDayActivityById(Long id);

    /**
     * 查询会员日活动列表
     *
     * @param dayActivity 会员日活动
     * @return 会员日活动集合
     */
    public List<DayActivity> selectDayActivityList(DayActivity dayActivity);

    /**
     * 是否会员日
     *
     * @param shopId
     * @return
     */
    boolean isHoliday(Long shopId);

    /**
     * 新增会员日活动
     *
     * @param dayActivity 会员日活动
     * @return 结果
     */
    public int insertDayActivity(DayActivity dayActivity);

    /**
     * 修改会员日活动
     *
     * @param dayActivity 会员日活动
     * @return 结果
     */
    public int updateDayActivity(DayActivity dayActivity);

    /**
     * 批量删除会员日活动
     *
     * @param ids 需要删除的会员日活动主键集合
     * @return 结果
     */
    public int deleteDayActivityByIds(Long[] ids);

    /**
     * 删除会员日活动信息
     *
     * @param id 会员日活动主键
     * @return 结果
     */
    public int deleteDayActivityById(Long id);
}

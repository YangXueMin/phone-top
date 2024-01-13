package com.ruoyi.shop.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import com.ruoyi.shop.domain.DayActivity;

/**
 * 会员日活动Mapper接口
 *
 * @author ruoyi
 * @date 2024-01-13
 */
@Mapper
public interface DayActivityMapper {
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
     * 删除会员日活动
     *
     * @param id 会员日活动主键
     * @return 结果
     */
    public int deleteDayActivityById(Long id);

    /**
     * 批量删除会员日活动
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDayActivityByIds(Long[] ids);
}

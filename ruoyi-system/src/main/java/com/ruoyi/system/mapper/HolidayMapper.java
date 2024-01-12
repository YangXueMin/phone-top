package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.Holiday;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author yangxuemin
 * @ClassName HolidayMapper
 * @Description
 * @date 2024/1/12 6:59 PM
 */
@Mapper
public interface HolidayMapper {
    /**
     * 根据日期查询节假日
     *
     * @param day 日期
     * @return 节假日
     */
    public Holiday selectHolidayById(String day);

    /**
     * 删除数据
     *
     * @return 结果
     */
    public int deleteAll();

    /**
     * 批量添加数据
     *
     * @param holidayList
     * @return
     */
    public int insertAll(List<Holiday> holidayList);
}

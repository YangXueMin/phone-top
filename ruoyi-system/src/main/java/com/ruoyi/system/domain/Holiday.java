package com.ruoyi.system.domain;

import lombok.ToString;

/**
 * @author yangxuemin
 * @ClassName Holiday
 * @Description
 * @date 2024/1/12 6:58 PM
 */
@ToString
public class Holiday {
    /**
     * 日期（格式为：yyyy-MM-dd）
     */
    private String date;
    /**
     * 节假日名称
     */
    private String name;
    /**
     * 是否节假日
     */
    private boolean holiday;

    /**
     * 权重
     */
    private String wage;

    public Holiday() {
    }

    public Holiday(String date, String name, boolean holiday, String wage) {
        this.date = date;
        this.name = name;
        this.holiday = holiday;
        this.wage = wage;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isHoliday() {
        return holiday;
    }

    public void setHoliday(boolean holiday) {
        this.holiday = holiday;
    }

    public String getWage() {
        return wage;
    }

    public void setWage(String wage) {
        this.wage = wage;
    }
}

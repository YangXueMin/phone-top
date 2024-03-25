package com.ruoyi.common.core.domain.entity;

import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.common.utils.time.DateUtil;
import lombok.ToString;

import java.util.Date;

/**
 * @author yangxuemin
 * @ClassName DataRequest
 * @Description
 * @date 2024/3/25 4:13 PM
 */
@ToString
public class DataRequest extends BaseEntity {

    /**
     * APPID
     */
    private String appId;

    /**
     * 小程序名称
     */
    private String title;

    /**
     * 查询开始时间
     */
    private Date beginDate;

    /**
     * 查询结束时间
     */
    private Date endDate;


    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getBeginDate() {
        if (beginDate != null) {
            return DateUtil.beginOfDate(beginDate);
        }
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        if (endDate != null) {
            return DateUtil.endOfDate(endDate);
        }
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}

package com.ruoyi.system.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.utils.DictUtils;
import lombok.ToString;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.StringUtils;

/**
 * 后台卡密管理对象 sys_card
 *
 * @author ruoyi
 * @date 2024-03-13
 */
@ToString
public class SysCard extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 公司ID
     */
    @Excel(name = "公司ID")
    private Long deptId;

    /**
     * appId
     */
    @Excel(name = "appId")
    private String appId;

    /**
     * 卡密
     */
    @Excel(name = "卡密")
    private String cardNo;

    /**
     * 有效时长
     */
    @Excel(name = "有效时长")
    private Integer timeSpan;

    /**
     * 状态
     */
    @Excel(name = "有效状态（字典：phone_status）")
    private String status;

    /**
     * 状态
     */
    @Excel(name = "有效状态（字典：phone_status）")
    private String statusLabel;

    /**
     * 核销状态
     */
    @Excel(name = "核销状态（字典：phone_cancel_status）")
    private String cancelStatus;

    /**
     * 核销状态
     */
    @Excel(name = "核销状态（字典：phone_cancel_status）")
    private String cancelStatusLabel;

    /**
     * 核销时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "核销时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date cancelTime;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppId() {
        return appId;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setTimeSpan(Integer timeSpan) {
        this.timeSpan = timeSpan;
    }

    public Integer getTimeSpan() {
        return timeSpan;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setCancelStatus(String cancelStatus) {
        this.cancelStatus = cancelStatus;
    }

    public String getCancelStatus() {
        return cancelStatus;
    }

    public void setCancelTime(Date cancelTime) {
        this.cancelTime = cancelTime;
    }

    public Date getCancelTime() {
        return cancelTime;
    }

    public String getStatusLabel() {
        if (StringUtils.isNotBlank(status)) {
            return DictUtils.getDictLabel("phone_status", status);
        }
        return statusLabel;
    }

    public void setStatusLabel(String statusLabel) {
        this.statusLabel = statusLabel;
    }

    public String getCancelStatusLabel() {
        if (StringUtils.isNotBlank(cancelStatus)) {
            return DictUtils.getDictLabel("phone_cancel_status", cancelStatus);
        }
        return cancelStatusLabel;
    }

    public void setCancelStatusLabel(String cancelStatusLabel) {
        this.cancelStatusLabel = cancelStatusLabel;
    }
}

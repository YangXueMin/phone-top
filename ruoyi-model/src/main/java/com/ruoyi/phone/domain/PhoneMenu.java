package com.ruoyi.phone.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.utils.DictUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.StringUtils;

/**
 * 菜单管理对象 phone_menu
 *
 * @author ruoyi
 * @date 2024-04-11
 */
@ApiModel(value = "PhoneMenu", description = "菜单管理")
@ToString
public class PhoneMenu extends BaseEntity{
    private static final long serialVersionUID=1L;

    /** 主键 */
    private Long id;

    /** 菜单名称 */
    @Excel(name = "菜单名称")
    @ApiModelProperty("菜单名称")
    private String name;

    /** 菜单路径 */
    @Excel(name = "菜单路径")
    @ApiModelProperty("菜单路径")
    private String url;

    /** banner */
    @Excel(name = "banner")
    @ApiModelProperty("banner")
    private String banner;

    /**
     * 是否启用 字典：phone_status
     */
    @Excel(name = "是否启用 字典：phone_status")
    private String status;

    /**
     * 是否启用 字典：phone_status
     */
    @Excel(name = "是否启用 字典：phone_status")
    private String statusLabel;

    public void setId(Long id){
        this.id = id;
    }

    public Long getId(){
        return id;
    }
    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }
    public void setUrl(String url){
        this.url = url;
    }

    public String getUrl(){
        return url;
    }
    public void setBanner(String banner){
        this.banner = banner;
    }

    public String getBanner(){
        return banner;
    }
    public void setStatus(String status){
        this.status = status;
    }

    public String getStatus(){
        return status;
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

}

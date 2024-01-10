package com.ruoyi.shop.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yangxuemin
 * @ClassName ShopTreeSelect
 * @Description
 * @date 2024/1/5 3:16 PM
 */
public class ShopTreeSelect implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 节点ID
     */
    private Long id;

    /**
     * 节点名称
     */
    private String label;

    /**
     * 子节点
     */
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<ShopTreeSelect> children;

    public ShopTreeSelect() {

    }

    public ShopTreeSelect(GoodsClassify goodsClassify) {
        this.id = goodsClassify.getClassId();
        this.label = goodsClassify.getName();
        this.children = goodsClassify.getChildren().stream().map(ShopTreeSelect::new).collect(Collectors.toList());
    }

    public ShopTreeSelect(ContentType contentType) {
        this.id = contentType.getTypeId();
        this.label = contentType.getName();
        this.children = contentType.getChildren().stream().map(ShopTreeSelect::new).collect(Collectors.toList());
    }

    public ShopTreeSelect(BannerType bannerType) {
        this.id = bannerType.getTypeId();
        this.label = bannerType.getName();
        this.children = bannerType.getChildren().stream().map(ShopTreeSelect::new).collect(Collectors.toList());
    }

    public ShopTreeSelect(Column column) {
        this.id = column.getId();
        this.label = column.getColumnName();
        this.children = column.getChildren().stream().map(ShopTreeSelect::new).collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<ShopTreeSelect> getChildren() {
        return children;
    }

    public void setChildren(List<ShopTreeSelect> children) {
        this.children = children;
    }
}

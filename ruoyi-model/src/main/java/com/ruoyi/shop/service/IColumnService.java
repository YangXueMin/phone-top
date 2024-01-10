package com.ruoyi.shop.service;

import java.util.List;
import com.ruoyi.shop.domain.Column;
import com.ruoyi.shop.domain.ContentType;
import com.ruoyi.shop.domain.ShopTreeSelect;

/**
 * 栏目设置Service接口
 *
 * @author ruoyi
 * @date 2024-01-05
 */
public interface IColumnService
{
    /**
     * 查询栏目设置
     *
     * @param id 栏目设置主键
     * @return 栏目设置
     */
    public Column selectColumnById(Long id);

    /**
     * 查询栏目设置列表
     *
     * @param column 栏目设置
     * @return 栏目设置集合
     */
    public List<Column> selectColumnList(Column column);

    /**
     * 查询栏目树结构信息
     *
     * @param column 栏目
     * @return 栏目集合
     */
    public List<ShopTreeSelect> selectTreeList(Column column);

    /**
     * 构建前端所需要下拉树结构
     *
     * @param columnList 栏目表
     * @return 下拉树结构列表
     */
    public List<ShopTreeSelect> buildTreeSelect(List<Column> columnList);

    /**
     * 构建前端所需要树结构
     *
     * @param columnList 栏目表
     * @return 树结构列表
     */
    public List<Column> buildTree(List<Column> columnList);

    /**
     * 新增栏目设置
     *
     * @param column 栏目设置
     * @return 结果
     */
    public int insertColumn(Column column);

    /**
     * 修改栏目设置
     *
     * @param column 栏目设置
     * @return 结果
     */
    public int updateColumn(Column column);

    /**
     * 批量删除栏目设置
     *
     * @param ids 需要删除的栏目设置主键集合
     * @return 结果
     */
    public int deleteColumnByIds(Long[] ids);

    /**
     * 删除栏目设置信息
     *
     * @param id 栏目设置主键
     * @return 结果
     */
    public int deleteColumnById(Long id);
}

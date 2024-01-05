package com.ruoyi.shop.service;

import java.util.List;
import com.ruoyi.shop.domain.Column;

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

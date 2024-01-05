package com.ruoyi.shop.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.shop.mapper.ColumnMapper;
import com.ruoyi.shop.domain.Column;
import com.ruoyi.shop.service.IColumnService;

/**
 * 栏目设置Service业务层处理
 *
 * @author ruoyi
 * @date 2024-01-05
 */
@Service
public class ColumnServiceImpl implements IColumnService
{
    @Autowired
    private ColumnMapper columnMapper;

    /**
     * 查询栏目设置
     *
     * @param id 栏目设置主键
     * @return 栏目设置
     */
    @Override
    public Column selectColumnById(Long id)
    {
        return columnMapper.selectColumnById(id);
    }

    /**
     * 查询栏目设置列表
     *
     * @param column 栏目设置
     * @return 栏目设置
     */
    @Override
    public List<Column> selectColumnList(Column column)
    {
        return columnMapper.selectColumnList(column);
    }

    /**
     * 新增栏目设置
     *
     * @param column 栏目设置
     * @return 结果
     */
    @Override
    public int insertColumn(Column column)
    {
        column.setCreateTime(DateUtils.getNowDate());
        return columnMapper.insertColumn(column);
    }

    /**
     * 修改栏目设置
     *
     * @param column 栏目设置
     * @return 结果
     */
    @Override
    public int updateColumn(Column column)
    {
        column.setUpdateTime(DateUtils.getNowDate());
        return columnMapper.updateColumn(column);
    }

    /**
     * 批量删除栏目设置
     *
     * @param ids 需要删除的栏目设置主键
     * @return 结果
     */
    @Override
    public int deleteColumnByIds(Long[] ids)
    {
        return columnMapper.deleteColumnByIds(ids);
    }

    /**
     * 删除栏目设置信息
     *
     * @param id 栏目设置主键
     * @return 结果
     */
    @Override
    public int deleteColumnById(Long id)
    {
        return columnMapper.deleteColumnById(id);
    }
}

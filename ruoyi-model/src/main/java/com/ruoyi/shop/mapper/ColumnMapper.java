package com.ruoyi.shop.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.ruoyi.shop.domain.Column;

/**
 * 栏目设置Mapper接口
 *
 * @author ruoyi
 * @date 2024-01-05
 */
@Mapper
public interface ColumnMapper
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
     * 删除栏目设置
     *
     * @param id 栏目设置主键
     * @return 结果
     */
    public int deleteColumnById(Long id);

    /**
     * 批量删除栏目设置
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteColumnByIds(Long[] ids);
}

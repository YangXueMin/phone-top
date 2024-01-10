package com.ruoyi.shop.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.shop.domain.Column;
import com.ruoyi.shop.domain.ShopTreeSelect;
import com.ruoyi.shop.mapper.ColumnMapper;
import com.ruoyi.shop.service.IColumnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 栏目设置Service业务层处理
 *
 * @author ruoyi
 * @date 2024-01-05
 */
@Service
public class ColumnServiceImpl implements IColumnService {
    @Autowired
    private ColumnMapper columnMapper;

    /**
     * 查询栏目设置
     *
     * @param id 栏目设置主键
     * @return 栏目设置
     */
    @Override
    public Column selectColumnById(Long id) {
        return columnMapper.selectColumnById(id);
    }

    /**
     * 查询栏目设置列表
     *
     * @param column 栏目设置
     * @return 栏目设置
     */
    @Override
    public List<Column> selectColumnList(Column column) {
        return columnMapper.selectColumnList(column);
    }

    @Override
    public List<ShopTreeSelect> selectTreeList(Column column) {
        List<Column> columnList = SpringUtils.getAopProxy(this).selectColumnList(column);
        return buildTreeSelect(columnList);
    }

    @Override
    public List<ShopTreeSelect> buildTreeSelect(List<Column> columnList) {
        List<Column> typeTrees = buildTree(columnList);
        return typeTrees.stream().map(ShopTreeSelect::new).collect(Collectors.toList());
    }

    @Override
    public List<Column> buildTree(List<Column> columnList) {
        List<Column> returnList = new ArrayList<>();
        List<Long> tempList = columnList.stream().map(Column::getId).collect(Collectors.toList());
        for (Column column : columnList) {
            // 如果是顶级节点, 遍历该父节点的所有子节点
            if (!tempList.contains(column.getParentId())) {
                recursionFn(columnList, column);
                returnList.add(column);
            }
        }
        if (returnList.isEmpty()) {
            returnList = columnList;
        }
        return returnList;
    }

    /**
     * 新增栏目设置
     *
     * @param column 栏目设置
     * @return 结果
     */
    @Override
    public int insertColumn(Column column) {
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
    public int updateColumn(Column column) {
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
    public int deleteColumnByIds(Long[] ids) {
        return columnMapper.deleteColumnByIds(ids);
    }

    /**
     * 删除栏目设置信息
     *
     * @param id 栏目设置主键
     * @return 结果
     */
    @Override
    public int deleteColumnById(Long id) {
        return columnMapper.deleteColumnById(id);
    }

    /**
     * 递归列表
     */
    private void recursionFn(List<Column> list, Column t) {
        // 得到子节点列表
        List<Column> childList = getChildList(list, t);
        t.setChildren(childList);
        for (Column column : childList) {
            if (hasChild(list, column)) {
                recursionFn(list, column);
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<Column> getChildList(List<Column> list, Column t) {
        List<Column> tlist = new ArrayList<>();
        Iterator<Column> it = list.iterator();
        while (it.hasNext()) {
            Column n = it.next();
            if (StringUtils.isNotNull(n.getParentId()) && n.getParentId().longValue() == t.getId().longValue()) {
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<Column> list, Column t) {
        return getChildList(list, t).size() > 0;
    }
}

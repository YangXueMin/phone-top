package com.ruoyi.shop.service;

import java.util.List;
import com.ruoyi.shop.domain.ContentType;
import com.ruoyi.shop.domain.GoodsClassify;
import com.ruoyi.shop.domain.ShopTreeSelect;

/**
 * 内容分类Service接口
 *
 * @author ruoyi
 * @date 2024-01-07
 */
public interface IContentTypeService
{
    /**
     * 查询内容分类
     *
     * @param typeId 内容分类主键
     * @return 内容分类
     */
    public ContentType selectContentTypeByTypeId(Long typeId);

    /**
     * 查询内容分类列表
     *
     * @param contentType 内容分类
     * @return 内容分类集合
     */
    public List<ContentType> selectContentTypeList(ContentType contentType);

    /**
     * 查询内容分类树结构信息
     *
     * @param contentType 内容分类
     * @return 内容分类信息集合
     */
    public List<ShopTreeSelect> selectTreeList(ContentType contentType);

    /**
     * 构建前端所需要下拉树结构
     *
     * @param typeList 内容分类表
     * @return 下拉树结构列表
     */
    public List<ShopTreeSelect> buildTreeSelect(List<ContentType> typeList);

    /**
     * 构建前端所需要树结构
     *
     * @param typeList 内容分类表
     * @return 树结构列表
     */
    public List<ContentType> buildTree(List<ContentType> typeList);

    /**
     * 新增内容分类
     *
     * @param contentType 内容分类
     * @return 结果
     */
    public int insertContentType(ContentType contentType);

    /**
     * 修改内容分类
     *
     * @param contentType 内容分类
     * @return 结果
     */
    public int updateContentType(ContentType contentType);

    /**
     * 批量删除内容分类
     *
     * @param typeIds 需要删除的内容分类主键集合
     * @return 结果
     */
    public int deleteContentTypeByTypeIds(Long[] typeIds);

    /**
     * 删除内容分类信息
     *
     * @param typeId 内容分类主键
     * @return 结果
     */
    public int deleteContentTypeByTypeId(Long typeId);
}

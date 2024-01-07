package com.ruoyi.shop.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.ruoyi.shop.domain.ContentType;

/**
 * 内容分类Mapper接口
 *
 * @author ruoyi
 * @date 2024-01-07
 */
@Mapper
public interface ContentTypeMapper
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
     * 删除内容分类
     *
     * @param typeId 内容分类主键
     * @return 结果
     */
    public int deleteContentTypeByTypeId(Long typeId);

    /**
     * 批量删除内容分类
     *
     * @param typeIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteContentTypeByTypeIds(Long[] typeIds);
}

package com.ruoyi.shop.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.shop.domain.ContentType;
import com.ruoyi.shop.domain.ShopTreeSelect;
import com.ruoyi.shop.mapper.ContentTypeMapper;
import com.ruoyi.shop.service.IContentTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 内容分类Service业务层处理
 *
 * @author ruoyi
 * @date 2024-01-07
 */
@Service
public class ContentTypeServiceImpl implements IContentTypeService {
    @Autowired
    private ContentTypeMapper contentTypeMapper;

    /**
     * 查询内容分类
     *
     * @param typeId 内容分类主键
     * @return 内容分类
     */
    @Override
    public ContentType selectContentTypeByTypeId(Long typeId) {
        return contentTypeMapper.selectContentTypeByTypeId(typeId);
    }

    /**
     * 查询内容分类列表
     *
     * @param contentType 内容分类
     * @return 内容分类
     */
    @Override
    public List<ContentType> selectContentTypeList(ContentType contentType) {
        return contentTypeMapper.selectContentTypeList(contentType);
    }

    @Override
    public List<ShopTreeSelect> selectTreeList(ContentType contentType) {
        List<ContentType> typeList = SpringUtils.getAopProxy(this).selectContentTypeList(contentType);
        return buildTreeSelect(typeList);
    }

    @Override
    public List<ShopTreeSelect> buildTreeSelect(List<ContentType> typeList) {
        List<ContentType> typeTrees = buildTree(typeList);
        return typeTrees.stream().map(ShopTreeSelect::new).collect(Collectors.toList());
    }

    @Override
    public List<ContentType> buildTree(List<ContentType> typeList) {
        List<ContentType> returnList = new ArrayList<>();
        List<Long> tempList = typeList.stream().map(ContentType::getTypeId).collect(Collectors.toList());
        for (ContentType contentType : typeList) {
            // 如果是顶级节点, 遍历该父节点的所有子节点
            if (!tempList.contains(contentType.getParentId())) {
                recursionFn(typeList, contentType);
                returnList.add(contentType);
            }
        }
        if (returnList.isEmpty()) {
            returnList = typeList;
        }
        return returnList;
    }

    /**
     * 新增内容分类
     *
     * @param contentType 内容分类
     * @return 结果
     */
    @Override
    public int insertContentType(ContentType contentType) {
        contentType.setCreateTime(DateUtils.getNowDate());
        return contentTypeMapper.insertContentType(contentType);
    }

    /**
     * 修改内容分类
     *
     * @param contentType 内容分类
     * @return 结果
     */
    @Override
    public int updateContentType(ContentType contentType) {
        contentType.setUpdateTime(DateUtils.getNowDate());
        return contentTypeMapper.updateContentType(contentType);
    }

    /**
     * 批量删除内容分类
     *
     * @param typeIds 需要删除的内容分类主键
     * @return 结果
     */
    @Override
    public int deleteContentTypeByTypeIds(Long[] typeIds) {
        return contentTypeMapper.deleteContentTypeByTypeIds(typeIds);
    }

    /**
     * 删除内容分类信息
     *
     * @param typeId 内容分类主键
     * @return 结果
     */
    @Override
    public int deleteContentTypeByTypeId(Long typeId) {
        return contentTypeMapper.deleteContentTypeByTypeId(typeId);
    }

    /**
     * 递归列表
     */
    private void recursionFn(List<ContentType> list, ContentType t) {
        // 得到子节点列表
        List<ContentType> childList = getChildList(list, t);
        t.setChildren(childList);
        for (ContentType contentType : childList) {
            if (hasChild(list, contentType)) {
                recursionFn(list, contentType);
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<ContentType> getChildList(List<ContentType> list, ContentType t) {
        List<ContentType> tlist = new ArrayList<>();
        Iterator<ContentType> it = list.iterator();
        while (it.hasNext()) {
            ContentType n = it.next();
            if (StringUtils.isNotNull(n.getParentId()) && n.getParentId().longValue() == t.getTypeId().longValue()) {
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<ContentType> list, ContentType t) {
        return getChildList(list, t).size() > 0;
    }
}

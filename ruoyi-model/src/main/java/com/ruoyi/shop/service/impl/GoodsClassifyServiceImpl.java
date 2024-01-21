package com.ruoyi.shop.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.domain.TreeSelect;
import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.shop.domain.ShopTreeSelect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.shop.mapper.GoodsClassifyMapper;
import com.ruoyi.shop.domain.GoodsClassify;
import com.ruoyi.shop.service.IGoodsClassifyService;

/**
 * 商品分类Service业务层处理
 *
 * @author ruoyi
 * @date 2024-01-05
 */
@Service
public class GoodsClassifyServiceImpl implements IGoodsClassifyService {
    @Autowired
    private GoodsClassifyMapper goodsClassifyMapper;

    /**
     * 查询商品分类
     *
     * @param classId 商品分类主键
     * @return 商品分类
     */
    @Override
    public GoodsClassify selectGoodsClassifyByClassId(Long classId) {
        return goodsClassifyMapper.selectGoodsClassifyByClassId(classId);
    }

    /**
     * 查询商品分类列表
     *
     * @param goodsClassify 商品分类
     * @return 商品分类
     */
    @Override
    public List<GoodsClassify> selectGoodsClassifyList(GoodsClassify goodsClassify) {
        return goodsClassifyMapper.selectGoodsClassifyList(goodsClassify);
    }

    @Override
    public List<ShopTreeSelect> selectShopTreeList(GoodsClassify goodsClassify) {
        List<GoodsClassify> classifyList = SpringUtils.getAopProxy(this).selectGoodsClassifyList(goodsClassify);
        return buildClassifyTreeSelect(classifyList);
    }

    @Override
    public List<ShopTreeSelect> buildClassifyTreeSelect(List<GoodsClassify> classifyList) {
        List<GoodsClassify> classifyTrees = buildClassifyTree(classifyList);
        return classifyTrees.stream().map(ShopTreeSelect::new).collect(Collectors.toList());
    }

    /**
     * 构建前端所需要树结构
     *
     * @param classifyList 商品分类列表
     * @return 树结构列表
     */
    @Override
    public List<GoodsClassify> buildClassifyTree(List<GoodsClassify> classifyList) {
        List<GoodsClassify> returnList = new ArrayList<>();
        List<Long> tempList = classifyList.stream().map(GoodsClassify::getClassId).collect(Collectors.toList());
        for (GoodsClassify classify : classifyList) {
            // 如果是顶级节点, 遍历该父节点的所有子节点
            if (!tempList.contains(classify.getParentId())) {
                recursionFn(classifyList, classify);
                returnList.add(classify);
            }
        }
        if (returnList.isEmpty()) {
            returnList = classifyList;
        }
        return returnList;
    }

    @Override
    public boolean hasChildByClassId(Long classId) {
        int result = goodsClassifyMapper.hasChildByClassId(classId);
        return result > 0;
    }

    @Override
    public String checkNameUnique(GoodsClassify goodsClassify) {
        Long deptId = StringUtils.isNull(goodsClassify.getClassId()) ? -1L : goodsClassify.getClassId();
        SysDept info = goodsClassifyMapper.checkNameUnique(goodsClassify.getName(), goodsClassify.getParentId());
        if (StringUtils.isNotNull(info) && info.getDeptId().longValue() != deptId.longValue()) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    /**
     * 新增商品分类
     *
     * @param goodsClassify 商品分类
     * @return 结果
     */
    @Override
    public int insertGoodsClassify(GoodsClassify goodsClassify) {
        goodsClassify.setCreateTime(DateUtils.getNowDate());
        if(goodsClassify.getParentId() != null){
            GoodsClassify info = goodsClassifyMapper.selectGoodsClassifyByClassId(goodsClassify.getParentId());
            goodsClassify.setAncestors(info.getAncestors() + "," + goodsClassify.getParentId());
        }else {
            goodsClassify.setParentId(0L);
            goodsClassify.setAncestors("0");
        }
        return goodsClassifyMapper.insertGoodsClassify(goodsClassify);
    }

    /**
     * 修改商品分类
     *
     * @param goodsClassify 商品分类
     * @return 结果
     */
    @Override
    public int updateGoodsClassify(GoodsClassify goodsClassify) {
        goodsClassify.setUpdateTime(DateUtils.getNowDate());
        return goodsClassifyMapper.updateGoodsClassify(goodsClassify);
    }

    /**
     * 批量删除商品分类
     *
     * @param classIds 需要删除的商品分类主键
     * @return 结果
     */
    @Override
    public int deleteGoodsClassifyByClassIds(Long[] classIds) {
        return goodsClassifyMapper.deleteGoodsClassifyByClassIds(classIds);
    }

    /**
     * 删除商品分类信息
     *
     * @param classId 商品分类主键
     * @return 结果
     */
    @Override
    public int deleteGoodsClassifyByClassId(Long classId) {
        return goodsClassifyMapper.deleteGoodsClassifyByClassId(classId);
    }

    /**
     * 查询分类是否存在商品
     *
     * @param classId 分类ID
     * @return 结果 true 存在 false 不存在
     */
    @Override
    public boolean checkClassifyExistGoods(Long classId) {
        int result = goodsClassifyMapper.checkClassifyExistGoods(classId);
        return result > 0;
    }

    /**
     * 查询分类是否存在企业商品
     *
     * @param classId 分类ID
     * @return 结果 true 存在 false 不存在
     */
    @Override
    public boolean checkClassifyExistCompanyGoods(Long classId) {
        int result = goodsClassifyMapper.checkClassifyExistCompanyGoods(classId);
        return result > 0;
    }

    /**
     * 递归列表
     */
    private void recursionFn(List<GoodsClassify> list, GoodsClassify t) {
        // 得到子节点列表
        List<GoodsClassify> childList = getChildList(list, t);
        t.setChildren(childList);
        for (GoodsClassify tChild : childList) {
            if (hasChild(list, tChild)) {
                recursionFn(list, tChild);
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<GoodsClassify> getChildList(List<GoodsClassify> list, GoodsClassify t) {
        List<GoodsClassify> tlist = new ArrayList<>();
        Iterator<GoodsClassify> it = list.iterator();
        while (it.hasNext()) {
            GoodsClassify n = it.next();
            if (StringUtils.isNotNull(n.getParentId()) && n.getParentId().longValue() == t.getClassId().longValue()) {
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<GoodsClassify> list, GoodsClassify t) {
        return getChildList(list, t).size() > 0;
    }
}

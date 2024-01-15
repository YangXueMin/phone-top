package com.ruoyi.shop.service;

import java.util.List;

import com.ruoyi.common.core.domain.TreeSelect;
import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.shop.domain.GoodsClassify;
import com.ruoyi.shop.domain.ShopTreeSelect;

/**
 * 商品分类Service接口
 *
 * @author ruoyi
 * @date 2024-01-05
 */
public interface IGoodsClassifyService
{
    /**
     * 查询商品分类
     *
     * @param classId 商品分类主键
     * @return 商品分类
     */
    public GoodsClassify selectGoodsClassifyByClassId(Long classId);

    /**
     * 查询商品分类列表
     *
     * @param goodsClassify 商品分类
     * @return 商品分类集合
     */
    public List<GoodsClassify> selectGoodsClassifyList(GoodsClassify goodsClassify);

    /**
     * 查询商品分类树结构信息
     *
     * @param goodsClassify 商品分类
     * @return 部门树信息集合
     */
    public List<ShopTreeSelect> selectShopTreeList(GoodsClassify goodsClassify);

    /**
     * 构建前端所需要下拉树结构
     *
     * @param classifyList 商品分类列表
     * @return 下拉树结构列表
     */
    public List<ShopTreeSelect> buildClassifyTreeSelect(List<GoodsClassify> classifyList);

    /**
     * 构建前端所需要树结构
     *
     * @param classifyList 商品分类列表
     * @return 树结构列表
     */
    public List<GoodsClassify> buildClassifyTree(List<GoodsClassify> classifyList);

    /**
     * 是否存在部门子节点
     *
     * @param classId 分类ID
     * @return 结果
     */
    public boolean hasChildByClassId(Long classId);

    /**
     * 校验名称是否唯一
     *
     * @param goodsClassify 分类信息
     * @return 结果
     */
    public String checkNameUnique(GoodsClassify goodsClassify);

    /**
     * 新增商品分类
     *
     * @param goodsClassify 商品分类
     * @return 结果
     */
    public int insertGoodsClassify(GoodsClassify goodsClassify);

    /**
     * 修改商品分类
     *
     * @param goodsClassify 商品分类
     * @return 结果
     */
    public int updateGoodsClassify(GoodsClassify goodsClassify);

    /**
     * 批量删除商品分类
     *
     * @param classIds 需要删除的商品分类主键集合
     * @return 结果
     */
    public int deleteGoodsClassifyByClassIds(Long[] classIds);

    /**
     * 删除商品分类信息
     *
     * @param classId 商品分类主键
     * @return 结果
     */
    public int deleteGoodsClassifyByClassId(Long classId);

    /**
     * 查询分类是否存在商品
     *
     * @param classId 分类ID
     * @return 结果 true 存在 false 不存在
     */
    public boolean checkClassifyExistGoods(Long classId);

    /**
     * 查询分类是否存在企业商品
     *
     * @param classId 分类ID
     * @return 结果 true 存在 false 不存在
     */
    public boolean checkClassifyExistCompanyGoods(Long classId);
}

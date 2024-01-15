package com.ruoyi.shop.mapper;

import java.util.List;

import com.ruoyi.common.core.domain.entity.SysDept;
import org.apache.ibatis.annotations.Mapper;
import com.ruoyi.shop.domain.GoodsClassify;
import org.apache.ibatis.annotations.Param;

/**
 * 商品分类Mapper接口
 *
 * @author ruoyi
 * @date 2024-01-05
 */
@Mapper
public interface GoodsClassifyMapper
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
     * 是否存在子节点
     *
     * @param classId 分类ID
     * @return 结果
     */
    public int hasChildByClassId(Long classId);

    /**
     * 校验名称是否唯一
     *
     * @param name 名称
     * @param parentId 父部门ID
     * @return 结果
     */
    public SysDept checkNameUnique(@Param("name") String name, @Param("parentId") Long parentId);

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
     * 删除商品分类
     *
     * @param classId 商品分类主键
     * @return 结果
     */
    public int deleteGoodsClassifyByClassId(Long classId);

    /**
     * 查询分类是否存在商品
     * @param classId
     * @return
     */
    int checkClassifyExistGoods(Long classId);

    /**
     * 查询分类是否存在企业商品
     * @param classId
     * @return
     */
    int checkClassifyExistCompanyGoods(Long classId);

    /**
     * 批量删除商品分类
     *
     * @param classIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteGoodsClassifyByClassIds(Long[] classIds);
}

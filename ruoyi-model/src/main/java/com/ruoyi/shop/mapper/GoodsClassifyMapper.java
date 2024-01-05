package com.ruoyi.shop.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.ruoyi.shop.domain.GoodsClassify;

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
     * 批量删除商品分类
     *
     * @param classIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteGoodsClassifyByClassIds(Long[] classIds);
}

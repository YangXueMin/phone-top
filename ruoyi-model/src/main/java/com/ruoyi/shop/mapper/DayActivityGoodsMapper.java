package com.ruoyi.shop.mapper;

import com.ruoyi.shop.domain.DayActivityGoods;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author yangxuemin
 * @ClassName DayActivityGoodsMapper
 * @Description
 * @date 2024/1/13 4:23 PM
 */
@Mapper
public interface DayActivityGoodsMapper {
    /**
     * 通过会员日ID查询商品ID集合
     *
     * @param dayActivityId 会员日ID
     * @return 结果
     */
    public List<Long> selectDayActivityGoodsByDayActivityId(Long dayActivityId);

    /**
     * 通过会员日ID删除会员日和商品关联
     *
     * @param dayActivityId 会员日ID
     * @return 结果
     */
    public int deleteDayActivityGoodsByDayActivityId(Long dayActivityId);

    /**
     * 批量删除会员日和商品关联
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteDayActivityGoods(Long[] ids);

    /**
     * 通过商品ID查询商品使用数量
     *
     * @param goodsId 商品ID
     * @return 结果
     */
    public int countDayActivityGoodsByGoodsId(Long goodsId);

    /**
     * 批量新增用户商品信息
     *
     * @param DayActivityGoodsList 用户商品列表
     * @return 结果
     */
    public int batchDayActivityGoods(List<DayActivityGoods> DayActivityGoodsList);

    /**
     * 删除会员日和商品关联信息
     *
     * @param dayActivityGoods 会员日和商品关联信息
     * @return 结果
     */
    public int deleteDayActivityGoodsInfo(DayActivityGoods dayActivityGoods);

}

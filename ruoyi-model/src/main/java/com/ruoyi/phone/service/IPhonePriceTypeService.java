package com.ruoyi.phone.service;

import java.util.List;
import com.ruoyi.phone.domain.PhonePriceType;

/**
 * 价格类型Service接口
 *
 * @author ruoyi
 * @date 2024-03-08
 */
public interface IPhonePriceTypeService
{
    /**
     * 查询价格类型
     *
     * @param id 价格类型主键
     * @return 价格类型
     */
    public PhonePriceType selectPhonePriceTypeById(Long id);

    /**
     * 查询价格类型列表
     *
     * @param phonePriceType 价格类型
     * @return 价格类型集合
     */
    public List<PhonePriceType> selectPhonePriceTypeList(PhonePriceType phonePriceType);

    /**
     * 新增价格类型
     *
     * @param phonePriceType 价格类型
     * @return 结果
     */
    public int insertPhonePriceType(PhonePriceType phonePriceType);

    /**
     * 修改价格类型
     *
     * @param phonePriceType 价格类型
     * @return 结果
     */
    public int updatePhonePriceType(PhonePriceType phonePriceType);

    /**
     * 批量删除价格类型
     *
     * @param ids 需要删除的价格类型主键集合
     * @return 结果
     */
    public int deletePhonePriceTypeByIds(Long[] ids);

    /**
     * 删除价格类型信息
     *
     * @param id 价格类型主键
     * @return 结果
     */
    public int deletePhonePriceTypeById(Long id);
}

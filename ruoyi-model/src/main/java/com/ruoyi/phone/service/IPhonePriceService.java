package com.ruoyi.phone.service;

import java.util.List;
import com.ruoyi.phone.domain.PhonePrice;

/**
 * 价格配置Service接口
 *
 * @author ruoyi
 * @date 2024-03-05
 */
public interface IPhonePriceService
{
    /**
     * 查询价格配置
     *
     * @param id 价格配置主键
     * @return 价格配置
     */
    public PhonePrice selectPhonePriceById(Long id);

    /**
     * 查询价格配置列表
     *
     * @param phonePrice 价格配置
     * @return 价格配置集合
     */
    public List<PhonePrice> selectPhonePriceList(PhonePrice phonePrice);

    /**
     * 新增价格配置
     *
     * @param phonePrice 价格配置
     * @return 结果
     */
    public int insertPhonePrice(PhonePrice phonePrice);

    /**
     * 修改价格配置
     *
     * @param phonePrice 价格配置
     * @return 结果
     */
    public int updatePhonePrice(PhonePrice phonePrice);

    /**
     * 批量删除价格配置
     *
     * @param ids 需要删除的价格配置主键集合
     * @return 结果
     */
    public int deletePhonePriceByIds(Long[] ids);

    /**
     * 删除价格配置信息
     *
     * @param id 价格配置主键
     * @return 结果
     */
    public int deletePhonePriceById(Long id);
}

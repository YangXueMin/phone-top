package com.ruoyi.phone.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.ruoyi.phone.domain.PhonePrice;

/**
 * 价格配置Mapper接口
 *
 * @author ruoyi
 * @date 2024-03-05
 */
@Mapper
public interface PhonePriceMapper
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
     * 删除价格配置
     *
     * @param id 价格配置主键
     * @return 结果
     */
    public int deletePhonePriceById(Long id);

    /**
     * 批量删除价格配置
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePhonePriceByIds(Long[] ids);
}

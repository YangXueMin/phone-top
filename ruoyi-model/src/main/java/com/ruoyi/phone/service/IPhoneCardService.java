package com.ruoyi.phone.service;

import java.util.List;
import com.ruoyi.phone.domain.PhoneCard;

/**
 * 卡密管理Service接口
 *
 * @author ruoyi
 * @date 2024-03-04
 */
public interface IPhoneCardService
{
    /**
     * 查询卡密管理
     *
     * @param id 卡密管理主键
     * @return 卡密管理
     */
    public PhoneCard selectPhoneCardById(Long id);

    /**
     * 查询卡密管理列表
     *
     * @param phoneCard 卡密管理
     * @return 卡密管理集合
     */
    public List<PhoneCard> selectPhoneCardList(PhoneCard phoneCard);

    /**
     * 新增卡密管理
     *
     * @param phoneCard 卡密管理
     * @return 结果
     */
    public int insertPhoneCard(PhoneCard phoneCard);

    /**
     * 修改卡密管理
     *
     * @param phoneCard 卡密管理
     * @return 结果
     */
    public int updatePhoneCard(PhoneCard phoneCard);

    /**
     * 批量删除卡密管理
     *
     * @param ids 需要删除的卡密管理主键集合
     * @return 结果
     */
    public int deletePhoneCardByIds(Long[] ids);

    /**
     * 删除卡密管理信息
     *
     * @param id 卡密管理主键
     * @return 结果
     */
    public int deletePhoneCardById(Long id);
}

package com.ruoyi.phone.service.impl;

import com.ruoyi.common.annotation.DataScope;
import com.ruoyi.common.core.domain.entity.WechatConfig;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.phone.domain.PhonePriceType;
import com.ruoyi.phone.mapper.PhonePriceTypeMapper;
import com.ruoyi.phone.service.IPhonePriceTypeService;
import com.ruoyi.system.service.IWechatConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 价格类型Service业务层处理
 *
 * @author ruoyi
 * @date 2024-03-08
 */
@Service
public class PhonePriceTypeServiceImpl implements IPhonePriceTypeService {
    @Autowired
    private PhonePriceTypeMapper phonePriceTypeMapper;
    @Autowired
    private IWechatConfigService wechatConfigService;

    /**
     * 查询价格类型
     *
     * @param id 价格类型主键
     * @return 价格类型
     */
    @Override
    public PhonePriceType selectPhonePriceTypeById(Long id) {
        return phonePriceTypeMapper.selectPhonePriceTypeById(id);
    }

    /**
     * 查询价格类型列表
     *
     * @param phonePriceType 价格类型
     * @return 价格类型
     */
    @Override
    public List<PhonePriceType> selectPhonePriceTypeList(PhonePriceType phonePriceType) {
        return phonePriceTypeMapper.selectPhonePriceTypeList(phonePriceType);
    }

    /**
     * 新增价格类型
     *
     * @param phonePriceType 价格类型
     * @return 结果
     */
    @Override
    public int insertPhonePriceType(PhonePriceType phonePriceType) {
        final WechatConfig wechatConfig = wechatConfigService.selectWechatConfigByAppId(phonePriceType.getAppId());
        phonePriceType.setDeptId(wechatConfig.getDeptId());
        phonePriceType.setCreateTime(DateUtils.getNowDate());
        return phonePriceTypeMapper.insertPhonePriceType(phonePriceType);
    }

    /**
     * 修改价格类型
     *
     * @param phonePriceType 价格类型
     * @return 结果
     */
    @Override
    public int updatePhonePriceType(PhonePriceType phonePriceType) {
        phonePriceType.setUpdateTime(DateUtils.getNowDate());
        return phonePriceTypeMapper.updatePhonePriceType(phonePriceType);
    }

    /**
     * 批量删除价格类型
     *
     * @param ids 需要删除的价格类型主键
     * @return 结果
     */
    @Override
    public int deletePhonePriceTypeByIds(Long[] ids) {
        return phonePriceTypeMapper.deletePhonePriceTypeByIds(ids);
    }

    /**
     * 删除价格类型信息
     *
     * @param id 价格类型主键
     * @return 结果
     */
    @Override
    public int deletePhonePriceTypeById(Long id) {
        return phonePriceTypeMapper.deletePhonePriceTypeById(id);
    }
}

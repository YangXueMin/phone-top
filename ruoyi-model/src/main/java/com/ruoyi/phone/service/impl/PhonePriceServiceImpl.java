package com.ruoyi.phone.service.impl;

import java.util.List;

import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.service.ISysDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.phone.mapper.PhonePriceMapper;
import com.ruoyi.phone.domain.PhonePrice;
import com.ruoyi.phone.service.IPhonePriceService;

/**
 * 价格配置Service业务层处理
 *
 * @author ruoyi
 * @date 2024-03-05
 */
@Service
public class PhonePriceServiceImpl implements IPhonePriceService {
    @Autowired
    private PhonePriceMapper phonePriceMapper;
    @Autowired
    private ISysDeptService sysDeptService;

    /**
     * 查询价格配置
     *
     * @param id 价格配置主键
     * @return 价格配置
     */
    @Override
    public PhonePrice selectPhonePriceById(Long id) {
        return phonePriceMapper.selectPhonePriceById(id);
    }

    /**
     * 查询价格配置列表
     *
     * @param phonePrice 价格配置
     * @return 价格配置
     */
    @Override
    public List<PhonePrice> selectPhonePriceList(PhonePrice phonePrice) {
        return phonePriceMapper.selectPhonePriceList(phonePrice);
    }

    /**
     * 新增价格配置
     *
     * @param phonePrice 价格配置
     * @return 结果
     */
    @Override
    public int insertPhonePrice(PhonePrice phonePrice) {
        SysDept company = sysDeptService.selectCompany(SecurityUtils.getLoginUser().getDeptId());
        if(company != null && !company.getDeptId().equals(100L)){
            phonePrice.setCompanyId(company.getDeptId());
        }
        phonePrice.setCreateTime(DateUtils.getNowDate());
        return phonePriceMapper.insertPhonePrice(phonePrice);
    }

    /**
     * 修改价格配置
     *
     * @param phonePrice 价格配置
     * @return 结果
     */
    @Override
    public int updatePhonePrice(PhonePrice phonePrice) {
        phonePrice.setUpdateTime(DateUtils.getNowDate());
        return phonePriceMapper.updatePhonePrice(phonePrice);
    }

    /**
     * 批量删除价格配置
     *
     * @param ids 需要删除的价格配置主键
     * @return 结果
     */
    @Override
    public int deletePhonePriceByIds(Long[] ids) {
        return phonePriceMapper.deletePhonePriceByIds(ids);
    }

    /**
     * 删除价格配置信息
     *
     * @param id 价格配置主键
     * @return 结果
     */
    @Override
    public int deletePhonePriceById(Long id) {
        return phonePriceMapper.deletePhonePriceById(id);
    }
}

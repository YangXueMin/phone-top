package com.ruoyi.shop.service.impl;

import java.util.List;

import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.shop.mapper.BalanceInfoMapper;
import com.ruoyi.shop.domain.BalanceInfo;
import com.ruoyi.shop.service.IBalanceInfoService;

/**
 * 余额消费记录Service业务层处理
 *
 * @author ruoyi
 * @date 2024-01-14
 */
@Service
public class BalanceInfoServiceImpl implements IBalanceInfoService {
    @Autowired
    private BalanceInfoMapper balanceInfoMapper;

    /**
     * 查询余额消费记录
     *
     * @param id 余额消费记录主键
     * @return 余额消费记录
     */
    @Override
    public BalanceInfo selectBalanceInfoById(Long id) {
        return balanceInfoMapper.selectBalanceInfoById(id);
    }

    /**
     * 查询余额消费记录列表
     *
     * @param balanceInfo 余额消费记录
     * @return 余额消费记录
     */
    @Override
    public List<BalanceInfo> selectBalanceInfoList(BalanceInfo balanceInfo) {
        return balanceInfoMapper.selectBalanceInfoList(balanceInfo);
    }

    /**
     * 新增余额消费记录
     *
     * @param balanceInfo 余额消费记录
     * @return 结果
     */
    @Override
    public int insertBalanceInfo(BalanceInfo balanceInfo) {
        balanceInfo.setCreateTime(DateUtils.getNowDate());
        return balanceInfoMapper.insertBalanceInfo(balanceInfo);
    }

    /**
     * 修改余额消费记录
     *
     * @param balanceInfo 余额消费记录
     * @return 结果
     */
    @Override
    public int updateBalanceInfo(BalanceInfo balanceInfo) {
        balanceInfo.setUpdateTime(DateUtils.getNowDate());
        return balanceInfoMapper.updateBalanceInfo(balanceInfo);
    }

    /**
     * 批量删除余额消费记录
     *
     * @param ids 需要删除的余额消费记录主键
     * @return 结果
     */
    @Override
    public int deleteBalanceInfoByIds(Long[] ids) {
        return balanceInfoMapper.deleteBalanceInfoByIds(ids);
    }

    /**
     * 删除余额消费记录信息
     *
     * @param id 余额消费记录主键
     * @return 结果
     */
    @Override
    public int deleteBalanceInfoById(Long id) {
        return balanceInfoMapper.deleteBalanceInfoById(id);
    }
}

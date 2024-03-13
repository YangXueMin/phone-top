package com.ruoyi.system.service.impl;

import java.util.List;

import com.ruoyi.common.utils.CardGenerator;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.SysCardMapper;
import com.ruoyi.system.domain.SysCard;
import com.ruoyi.system.service.ISysCardService;

/**
 * 后台卡密管理Service业务层处理
 *
 * @author ruoyi
 * @date 2024-03-13
 */
@Service
public class SysCardServiceImpl implements ISysCardService {
    @Autowired
    private SysCardMapper sysCardMapper;

    /**
     * 查询后台卡密管理
     *
     * @param id 后台卡密管理主键
     * @return 后台卡密管理
     */
    @Override
    public SysCard selectSysCardById(Long id) {
        return sysCardMapper.selectSysCardById(id);
    }

    /**
     * 查询后台卡密管理列表
     *
     * @param sysCard 后台卡密管理
     * @return 后台卡密管理
     */
    @Override
    public List<SysCard> selectSysCardList(SysCard sysCard) {
        return sysCardMapper.selectSysCardList(sysCard);
    }

    /**
     * 新增后台卡密管理
     *
     * @param sysCard 后台卡密管理
     * @return 结果
     */
    @Override
    public synchronized int insertSysCard(SysCard sysCard) {
        sysCard.setCreateTime(DateUtils.getNowDate());
        sysCard.setCancelStatus("1");
        String cardNo = CardGenerator.generateCard(12);
        sysCard.setCardNo(cardNo);
        sysCard.setStatus("0");
        return sysCardMapper.insertSysCard(sysCard);
    }

    /**
     * 修改后台卡密管理
     *
     * @param sysCard 后台卡密管理
     * @return 结果
     */
    @Override
    public int updateSysCard(SysCard sysCard) {
        sysCard.setUpdateTime(DateUtils.getNowDate());
        return sysCardMapper.updateSysCard(sysCard);
    }

    /**
     * 批量删除后台卡密管理
     *
     * @param ids 需要删除的后台卡密管理主键
     * @return 结果
     */
    @Override
    public int deleteSysCardByIds(Long[] ids) {
        return sysCardMapper.deleteSysCardByIds(ids);
    }

    /**
     * 删除后台卡密管理信息
     *
     * @param id 后台卡密管理主键
     * @return 结果
     */
    @Override
    public int deleteSysCardById(Long id) {
        return sysCardMapper.deleteSysCardById(id);
    }
}

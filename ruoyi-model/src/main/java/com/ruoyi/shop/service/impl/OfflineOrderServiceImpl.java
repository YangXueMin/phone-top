package com.ruoyi.shop.service.impl;

import com.ruoyi.common.core.domain.entity.Member;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.shop.domain.BalanceInfo;
import com.ruoyi.shop.domain.OfflineOrder;
import com.ruoyi.shop.mapper.BalanceInfoMapper;
import com.ruoyi.shop.mapper.MemberMapper;
import com.ruoyi.shop.mapper.OfflineOrderMapper;
import com.ruoyi.shop.service.IOfflineOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * 线下订单Service业务层处理
 *
 * @author ruoyi
 * @date 2024-01-14
 */
@Service
public class OfflineOrderServiceImpl implements IOfflineOrderService {
    @Autowired
    private OfflineOrderMapper offlineOrderMapper;
    @Autowired
    private BalanceInfoMapper balanceInfoMapper;
    @Autowired
    private MemberMapper memberMapper;

    /**
     * 查询线下订单
     *
     * @param id 线下订单主键
     * @return 线下订单
     */
    @Override
    public OfflineOrder selectOfflineOrderById(Long id) {
        return offlineOrderMapper.selectOfflineOrderById(id);
    }

    /**
     * 查询线下订单列表
     *
     * @param offlineOrder 线下订单
     * @return 线下订单
     */
    @Override
    public List<OfflineOrder> selectOfflineOrderList(OfflineOrder offlineOrder) {
        return offlineOrderMapper.selectOfflineOrderList(offlineOrder);
    }

    /**
     * 新增线下订单
     *
     * @param offlineOrder 线下订单
     * @return 结果
     */
    @Override
    public int insertOfflineOrder(OfflineOrder offlineOrder) {
        offlineOrder.setCreateTime(DateUtils.getNowDate());
        return offlineOrderMapper.insertOfflineOrder(offlineOrder);
    }

    /**
     * 修改线下订单
     *
     * @param offlineOrder 线下订单
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateOfflineOrder(OfflineOrder offlineOrder) {
        offlineOrder.setUpdateTime(DateUtils.getNowDate());
        final int i = offlineOrderMapper.updateOfflineOrder(offlineOrder);
        if(i > 0){
            if(StringUtils.equals("2",offlineOrder.getOrderStatus())){
                Member member = memberMapper.selectMemberById(offlineOrder.getMemberId());
                BigDecimal beforeBalance = member.getBalance();
                member.setBalance(member.getBalance().subtract(offlineOrder.getMoney()));
                member.setUpdateTime(DateUtils.getNowDate());
                memberMapper.updateMember(member);
                //添加余额消费记录
                BalanceInfo balanceInfo = new BalanceInfo(offlineOrder.getMemberId(), "2", offlineOrder.getId(), beforeBalance, member.getBalance(), offlineOrder.getMoney());
                balanceInfo.setCreateTime(DateUtils.getNowDate());
                balanceInfoMapper.insertBalanceInfo(balanceInfo);
            }
        }
        return i;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int balanceRefund(OfflineOrder offlineOrder) {
        offlineOrder.setOrderStatus("3");
        offlineOrder.setUpdateTime(DateUtils.getNowDate());
        final int i = offlineOrderMapper.updateOfflineOrder(offlineOrder);
        if(i > 0){
            Member member = memberMapper.selectMemberById(offlineOrder.getMemberId());
            BigDecimal beforeBalance = member.getBalance();
            member.setBalance(member.getBalance().add(offlineOrder.getMoney()));
            member.setUpdateTime(DateUtils.getNowDate());
            memberMapper.updateMember(member);
            //删除余额消费记录
            balanceInfoMapper.deleteBalanceInfoByOrderIdAndOrderType(offlineOrder.getId(), "2");
        }
        return i;
    }

    /**
     * 批量删除线下订单
     *
     * @param ids 需要删除的线下订单主键
     * @return 结果
     */
    @Override
    public int deleteOfflineOrderByIds(Long[] ids) {
        return offlineOrderMapper.deleteOfflineOrderByIds(ids);
    }

    /**
     * 删除线下订单信息
     *
     * @param id 线下订单主键
     * @return 结果
     */
    @Override
    public int deleteOfflineOrderById(Long id) {
        return offlineOrderMapper.deleteOfflineOrderById(id);
    }
}

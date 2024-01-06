package com.ruoyi.shop.service.impl;

import com.ruoyi.shop.mapper.CardCouponMapper;
import com.ruoyi.shop.service.CardCouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author yangxuemin
 * @ClassName CardCouponServiceImpl
 * @Description
 * @date 2024/1/6 11:49 AM
 */
@Service
public class CardCouponServiceImpl implements CardCouponService {
    @Autowired
    private CardCouponMapper cardCouponMapper;

    @Override
    public int selectCountCardCouponByCouponId(Long couponId) {
        return cardCouponMapper.selectCountCardCouponByCouponId(couponId);
    }
}

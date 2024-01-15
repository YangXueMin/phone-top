package com.ruoyi.shop.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.shop.domain.CardCoupon;
import com.ruoyi.shop.domain.ShopCard;
import com.ruoyi.shop.mapper.CardCouponMapper;
import com.ruoyi.shop.mapper.ShopCardMapper;
import com.ruoyi.shop.service.IShopCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 储值卡Service业务层处理
 *
 * @author ruoyi
 * @date 2024-01-06
 */
@Service
public class ShopCardServiceImpl implements IShopCardService {
    @Autowired
    private ShopCardMapper shopCardMapper;
    @Autowired
    private CardCouponMapper cardCouponMapper;

    /**
     * 查询储值卡
     *
     * @param id 储值卡主键
     * @return 储值卡
     */
    @Override
    public ShopCard selectShopCardById(Long id) {
        ShopCard shopCard = shopCardMapper.selectShopCardById(id);
        if (shopCard != null) {
            List<CardCoupon> cardCouponList = cardCouponMapper.selectCardCouponByCardId(id);
            shopCard.setCardCouponList(cardCouponList);
        }
        return shopCard;
    }

    /**
     * 查询储值卡列表
     *
     * @param shopCard 储值卡
     * @return 储值卡
     */
    @Override
    public List<ShopCard> selectShopCardList(ShopCard shopCard) {
        List<ShopCard> cardList = shopCardMapper.selectShopCardList(shopCard);
        if (cardList != null && cardList.size() > 0) {
            for (ShopCard card : cardList) {
                card.setCardCouponList(cardCouponMapper.selectCardCouponByCardId(card.getId()));
            }
        }
        return cardList;
    }

    /**
     * 新增储值卡
     *
     * @param shopCard 储值卡
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertShopCard(ShopCard shopCard) {
        shopCard.setCreateTime(DateUtils.getNowDate());
        int i = shopCardMapper.insertShopCard(shopCard);
        if (shopCard.getCardCouponList() != null && shopCard.getCardCouponList().size() > 0) {
            for (CardCoupon cardCoupon : shopCard.getCardCouponList()) {
                cardCoupon.setCardId(shopCard.getId());
            }
            cardCouponMapper.batchCardCoupon(shopCard.getCardCouponList());
        }
        return i;
    }

    /**
     * 修改储值卡
     *
     * @param shopCard 储值卡
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateShopCard(ShopCard shopCard) {
        shopCard.setUpdateTime(DateUtils.getNowDate());
        int i = shopCardMapper.updateShopCard(shopCard);
        cardCouponMapper.deleteCardCouponByCardId(shopCard.getId());
        if (shopCard.getCardCouponList() != null && shopCard.getCardCouponList().size() > 0) {
            for (CardCoupon cardCoupon : shopCard.getCardCouponList()) {
                cardCoupon.setCardId(shopCard.getId());
            }
            cardCouponMapper.batchCardCoupon(shopCard.getCardCouponList());
        }
        return i;
    }

    /**
     * 批量删除储值卡
     *
     * @param ids 需要删除的储值卡主键
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteShopCardByIds(Long[] ids) {
        int i = shopCardMapper.deleteShopCardByIds(ids);
        cardCouponMapper.deleteCardCoupon(ids);
        return i;
    }

    /**
     * 删除储值卡信息
     *
     * @param id 储值卡主键
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteShopCardById(Long id) {
        int i = shopCardMapper.deleteShopCardById(id);
        cardCouponMapper.deleteCardCouponByCardId(id);
        return i;
    }
}

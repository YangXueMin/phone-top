package com.ruoyi.shop.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.ruoyi.common.annotation.ShopScope;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.ToolUtils;
import com.ruoyi.shop.domain.*;
import com.ruoyi.shop.mapper.*;
import com.ruoyi.system.mapper.SysUserShopMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.shop.service.IShopInfoService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 店铺信息Service业务层处理
 *
 * @author ruoyi
 * @date 2024-01-04
 */
@Service
public class ShopInfoServiceImpl implements IShopInfoService {
    @Autowired
    private ShopInfoMapper shopInfoMapper;
    @Autowired
    private CompanyGoodsMapper companyGoodsMapper;
    @Autowired
    private CompanyGoodsSpecsMapper companyGoodsSpecsMapper;
    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private GoodsSpecsMapper goodsSpecsMapper;

    /**
     * 查询店铺信息
     *
     * @param id 店铺信息主键
     * @return 店铺信息
     */
    @Override
    public ShopInfo selectShopInfoById(Long id) {
        return shopInfoMapper.selectShopInfoById(id);
    }

    /**
     * 查询店铺信息列表
     *
     * @param shopInfo 店铺信息
     * @return 店铺信息
     */
    @Override
    @ShopScope(shopAlias = "shop_info")
    public List<ShopInfo> selectShopInfoList(ShopInfo shopInfo) {
        return shopInfoMapper.selectShopInfoList(shopInfo);
    }

    /**
     * 查询店铺信息列表
     *
     * @param shopInfo 店铺信息
     * @return 店铺信息
     */
    @Override
    public List<ShopInfo> selectShopInfoListApi(ShopInfo shopInfo) {
        return shopInfoMapper.selectShopInfoList(shopInfo);
    }

    @Override
    public List<ShopInfo> selectShopInfoAll() {
        return shopInfoMapper.selectShopAll();
    }

    @Override
    public List<Long> selectShopListByUserId(Long userId) {
        return shopInfoMapper.selectShopListByUserId(userId);
    }

    /**
     * 根据用户获取店铺数据
     *
     * @param userId 用户ID
     * @return
     */
    @Override
    public List<ShopInfo> findShopInfoListByUserId(Long userId) {
        List<Long> shopIdList = this.selectShopListByUserId(userId);
        if (shopIdList != null && shopIdList.size() > 0) {
            return shopInfoMapper.selectShopInfoByIds(shopIdList.toArray(new Long[0]));
        }
        return new ArrayList<>();
    }

    /**
     * 新增店铺信息
     *
     * @param shopInfo 店铺信息
     * @return 结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertShopInfo(ShopInfo shopInfo) {
        shopInfo.setCreateTime(DateUtils.getNowDate());
        final int i = shopInfoMapper.insertShopInfo(shopInfo);
        if (i > 0 && StringUtils.equals("1", shopInfo.getIsSyncShop())) {
            CompanyGoods companyGoodsQuery = new CompanyGoods();
            companyGoodsQuery.setStatus("1");
            List<CompanyGoods> list = companyGoodsMapper.selectCompanyGoodsList(companyGoodsQuery);
            if (list != null && list.size() > 0) {
                for (CompanyGoods companyGoods : list) {
                    Goods goods = new Goods();
                    ToolUtils.copyPropertiesIgnoreNull(companyGoods, goods);
                    goods.setShopId(shopInfo.getId());
                    goods.setCompanyGoodsId(companyGoods.getId());
                    goodsMapper.insertGoods(goods);

                    List<CompanyGoodsSpecs> companyGoodsSpecsList = companyGoodsSpecsMapper.selectCompanyGoodsSpecsByGoodId(companyGoods.getId());
                    if (companyGoodsSpecsList.size() > 0) {
                        for (CompanyGoodsSpecs companyGoodsSpecs : companyGoodsSpecsList) {
                            GoodsSpecs goodsSpecs = new GoodsSpecs();
                            ToolUtils.copyPropertiesIgnoreNull(companyGoodsSpecs, goodsSpecs);
                            goodsSpecs.setGoodsId(goods.getId());
                            goodsSpecs.setCreateTime(DateUtils.getNowDate());
                            goodsSpecsMapper.insertGoodsSpecs(goodsSpecs);
                        }
                    }
                }
            }
        }
        return i;
    }

    /**
     * 修改店铺信息
     *
     * @param shopInfo 店铺信息
     * @return 结果
     */
    @Override
    public int updateShopInfo(ShopInfo shopInfo) {
        shopInfo.setUpdateTime(DateUtils.getNowDate());
        return shopInfoMapper.updateShopInfo(shopInfo);
    }

    /**
     * 批量删除店铺信息
     *
     * @param ids 需要删除的店铺信息主键
     * @return 结果
     */
    @Override
    public int deleteShopInfoByIds(Long[] ids) {
        return shopInfoMapper.deleteShopInfoByIds(ids);
    }

    /**
     * 删除店铺信息信息
     *
     * @param id 店铺信息主键
     * @return 结果
     */
    @Override
    public int deleteShopInfoById(Long id) {
        return shopInfoMapper.deleteShopInfoById(id);
    }
}

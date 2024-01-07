package com.ruoyi.shop.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.shop.domain.ContentType;
import com.ruoyi.shop.domain.ShopTreeSelect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.shop.mapper.BannerTypeMapper;
import com.ruoyi.shop.domain.BannerType;
import com.ruoyi.shop.service.IBannerTypeService;

/**
 * banner类型Service业务层处理
 *
 * @author ruoyi
 * @date 2024-01-07
 */
@Service
public class BannerTypeServiceImpl implements IBannerTypeService {
    @Autowired
    private BannerTypeMapper bannerTypeMapper;

    /**
     * 查询banner类型
     *
     * @param typeId banner类型主键
     * @return banner类型
     */
    @Override
    public BannerType selectBannerTypeByTypeId(Long typeId) {
        return bannerTypeMapper.selectBannerTypeByTypeId(typeId);
    }

    /**
     * 查询banner类型列表
     *
     * @param bannerType banner类型
     * @return banner类型
     */
    @Override
    public List<BannerType> selectBannerTypeList(BannerType bannerType) {
        return bannerTypeMapper.selectBannerTypeList(bannerType);
    }

    @Override
    public List<ShopTreeSelect> selectTreeList(BannerType bannerType) {
        List<BannerType> typeList = SpringUtils.getAopProxy(this).selectBannerTypeList(bannerType);
        return buildTreeSelect(typeList);
    }

    @Override
    public List<ShopTreeSelect> buildTreeSelect(List<BannerType> typeList) {
        List<BannerType> typeTrees = buildTree(typeList);
        return typeTrees.stream().map(ShopTreeSelect::new).collect(Collectors.toList());
    }

    @Override
    public List<BannerType> buildTree(List<BannerType> typeList) {
        List<BannerType> returnList = new ArrayList<>();
        List<Long> tempList = typeList.stream().map(BannerType::getTypeId).collect(Collectors.toList());
        for (BannerType bannerType : typeList) {
            // 如果是顶级节点, 遍历该父节点的所有子节点
            if (!tempList.contains(bannerType.getParentId())) {
                recursionFn(typeList, bannerType);
                returnList.add(bannerType);
            }
        }
        if (returnList.isEmpty()) {
            returnList = typeList;
        }
        return returnList;
    }

    /**
     * 新增banner类型
     *
     * @param bannerType banner类型
     * @return 结果
     */
    @Override
    public int insertBannerType(BannerType bannerType) {
        bannerType.setCreateTime(DateUtils.getNowDate());
        return bannerTypeMapper.insertBannerType(bannerType);
    }

    /**
     * 修改banner类型
     *
     * @param bannerType banner类型
     * @return 结果
     */
    @Override
    public int updateBannerType(BannerType bannerType) {
        bannerType.setUpdateTime(DateUtils.getNowDate());
        return bannerTypeMapper.updateBannerType(bannerType);
    }

    /**
     * 批量删除banner类型
     *
     * @param typeIds 需要删除的banner类型主键
     * @return 结果
     */
    @Override
    public int deleteBannerTypeByTypeIds(Long[] typeIds) {
        return bannerTypeMapper.deleteBannerTypeByTypeIds(typeIds);
    }

    /**
     * 删除banner类型信息
     *
     * @param typeId banner类型主键
     * @return 结果
     */
    @Override
    public int deleteBannerTypeByTypeId(Long typeId) {
        return bannerTypeMapper.deleteBannerTypeByTypeId(typeId);
    }

    /**
     * 递归列表
     */
    private void recursionFn(List<BannerType> list, BannerType t) {
        // 得到子节点列表
        List<BannerType> childList = getChildList(list, t);
        t.setChildren(childList);
        for (BannerType bannerType : childList) {
            if (hasChild(list, bannerType)) {
                recursionFn(list, bannerType);
            }
        }
    }

    /**
     * 得到子节点列表
     */
    private List<BannerType> getChildList(List<BannerType> list, BannerType t) {
        List<BannerType> tlist = new ArrayList<>();
        Iterator<BannerType> it = list.iterator();
        while (it.hasNext()) {
            BannerType n = it.next();
            if (StringUtils.isNotNull(n.getParentId()) && n.getParentId().longValue() == t.getTypeId().longValue()) {
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<BannerType> list, BannerType t) {
        return getChildList(list, t).size() > 0;
    }
}

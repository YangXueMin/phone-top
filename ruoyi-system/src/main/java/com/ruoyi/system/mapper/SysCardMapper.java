package com.ruoyi.system.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.ruoyi.system.domain.SysCard;

/**
 * 后台卡密管理Mapper接口
 *
 * @author ruoyi
 * @date 2024-03-13
 */
@Mapper
public interface SysCardMapper
{
    /**
     * 查询后台卡密管理
     *
     * @param id 后台卡密管理主键
     * @return 后台卡密管理
     */
    public SysCard selectSysCardById(Long id);

    /**
     * 查询后台卡密管理列表
     *
     * @param sysCard 后台卡密管理
     * @return 后台卡密管理集合
     */
    public List<SysCard> selectSysCardList(SysCard sysCard);

    /**
     * 新增后台卡密管理
     *
     * @param sysCard 后台卡密管理
     * @return 结果
     */
    public int insertSysCard(SysCard sysCard);

    /**
     * 修改后台卡密管理
     *
     * @param sysCard 后台卡密管理
     * @return 结果
     */
    public int updateSysCard(SysCard sysCard);

    /**
     * 删除后台卡密管理
     *
     * @param id 后台卡密管理主键
     * @return 结果
     */
    public int deleteSysCardById(Long id);

    /**
     * 批量删除后台卡密管理
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysCardByIds(Long[] ids);
}

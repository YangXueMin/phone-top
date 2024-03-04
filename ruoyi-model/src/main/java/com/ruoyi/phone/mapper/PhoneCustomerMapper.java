package com.ruoyi.phone.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.ruoyi.phone.domain.PhoneCustomer;

/**
 * 客服配置Mapper接口
 *
 * @author ruoyi
 * @date 2024-03-04
 */
@Mapper
public interface PhoneCustomerMapper
{
    /**
     * 查询客服配置
     *
     * @param id 客服配置主键
     * @return 客服配置
     */
    public PhoneCustomer selectPhoneCustomerById(Long id);

    /**
     * 查询客服配置列表
     *
     * @param phoneCustomer 客服配置
     * @return 客服配置集合
     */
    public List<PhoneCustomer> selectPhoneCustomerList(PhoneCustomer phoneCustomer);

    /**
     * 新增客服配置
     *
     * @param phoneCustomer 客服配置
     * @return 结果
     */
    public int insertPhoneCustomer(PhoneCustomer phoneCustomer);

    /**
     * 修改客服配置
     *
     * @param phoneCustomer 客服配置
     * @return 结果
     */
    public int updatePhoneCustomer(PhoneCustomer phoneCustomer);

    /**
     * 删除客服配置
     *
     * @param id 客服配置主键
     * @return 结果
     */
    public int deletePhoneCustomerById(Long id);

    /**
     * 批量删除客服配置
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePhoneCustomerByIds(Long[] ids);
}

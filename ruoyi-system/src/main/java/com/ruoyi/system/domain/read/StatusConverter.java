package com.ruoyi.system.domain.read;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

/**
 * 状态字符串处理
 *
 * @author ruoyi
 */
@SuppressWarnings("rawtypes")
public class StatusConverter implements Converter<Integer>
{
    @Override
    public Class supportJavaTypeKey()
    {
        return Integer.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey()
    {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public CellData convertToExcelData(Integer value, ExcelContentProperty contentProperty,
                                       GlobalConfiguration globalConfiguration)
    {
        return new CellData(0 == value ? "正常" : "异常");
    }

    @Override
    public Integer convertToJavaData(CellData cellData, ExcelContentProperty contentProperty,
                                     GlobalConfiguration globalConfiguration) throws Exception
    {
        return "正常".equals(cellData.getStringValue()) ? 0 : 1;
    }
}
package com.ruoyi.system.domain.read;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

/**
 * 操作类别字符串处理
 *
 * @author ruoyi
 */
@SuppressWarnings("rawtypes")
public class OperTypeConverter implements Converter<Integer>
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
    public Integer convertToJavaData(CellData cellData, ExcelContentProperty contentProperty,
                                     GlobalConfiguration globalConfiguration)
    {
        Integer value = 0;
        String str = cellData.getStringValue();
        if ("后台用户".equals(str))
        {
            value = 1;
        }
        else if ("手机端用户".equals(str))
        {
            value = 2;
        }
        return value;
    }

    @Override
    public CellData convertToExcelData(Integer value, ExcelContentProperty contentProperty,
                                       GlobalConfiguration globalConfiguration)
    {
        String str = "其他";
        if (1 == value)
        {
            str = "后台用户";
        }
        else if (2 == value)
        {
            str = "手机端用户";
        }
        return new CellData(str);
    }
}
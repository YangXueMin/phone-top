package com.ruoyi.system.domain.read;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

/**
 * 业务类型字符串处理
 *
 * @author ruoyi
 */
@SuppressWarnings("rawtypes")
public class BusiTypeStringNumberConverter implements Converter<Integer>
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
        if ("新增".equals(str))
        {
            value = 1;
        }
        else if ("修改".equals(str))
        {
            value = 2;
        }
        else if ("删除".equals(str))
        {
            value = 3;
        }
        else if ("授权".equals(str))
        {
            value = 4;
        }
        else if ("导出".equals(str))
        {
            value = 5;
        }
        else if ("导入".equals(str))
        {
            value = 6;
        }
        else if ("强退".equals(str))
        {
            value = 7;
        }
        else if ("生成代码".equals(str))
        {
            value = 8;
        }
        else if ("清空数据".equals(str))
        {
            value = 9;
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
            str = "新增";
        }
        else if (2 == value)
        {
            str = "修改";
        }
        else if (3 == value)
        {
            str = "删除";
        }
        else if (4 == value)
        {
            str = "授权";
        }
        else if (5 == value)
        {
            str = "导出";
        }
        else if (6 == value)
        {
            str = "导入";
        }
        else if (7 == value)
        {
            str = "强退";
        }
        else if (8 == value)
        {
            str = "生成代码";
        }
        else if (9 == value)
        {
            str = "清空数据";
        }
        return new CellData(str);
    }
}
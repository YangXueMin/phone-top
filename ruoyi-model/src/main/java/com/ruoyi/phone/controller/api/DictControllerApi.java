package com.ruoyi.phone.controller.api;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.service.ISysDictTypeService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ruoyi
 * @ClassName DictControllerApi
 * @Description
 * @date 2024/3/10 12:45 AM
 */
@Api("字典管理")
@RestController
@RequestMapping("/api/phone/dict")
public class DictControllerApi extends BaseController {
    private final ISysDictTypeService dictTypeService;

    public DictControllerApi(ISysDictTypeService dictTypeService) {
        this.dictTypeService = dictTypeService;
    }

    /**
     * 根据字典类型查询字典数据信息
     */
    @GetMapping(value = "/type/{dictType}")
    public AjaxResult dictType(@PathVariable String dictType)
    {
        List<SysDictData> data = dictTypeService.selectDictDataByType(dictType);
        if (StringUtils.isNull(data))
        {
            data = new ArrayList<SysDictData>();
        }
        return success(data);
    }


}

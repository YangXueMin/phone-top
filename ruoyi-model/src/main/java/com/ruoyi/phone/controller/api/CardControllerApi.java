package com.ruoyi.phone.controller.api;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.time.DateUtil;
import com.ruoyi.phone.domain.PhoneCard;
import com.ruoyi.phone.service.IPhoneCardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * @author yangxuemin
 * @ClassName CardControllerApi
 * @Description
 * @date 2024/3/5 6:44 PM
 */
@Api("卡密管理")
@RestController
@RequestMapping("/api/phone/card")
public class CardControllerApi extends BaseController {
    private final IPhoneCardService phoneCardService;

    public CardControllerApi(IPhoneCardService phoneCardService) {
        this.phoneCardService = phoneCardService;
    }

    /**
     * 核销卡
     */
    @ApiOperation("核销卡")
    @GetMapping("/cancel")
    public synchronized AjaxResult cancel(PhoneCard phoneCard) {
        phoneCard = phoneCardService.selectPhoneCardById(phoneCard.getId());
        if (phoneCard != null) {
            if (!StringUtils.equals("1", phoneCard.getStatus())) {
                return error("无效卡，请核对");
            }
            if (StringUtils.equals("1", phoneCard.getCancelStatus())) {
                return error("卡已核销，请核对");
            }
            Date date = new Date();
            if(phoneCard.getBeginTime() != null && phoneCard.getEndTime() != null){
                if(date.before(phoneCard.getBeginTime())){
                    return error("此卡还未到有效期内");
                }
                if(date.after(phoneCard.getBeginTime())){
                    return error("此卡已过有效期");
                }
                if(!DateUtil.isBetween(date,phoneCard.getBeginTime(),phoneCard.getEndTime())){
                    return error("不在当前时间段内");
                }
            }
            return success(phoneCardService.cancel(phoneCard));
        }
        return error("不存在的卡");
    }
}

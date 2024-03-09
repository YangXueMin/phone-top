package com.ruoyi.phone.controller.api;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.domain.SysNotice;
import com.ruoyi.system.service.ISysNoticeService;
import io.swagger.annotations.Api;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author yangxuemin
 * @ClassName NoticeControllerApi
 * @Description
 * @date 2024/3/9 11:32 PM
 */
@Api("通知管理")
@RestController
@RequestMapping("/api/phone/notice")
public class NoticeControllerApi extends BaseController {
    private final ISysNoticeService noticeService;

    public NoticeControllerApi(ISysNoticeService noticeService) {
        this.noticeService = noticeService;
    }


    /**
     * 获取通知公告列表
     */
    @GetMapping("/list")
    public TableDataInfo list(SysNotice notice) {
        startPage();
        List<SysNotice> list = noticeService.selectNoticeList(notice);
        return getDataTable(list);
    }

    /**
     * 根据通知公告编号获取详细信息
     */
    @GetMapping(value = "getInfo")
    public AjaxResult getInfo(@RequestParam("noticeId") Long noticeId) {
        return success(noticeService.selectNoticeById(noticeId));
    }
}

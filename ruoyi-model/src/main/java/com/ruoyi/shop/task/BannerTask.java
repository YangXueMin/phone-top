package com.ruoyi.shop.task;

import com.ruoyi.shop.mapper.BannerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author yangxuemin
 * @ClassName BannerTask
 * @Description
 * @date 2024/1/23 10:08 PM
 */
@Component("bannerTask")
public class BannerTask {
    @Autowired
    private BannerMapper bannerMapper;

    public void updateBannerStatus() {
        System.out.println("更新轮播图状态");
        bannerMapper.updateStatusOn();
        bannerMapper.updateStatusOff();
    }
}

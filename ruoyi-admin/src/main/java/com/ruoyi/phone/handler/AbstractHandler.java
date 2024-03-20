package com.ruoyi.phone.handler;

import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ruoyi
 * @ClassName AbstractHandler
 * @Description
 * @date 2024/3/1 10:12 AM
 */
public abstract class AbstractHandler implements WxMpMessageHandler {
    protected Logger logger = LoggerFactory.getLogger(getClass());
}

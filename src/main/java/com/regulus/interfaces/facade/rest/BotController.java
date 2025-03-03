/*
 * 欲买桂花同载酒
 * 终不似，少年游
 * Copyright (c) VernonSong. All rights reserved.
 * -----------------------------------------------------------------------------
 */

package com.regulus.interfaces.facade.rest;

import com.alibaba.fastjson2.JSONObject;
import com.regulus.domain.core.message.service.BotService;
import jakarta.annotation.Resource;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/** 机器人相关 Controller层 */
@Slf4j
@RestController
@RequestMapping("/bot")
public class BotController {
    @Resource private BotService botService;

    /**
     * 获取消息
     *
     * @param event 请求体
     * @param headers 请求头
     * @return 返回体
     */
    @PostMapping("/message/callback")
    public JSONObject getMsg(
            @RequestBody JSONObject event, @RequestHeader Map<String, String> headers) {
        log.info("POST /bot/message/callback");
        return botService.receiveEvent(event, headers);
    }
}

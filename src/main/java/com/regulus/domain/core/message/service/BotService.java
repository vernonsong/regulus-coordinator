/*
 * 欲买桂花同载酒
 * 终不似，少年游
 * Copyright (c) VernonSong. All rights reserved.
 * -----------------------------------------------------------------------------
 */

package com.regulus.domain.core.message.service;

import com.alibaba.fastjson2.JSONObject;
import java.util.Map;

/** 机器人 接口 */
public interface BotService {
    /**
     * 发送消息
     *
     * @param message 消息内容
     * @param userId 用户ID
     * @param messageId 消息ID【回复用】
     */
    void sendMessage(String message, String userId, String messageId);

    /**
     * 获取消息
     *
     * @param payload 请求体
     * @param header 请求头
     * @return 返回体
     */
    JSONObject receiveEvent(JSONObject payload, Map<String, String> header);
}

/*
 * 欲买桂花同载酒
 * 终不似，少年游
 * Copyright (c) VernonSong. All rights reserved.
 * -----------------------------------------------------------------------------
 */

package com.regulus.domain.core.message.repositpory;

import com.regulus.domain.core.message.model.MessageDaily;

/** 每日消息 仓储层接口 */
public interface MessageDailyRepository {
    /**
     * 保存消息
     *
     * @param messageDaily 每日消息
     */
    void saveMessage(MessageDaily messageDaily);
}

/*
 * 欲买桂花同载酒
 * 终不似，少年游
 * Copyright (c) VernonSong. All rights reserved.
 * -----------------------------------------------------------------------------
 */

package com.regulus.domain.core.message.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Data;

/** 每日消息 实体 */
@Data
public class MessageDaily {
    private String content;

    private String imgPath;

    private LocalDate tradeDate;

    private SendPeriod sendPeriod;

    private String source;

    public MessageDaily(String content, String imgPath, LocalDateTime timeNow) {
        this.imgPath = imgPath;
        this.content = content;
        this.sendPeriod = SendPeriod.determineByCurrentTime();
        this.tradeDate = timeNow.toLocalDate();
        this.source = "华泰证券经理";
    }
}

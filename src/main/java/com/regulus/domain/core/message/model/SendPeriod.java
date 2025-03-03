/*
 * 欲买桂花同载酒
 * 终不似，少年游
 * Copyright (c) VernonSong. All rights reserved.
 * -----------------------------------------------------------------------------
 */

package com.regulus.domain.core.message.model;

import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import lombok.Getter;

/** 发送时间区间 枚举 */
public enum SendPeriod {
    /** 早晨 */
    MORNING("morning", "早晨", LocalTime.of(0, 0), LocalTime.of(9, 30)),
    /** 中午 */
    NOON("noon", "午间", LocalTime.of(9, 30), LocalTime.of(15, 0)),
    /** 晚上 */
    EVENING("evening", "晚间", LocalTime.of(15, 0), LocalTime.of(23, 59, 59));

    @Getter private final String code;
    @Getter private final String description;
    private final LocalTime start;
    private final LocalTime end;

    SendPeriod(String code, String description, LocalTime start, LocalTime end) {
        this.code = code;
        this.description = description;
        this.start = start;
        this.end = end;
    }

    /**
     * 根据当前时间确定时段（使用北京时间）
     *
     * @return 发送时间区间
     */
    public static SendPeriod determineByCurrentTime() {
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Asia/Shanghai"));
        LocalTime currentTime = now.toLocalTime();
        for (SendPeriod period : values()) {
            if (currentTime.isAfter(period.start) && currentTime.isBefore(period.end)) {
                return period;
            }
        }
        // 处理边界情况（如正好在分界点）
        return currentTime.equals(LocalTime.of(9, 30))
                ? NOON
                : currentTime.equals(LocalTime.of(15, 0)) ? EVENING : MORNING;
    }

    @Override
    public String toString() {
        return code;
    }
}

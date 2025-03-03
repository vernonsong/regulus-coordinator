/*
 * 欲买桂花同载酒
 * 终不似，少年游
 * Copyright (c) VernonSong. All rights reserved.
 * -----------------------------------------------------------------------------
 */

package com.regulus.domain.core.ai.repository;

import java.time.LocalDate;

/** 每日持仓 仓储层接口 */
public interface PositionDailyRepository {
    /**
     * 保存持仓信息
     *
     * @param position 持仓信息
     * @param tradeDate 交易日期
     */
    void savePosition(String position, LocalDate tradeDate);
}

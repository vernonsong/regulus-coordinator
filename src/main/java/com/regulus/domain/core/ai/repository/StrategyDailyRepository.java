/*
 * 欲买桂花同载酒
 * 终不似，少年游
 * Copyright (c) VernonSong. All rights reserved.
 * -----------------------------------------------------------------------------
 */

package com.regulus.domain.core.ai.repository;

import java.time.LocalDate;

/** 每日策略 仓储层接口 */
public interface StrategyDailyRepository {
    /**
     * 保存策略正文
     *
     * @param content 策略正文
     * @param tradeDate 交易日期
     */
    void saveStrategyContent(String content, LocalDate tradeDate);

    /**
     * 保存策略打分
     *
     * @param score 策略打分
     * @param tradeDate 交易日期
     */
    void saveStrategyScore(String score, LocalDate tradeDate);

    /**
     * 获取策略
     *
     * @param tradeDate 交易日期
     * @return 策略信息
     */
    String getStrategy(LocalDate tradeDate);

    /**
     * 查看是否有打分
     *
     * @param tradeDate 交易日期
     * @return 是否有策略打分
     */
    Boolean checkScore(LocalDate tradeDate);
}

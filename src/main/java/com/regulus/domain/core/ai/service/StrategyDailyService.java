/*
 * 欲买桂花同载酒
 * 终不似，少年游
 * Copyright (c) VernonSong. All rights reserved.
 * -----------------------------------------------------------------------------
 */

package com.regulus.domain.core.ai.service;

import com.regulus.domain.core.ai.repository.StrategyDailyRepository;
import jakarta.annotation.Resource;
import java.time.LocalDate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

/** 每日策略类 */
@Service
@Slf4j
public class StrategyDailyService {
    @Resource private AiService aiService;

    @Resource private StrategyDailyRepository strategyDailyRepository;

    private String getStrategyContentDaily(LocalDate tradeDate) {
        String content = aiService.getPreMarketStrategy(tradeDate);
        strategyDailyRepository.saveStrategyContent(content, tradeDate);
        return "content";
    }

    /** 保存策略正文 */
    @Retryable(retryFor = RuntimeException.class, maxAttempts = 2, backoff = @Backoff(delay = 60000))
    public void saveStrategyContentDaily() {
        LocalDate tradeDate = LocalDate.now();
        if (!strategyDailyRepository.checkScore(tradeDate)) {
            log.warn("未获取到策略打分");
            throw new RuntimeException("未获取到策略打分");
        }
        String content = getStrategyContentDaily(tradeDate);
        log.info("盘前策略为:\n==={}===\n", content);
    }

    @Recover
    public void recoverSaveStrategy(RuntimeException e) {
        log.error("重试全部失败，执行补偿操作", e);
        LocalDate tradeDate = LocalDate.now();
        String content = getStrategyContentDaily(tradeDate);
        log.info("无策略打分的盘前策略为:\n==={}===\n", content);
    }

    /** 保存策略打分 */
    public void saveStrategyScoreDaily(String score) {
        LocalDate tradeDate = LocalDate.now();
        log.info("策略打分为:\n==={}===\n", score);
        strategyDailyRepository.saveStrategyScore(score, tradeDate);
    }
}

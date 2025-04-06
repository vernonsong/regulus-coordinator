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
import org.springframework.stereotype.Service;

/** 每日策略类 */
@Service
@Slf4j
public class StrategyDailyService {
    @Resource private AiService aiService;

    @Resource private StrategyDailyRepository strategyDailyRepository;

    /** 保存策略 */
    public void saveStrategyDaily() {
        LocalDate tradeDate = LocalDate.now();
        String content = aiService.getPreMarketStrategy(tradeDate);
        log.info("盘前策略为:\n==={}===\n", content);
        strategyDailyRepository.saveStrategy(content, tradeDate);
    }
}

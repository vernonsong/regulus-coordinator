/*
 * 欲买桂花同载酒
 * 终不似，少年游
 * Copyright (c) VernonSong. All rights reserved.
 * -----------------------------------------------------------------------------
 */

package com.regulus.interfaces.facade.schedule;

import com.regulus.domain.core.ai.service.StrategyDailyService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/** 策略调度 */
@Component
@Slf4j
public class StrategySchedule {
    @Resource private StrategyDailyService strategyDailyService;

    /** 保存每日策略 */
    @Scheduled(cron = "0 45 09 * * ?")
    public void saveStrategyDaily() {
        log.info("saveStrategyDaily 开始调度");
        strategyDailyService.saveStrategyDaily();
        log.info("saveStrategyDaily 完成调度");
    }
}

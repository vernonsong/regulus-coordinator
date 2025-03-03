/*
 * 欲买桂花同载酒
 * 终不似，少年游
 * Copyright (c) VernonSong. All rights reserved.
 * -----------------------------------------------------------------------------
 */

package com.regulus.infrastructure.repository;

import com.regulus.domain.core.ai.repository.StrategyDailyRepository;
import com.regulus.infrastructure.persistence.StrategyDailyDo;
import com.regulus.infrastructure.repository.mapper.StrategyDailyMapper;
import jakarta.annotation.Resource;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import org.springframework.stereotype.Service;

/** 每日策略 仓储层实现 */
@Service
public class DefaultStrategyDailyRepository implements StrategyDailyRepository {
    @Resource private StrategyDailyMapper strategyDailyDoMapper;

    @Override
    public void saveStrategy(String content, LocalDate tradeDate) {
        StrategyDailyDo strategyDailyDo = new StrategyDailyDo();
        strategyDailyDo.setContent(content);
        strategyDailyDo.setTradeDate(
                Date.from(tradeDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        strategyDailyDo.setCreateTime(new Date());
        strategyDailyDo.setUpdateTime(new Date());
        strategyDailyDoMapper.insert(strategyDailyDo);
    }

    @Override
    public String getStrategy(LocalDate tradeDate) {
        return strategyDailyDoMapper.queryStrategyByTradeDate(tradeDate);
    }
}

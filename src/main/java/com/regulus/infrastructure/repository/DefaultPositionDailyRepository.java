/*
 * 欲买桂花同载酒
 * 终不似，少年游
 * Copyright (c) VernonSong. All rights reserved.
 * -----------------------------------------------------------------------------
 */

package com.regulus.infrastructure.repository;

import com.regulus.domain.core.ai.repository.PositionDailyRepository;
import com.regulus.infrastructure.persistence.PositionDailyDo;
import com.regulus.infrastructure.repository.mapper.PositionDailyMapper;
import jakarta.annotation.Resource;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import org.springframework.stereotype.Service;

/** 每日持仓 仓储层实现 */
@Service
public class DefaultPositionDailyRepository implements PositionDailyRepository {
    @Resource private PositionDailyMapper positionDailyMapper;

    @Override
    public void savePosition(String position, LocalDate tradeDate) {
        PositionDailyDo positionDailyDo = new PositionDailyDo();
        positionDailyDo.setPosition(position);
        positionDailyDo.setTradeDate(
                Date.from(tradeDate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        positionDailyMapper.insert(positionDailyDo);
    }
}

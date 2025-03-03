/*
 * 欲买桂花同载酒
 * 终不似，少年游
 * Copyright (c) VernonSong. All rights reserved.
 * -----------------------------------------------------------------------------
 */
package com.regulus.infrastructure.repository;

import com.regulus.domain.core.data.model.StockAlpha191Daily;
import com.regulus.domain.core.data.repository.StockAlpha191DailyRepository;
import com.regulus.infrastructure.assembler.StockAlpha191DailyMapper;
import com.regulus.infrastructure.repository.mapper.StockAlpha191DailyDoMapper;
import jakarta.annotation.Resource;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class DefaultStockAlpha191DailyRepository implements StockAlpha191DailyRepository {
    @Resource
    StockAlpha191DailyDoMapper stockAlpha191DailyDoMapper;

    @Resource
    StockAlpha191DailyMapper stockAlpha191DailyMapper;

    @Override
    public void batchSave(List<StockAlpha191Daily> dataList) {
        Set<LocalDate> dateSet = dataList.stream().map(StockAlpha191Daily::getTradeDate).filter(Objects::nonNull)
                .collect(Collectors.toSet());
        for (LocalDate tradeDate : dateSet) {
            stockAlpha191DailyDoMapper.deleteBydate(tradeDate);
        }
        stockAlpha191DailyDoMapper
                .insert(dataList.stream().map(x -> stockAlpha191DailyMapper.toStockAlpha191DailyDo(x)).toList());
    }
}

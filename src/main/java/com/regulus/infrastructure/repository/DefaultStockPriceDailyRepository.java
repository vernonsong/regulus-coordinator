/*
 * 欲买桂花同载酒
 * 终不似，少年游
 * Copyright (c) VernonSong. All rights reserved.
 * -----------------------------------------------------------------------------
 */

package com.regulus.infrastructure.repository;

import com.regulus.domain.core.stock.model.StockPriceDaily;
import com.regulus.domain.core.stock.repository.StockPriceDailyRepository;
import com.regulus.infrastructure.assembler.StockPriceDailyMapper;
import com.regulus.infrastructure.repository.mapper.StockPricesDailyDoMapper;
import jakarta.annotation.Resource;
import java.util.List;
import org.springframework.stereotype.Service;

/** 股票每日行情 仓储层实现 */
@Service
public class DefaultStockPriceDailyRepository implements StockPriceDailyRepository {
    @Resource private StockPriceDailyMapper stockPriceDailyMapper;

    @Resource private StockPricesDailyDoMapper stockPricesDailyDoMapper;

    @Override
    public void batchSave(List<StockPriceDaily> dataList) {
        stockPricesDailyDoMapper.batchUpsert(
                dataList.stream().map(x -> stockPriceDailyMapper.modelToDo(x)).toList());
    }
}

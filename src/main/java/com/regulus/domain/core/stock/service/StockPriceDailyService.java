/*
 * 欲买桂花同载酒
 * 终不似，少年游
 * Copyright (c) VernonSong. All rights reserved.
 * -----------------------------------------------------------------------------
 */

package com.regulus.domain.core.stock.service;

import com.regulus.domain.core.stock.model.StockPriceDaily;
import com.regulus.domain.core.stock.repository.StockPriceDailyRepository;
import jakarta.annotation.Resource;
import java.util.List;
import org.springframework.stereotype.Service;

/** 股票行情类 */
@Service
public class StockPriceDailyService {
    @Resource private StockPriceDailyRepository stockPriceDailyRepository;

    /**
     * 保存每日股票行情数据
     *
     * @param dataList 每日股票行情数据列表
     */
    public void saveStockPriceDaily(List<StockPriceDaily> dataList) {
        stockPriceDailyRepository.batchSave(dataList);
    }
}

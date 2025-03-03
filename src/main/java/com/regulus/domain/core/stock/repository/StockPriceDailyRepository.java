/*
 * 欲买桂花同载酒
 * 终不似，少年游
 * Copyright (c) VernonSong. All rights reserved.
 * -----------------------------------------------------------------------------
 */

package com.regulus.domain.core.stock.repository;

import com.regulus.domain.core.stock.model.StockPriceDaily;
import java.util.List;

/** 每日股票行情 仓储层接口 */
public interface StockPriceDailyRepository {
    /**
     * 批量存储
     *
     * @param dataList 每日股票行情数据列表
     */
    void batchSave(List<StockPriceDaily> dataList);
}

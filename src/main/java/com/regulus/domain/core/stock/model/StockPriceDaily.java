/*
 * 欲买桂花同载酒
 * 终不似，少年游
 * Copyright (c) VernonSong. All rights reserved.
 * -----------------------------------------------------------------------------
 */

package com.regulus.domain.core.stock.model;

import java.math.BigDecimal;
import lombok.Data;

/** 每日股票行情 实体 */
@Data
public class StockPriceDaily {
    private StockDailyId stockDailyId;

    private BigDecimal open;

    private BigDecimal close;

    private BigDecimal high;

    private BigDecimal low;

    private BigDecimal volume;

    private BigDecimal money;
}

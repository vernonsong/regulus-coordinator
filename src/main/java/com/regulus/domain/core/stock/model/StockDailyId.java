/*
 * 欲买桂花同载酒
 * 终不似，少年游
 * Copyright (c) VernonSong. All rights reserved.
 * -----------------------------------------------------------------------------
 */

package com.regulus.domain.core.stock.model;

import java.time.LocalDate;
import lombok.Data;

/** 股票/日 股票域聚合根 */
@Data
public class StockDailyId {
    private String stockCode;

    private LocalDate tradeDate;
}

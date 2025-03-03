/*
 * 欲买桂花同载酒
 * 终不似，少年游
 * Copyright (c) VernonSong. All rights reserved.
 * -----------------------------------------------------------------------------
 */

package com.regulus.interfaces.dto;

import com.alibaba.fastjson2.annotation.JSONField;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Data;

/** 保存每日股票行情 DTO */
@Data
public class SaveStockPriceDailyDto {
    @NotBlank(message = "股票代码不能为空")
    @JSONField(name = "stock_code")
    private String stockCode;

    @NotNull(message = "交易日期不能为空")
    @JSONField(name = "trade_date", format = "yyyy-MM-dd")
    private LocalDate tradeDate;

    private BigDecimal open;

    private BigDecimal close;

    private BigDecimal high;

    private BigDecimal low;

    private BigDecimal volume;

    private BigDecimal money;
}

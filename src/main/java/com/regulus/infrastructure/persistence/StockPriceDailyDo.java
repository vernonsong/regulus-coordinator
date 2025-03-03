/*
 * 欲买桂花同载酒
 * 终不似，少年游
 * Copyright (c) VernonSong. All rights reserved.
 * -----------------------------------------------------------------------------
 */

package com.regulus.infrastructure.persistence;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/** 股票每日行情数据表 */
@TableName(value = "stock_price_daily")
@Data
public class StockPriceDailyDo {
    /** 股票代码（如：600519.SH） */
    @TableField(value = "stock_code")
    private String stockCode;

    /** 交易日期 */
    @TableField(value = "trade_date")
    private Date tradeDate;

    /** 开盘价 */
    @TableField(value = "open")
    private BigDecimal open;

    /** 收盘价 */
    @TableField(value = "close")
    private BigDecimal close;

    /** 最高价 */
    @TableField(value = "high")
    private BigDecimal high;

    /** 最低价 */
    @TableField(value = "low")
    private BigDecimal low;

    /** 成交量 */
    @TableField(value = "volume")
    private Long volume;

    /** 成交金额 */
    @TableField(value = "money")
    private BigDecimal money;
}

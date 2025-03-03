/*
 * 欲买桂花同载酒
 * 终不似，少年游
 * Copyright (c) VernonSong. All rights reserved.
 * -----------------------------------------------------------------------------
 */

package com.regulus.infrastructure.repository.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.regulus.infrastructure.persistence.StockPriceDailyDo;
import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

/** 针对表【stock_prices_daily(股票每日行情数据表)】的数据库操作Mapper */
public interface StockPricesDailyDoMapper extends BaseMapper<StockPriceDailyDo> {
  @Insert(
      """
            <script>
            INSERT INTO stock_price_daily
              (stock_code, trade_date, open, close, high, low, volume, money)
            VALUES
              <foreach collection="list" item="item" separator=",">
                (#{item.stockCode},
                 #{item.tradeDate},
                 #{item.open},
                 #{item.close},
                 #{item.high},
                 #{item.low},
                 #{item.volume},
                 #{item.money})
              </foreach>
            ON DUPLICATE KEY UPDATE
              open = VALUES(open),
              close = VALUES(close),
              high = VALUES(high),
              low = VALUES(low),
              volume = VALUES(volume),
              money = VALUES(money)
            </script>
            """)
  void batchUpsert(@Param("list") List<StockPriceDailyDo> list);
}

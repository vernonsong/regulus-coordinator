package com.regulus.infrastructure.repository.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.regulus.infrastructure.persistence.StrategyDailyDo;
import java.time.LocalDate;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/** 针对表【strategy_daily】的数据库操作Mapper */
public interface StrategyDailyMapper extends BaseMapper<StrategyDailyDo> {
    /**
     * 根据交易日期查询策略
     *
     * @param tradeDate the trade date
     * @return the string
     */
    @Select("SELECT content FROM strategy_daily WHERE trade_date = #{tradeDate}")
    String queryStrategyByTradeDate(@Param("tradeDate") LocalDate tradeDate);
}

package com.regulus.infrastructure.repository.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.regulus.infrastructure.persistence.StockAlpha191DailyDo;
import java.time.LocalDate;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author vernonsong
 * @description 针对表【stock_alpha191_daily(股票每日Alpha191因子数据表)】的数据库操作Mapper
 * @createDate 2025-03-02 14:54:25 @Entity
 *             com.regulus.infrastructure.persistence.StockAlpha191Daily
 */
public interface StockAlpha191DailyDoMapper extends BaseMapper<StockAlpha191DailyDo> {
    @Select("DELETE FROM stock_alpha191_daily WHERE trade_date = #{date}")
    void deleteBydate(@Param("date") LocalDate date);
}

/*
 * 欲买桂花同载酒
 * 终不似，少年游
 * Copyright (c) VernonSong. All rights reserved.
 * -----------------------------------------------------------------------------
 */

package com.regulus.infrastructure.assembler;

import com.regulus.domain.core.stock.model.StockPriceDaily;
import com.regulus.infrastructure.persistence.StockPriceDailyDo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/** StockPriceDaily 类型转换器 */
@Mapper(componentModel = "spring")
public interface StockPriceDailyMapper {
    /**
     * StockPriceDaily → StockPriceDailyDo
     *
     * @param model StockPriceDaily
     * @return tStockPriceDailyDo
     */
    @Mappings({
        @Mapping(target = "stockCode", source = "stockDailyId.stockCode"),
        @Mapping(target = "tradeDate", source = "stockDailyId.tradeDate"),
    })
    StockPriceDailyDo modelToDo(StockPriceDaily model);
}

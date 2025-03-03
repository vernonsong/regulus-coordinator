/*
 * 欲买桂花同载酒
 * 终不似，少年游
 * Copyright (c) VernonSong. All rights reserved.
 * -----------------------------------------------------------------------------
 */

package com.regulus.interfaces.assembler;

import com.regulus.domain.core.stock.model.StockPriceDaily;
import com.regulus.interfaces.dto.SaveStockPriceDailyDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/** SaveStockPriceDailyDto 类型转化器 */
@Mapper(componentModel = "spring")
public interface SaveStockPriceDailyDtoMapper {
    /**
     * SaveStockPriceDailyDto -> StockPriceDaily
     *
     * @param dto SaveStockPriceDailyDto
     * @return StockPriceDaily
     */
    @Mappings({
        @Mapping(target = "stockDailyId.stockCode", source = "stockCode"),
        @Mapping(target = "stockDailyId.tradeDate", source = "tradeDate"),
    })
    StockPriceDaily dtoToModel(SaveStockPriceDailyDto dto);
}

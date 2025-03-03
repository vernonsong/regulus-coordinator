/*
 * 欲买桂花同载酒
 * 终不似，少年游
 * Copyright (c) VernonSong. All rights reserved.
 * -----------------------------------------------------------------------------
 */
package com.regulus.infrastructure.assembler;

import com.regulus.domain.core.data.model.StockAlpha191Daily;
import com.regulus.infrastructure.persistence.StockAlpha191DailyDo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StockAlpha191DailyMapper {
    StockAlpha191DailyDo toStockAlpha191DailyDo(StockAlpha191Daily stockAlpha191Daily);
}

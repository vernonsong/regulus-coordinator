/*
 * 欲买桂花同载酒
 * 终不似，少年游
 * Copyright (c) VernonSong. All rights reserved.
 * -----------------------------------------------------------------------------
 */
package com.regulus.interfaces.assembler;

import com.regulus.domain.core.data.model.StockAlpha191Daily;
import com.regulus.interfaces.dto.SaveStockAlpha191DailyDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SaveStockAlpha191DailyDtoMapper {
    StockAlpha191Daily toSaveStockAlpha191Daily(SaveStockAlpha191DailyDto doctor);
}

/*
 * 欲买桂花同载酒
 * 终不似，少年游
 * Copyright (c) VernonSong. All rights reserved.
 * -----------------------------------------------------------------------------
 */

package com.regulus.domain.core.data.repository;

import com.regulus.domain.core.data.model.StockAlpha191Daily;
import java.util.List;

public interface StockAlpha191DailyRepository {
    void batchSave(List<StockAlpha191Daily> dataList);
}

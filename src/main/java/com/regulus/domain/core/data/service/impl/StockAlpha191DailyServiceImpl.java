/*
 * 欲买桂花同载酒
 * 终不似，少年游
 * Copyright (c) VernonSong. All rights reserved.
 * -----------------------------------------------------------------------------
 */
package com.regulus.domain.core.data.service.impl;

import com.regulus.domain.core.data.model.StockAlpha191Daily;
import com.regulus.domain.core.data.repository.StockAlpha191DailyRepository;
import com.regulus.domain.core.data.service.StockAlpha191DailyService;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class StockAlpha191DailyServiceImpl implements StockAlpha191DailyService {
    @Autowired
    StockAlpha191DailyRepository stockAlpha191DailyRepository;

    @Transactional
    @Override
    public void saveStockAlpha191Daily(List<StockAlpha191Daily> dataList) {

        stockAlpha191DailyRepository.batchSave(dataList);
    }
}

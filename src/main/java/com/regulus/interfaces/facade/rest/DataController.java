/*
 * 欲买桂花同载酒
 * 终不似，少年游
 * Copyright (c) VernonSong. All rights reserved.
 * -----------------------------------------------------------------------------
 */
package com.regulus.interfaces.facade.rest;

import com.alibaba.fastjson2.JSONObject;
import com.regulus.domain.core.stock.model.StockPriceDaily;
import com.regulus.domain.core.stock.service.StockPriceDailyService;
import com.regulus.interfaces.assembler.SaveStockPriceDailyDtoMapper;
import com.regulus.interfaces.dto.SaveStockPriceDailyDto;
import jakarta.annotation.Resource;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** 数据相关 controller. */
@Slf4j
@RestController
@RequestMapping("/data")
public class DataController {
    @Resource private StockPriceDailyService stockPriceDailyService;

    @Resource private SaveStockPriceDailyDtoMapper saveStockPriceDailyDTOMapper;

    /**
     * 保存每日股市行情
     *
     * @param dtoList 每日股票行情数据列表
     * @return 返回体
     */
    @PostMapping("/price/save")
    public JSONObject saveStockPriceDaily(@RequestBody List<SaveStockPriceDailyDto> dtoList) {
        List<StockPriceDaily> stockAlpha191DailyList =
                dtoList.stream().map(x -> saveStockPriceDailyDTOMapper.dtoToModel(x)).toList();
        stockPriceDailyService.saveStockPriceDaily(stockAlpha191DailyList);
        log.info("POST /data/price/save");
        ResponseEntity.ok("行情数据存储成功");
        return null;
    }
}

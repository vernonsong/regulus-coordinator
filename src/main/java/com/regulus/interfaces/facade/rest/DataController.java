/*
 * 欲买桂花同载酒
 * 终不似，少年游
 * Copyright (c) VernonSong. All rights reserved.
 * -----------------------------------------------------------------------------
 */
package com.regulus.interfaces.facade.rest;

import com.alibaba.fastjson2.JSONObject;
import com.regulus.domain.core.data.model.StockAlpha191Daily;
import com.regulus.domain.core.data.service.StockAlpha191DailyService;
import com.regulus.interfaces.assembler.SaveStockAlpha191DailyDtoMapper;
import com.regulus.interfaces.dto.SaveStockAlpha191DailyDto;
import jakarta.annotation.Resource;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/data")
public class DataController {
    @Resource
    StockAlpha191DailyService stockAlpha191DailyService;

    @Resource
    SaveStockAlpha191DailyDtoMapper saveStockAlpha191DailyDTOMapper;

    @PostMapping("/alpha191/save")
    public JSONObject saveStockAlpha191Daily(@RequestBody List<SaveStockAlpha191DailyDto> dtoList) {

        List<StockAlpha191Daily> stockAlpha191DailyList = dtoList.stream()
                .map(x -> saveStockAlpha191DailyDTOMapper.toSaveStockAlpha191Daily(x)).toList();
        stockAlpha191DailyService.saveStockAlpha191Daily(stockAlpha191DailyList);
        log.info("POST /data/alpha191/save");
        ResponseEntity.ok("alpha191数据存储成功");
        return null;
    }
}

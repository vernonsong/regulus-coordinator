/*
 * 欲买桂花同载酒
 * 终不似，少年游
 * Copyright (c) VernonSong. All rights reserved.
 * -----------------------------------------------------------------------------
 */

package com.regulus.interfaces.facade.rest;

import com.alibaba.fastjson2.JSONObject;
import com.regulus.domain.core.ai.service.StrategyDailyService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** 策略相关 controller. */
@Slf4j
@RestController
@RequestMapping("/strategy")
public class StrategyController {
    @Resource private StrategyDailyService strategyDailyService;

    /**
     * 保存每日策略打分
     *
     * @param score 每日股票行情数据列表
     * @return 返回体
     */
    @PostMapping("/score/save")
    public JSONObject saveStockPriceDaily(@RequestBody JSONObject score) {

        strategyDailyService.saveStrategyScoreDaily(score.toJSONString());
        log.info("POST /strategy/score/save");
        ResponseEntity.ok("策略打分存储成功");
        return null;
    }
}

/*
 * 欲买桂花同载酒
 * 终不似，少年游
 * Copyright (c) VernonSong. All rights reserved.
 * -----------------------------------------------------------------------------
 */

package com.regulus.infrastructure.integration.ai;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.regulus.domain.core.ai.model.RecognizeResult;
import com.regulus.domain.core.ai.service.AiService;
import com.regulus.domain.generic.service.ExternalOpenapiService;
import com.regulus.infrastructure.util.HttpUtil;
import jakarta.annotation.Resource;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

/** Regulus AI服务 接口层实现 */
@Service
public class RegulusQuantService implements AiService {
    @Resource private ExternalOpenapiService externalOpenapiService;

    @Resource private HttpUtil httpUtil;

    @Override
    public RecognizeResult getRecognizeResult(String imgPath) {
        String apiUrl = externalOpenapiService.getApiUrl("Regulus", "recognize");
        Map<String, String> params = new HashMap<>();
        params.put("img_path", imgPath);
        String result = httpUtil.post(apiUrl, null, JSON.toJSONString(params));
        return JSON.parseObject(result, RecognizeResult.class);
    }

    @Override
    public String getPreMarketStrategy(LocalDate tradeDate) {
        String apiUrl = externalOpenapiService.getApiUrl("Regulus", "pre_market_strategy");
        Map<String, Object> params = new HashMap<>();
        params.put("trade_date", tradeDate);

        String result = httpUtil.post(apiUrl, null, JSON.toJSONString(params));
        return JSONObject.parseObject(result).getString("content");
    }
}

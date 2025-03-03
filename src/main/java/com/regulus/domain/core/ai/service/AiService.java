/*
 * 欲买桂花同载酒
 * 终不似，少年游
 * Copyright (c) VernonSong. All rights reserved.
 * -----------------------------------------------------------------------------
 */

package com.regulus.domain.core.ai.service;

import com.regulus.domain.core.ai.model.RecognizeResult;
import java.time.LocalDate;

/** AI服务 接口 */
public interface AiService {
    /**
     * 获取图片识别结果
     *
     * @param imgPath 图片路径【本地】
     * @return 图片识别结果
     */
    RecognizeResult getRecognizeResult(String imgPath);

    /**
     * 获取盘前策略
     *
     * @param tradeDate 交易日期
     * @return 盘前策略
     */
    String getPreMarketStrategy(LocalDate tradeDate);
}

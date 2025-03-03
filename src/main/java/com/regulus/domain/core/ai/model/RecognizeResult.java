/*
 * 欲买桂花同载酒
 * 终不似，少年游
 * Copyright (c) VernonSong. All rights reserved.
 * -----------------------------------------------------------------------------
 */

package com.regulus.domain.core.ai.model;

import lombok.Data;

/** 识别结果 数据类 */
@Data
public class RecognizeResult {
    private ImgType type;

    private String content;

    RecognizeResult(String type, String content) {
        this.type = ImgType.fromValue(type);
        this.content = content;
    }
}

/*
 * 欲买桂花同载酒
 * 终不似，少年游
 * Copyright (c) VernonSong. All rights reserved.
 * -----------------------------------------------------------------------------
 */

package com.regulus.domain.core.ai.model;

import lombok.Getter;

/** 图片类型 枚举类 */
@Getter
public enum ImgType {
    /** 研报分析 */
    ANALYZE("analyze"),
    /** 持仓信息 */
    POSITION("position");

    private final String value;

    ImgType(String value) {
        this.value = value;
    }

    /**
     * 通过值获取枚举实例的静态方法
     *
     * @param value 图片类型值
     * @return ImgType
     */
    public static ImgType fromValue(String value) {
        for (ImgType imgType : values()) {
            if (imgType.getValue().equals(value)) {
                return imgType;
            }
        }
        throw new IllegalArgumentException("Unknown value: " + value);
    }
}

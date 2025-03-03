/*
 * 欲买桂花同载酒
 * 终不似，少年游
 * Copyright (c) VernonSong. All rights reserved.
 * -----------------------------------------------------------------------------
 */

package com.regulus.infrastructure.util;

import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/** UUID 工具类 */
@Component
@Slf4j
public class UuidUtil {
    /**
     * 生成UUID文件名
     *
     * @param fileExtension 文件后缀
     * @return uuid文件名
     */
    public static String generateUUIDFileName(String fileExtension) {
        UUID uuid = UUID.randomUUID();
        return uuid + "." + fileExtension;
    }
}

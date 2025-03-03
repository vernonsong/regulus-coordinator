/*
 * 欲买桂花同载酒
 * 终不似，少年游
 * Copyright (c) VernonSong. All rights reserved.
 * -----------------------------------------------------------------------------
 */

package com.regulus.infrastructure.util;

import jakarta.annotation.Resource;
import java.io.FileOutputStream;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/** 图片 工具类 */
@Component
@Slf4j
public class ImgUtil {
    @Resource private HttpUtil httpUtil;

    private void imgToFile(byte[] imageBytes, String filePath) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            fos.write(imageBytes);
        }
    }

    /**
     * 从url中下载图片到本地
     *
     * @param imgUrl url
     * @param imgPath 本地路径
     * @throws Exception 异常
     */
    public void imgUrlToFile(String imgUrl, String imgPath) throws Exception {
        byte[] imageBytes = httpUtil.getAsBytes(imgUrl, null, null);
        imgToFile(imageBytes, imgPath);
    }
}

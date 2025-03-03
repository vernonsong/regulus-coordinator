/*
 * 欲买桂花同载酒
 * 终不似，少年游
 * Copyright (c) VernonSong. All rights reserved.
 * -----------------------------------------------------------------------------
 */

package com.regulus.domain.generic.service;

import com.regulus.domain.generic.repository.ExternalOpenapiRepository;
import jakarta.annotation.Resource;
import java.util.Optional;
import org.springframework.stereotype.Service;

/** 其它系统对外API 相关操作类 */
@Service
public class ExternalOpenapiService {

    @Resource private ExternalOpenapiRepository externalOpenapiRepository;

    /**
     * 根据服务名和API名获取API URL
     *
     * @param serviceName 服务名
     * @param apiName API名
     * @return API URL
     */
    public String getApiUrl(String serviceName, String apiName) {
        Optional<String> url = externalOpenapiRepository.findByServiceAndApiName(serviceName, apiName);
        // 校验结果并返回
        if (url.isEmpty()) {
            throw new IllegalArgumentException(
                    "未查询到指定的 API 信息：" + "serviceName=" + serviceName + ", apiName=" + apiName);
        }
        return url.get();
    }
}

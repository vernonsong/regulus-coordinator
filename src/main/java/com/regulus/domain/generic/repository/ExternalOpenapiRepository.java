/*
 * 欲买桂花同载酒
 * 终不似，少年游
 * Copyright (c) VernonSong. All rights reserved.
 * -----------------------------------------------------------------------------
 */

package com.regulus.domain.generic.repository;

import java.util.Optional;

/** 其它系统对外API 仓储层接口 */
public interface ExternalOpenapiRepository {
    /**
     * 根据服务名和API名获取API URL
     *
     * @param serviceName 服务名
     * @param apiName API名
     * @return API URL
     */
    Optional<String> findByServiceAndApiName(String serviceName, String apiName);
}

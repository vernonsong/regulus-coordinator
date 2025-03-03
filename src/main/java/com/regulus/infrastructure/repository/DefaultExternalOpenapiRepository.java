/*
 * 欲买桂花同载酒
 * 终不似，少年游
 * Copyright (c) VernonSong. All rights reserved.
 * -----------------------------------------------------------------------------
 */

package com.regulus.infrastructure.repository;

import static com.regulus.infrastructure.config.CacheConfig.CACHE_24H;

import com.regulus.domain.generic.repository.ExternalOpenapiRepository;
import com.regulus.infrastructure.repository.mapper.ExternalOpenapiDoMapper;
import jakarta.annotation.Resource;
import java.util.Optional;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/** 其它系统对外API 仓储层实现 */
@Service
public class DefaultExternalOpenapiRepository implements ExternalOpenapiRepository {
    @Resource private ExternalOpenapiDoMapper externalOpenapiDoMapper;

    @Override
    @Cacheable(value = CACHE_24H, key = "#serviceName + ':' + #apiName")
    public Optional<String> findByServiceAndApiName(String serviceName, String apiName) {
        return Optional.ofNullable(
                externalOpenapiDoMapper.selectByServiceNameAndApiName(serviceName, apiName).getApiUrl());
    }
}

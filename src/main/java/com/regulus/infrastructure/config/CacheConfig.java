package com.regulus.infrastructure.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import java.util.concurrent.TimeUnit;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** 缓存配置 */
@Configuration
@EnableCaching
public class CacheConfig {
    public static final String CACHE_24H = "twentyFourHoursCache";
    public static final String CACHE_1H = "oneHourCache";
    public static final String CACHE_60S = "sixtySecondsCache";

    /**
     * 本地缓存配置
     *
     * @return CaffeineCacheManager
     */
    @Bean
    public CaffeineCacheManager localCacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.registerCustomCache(
                CACHE_24H,
                Caffeine.newBuilder().expireAfterWrite(24, TimeUnit.HOURS).maximumSize(100).build());
        cacheManager.registerCustomCache(
                CACHE_1H,
                Caffeine.newBuilder().expireAfterWrite(1, TimeUnit.HOURS).maximumSize(100).build());
        cacheManager.registerCustomCache(
                CACHE_60S,
                Caffeine.newBuilder().expireAfterWrite(60, TimeUnit.SECONDS).maximumSize(100).build());
        return cacheManager;
    }
}

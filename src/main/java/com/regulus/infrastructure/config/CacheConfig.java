package com.regulus.infrastructure.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import java.util.concurrent.TimeUnit;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CacheConfig {
    @Bean
    public CaffeineCacheManager localCacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.registerCustomCache("24HoursLocalCache",
                Caffeine.newBuilder().expireAfterWrite(24, TimeUnit.HOURS).maximumSize(100).build());
        cacheManager.registerCustomCache("1HourLocalCache",
                Caffeine.newBuilder().expireAfterWrite(1, TimeUnit.HOURS).maximumSize(100).build());
        cacheManager.registerCustomCache("60SecondLocalCache",
                Caffeine.newBuilder().expireAfterWrite(60, TimeUnit.SECONDS).maximumSize(100).build());
        return cacheManager;
    }
}

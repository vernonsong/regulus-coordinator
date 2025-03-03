/*
 * 欲买桂花同载酒
 * 终不似，少年游
 * Copyright (c) VernonSong. All rights reserved.
 * -----------------------------------------------------------------------------
 */
package com.regulus.infrastructure.config;

import jakarta.annotation.PreDestroy;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/** 线程池配置 */
@Configuration
@EnableAsync
public class ThreadPoolConfig {
    // 线程名称前缀
    private static final String FIXED_POOL_NAME = "fixed-task-pool";
    private static final String CACHE_POOL_NAME = "cached-task-pool";
    private static final String SCHEDULED_POOL_NAME = "scheduled-task-pool";
    private static final String SINGLE_POOL_NAME = "single-task-pool";

    // 配置参数（从application.properties读取）
    @Value("${thread-pool.fixed.core-size:10}")
    private int fixedCorePoolSize;

    @Value("${thread-pool.fixed.max-size:20}")
    private int fixedMaxPoolSize;

    @Value("${thread-pool.fixed.queue-capacity:1000}")
    private int fixedQueueCapacity;

    @Value("${thread-pool.cached.keep-alive-seconds:60}")
    private int cachedKeepAliveSeconds;

    @Value("${thread-pool.scheduled.core-size:5}")
    private int scheduledCorePoolSize;

    // 线程工厂创建方法（使用Lambda）
    private ThreadFactory threadFactory(String poolName) {
        AtomicInteger threadNumber = new AtomicInteger(1);
        return runnable -> {
            Thread thread = new Thread(runnable);
            thread.setName(poolName + "-thread-" + threadNumber.getAndIncrement());
            thread.setDaemon(true);
            return thread;
        };
    }

    /**
     * 定义固定大小的线程池
     *
     * @return ThreadPoolTaskExecutor
     */
    @Bean("fixedThreadPool")
    public ThreadPoolTaskExecutor fixedThreadPool() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(fixedCorePoolSize);
        executor.setMaxPoolSize(fixedMaxPoolSize);
        executor.setQueueCapacity(fixedQueueCapacity);
        executor.setThreadFactory(threadFactory(FIXED_POOL_NAME));
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }

    /**
     * 定义缓存线程池
     *
     * @return ThreadPoolTaskExecutor
     */
    @Bean("cachedThreadPool")
    public ThreadPoolTaskExecutor cachedThreadPool() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(0);
        executor.setMaxPoolSize(Integer.MAX_VALUE);
        executor.setKeepAliveSeconds(cachedKeepAliveSeconds);
        executor.setThreadFactory(threadFactory(CACHE_POOL_NAME));
        executor.initialize();
        return executor;
    }

    /**
     * 定义调度线程池
     *
     * @return ScheduledThreadPoolExecutor
     */
    @Bean("scheduledThreadPool")
    public ScheduledThreadPoolExecutor scheduledThreadPool() {
        ScheduledThreadPoolExecutor executor =
                new ScheduledThreadPoolExecutor(scheduledCorePoolSize, threadFactory(SCHEDULED_POOL_NAME));
        executor.setRemoveOnCancelPolicy(true);
        return executor;
    }

    /**
     * 定义单线程池
     *
     * @return ThreadPoolTaskExecutor
     */
    @Bean("singleThreadPool")
    public ThreadPoolTaskExecutor singleThreadPool() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(1);
        executor.setMaxPoolSize(1);
        executor.setQueueCapacity(100);
        executor.setThreadFactory(threadFactory(SINGLE_POOL_NAME));
        executor.initialize();
        return executor;
    }

    /** 关闭线程池 */
    @PreDestroy
    public void shutdown() {
        // 固定线程池：ThreadPoolTaskExecutor 实现了 Executor，但 shutdown() 是父类方法
        fixedThreadPool().shutdown(); // ThreadPoolTaskExecutor 继承自 ThreadPoolExecutor，有 shutdown()
        cachedThreadPool().shutdown();
        singleThreadPool().shutdown();

        // 调度线程池：ScheduledThreadPoolExecutor 有 shutdown()
        if (scheduledThreadPool() != null) {
            scheduledThreadPool().shutdown();
        }
    }
}

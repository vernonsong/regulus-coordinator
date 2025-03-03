package com.regulus.infrastructure.config;

import jakarta.annotation.Resource;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

/** 调度配置 */
@Configuration
@EnableScheduling
public class ScheduleConfig implements SchedulingConfigurer {

    @Resource(name = "scheduledThreadPool")
    private ScheduledThreadPoolExecutor scheduledThreadPool;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(scheduledThreadPool);
    }
}

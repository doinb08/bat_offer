package com.doinb.executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
@EnableScheduling
public class AsyncTaskConfig implements AsyncConfigurer {

    /**
     * 核心线程数
     */
    private static final int CORE_POOL_SIZE = Runtime.getRuntime().availableProcessors();
    /**
     * 最大线程数
     */
    private static final int MAX_POOL_SIZE = 100;
    /**
     * 队列容量
     */
    private static final int QUEUE_CAPACITY = 5000;
    /**
     * 最大空闲时间
     */
    private static final int KEEPALIVE_SECONDS = 60;

    /**
     * 创建线程
     */
    @Override
    @Bean(name = "asyncExecutor")
    public AsyncTaskExecutor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //当前线程数
        executor.setCorePoolSize(CORE_POOL_SIZE);
        // 最大线程数
        executor.setMaxPoolSize(MAX_POOL_SIZE);
        //线程池所使用的缓冲队列大小
        executor.setQueueCapacity(QUEUE_CAPACITY);
        //线程最大空闲时间
        executor.setKeepAliveSeconds(KEEPALIVE_SECONDS);
        //指定用于新创建的线程名称的前缀。
        executor.setThreadNamePrefix("bat-Async-Executor-");
        //初始化
        executor.initialize();

        return executor;
    }

}

package de.bht_berlin.paf2023.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;


@Configuration
@EnableAsync
public class AsyncConfig implements AsyncConfigurer {

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // number of minimum workers to keep alive
        executor.setCorePoolSize(5);
        // number of max number of threads that can be created
        executor.setMaxPoolSize(10);
        // max number of threads for queue
        executor.setQueueCapacity(25);
        // thread naming
        executor.setThreadNamePrefix("MeasurementCreation-");
        executor.initialize();
        return executor;
    }
}
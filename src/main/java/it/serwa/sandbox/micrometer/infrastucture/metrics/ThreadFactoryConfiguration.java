package it.serwa.sandbox.micrometer.infrastucture.metrics;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.concurrent.ThreadFactory;

@EnableAsync
@Configuration
class ThreadFactoryConfiguration {

    @Bean
    ThreadFactory threadFactory(@Value("${scheduler.thread.pool.size:2}") int threadPoolSize) {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(threadPoolSize);
        threadPoolTaskScheduler.initialize();
        return threadPoolTaskScheduler;
    }
}

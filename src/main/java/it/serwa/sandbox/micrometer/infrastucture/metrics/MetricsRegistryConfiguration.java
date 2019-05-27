package it.serwa.sandbox.micrometer.infrastucture.metrics;

import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.MeterRegistry;
import it.serwa.sandbox.micrometer.application.domain.BookFacade;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.util.concurrent.ThreadFactory;

@Configuration
@EnableAspectJAutoProxy
class MetricsRegistryConfiguration {
    private final MeterRegistry meterRegistry;
    private final BookFacade bookFacade;
    private final ThreadFactory threadFactory;

    public MetricsRegistryConfiguration(MeterRegistry meterRegistry, BookFacade bookFacade, ThreadFactory threadFactory) {
        this.meterRegistry = meterRegistry;
        this.bookFacade = bookFacade;
        this.threadFactory = threadFactory;
    }

    @Bean
    MetricsRegistry metricsRegistry() {
        return new MetricsRegistry(meterRegistry, bookFacade, threadFactory);
    }

    @Bean
    TimedAspect timedAspect() {
        return new TimedAspect(meterRegistry);
    }
}
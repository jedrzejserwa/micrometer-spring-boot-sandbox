package it.serwa.sandbox.micrometer.infrastucture.metrics;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import it.serwa.sandbox.micrometer.application.domain.BookFacade;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

class MetricsRegistry {
    private final MeterRegistry registry;
    private final BookFacade bookFacade;
    private final ThreadFactory threadFactory;

    MetricsRegistry(MeterRegistry registry, BookFacade bookFacade, ThreadFactory threadFactory) {
        this.registry = registry;
        this.bookFacade = bookFacade;
        this.threadFactory = threadFactory;

        initializeMicrometerCustomMeasures();
    }

    private void initializeMicrometerCustomMeasures() {
        measureLibrarySize();
        measureBooksFindingFlow();
    }

    private void measureLibrarySize() {
        registry.gauge("library.library-size", bookFacade, BookFacade::getLibrarySize);
    }

    private void measureBooksFindingFlow() {
        Executors.newSingleThreadScheduledExecutor(threadFactory).scheduleWithFixedDelay(() -> {
            Timer timer = Timer
                    .builder("library.book.finding.timer")
                    .description("a timer that measures all book finding process")
                    .tags("books", "finding")
                    .register(registry);

            timer.record(bookFacade::findAll);
        }, 1, 60, TimeUnit.SECONDS);
    }
}

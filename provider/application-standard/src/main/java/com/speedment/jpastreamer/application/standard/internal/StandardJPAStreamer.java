package com.speedment.jpastreamer.application.standard.internal;

import static java.util.Objects.requireNonNull;

import com.speedment.jpastreamer.analytics.AnalyticsReporter;
import com.speedment.jpastreamer.appinfo.ApplicationInformation;
import com.speedment.jpastreamer.application.JPAStreamer;
import com.speedment.jpastreamer.rootfactory.RootFactory;

import javax.persistence.EntityManagerFactory;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

final class StandardJPAStreamer implements JPAStreamer {

    private final EntityManagerFactory entityManagerFactory;
    private final Map<Class<?>, Streamer<?>> streamerCache;
    private final AnalyticsReporter analyticsReporter;

    StandardJPAStreamer(final EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = requireNonNull(entityManagerFactory);
        streamerCache = new ConcurrentHashMap<>();

        analyticsReporter = RootFactory.getOrThrow(AnalyticsReporter.class, ServiceLoader::load);
        final ApplicationInformation applicationInformation = RootFactory.getOrThrow(ApplicationInformation.class, ServiceLoader::load);
        analyticsReporter.start(applicationInformation.implementationVersion());
        printGreeting(applicationInformation);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> Stream<T> stream(Class<T> entityClass) {
        requireNonNull(entityClass);
        return (Stream<T>) streamerCache
                .computeIfAbsent(entityClass, ec -> new StandardStreamer<>(entityClass, entityManagerFactory))
                .stream();
    }

    @Override
    public void close() {
        analyticsReporter.stop();
        streamerCache.values().forEach(Streamer::close);
    }

    private void printGreeting(final ApplicationInformation info) {
        final String greeting = String.format("%s%n" +
                        ":: %s %s :: %s%n" +
                        "Copyright %s%n" +
                        "Licensed under %s%n" +
                        "Running under %s %s",
                info.banner(),
                info.title(), info.subtitle(), info.implementationVersion(),
                info.vendor(),
                info.licenseName(),
                System.getProperty("java.runtime.name"), System.getProperty("java.runtime.version")
        );
        System.out.println(greeting);
    }

}
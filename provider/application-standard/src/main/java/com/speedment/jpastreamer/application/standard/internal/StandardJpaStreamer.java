package com.speedment.jpastreamer.application.standard.internal;

import com.speedment.jpastreamer.application.JpaStreamer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

public final class StandardJpaStreamer implements JpaStreamer {

    private final String persistenceUnitName;
    private final Map<Class<?>, Streamer<?>> streamerCache;

    public StandardJpaStreamer(final String persistenceUnitName) {
        this.persistenceUnitName = requireNonNull(persistenceUnitName);
        streamerCache = new ConcurrentHashMap<>();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> Stream<T> stream(Class<T> entityClass) {
        requireNonNull(entityClass);
        return (Stream<T>) streamerCache
                .computeIfAbsent(entityClass, ec -> new StandardStreamer<>(entityClass, persistenceUnitName))
                .stream();
    }

    @Override
    public void stop() {
        streamerCache.values().forEach(Streamer::close);
    }
}
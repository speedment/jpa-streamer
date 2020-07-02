package com.speedment.jpastreamer.application.standard.internal;

import static java.util.Objects.requireNonNull;

import com.speedment.jpastreamer.application.JPAStreamer;

import javax.persistence.EntityManagerFactory;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

final class StandardJPAStreamer implements JPAStreamer {

    private final EntityManagerFactory entityManagerFactory;
    private final Map<Class<?>, Streamer<?>> streamerCache;

    StandardJPAStreamer(final EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = requireNonNull(entityManagerFactory);
        streamerCache = new ConcurrentHashMap<>();
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
        streamerCache.values().forEach(Streamer::close);
    }
}

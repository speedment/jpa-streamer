package com.speedment.jpastreamer.application;

import java.util.ServiceLoader;
import java.util.stream.Stream;

public interface JpaStreamer {

    <T> Stream<T> stream(Class<T> entityClass);

    void stop();

    static JpaStreamerBuilder builder() {
        return ServiceLoader.load(JpaStreamerBuilder.class).iterator().next();
    }

}
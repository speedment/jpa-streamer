package com.speedment.jpastreamer.application;

import java.util.ServiceLoader;

public interface JpaStreamer {

    <T> Streamer<T> streamer(Class<T> entity);

    static JpaStreamerBuilder builder() {
        return ServiceLoader.load(JpaStreamerBuilder.class).iterator().next();
    }

}

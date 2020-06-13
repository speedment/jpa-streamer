package com.speedment.jpastreamer.application.standard.internal;

import java.util.stream.Stream;

interface Streamer<E> {

    Stream<E> stream();

    void close();
}

package com.speedment.jpastreamer.application;

import java.util.stream.Stream;

public interface Streamer<T> {

    Stream<T> stream();
}

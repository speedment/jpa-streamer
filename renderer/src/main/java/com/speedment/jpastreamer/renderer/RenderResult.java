package com.speedment.jpastreamer.renderer;

import com.speedment.jpastreamer.pipeline.terminating.TerminatingOperation;

import java.util.stream.Stream;

public interface RenderResult<T> {

    Stream<T> stream();

    TerminatingOperation<?, ?> terminatingOperation();

}

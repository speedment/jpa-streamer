package com.speedment.jpastreamer.pipeline.intermediate;

import com.speedment.jpastreamer.pipeline.trait.*;

import java.util.stream.BaseStream;

public interface IntermediateOperator<S extends BaseStream<?, S>, R extends BaseStream<?, R>> extends
        HasType<IntermediateOperationType>,
        HasStreamType<S>,
        HasReturnType<R>,
        HasArguments,
        HasFunction<S, R> {}
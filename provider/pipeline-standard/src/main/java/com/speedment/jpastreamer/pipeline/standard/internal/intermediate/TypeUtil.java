package com.speedment.jpastreamer.pipeline.standard.internal.intermediate;

import com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperation;

import java.util.stream.BaseStream;

enum TypeUtil {;

    @SuppressWarnings("unchecked")
    static  <S extends BaseStream<?, S>, R extends BaseStream<?, R>> IntermediateOperation<S, R> typed(final IntermediateOperation<?, ?> terminalOperation) {
        return (IntermediateOperation<S, R>) terminalOperation;
    }

}

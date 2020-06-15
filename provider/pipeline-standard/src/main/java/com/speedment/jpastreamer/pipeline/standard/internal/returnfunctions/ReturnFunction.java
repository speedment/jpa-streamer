package com.speedment.jpastreamer.pipeline.standard.internal.returnfunctions;

public interface ReturnFunction<R> {

    R castToTyped(Object untypedFunction);

}
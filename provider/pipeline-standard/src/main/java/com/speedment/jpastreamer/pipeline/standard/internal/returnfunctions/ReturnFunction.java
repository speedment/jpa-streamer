package com.speedment.jpastreamer.pipeline.standard.internal;

public final interface ReturnFunction {


    FUNCTION, LONG_FUNCTION, INT_FUNCTION, DOUBLE_FUNCTION, PREDICATE, CONSUMER;

    Object castToTyped(Object untypedFunction);

}

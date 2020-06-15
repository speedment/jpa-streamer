package com.speedment.jpastreamer.pipeline.standard;

import com.speedment.jpastreamer.pipeline.IntermediateOperationFactory;
import com.speedment.jpastreamer.pipeline.action.Action;
import com.speedment.jpastreamer.pipeline.standard.internal.InternalStandardIntermediateOperationFactory;

import java.util.Comparator;
import java.util.function.*;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public final class StandardIntermediateOperationFactory {

    private final IntermediateOperationFactory delegate = new InternalStandardIntermediateOperationFactory();


}
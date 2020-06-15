package com.speedment.jpastreamer.pipeline.standard;

import com.speedment.jpastreamer.pipeline.IntermediateOperationFactory;
import com.speedment.jpastreamer.pipeline.standard.internal.InternalIntermediateOperationFactory;

public final class StandardIntermediateOperationFactory {

    private final IntermediateOperationFactory delegate = new InternalIntermediateOperationFactory();


}
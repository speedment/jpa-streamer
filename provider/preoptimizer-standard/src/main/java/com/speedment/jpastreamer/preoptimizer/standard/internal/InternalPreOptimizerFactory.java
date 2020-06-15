package com.speedment.jpastreamer.preoptimizer.standard.internal;

import com.speedment.jpastreamer.preoptimizer.PreOptimizer;
import com.speedment.jpastreamer.preoptimizer.PreOptimizerFactory;

import java.util.stream.Stream;

public final class InternalPreOptimizerFactory implements PreOptimizerFactory {

    public Stream<PreOptimizer> stream() {
        return Stream.empty();
    }


}

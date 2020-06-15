package com.speedment.jpastreamer.preoptimizer.standard;

import com.speedment.jpastreamer.preoptimizer.PreOptimizerFactory;
import com.speedment.jpastreamer.preoptimizer.PreOptimizer;
import com.speedment.jpastreamer.preoptimizer.standard.internal.InternalPreOptimizerFactory;

import java.util.stream.Stream;

public final class StandardPreOptimizerFactory implements PreOptimizerFactory {

    private final PreOptimizerFactory delegate = new InternalPreOptimizerFactory();

    public Stream<PreOptimizer> stream() {
        return delegate.stream();
    }


}

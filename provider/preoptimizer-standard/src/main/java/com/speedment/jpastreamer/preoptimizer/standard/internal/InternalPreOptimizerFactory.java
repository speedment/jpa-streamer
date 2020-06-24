package com.speedment.jpastreamer.preoptimizer.standard.internal;

import com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperationFactory;
import com.speedment.jpastreamer.preoptimizer.PreOptimizer;
import com.speedment.jpastreamer.preoptimizer.PreOptimizerFactory;
import com.speedment.jpastreamer.preoptimizer.standard.internal.strategy.RemovePeek;
import com.speedment.jpastreamer.preoptimizer.standard.internal.strategy.SquashFilter;
import com.speedment.jpastreamer.preoptimizer.standard.internal.strategy.SquashLimit;
import com.speedment.jpastreamer.preoptimizer.standard.internal.strategy.SquashSkip;
import com.speedment.jpastreamer.rootfactory.RootFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public final class InternalPreOptimizerFactory implements PreOptimizerFactory {

    private final List<PreOptimizer> preOptimizers = new ArrayList<>();

    public InternalPreOptimizerFactory() {
        final IntermediateOperationFactory intermediateOperationFactory = RootFactory
            .getOrThrow(IntermediateOperationFactory.class);

        preOptimizers.add(new SquashSkip(intermediateOperationFactory));
        preOptimizers.add(new SquashLimit(intermediateOperationFactory));
        preOptimizers.add(new SquashFilter(intermediateOperationFactory));
    }

    public Stream<PreOptimizer> stream() {
        return preOptimizers.stream();
    }

}

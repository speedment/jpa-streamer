package com.speedment.jpastreamer.preoptimizer;

import java.util.stream.Stream;

public interface PreOptimizerFactory {

    Stream<PreOptimizer> stream();

}

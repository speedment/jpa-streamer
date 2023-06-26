/*
 * JPAstreamer - Express JPA queries with Java Streams
 * Copyright (c) 2020-2022, Speedment, Inc. All Rights Reserved.
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * See: https://github.com/speedment/jpa-streamer/blob/master/LICENSE
 */
package com.speedment.jpastreamer.termopmodifier.standard.internal;

import com.speedment.jpastreamer.field.predicate.SpeedmentPredicate;
import com.speedment.jpastreamer.pipeline.Pipeline;
import com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperationFactory;
import com.speedment.jpastreamer.pipeline.terminal.TerminalOperation;
import com.speedment.jpastreamer.pipeline.terminal.TerminalOperationFactory;
import com.speedment.jpastreamer.pipeline.terminal.TerminalOperationType;
import com.speedment.jpastreamer.rootfactory.RootFactory;
import com.speedment.jpastreamer.termopmodifier.TerminalOperationModifier;

import java.util.Optional;
import java.util.ServiceLoader;

import static java.util.Objects.requireNonNull;

final class StandardTerminalOperatorModifier implements TerminalOperationModifier {

    private final IntermediateOperationFactory intermediateOperationFactory;
    private final TerminalOperationFactory terminalOperationFactory;

    StandardTerminalOperatorModifier() {
        this.intermediateOperationFactory = RootFactory.getOrThrow(IntermediateOperationFactory.class, ServiceLoader::load);
        this.terminalOperationFactory = RootFactory.getOrThrow(TerminalOperationFactory.class, ServiceLoader::load);
    }
    
    @Override
    public <T> Pipeline<T> modify(Pipeline<T> pipeline) {
        requireNonNull(pipeline);

        final TerminalOperationType terminalOperationType = pipeline.terminatingOperation().type();

        switch (terminalOperationType) {
            case ANY_MATCH:
                return modifyAnyMatch(pipeline);
            case NONE_MATCH:
                return modifyNoneMatch(pipeline);
            case FIND_FIRST:
                return modifyFindFirst(pipeline);
            case FIND_ANY:
                return modifyFindAny(pipeline);
            default:
                return pipeline;
        }
    }
    
    private <T> Pipeline<T> modifyAnyMatch(Pipeline<T> pipeline) {
        this.<T>getPredicate(pipeline.terminatingOperation()).ifPresent(speedmentPredicate -> {
            pipeline.intermediateOperations().add(intermediateOperationFactory.createFilter(speedmentPredicate));
            pipeline.intermediateOperations().add(intermediateOperationFactory.createLimit(1));
            pipeline.terminatingOperation(terminalOperationFactory.createAnyMatch(p -> true));
        });
        return pipeline;
    }

    private <T> Pipeline<T> modifyNoneMatch(Pipeline<T> pipeline) {
        this.<T>getPredicate(pipeline.terminatingOperation()).ifPresent(speedmentPredicate -> {
            pipeline.intermediateOperations().add(intermediateOperationFactory.createFilter(speedmentPredicate));
            pipeline.intermediateOperations().add(intermediateOperationFactory.createLimit(1));
            // NoneMatch() - If the stream is empty then true is returned and the predicate is not evaluated. 
            // If the expression is evaluated => There is a match and the expression is always false. 
            pipeline.terminatingOperation(terminalOperationFactory.createNoneMatch(e -> true));
        });
        return pipeline;
    }

    private <T> Pipeline<T> modifyFindFirst(Pipeline<T> pipeline) {
        pipeline.intermediateOperations().add(intermediateOperationFactory.createLimit(1));
        return pipeline;
    }

    private <T> Pipeline<T> modifyFindAny(Pipeline<T> pipeline) {
        pipeline.ordered(false);
        pipeline.intermediateOperations().add(intermediateOperationFactory.createLimit(1));
        return pipeline;
    }

    private <T> Optional<SpeedmentPredicate<T>> getPredicate(final TerminalOperation<?, ?> operation) {
        final Object[] arguments = operation.arguments();

        if (arguments.length != 1) {
            return Optional.empty();
        }

        if (arguments[0] instanceof SpeedmentPredicate) {
            return Optional.of((SpeedmentPredicate<T>) arguments[0]);
        }

        return Optional.empty();
    }

}

package com.speedment.jpastreamer.pipeline;

import com.speedment.jpastreamer.pipeline.terminating.TerminatingOperation;

import java.util.Comparator;
import java.util.Optional;
import java.util.function.*;
import java.util.stream.Collector;
import java.util.stream.Stream;

public interface TerminatingOperationFactory extends BaseStreamTerminatingOperationsFactory {

    <T> TerminatingOperation<Stream<T>, Void> createForEach(Consumer<? super T> action);

    <T> TerminatingOperation<Stream<T>, Void> createForEachOrdered(Consumer<? super T> action);

    <T> TerminatingOperation<Stream<T>, Object[]> createToArray();

    <T, A> TerminatingOperation<Stream<T>, A[]> createToArray(IntFunction<A[]> generator);

    <T> TerminatingOperation<Stream<T>, T> createReduce(T identity, BinaryOperator<T> accumulator);

    <T> TerminatingOperation<Stream<T>, Optional<T>> createReduce(BinaryOperator<T> accumulator);

    <T, U> TerminatingOperation<Stream<T>, U> createReduce(U identity,
                                                               BiFunction<U, ? super T, U> accumulator,
                                                               BinaryOperator<U> combiner);

    <T, R> TerminatingOperation<Stream<T>, R> createCollect(Supplier<R> supplier,
                                                             BiConsumer<R, ? super T> accumulator,
                                                             BiConsumer<R, R> combiner);

    <T, R, A> TerminatingOperation<Stream<T>, R> createCollect(Collector<? super T, A, R> collector);

    <T> TerminatingOperation<Stream<T>, Optional<T>> createMin(Comparator<? super T> comparator);

    <T> TerminatingOperation<Stream<T>, Optional<T>> createMax(Comparator<? super T> comparator);

    <T> TerminatingOperation<Stream<T>, Long> createCount();

    <T> TerminatingOperation<Stream<T>, Boolean> createAnyMatch(Predicate<? super T> predicate);

    <T> TerminatingOperation<Stream<T>, Boolean> createAllMatch(Predicate<? super T> predicate);

    <T> TerminatingOperation<Stream<T>, Boolean> createNoneMatch(Predicate<? super T> predicate);

    <T> TerminatingOperation<Stream<T>, Optional<T>> createFindFirst();

    <T> TerminatingOperation<Stream<T>, Optional<T>> createFindAny();

}
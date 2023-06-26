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
package com.speedment.jpastreamer.pipeline.terminal;

import java.util.Comparator;
import java.util.Optional;
import java.util.function.*;
import java.util.stream.Collector;
import java.util.stream.Stream;

public interface TerminalOperationFactory extends BaseStreamTerminalOperationFactory {

    <T> TerminalOperation<Stream<T>, Void> createForEach(Consumer<? super T> action);

    <T> TerminalOperation<Stream<T>, Void> createForEachOrdered(Consumer<? super T> action);

    <T> TerminalOperation<Stream<T>, Object[]> acquireToArray();

    <T, A> TerminalOperation<Stream<T>, A[]> createToArray(IntFunction<A[]> generator);

    <T> TerminalOperation<Stream<T>, T> createReduce(T identity, BinaryOperator<T> accumulator);

    <T> TerminalOperation<Stream<T>, Optional<T>> createReduce(BinaryOperator<T> accumulator);

    <T, U> TerminalOperation<Stream<T>, U> createReduce(U identity,
                                                        BiFunction<U, ? super T, U> accumulator,
                                                        BinaryOperator<U> combiner);

    <T, R> TerminalOperation<Stream<T>, R> createCollect(Supplier<R> supplier,
                                                         BiConsumer<R, ? super T> accumulator,
                                                         BiConsumer<R, R> combiner);

    <T, R, A> TerminalOperation<Stream<T>, R> createCollect(Collector<? super T, A, R> collector);

    <T> TerminalOperation<Stream<T>, Optional<T>> createMin(Comparator<? super T> comparator);

    <T> TerminalOperation<Stream<T>, Optional<T>> createMax(Comparator<? super T> comparator);

    <T> TerminalOperation<Stream<T>, Long> acquireCount();

    <T> TerminalOperation<Stream<T>, Boolean> createAnyMatch(Predicate<? super T> predicate);

    <T> TerminalOperation<Stream<T>, Boolean> createAllMatch(Predicate<? super T> predicate);

    <T> TerminalOperation<Stream<T>, Boolean> createNoneMatch(Predicate<? super T> predicate);

    <T> TerminalOperation<Stream<T>, Optional<T>> acquireFindFirst();

    <T> TerminalOperation<Stream<T>, Optional<T>> acquireFindAny();

}

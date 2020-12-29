package com.speedment.jpastreamer.autoclose.standard.internal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.IntPredicate;
import java.util.function.UnaryOperator;
import java.util.stream.BaseStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.*;

abstract class AbstractAutoClosingStreamTest<T, S extends BaseStream<T, S>> {

    static final int[] INPUT = {0, 1, 2, 3, 4, 0};
    // private static final IntPredicate EVEN = i -> i % 2 == 0;

    protected S stream;
    protected AtomicInteger closeCounter;

    protected abstract S initialStream();

    protected abstract S autoClosingStream(S stream, boolean allowStreamIteratorAndSpliterator);

    protected abstract long count(S stream);

    protected abstract Stream<T> boxed(S stream);

    @BeforeEach
    void beforeEach() {
        stream = autoClosingStream(initialStream(), false);
        closeCounter = new AtomicInteger();
    }

    @Test
    void boxed() {
        testTerminal(s -> boxed(s).collect(toList()));
    }

    @Test
    void sequential() {
        testIntermediate(S::sequential);
    }

    @Test
    void parallel() {
        testIntermediate(S::parallel);
    }

    @Test
    void iterator() {
        assertThrows(UnsupportedOperationException.class, stream::iterator);
    }

    @Test
    void spliterator() {
        assertThrows(UnsupportedOperationException.class, stream::spliterator);
    }

    @Test
    void isParallel() {
        assertFalse(stream.isParallel());
        stream.parallel();
        assertTrue(stream.isParallel());
    }

    @Test
    void unordered() {
        testIntermediate(S::unordered);
    }

    @Test
    void onClose() {
        // Test the idempotency of close
        testTerminal(this::count);
        stream.close();
        stream.close();
        assertEquals(1, closeCounter.get());
    }

    protected void testIntermediate(final UnaryOperator<S> intermediateOperation) {
        testTerminal(s -> boxed(intermediateOperation.apply(s)).collect(toList()));
    }

    protected <R> void testTerminal(final Function<S, R> terminalOperation) {
        testTerminal(terminalOperation, Objects::equals);
    }

    protected <R> void testTerminal(final Function<S, R> terminalOperation, final BiPredicate<R, R> equalPredicate) {
        final R expected = terminalOperation.apply(initialStream());

        final S actualStream = stream
                .onClose(closeCounter::getAndIncrement);

        final R actual = terminalOperation.apply(actualStream);

        // Did we get the same result?
        final boolean success = equalPredicate.test(expected, actual);

        if (!success) {
            // Use assertEquals to report the problem in a user-friendly way
            assertEquals(expected, actual);
        }

        /*        if (expected instanceof IntSummaryStatistics) {
            assertEqualsIntSummaryStatistics((IntSummaryStatistics) expected, (IntSummaryStatistics) actual);
        } else {
            assertEquals(expected, actual);
        }*/

        // Was the close handler invoked exactly one time?
        assertEquals(1, closeCounter.get());
        // Check that we protect against re-use of already mapped stream
        assertThrows(IllegalStateException.class, () -> count(actualStream));
    }

    boolean equalsSummaryStatistics(final IntSummaryStatistics a, final IntSummaryStatistics b) {
        return Stream.<Function<IntSummaryStatistics, Number>>of(
                IntSummaryStatistics::getCount,
                IntSummaryStatistics::getSum,
                IntSummaryStatistics::getMin,
                IntSummaryStatistics::getMax
        )
                .allMatch(op -> op.apply(a).equals(op.apply(b)));
    }

    boolean equalsSummaryStatistics(final LongSummaryStatistics a, final LongSummaryStatistics b) {
        return Stream.<Function<LongSummaryStatistics, Number>>of(
                LongSummaryStatistics::getCount,
                LongSummaryStatistics::getSum,
                LongSummaryStatistics::getMin,
                LongSummaryStatistics::getMax
        )
                .allMatch(op -> op.apply(a).equals(op.apply(b)));
    }

    boolean equalsSummaryStatistics(final DoubleSummaryStatistics a, final DoubleSummaryStatistics b) {
        return Stream.<Function<DoubleSummaryStatistics, Number>>of(
                DoubleSummaryStatistics::getCount,
                DoubleSummaryStatistics::getSum,
                DoubleSummaryStatistics::getMin,
                DoubleSummaryStatistics::getMax
        )
                .allMatch(op -> op.apply(a).equals(op.apply(b)));
    }

}
/*
 * JPAstreamer - Express JPA queries with Java Streams
 * Copyright (c) 2020-2020, Speedment, Inc. All Rights Reserved.
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * See: https://github.com/speedment/jpa-streamer/blob/master/LICENSE
 */
package com.speedment.jpastreamer.autoclose.standard.internal;

import com.speedment.jpastreamer.javanine.Java9StreamUtil;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntPredicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertEquals;

final class AutoClosingIntStreamTest extends AbstractAutoClosingStreamTest<Integer, IntStream> {

    private static final IntPredicate EVEN = i -> i % 2 == 0;

    @Override
    protected IntStream initialStream() {
        return IntStream.of(INPUT);
    }

    @Override
    protected IntStream autoClosingStream(IntStream stream, boolean allowStreamIteratorAndSpliterator) {
        return new AutoClosingIntStream(stream, allowStreamIteratorAndSpliterator);
    }

    @Override
    protected long count(IntStream stream) {
        return stream.count();
    }

    @Override
    protected Stream<Integer> boxed(IntStream stream) {
        return stream.boxed();
    }

    @Test
    void filter() {
        testIntermediate(s -> s.filter(EVEN));
    }

    @Test
    void map() {
        testIntermediate(s -> s.map(i -> i + 1));
    }

    @Test
    void mapToObj() {
        testTerminal(s -> s.mapToObj(i -> i + 1).collect(toList()));
    }

    @Test
    void mapToLong() {
        testTerminal(s -> s.mapToLong(i -> i + 1).boxed().collect(toList()));
    }

    @Test
    void mapToDouble() {
        testTerminal(s -> s.mapToDouble(i -> i + 1).boxed().collect(toList()));
    }

    @Test
    void flatMap() {
        testIntermediate(s -> s.flatMap(i -> IntStream.range(0, i)));
    }

    @Test
    void distinct() {
        testIntermediate(IntStream::distinct);
    }

    @Test
    void sorted() {
        testIntermediate(IntStream::sorted);
    }

    @Test
    void peek() {
        final AtomicInteger cnt = new AtomicInteger();
        testIntermediate(s -> s.peek(i -> cnt.getAndIncrement()));
        // Well, the consumer is invoked twice per element
        // Once in the tested stream and once in the reference stream
        assertEquals(INPUT.length * 2, cnt.get());
    }

    @Test
    void limit() {
        testIntermediate(s -> s.limit(3));
    }

    @Test
    void skip() {
        testIntermediate(s -> s.limit(4));
    }

    @Test
    void takeWhile() {
        testIntermediate(s -> Java9StreamUtil.takeWhile(s, i -> i < 3));
    }

    @Test
    void dropWhile() {
        testIntermediate(s -> Java9StreamUtil.dropWhile(s, i -> i < 3));
    }

    @Test
    void forEach() {
        final AtomicInteger cnt = new AtomicInteger();
        testTerminal(s -> {s.forEach(i -> cnt.incrementAndGet()); return 1;});
        assertEquals(INPUT.length * 2, cnt.get());
    }

    @Test
    void forEachOrdered() {
        final AtomicInteger cnt = new AtomicInteger();
        testTerminal(s -> {s.forEachOrdered(i -> cnt.incrementAndGet()); return 1;});
        assertEquals(INPUT.length * 2, cnt.get());
    }

    @Test
    void toArray() {
        testTerminal(IntStream::toArray, Arrays::equals);
    }

    @Test
    void reduce() {
        final AtomicInteger cnt = new AtomicInteger();
        testTerminal(s -> s.reduce(Integer::sum));
    }

    @Test
    void testReduce() {
        testTerminal(s -> s.reduce(1, Integer::sum));
    }

    @Test
    void collect() {
        testTerminal(s -> s.collect(AtomicInteger::new, AtomicInteger::addAndGet, (a, b) -> a.addAndGet(b.get())), (a, b) -> a.get() == b.get());
    }

    @Test
    void sum() {
        testTerminal(IntStream::sum);
    }

    @Test
    void min() {
        testTerminal(IntStream::min);
    }

    @Test
    void max() {
        testTerminal(IntStream::max);
    }

    @Test
    void count() {
        testTerminal(IntStream::count);
    }

    @Test
    void average() {
        testTerminal(IntStream::average);
    }

    @Test
    void summaryStatistics() {
        testTerminal(IntStream::summaryStatistics, this::equalsSummaryStatistics);
    }

    @Test
    void anyMatch() {
        testTerminal(s -> s.anyMatch(EVEN));
    }

    @Test
    void allMatch() {
        testTerminal(s -> s.allMatch(EVEN));
    }

    @Test
    void noneMatch() {
        testTerminal(s -> s.noneMatch(EVEN));
    }

    @Test
    void findFirst() {
        testTerminal(IntStream::findFirst);
    }

    @Test
    void findAny() {
        testTerminal(IntStream::findAny);
    }

    @Test
    void asLongStream() {
        testTerminal(s -> s.asLongStream().boxed().collect(toList()));
    }

    @Test
    void asDoubleStream() {
        testTerminal(s -> s.asDoubleStream().boxed().collect(toList()));
    }
}

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
package com.speedment.jpastreamer.autoclose.standard.internal;

import com.speedment.jpastreamer.javanine.Java9StreamUtil;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.DoublePredicate;
import java.util.function.LongPredicate;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertEquals;

final class AutoClosingDoubleStreamTest extends AbstractAutoClosingStreamTest<Double, DoubleStream> {

    private static final DoublePredicate EVEN = i -> i % 2 == 0;

    @Override
    protected DoubleStream initialStream() {
        return IntStream.of(INPUT).asDoubleStream();
    }

    @Override
    protected DoubleStream autoClosingStream(DoubleStream stream, boolean allowStreamIteratorAndSpliterator) {
        return new AutoClosingDoubleStream(stream, allowStreamIteratorAndSpliterator);
    }

    @Override
    protected long count(DoubleStream stream) {
        return stream.count();
    }

    @Override
    protected Stream<Double> boxed(DoubleStream stream) {
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
    void mapToInt() {
        testTerminal(s -> s.mapToInt(i -> (int) i + 1).boxed().collect(toList()));
    }

    @Test
    void mapToLong() {
        testTerminal(s -> s.mapToLong(i -> (long) i + 1).boxed().collect(toList()));
    }

    @Test
    void flatMap() {
        testIntermediate(s -> s.flatMap(i -> DoubleStream.iterate(0, j -> j + 1).limit((long) i)));
    }

    @Test
    void distinct() {
        testIntermediate(DoubleStream::distinct);
    }

    @Test
    void sorted() {
        testIntermediate(DoubleStream::sorted);
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
        testTerminal(DoubleStream::toArray, Arrays::equals);
    }

    @Test
    void reduce() {
        final AtomicInteger cnt = new AtomicInteger();
        testTerminal(s -> s.reduce(Double::sum));
    }

    @Test
    void testReduce() {
        testTerminal(s -> s.reduce(1, Double::sum));
    }

    @Test
    void collect() {
        testTerminal(s -> s.collect(AtomicLong::new, (al, d) -> al.addAndGet((long) d), (a, b) -> a.addAndGet(b.get())), (a, b) -> a.get() == b.get());
    }

    @Test
    void sum() {
        testTerminal(DoubleStream::sum);
    }

    @Test
    void min() {
        testTerminal(DoubleStream::min);
    }

    @Test
    void max() {
        testTerminal(DoubleStream::max);
    }

    @Test
    void count() {
        testTerminal(DoubleStream::count);
    }

    @Test
    void average() {
        testTerminal(DoubleStream::average);
    }

    @Test
    void summaryStatistics() {
        testTerminal(DoubleStream::summaryStatistics, this::equalsSummaryStatistics);
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
        testTerminal(DoubleStream::findFirst);
    }

    @Test
    void findAny() {
        testTerminal(DoubleStream::findAny);
    }

}

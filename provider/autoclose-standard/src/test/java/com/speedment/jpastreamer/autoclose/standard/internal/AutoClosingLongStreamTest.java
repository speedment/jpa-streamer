/*
 * JPAstreamer - Express JPA queries with Java Streams
 * Copyright (c) 2020-2021, Speedment, Inc. All Rights Reserved.
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * See: https://github.com/speedment/jpa-streamer/blob/master/LICENSE
 *
 */
package com.speedment.jpastreamer.autoclose.standard.internal;

import com.speedment.jpastreamer.javanine.Java9StreamUtil;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.LongPredicate;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertEquals;

final class AutoClosingLongStreamTest extends AbstractAutoClosingStreamTest<Long, LongStream> {

    private static final LongPredicate EVEN = i -> i % 2 == 0;

    @Override
    protected LongStream initialStream() {
        return IntStream.of(INPUT).asLongStream();
    }

    @Override
    protected LongStream autoClosingStream(LongStream stream, boolean allowStreamIteratorAndSpliterator) {
        return new AutoClosingLongStream(stream, allowStreamIteratorAndSpliterator);
    }

    @Override
    protected long count(LongStream stream) {
        return stream.count();
    }

    @Override
    protected Stream<Long> boxed(LongStream stream) {
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
    void mapToDouble() {
        testTerminal(s -> s.mapToDouble(i -> i + 1).boxed().collect(toList()));
    }

    @Test
    void flatMap() {
        testIntermediate(s -> s.flatMap(i -> LongStream.range(0, i)));
    }

    @Test
    void distinct() {
        testIntermediate(LongStream::distinct);
    }

    @Test
    void sorted() {
        testIntermediate(LongStream::sorted);
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
        testTerminal(LongStream::toArray, Arrays::equals);
    }

    @Test
    void reduce() {
        final AtomicInteger cnt = new AtomicInteger();
        testTerminal(s -> s.reduce(Long::sum));
    }

    @Test
    void testReduce() {
        testTerminal(s -> s.reduce(1, Long::sum));
    }

    @Test
    void collect() {
        testTerminal(s -> s.collect(AtomicLong::new, AtomicLong::addAndGet, (a, b) -> a.addAndGet(b.get())), (a, b) -> a.get() == b.get());
    }

    @Test
    void sum() {
        testTerminal(LongStream::sum);
    }

    @Test
    void min() {
        testTerminal(LongStream::min);
    }

    @Test
    void max() {
        testTerminal(LongStream::max);
    }

    @Test
    void count() {
        testTerminal(LongStream::count);
    }

    @Test
    void average() {
        testTerminal(LongStream::average);
    }

    @Test
    void summaryStatistics() {
        testTerminal(LongStream::summaryStatistics, this::equalsSummaryStatistics);
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
        testTerminal(LongStream::findFirst);
    }

    @Test
    void findAny() {
        testTerminal(LongStream::findAny);
    }

    @Test
    void asDoubleStream() {
        testTerminal(s -> s.asDoubleStream().boxed().collect(toList()));
    }

}

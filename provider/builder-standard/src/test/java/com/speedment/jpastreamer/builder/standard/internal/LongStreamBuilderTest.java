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
package com.speedment.jpastreamer.builder.standard.internal;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

final class LongStreamBuilderTest extends BaseStreamBuilderTest<Long, LongStream> {

    @Override
    protected LongStream unboxed(Stream<String> stream) {
        return stream.mapToLong(v -> v.charAt(0) - '0');
    }

    @Override
    protected long count(LongStream stream) {
        return stream.count();
    }

    // Intermediate operators

    @Test
    void filter() {
        testIntermediate(s -> s.filter(v -> v > 1));
    }

    @Test
    void map() {
        testIntermediate(s -> s.map(v -> v + 1));
    }

    @Test
    void mapToObj() {
        testIntermediate(s -> s.mapToObj(v -> "" + (char) (v + '0')));
    }

    @Test
    void mapToInt() {
        testIntermediate(s -> s.mapToInt(v -> (int) v + 1));
    }

    @Test
    void mapToDouble() {
        testIntermediate(s -> s.mapToDouble(v -> v + 1));
    }

    @Test
    void flatMap() {
        testIntermediate(s -> s.flatMap(v -> LongStream.range(0, v)));
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
        testIntermediate(s -> s.peek(v -> cnt.incrementAndGet()));
        // The peek operation is called twice, once for the ref stream
        // once for the tested stream.
        assertEquals(SOURCE.get().count() * 2, cnt.get());
    }

    @Test
    void limit() {
        testIntermediate(s -> s.limit(3));
    }

    @Test
    void skip() {
        testIntermediate(s -> s.skip(2));
    }

    // Todo: TakeWhile, DropWhile

    @Test
    void boxed() {
        testIntermediate(LongStream::boxed);
    }


    @Test
    void asDoubleStream() {
        testIntermediate(LongStream::asDoubleStream);
    }


    // Terminal operators

    @Test
    void forEach() {
        final List<Long> expected = LongStream.concat(unboxed(SOURCE.get()), unboxed(SOURCE.get())).boxed().collect(toList());
        final List<Long> actual = new ArrayList<>();
        testTerminal(s -> {s.forEach(actual::add); return 1;});
        assertEquals(expected, actual);
    }

    @Test
    void forEachOrdered() {
        final List<Long> expected = LongStream.concat(unboxed(SOURCE.get()), unboxed(SOURCE.get())).boxed().collect(toList());
        final List<Long> actual = new ArrayList<>();
        testTerminal(s -> {s.forEachOrdered(actual::add); return 1;});
        assertEquals(expected, actual);
    }

    @Test
    void toArray() {
        testTerminal(LongStream::toArray, Arrays::equals);
    }

    @Test
    void reduce2Arg() {
        testTerminal(s -> s.reduce(42, Long::sum));
    }

    @Test
    void reduce3Arg() {
        testTerminal(s -> s.reduce(Long::sum));
    }

    @Test
    void collect() {
        testTerminal(s -> s.collect(HashSet::new, Set::add, Set::addAll));
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
        testTerminal(s -> s.anyMatch(i -> i == 2));
    }

    @Test
    void allMatch() {
        testTerminal(s -> s.allMatch(i -> i == 2));
    }

    @Test
    void noneMatch() {
        testTerminal(s -> s.noneMatch(i -> i == 2));
    }

    @Test
    void findFirst() {
        testTerminal(LongStream::findFirst);
    }

    @Test
    void findAny() {
        testTerminal(LongStream::findAny);
    }

    /// Various

    @Test
    void consumed() {
        final OptionalLong first = unboxed(builder)
                .limit(1)
                .findFirst();

        assertThrows(IllegalStateException.class, () -> {
            // We have already consumed the Stream so
            // this should not work.
            final Stream<Integer> illegal = builder.map(String::length);
        });
    }


}
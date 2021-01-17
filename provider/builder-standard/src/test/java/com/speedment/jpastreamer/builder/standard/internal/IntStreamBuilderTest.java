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
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

final class IntStreamBuilderTest extends BaseStreamBuilderTest<Integer, IntStream> {

    @Override
    protected IntStream unboxed(Stream<String> stream) {
        return stream.mapToInt(v -> v.charAt(0) - '0');
    }

    @Override
    protected long count(IntStream stream) {
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
    void mapToLong() {
        testIntermediate(s -> s.mapToLong(v -> v + 1));
    }

    @Test
    void mapToDouble() {
        testIntermediate(s -> s.mapToDouble(v -> v + 1));
    }

    @Test
    void flatMap() {
        testIntermediate(s -> s.flatMap(v -> IntStream.range(0, v)));
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
        testIntermediate(IntStream::boxed);
    }


    @Test
    void asLongStream() {
        testIntermediate(IntStream::asLongStream);
    }

    @Test
    void asDoubleStream() {
        testIntermediate(IntStream::asDoubleStream);
    }


    // Terminal operators

    @Test
    void forEach() {
        final List<Integer> expected = IntStream.concat(unboxed(SOURCE.get()), unboxed(SOURCE.get())).boxed().collect(toList());
        final List<Integer> actual = new ArrayList<>();
        testTerminal(s -> {s.forEach(actual::add); return 1;});
        assertEquals(expected, actual);
    }

    @Test
    void forEachOrdered() {
        final List<Integer> expected = IntStream.concat(unboxed(SOURCE.get()), unboxed(SOURCE.get())).boxed().collect(toList());
        final List<Integer> actual = new ArrayList<>();
        testTerminal(s -> {s.forEachOrdered(actual::add); return 1;});
        assertEquals(expected, actual);
    }

    @Test
    void toArray() {
        testTerminal(IntStream::toArray, Arrays::equals);
    }

    @Test
    void reduce2Arg() {
        testTerminal(s -> s.reduce(42, Integer::sum));
    }

    @Test
    void reduce3Arg() {
        testTerminal(s -> s.reduce(Integer::sum));
    }

    @Test
    void collect() {
        testTerminal(s -> s.collect(HashSet::new, Set::add, Set::addAll));
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
        testTerminal(IntStream::findFirst);
    }

    @Test
    void findAny() {
        testTerminal(IntStream::findAny);
    }

    /// Various

    @Test
    void consumed() {
        final OptionalInt first = unboxed(builder)
                .limit(1)
                .findFirst();

        assertThrows(IllegalStateException.class, () -> {
            // We have already consumed the Stream so
            // this should not work.
            final Stream<Integer> illegal = builder.map(String::length);
        });
    }


}
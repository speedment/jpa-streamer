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
package com.speedment.jpastreamer.builder.standard.internal;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.*;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

final class StreamBuilderTest extends BaseStreamBuilderTest<String, Stream<String>> {

    @Override
    protected Stream<String> unboxed(Stream<String> stream) {
        return stream;
    }

    @Override
    protected long count(Stream<String> stream) {
        return stream.count();
    }

    // Intermediate operators

    @Test
    void filter() {
        testIntermediate(s -> s.filter(v -> v.compareTo("1") > 0));
    }

    @Test
    void map() {
        testIntermediate(s -> s.map(v -> "A" + v));
    }

    @Test
    void mapToInt() {
        testIntermediate(s -> s.mapToInt(v -> (int) v.charAt(0)));
    }

    @Test
    void mapToLong() {
        testIntermediate(s -> s.mapToLong(v -> (long) v.charAt(0)));
    }

    @Test
    void mapToDouble() {
        testIntermediate(s -> s.mapToDouble(v -> (double) v.charAt(0)));
    }

    @Test
    void flatMap() {
        testIntermediate(s -> s.flatMap(v -> Stream.iterate("A", i -> i + "A").limit(v.charAt(0))));
    }

    @Test
    void flatMapToInt() {
        testIntermediate(s -> s.flatMapToInt(v -> IntStream.iterate(0, i -> i + 1).limit(v.charAt(0))));
    }

    @Test
    void flatMapToLong() {
        testIntermediate(s -> s.flatMapToLong(v -> LongStream.iterate(0, i -> i + 1).limit(v.charAt(0))));
    }

    @Test
    void flatMapToDouble() {
        testIntermediate(s -> s.flatMapToDouble(v -> DoubleStream.iterate(0, i -> i + 1).limit(v.charAt(0))));
    }

    @Test
    void distinct() {
        testIntermediate(Stream::distinct);
    }

    @Test
    void sorted() {
        testIntermediate(Stream::sorted);
    }

    @Test
    void sortedComparator() {
        testIntermediate(s -> s.sorted(Comparator.reverseOrder()));
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


    // Terminal operators

    @Test
    void forEach() {
        final List<String> expected = Stream.concat(SOURCE.get(), SOURCE.get()).collect(toList());
        final List<String> actual = new ArrayList<>();
        testTerminal(s -> {s.forEach(actual::add); return 1;});
        assertEquals(expected, actual);
    }

    @Test
    void forEachOrdered() {
        final List<String> expected = Stream.concat(SOURCE.get(), SOURCE.get()).collect(toList());
        final List<String> actual = new ArrayList<>();
        testTerminal(s -> {s.forEachOrdered(actual::add); return 1;});
        assertEquals(expected, actual);
    }

    @Test
    void toArray() {
        testTerminal(Stream::toArray, Arrays::equals);
    }

    @Test
    void toArrayGenerator() {
        testTerminal(s -> s.toArray(String[]::new), Arrays::equals);
    }

    @Test
    void reduce1Arg() {
        testTerminal(s -> s.reduce(String::concat));
    }

    @Test
    void reduce2Arg() {
        testTerminal(s -> s.reduce("-", String::concat));
    }

    @Test
    void reduce3Arg() {
        testTerminal(s -> s.reduce(0, (i, v) -> i + v.charAt(0), Integer::sum));
    }

    @Test
    void collect3Arg() {
        testTerminal(s -> s.collect(HashSet::new, Set::add, Set::addAll));
    }

    @Test
    void collect1Arg() {
        testTerminal(s -> s.collect(toCollection(LinkedHashSet::new)));
    }

    @Test
    void min() {
        testTerminal(s -> s.min(comparing(v -> v.charAt(0))));
    }

    @Test
    void max() {
        testTerminal(s -> s.max(comparing(v -> v.charAt(0))));
    }

    /*
    @Test
    void count() {
        testTerminal(Stream::count);
    }
     */

    @Test
    void anyMatch() {
        testTerminal(s -> s.anyMatch("2"::equals));
    }

    @Test
    void allMatch() {
        testTerminal(s -> s.allMatch("2"::equals));
    }

    @Test
    void noneMatch() {
        testTerminal(s -> s.noneMatch("2"::equals));
    }

    @Test
    void findFirst() {
        testTerminal(Stream::findFirst);
    }

    @Test
    void findAny() {
        testTerminal(Stream::findAny);
    }

    /// Various

    @Test
    void consumed() {
        final Optional<String> first = builder
                .filter(s -> s.length() > 0)
                .limit(1)
                .findFirst();

        assertThrows(IllegalStateException.class, () -> {
            // We have already consumed the Stream so
            // this should not work.
            final Stream<Integer> illegal = builder.map(String::length);
        });
    }


}

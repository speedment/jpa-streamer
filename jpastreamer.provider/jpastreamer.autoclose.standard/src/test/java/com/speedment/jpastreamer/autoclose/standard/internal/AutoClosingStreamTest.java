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

import com.speedment.jpastreamer.javasixteen.Java16StreamUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledForJreRange;
import org.junit.jupiter.api.condition.JRE;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

final class AutoClosingStreamTest extends AbstractAutoClosingStreamTest<Integer, Stream<Integer>> {

    private static final Predicate<Integer> EVEN = i -> i % 2 == 0;

    @Override
    protected Stream<Integer> initialStream() {
        return IntStream.of(INPUT).boxed();
    }

    @Override
    protected Stream<Integer> autoClosingStream(Stream<Integer> stream, boolean allowStreamIteratorAndSpliterator) {
        return new AutoClosingStream<>(stream, allowStreamIteratorAndSpliterator);
    }

    @Override
    protected long count(Stream<Integer> stream) {
        return stream.count();
    }

    @Override
    protected Stream<Integer> boxed(Stream<Integer> stream) {
        return stream;
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
    void mapToInt() {
        testTerminal(s -> s.mapToInt(i -> i + 1).boxed().collect(Collectors.toList()));
    }

    @Test
    void mapToLong() {
        testTerminal(s -> s.mapToLong(i -> i + 1).boxed().collect(Collectors.toList()));
    }

    @Test
    void mapToDouble() {
        testTerminal(s -> s.mapToDouble(i -> i + 1).boxed().collect(Collectors.toList()));
    }

    @Test
    void flatMap() {
        testIntermediate(s -> s.flatMap(i -> IntStream.range(0, i).boxed()));
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
        testIntermediate(s -> s.takeWhile(i -> i < 3));
    }

    @Test
    void dropWhile() {
        testIntermediate(s -> s.dropWhile(i -> i < 3));
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
        testTerminal(Stream::toArray, Arrays::equals);
    }

    @Test
    void toArray2Arg() {
        testTerminal(s -> s.toArray(Integer[]::new), Arrays::equals);
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
        testTerminal(Stream::findFirst);
    }

    @Test
    void findAny() {
        testTerminal(Stream::findAny);
    }

    @Test
    @EnabledForJreRange(min = JRE.JAVA_16)
    void toList() { testTerminal(Java16StreamUtil::toList); }

    @Test
    @EnabledForJreRange(min = JRE.JAVA_16)
    void mapMulti() { testIntermediate(s -> Java16StreamUtil.mapMulti(s, ((i, mapper) -> {
        if (EVEN.test(i)) {
            mapper.accept(i);
        }
    }))); }

    @Test
    @EnabledForJreRange(min = JRE.JAVA_16)
    void mapMultiToInt16() { testTerminal(s -> Java16StreamUtil.mapMultiToInt(s, ((i, mapper) -> {
        if (EVEN.test(i)) {
            mapper.accept(i);
        }
    })).boxed().collect(Collectors.toList())); }
    
    @Test
    @EnabledForJreRange(max = JRE.JAVA_15) 
    void mapMultiToInt() {
        Exception exception = assertThrows(UnsupportedOperationException.class, () -> {
            testTerminal(s -> Java16StreamUtil.mapMultiToInt(s, ((i, mapper) -> {
                if (EVEN.test(i)) {
                    mapper.accept(i);
                }
            })).boxed().collect(Collectors.toList()));
        });
    }

    @Test
    @EnabledForJreRange(min = JRE.JAVA_16)
    void mapMultiToDouble16() { testTerminal(s -> Java16StreamUtil.mapMultiToDouble(s, ((i, mapper) -> {
        if (EVEN.test(i)) {
            mapper.accept(i);
        }
    })).boxed().collect(Collectors.toList())); }

    @Test
    @EnabledForJreRange(max = JRE.JAVA_15)
    void mapMultiToDouble() {
        Exception exception = assertThrows(UnsupportedOperationException.class, () -> {
            testTerminal(s -> Java16StreamUtil.mapMultiToDouble(s, ((i, mapper) -> {
                if (EVEN.test(i)) {
                    mapper.accept(i);
                }
            })).boxed().collect(Collectors.toList()));
        });
    }
    
    @Test
    @EnabledForJreRange(min = JRE.JAVA_16)
    void mapMultiToLong16() { testTerminal(s -> Java16StreamUtil.mapMultiToLong(s, ((i, mapper) -> {
        if (EVEN.test(i)) {
            mapper.accept(i);
        }
    })).boxed().collect(Collectors.toList())); }

    @Test
    @EnabledForJreRange(max = JRE.JAVA_15)
    void mapMultiToLong() {
        Exception exception = assertThrows(UnsupportedOperationException.class, () -> {
            testTerminal(s -> Java16StreamUtil.mapMultiToLong(s, ((i, mapper) -> {
                if (EVEN.test(i)) {
                    mapper.accept(i);
                }
            })).boxed().collect(Collectors.toList()));
        });
    }
    
}

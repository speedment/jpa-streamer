package com.speedment.jpastreamer.autoclose.standard.internal;

import com.speedment.jpastreamer.javanine.Java9StreamUtil;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
        testTerminal(s -> s.mapToInt(i -> i + 1).boxed().collect(toList()));
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

}
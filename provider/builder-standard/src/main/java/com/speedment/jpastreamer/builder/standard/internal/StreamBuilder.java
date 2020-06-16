package com.speedment.jpastreamer.builder.standard.internal;

import com.speedment.jpastreamer.pipeline.Pipeline;
import com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperation;
import com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperationFactory;
import com.speedment.jpastreamer.pipeline.terminal.TerminalOperation;
import com.speedment.jpastreamer.pipeline.terminal.TerminalOperationFactory;
import com.speedment.jpastreamer.renderer.RenderResult;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Optional;
import java.util.Spliterator;
import java.util.function.*;
import java.util.stream.*;

import static com.speedment.jpastreamer.builder.standard.internal.StreamBuilderUtil.MSG_STREAM_LINKED_CONSUMED_OR_CLOSED;
import static java.util.Objects.*;

final class StreamBuilder<T> implements Stream<T> {

    private final Factories factories;
    private final Pipeline<T> pipeline;
    private final BaseStreamSupport support;

    // Used to prevent improper reuse of builder
    private boolean linkedConsumedOrClosed;

    StreamBuilder(final Factories factories,
                  final Class<T> root) {

        this.factories = requireNonNull(factories);
        this.pipeline = factories.pipeline().createPipeline(requireNonNull(root));
        support = new BaseStreamSupport(pipeline);
    }

    @Override
    public Stream<T> filter(Predicate<? super T> predicate) {
        add(iof().createFilter(predicate));
        return this;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <R> Stream<R> map(Function<? super T, ? extends R> mapper) {
        add(iof().createMap(mapper));
        return (Stream<R>) this;
    }

    @Override
    public IntStream mapToInt(ToIntFunction<? super T> mapper) {
        add(iof().createMapToInt(mapper));
        linked();
        throw new UnsupportedOperationException();
    }

    @Override
    public LongStream mapToLong(ToLongFunction<? super T> mapper) {
        add(iof().createMapToLong(mapper));
        linked();
        throw new UnsupportedOperationException();
    }

    @Override
    public DoubleStream mapToDouble(ToDoubleFunction<? super T> mapper) {
        add(iof().createMapToDouble(mapper));
        linked();
        throw new UnsupportedOperationException();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <R> Stream<R> flatMap(Function<? super T, ? extends Stream<? extends R>> mapper) {
        add(iof().createFlatMap(mapper));
        return (Stream<R>) this;
    }

    @Override
    public IntStream flatMapToInt(Function<? super T, ? extends IntStream> mapper) {
        add(iof().createFlatMapToInt(mapper));
        linked();
        throw new UnsupportedOperationException();
    }

    @Override
    public LongStream flatMapToLong(Function<? super T, ? extends LongStream> mapper) {
        add(iof().createFlatMapToLong(mapper));
        linked();
        throw new UnsupportedOperationException();
    }

    @Override
    public DoubleStream flatMapToDouble(Function<? super T, ? extends DoubleStream> mapper) {
        add(iof().createFlatMapToDouble(mapper));
        linked();
        throw new UnsupportedOperationException();
    }

    @Override
    public Stream<T> distinct() {
        add(iof().createDistinct());
        return this;
    }

    @Override
    public Stream<T> sorted() {
        add(iof().createSorted());
        return this;
    }

    @Override
    public Stream<T> sorted(Comparator<? super T> comparator) {
        add(iof().createSorted(comparator));
        return this;
    }

    @Override
    public Stream<T> peek(Consumer<? super T> action) {
        add(iof().createPeek(action));
        return this;
    }

    @Override
    public Stream<T> limit(long maxSize) {
        add(iof().createLimit(maxSize));
        return this;
    }

    @Override
    public Stream<T> skip(long n) {
        add(iof().createSkip(n));
        return this;
    }

    @Override
    public void forEach(Consumer<? super T> action) {
        set(tof().createForEach(action));
        renderAndThenAccept();
    }

    @Override
    public void forEachOrdered(Consumer<? super T> action) {
        set(tof().createForEachOrdered(action));
        renderAndThenAccept();
    }

    @Override
    public Object[] toArray() {
        set(tof().createToArray());
        return renderAndThenApply();
    }

    @Override
    public <A> A[] toArray(IntFunction<A[]> generator) {
        set(tof().createToArray(generator));
        return renderAndThenApply();
    }

    @Override
    public T reduce(T identity, BinaryOperator<T> accumulator) {
        set(tof().createReduce(identity, accumulator));
        return renderAndThenApply();
    }

    @Override
    public Optional<T> reduce(BinaryOperator<T> accumulator) {
        set(tof().createReduce(accumulator));
        return renderAndThenApply();
    }

    @Override
    public <U> U reduce(U identity, BiFunction<U, ? super T, U> accumulator, BinaryOperator<U> combiner) {
        set(tof().createReduce(identity, accumulator, combiner));
        return renderAndThenApply();
    }

    @Override
    public <R> R collect(Supplier<R> supplier, BiConsumer<R, ? super T> accumulator, BiConsumer<R, R> combiner) {
        set(tof().createCollect(supplier, accumulator, combiner));
        return renderAndThenApply();
    }

    @Override
    public <R, A> R collect(Collector<? super T, A, R> collector) {
        set(tof().createCollect(collector));
        return renderAndThenApply();
    }

    @Override
    public Optional<T> min(Comparator<? super T> comparator) {
        set(tof().createMin(comparator));
        return renderAndThenApply();
    }

    @Override
    public Optional<T> max(Comparator<? super T> comparator) {
        set(tof().createMax(comparator));
        return renderAndThenApply();
    }

    @Override
    public long count() {
        set(tof().createCount());
        return renderAndThenApplyAsLong();
    }

    @Override
    public boolean anyMatch(Predicate<? super T> predicate) {
        set(tof().createAnyMatch(predicate));
        return renderAndThenTest();
    }

    @Override
    public boolean allMatch(Predicate<? super T> predicate) {
        set(tof().createAllMatch(predicate));
        return renderAndThenTest();
    }

    @Override
    public boolean noneMatch(Predicate<? super T> predicate) {
        set(tof().createNoneMatch(predicate));
        return renderAndThenTest();
    }

    @Override
    public Optional<T> findFirst() {
        set(tof().createFindFirst());
        return renderAndThenApply();
    }

    @Override
    public Optional<T> findAny() {
        set(tof().createFindAny());
        return renderAndThenApply();
    }

    @Override
    public Iterator<T> iterator() {
        set(tof().createIterator());
        return renderAndThenApply();
    }

    @Override
    public Spliterator<T> spliterator() {
        set(tof().createSpliterator());
        return renderAndThenApply();
    }

    @Override
    public boolean isParallel() {
        return pipeline.isParallel();
    }

    @Override
    public Stream<T> sequential() {
        pipeline.parallel();
        return this;
    }

    @Override
    public Stream<T> parallel() {
        pipeline.parallel();
        return this;
    }

    @Override
    public Stream<T> unordered() {
        pipeline.ordered(false);
        return this;
    }

    @Override
    public Stream<T> onClose(Runnable closeHandler) {
        pipeline.closeHandlers().add(closeHandler);
        return this;
    }

    @Override
    public void close() {
        // Close can be called even though the
        // stream is consumed.
        //
        // The stream has never been started so
        // we just run the close handlers.
        // Todo: Make it Exception tolerant
        closed();
        StreamBuilderUtil.runAll(pipeline.closeHandlers());
        pipeline.closeHandlers().clear(); // Only run once
    }

    private void linked() {
        linkedConsumedOrClosed = true;
    }

    private void consumed() {
        linkedConsumedOrClosed = true;
    }

    private void closed() {
        linkedConsumedOrClosed = true;
    }

    private void assertNotLikedConsumedOrClosed() {
        if (linkedConsumedOrClosed)
            throw new IllegalStateException(MSG_STREAM_LINKED_CONSUMED_OR_CLOSED);
    }

    private IntermediateOperationFactory iof() {
        return factories.intermediate();
    }

    private TerminalOperationFactory tof() {
        return factories.terminal();
    }

    private void add(final IntermediateOperation<Stream<T>, ?> intermediateOperation) {
        assertNotLikedConsumedOrClosed();
        pipeline.intermediateOperations().add(intermediateOperation);
    }

    private void set(final TerminalOperation<Stream<T>, ?> terminalOperation) {
        assertNotLikedConsumedOrClosed();
        consumed();
        pipeline.terminatingOperation(terminalOperation);
    }

    @SuppressWarnings("unchecked")
    private <R> R renderAndThenApply() {
        final RenderResult<T> renderResult = factories.renderer().render(pipeline);
        return ((TerminalOperation<Stream<T>, R>) renderResult.terminalOperation())
                .function()
                .apply(renderResult.stream());
    }

    @SuppressWarnings("unchecked")
    private long renderAndThenApplyAsLong() {
        final RenderResult<T> renderResult = factories.renderer().render(pipeline);
        return ((TerminalOperation<Stream<T>, Long>) renderResult.terminalOperation())
                .toLongFunction()
                .applyAsLong(renderResult.stream());
    }

    @SuppressWarnings("unchecked")
    private boolean renderAndThenTest() {
        final RenderResult<T> renderResult = factories.renderer().render(pipeline);
        return ((TerminalOperation<Stream<T>, Long>) renderResult.terminalOperation())
                .predicate()
                .test(renderResult.stream());
    }

    @SuppressWarnings("unchecked")
    private void renderAndThenAccept() {
        final RenderResult<T> renderResult = factories.renderer().render(pipeline);
        ((TerminalOperation<Stream<T>, ?>) renderResult.terminalOperation())
                .consumer()
                .accept(renderResult.stream());
    }


}
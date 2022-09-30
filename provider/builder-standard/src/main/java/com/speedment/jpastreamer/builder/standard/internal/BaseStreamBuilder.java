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

import com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperation;
import com.speedment.jpastreamer.pipeline.terminal.TerminalOperation;
import com.speedment.jpastreamer.renderer.RenderResult;

import java.util.stream.BaseStream;
import java.util.stream.Stream;

import static com.speedment.jpastreamer.builder.standard.internal.StreamBuilderUtil.MSG_STREAM_LINKED_CONSUMED_OR_CLOSED;
import static java.util.Objects.requireNonNull;

/**
 * A StreamBuilder that will accumulate intermediate and terminal operations
 * and, upon a terminal operation, will invoke a renderer to actually create a
 * Stream.
 *
 * @param <T> Type of the Stream to create
 * @param <E> Entity type which is the same as T for the initial stream but
 *            might be different after map operations.
 *
 *
 */
abstract class BaseStreamBuilder<E, T, S extends BaseStream<T, S>> implements BaseStream<T, S> {

    private final BaseBuilderState<E> baseState;

    // Used to prevent improper reuse of builder
    private boolean linkedConsumedOrClosed;

    BaseStreamBuilder(final BaseBuilderState<E> baseState) {
        this.baseState = requireNonNull(baseState);
    }

    @Override
    public boolean isParallel() {
        return baseState.pipeline().isParallel();
    }

    @Override
    public S sequential() {
        baseState.pipeline().parallel();
        return self();
    }

    @Override
    public S parallel() {
        baseState.pipeline().parallel();
        return self();
    }

    @Override
    public S unordered() {
        baseState.pipeline().ordered(false);
        return self();
    }

    @Override
    public S onClose(Runnable closeHandler) {
        baseState.pipeline().closeHandlers().add(closeHandler);
        return self();
    }

    @Override
    public void close() {
        // Close can be called even though the
        // stream is consumed.
        //
        // The stream has never been started so
        // we just run the close handlers.
        closed();
        StreamBuilderUtil.runAll(baseState.pipeline().closeHandlers());
        baseState.pipeline().closeHandlers().clear(); // Only run once
    }

    protected BaseBuilderState<E> baseState() {
        return baseState;
    }

    protected void linked() {
        linkedConsumedOrClosed = true;
    }

    protected void consumed() {
        linkedConsumedOrClosed = true;
    }

    protected void closed() {
        linkedConsumedOrClosed = true;
    }

    protected void assertNotLikedConsumedOrClosed() {
        if (linkedConsumedOrClosed)
            throw new IllegalStateException(MSG_STREAM_LINKED_CONSUMED_OR_CLOSED);
    }

    protected void add(final IntermediateOperation<S, ?> intermediateOperation) {
        assertNotLikedConsumedOrClosed();
        baseState.pipeline().intermediateOperations().add(intermediateOperation);
    }

    protected void set(final TerminalOperation<S, ?> terminalOperation) {
        assertNotLikedConsumedOrClosed();
        consumed();
        baseState.pipeline().terminatingOperation(terminalOperation);
    }

    @SuppressWarnings("unchecked")
    protected <R> R renderAndThenApply() {
        final RenderResult<E, ?, ?> renderResult = renderResult();
        return ((TerminalOperation<S, R>) renderResult.terminalOperation())
                .function()
                .apply((S) renderResult.stream());
    }

    @SuppressWarnings("unchecked")
    protected long renderAndThenApplyAsLong() {
        final RenderResult<E, ?, ?> renderResult = renderResult();
        return ((TerminalOperation<S, Long>) renderResult.terminalOperation())
                .toLongFunction()
                .applyAsLong((S) renderResult.stream());
    }

    @SuppressWarnings("unchecked")
    protected int renderAndThenApplyAsInt() {
        final RenderResult<E, ?, ?> renderResult = renderResult();
        return ((TerminalOperation<S, Long>) renderResult.terminalOperation())
                .toIntFunction()
                .applyAsInt((S) renderResult.stream());
    }

    @SuppressWarnings("unchecked")
    protected double renderAndThenApplyAsDouble() {
        final RenderResult<E, ?, ?> renderResult = renderResult();
        return ((TerminalOperation<S, Long>) renderResult.terminalOperation())
                .toDoubleFunction()
                .applyAsDouble((S) renderResult.stream());
    }

    @SuppressWarnings("unchecked")
    protected boolean renderAndThenTest() {
        final RenderResult<E, ?, ?> renderResult = renderResult();
        return ((TerminalOperation<S, Long>) renderResult.terminalOperation())
                .predicate()
                .test((S) renderResult.stream());
    }

    @SuppressWarnings("unchecked")
    protected void renderAndThenAccept() {
        final RenderResult<E, ?, ?> renderResult = renderResult();
        ((TerminalOperation<S, ?>) renderResult.terminalOperation())
                .consumer()
                .accept((S) renderResult.stream());
    }

    @SuppressWarnings("unchecked")
    protected long renderCount() {
        final RenderResult<E, ?, ?> renderResult = renderResult();

        if (renderResult.root().equals(Long.class) || baseState.pipeline().intermediateOperations().isEmpty()) {
            final Stream<Number> stream = (Stream<Number>) renderResult.stream();
            return stream.mapToLong(Number::longValue).sum();
        }

        return ((TerminalOperation<S, Long>) renderResult.terminalOperation())
                .toLongFunction()
                .applyAsLong((S) renderResult.stream());
    }

    private RenderResult<E, ?, ?> renderResult() {
        return baseState.renderer().render(baseState.pipeline(), baseState.streamConfiguration());
    }

    @SuppressWarnings("unchecked")
    private S self() {
        return (S) this;
    }

}

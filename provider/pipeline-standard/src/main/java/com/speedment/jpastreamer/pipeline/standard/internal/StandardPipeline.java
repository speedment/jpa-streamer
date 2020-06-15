package com.speedment.jpastreamer.pipeline.standard.internal;

import com.speedment.jpastreamer.pipeline.Pipeline;
import com.speedment.jpastreamer.pipeline.action.Action;
import com.speedment.jpastreamer.pipeline.terminating.TerminatingOperation;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static java.util.Objects.requireNonNull;

final class StandardPipeline<T> implements Pipeline<T> {

    private final List<Runnable> closeHandlers;
    private final LinkedList<Action<?, ?>> intermediateOperations;

    private final Class<T> root;
    private TerminatingOperation<?, ?> terminatingOperation;
    private boolean parallel;
    private boolean unordered;

    StandardPipeline(final Class<T> root) {
        this.root = requireNonNull(root);
        this.closeHandlers = new ArrayList<>();
        this.intermediateOperations = new LinkedList<>();
    }

    @Override
    public Class<T> root() {
        return root;
    }

    @Override
    public LinkedList<Action<?, ?>> intermediateOperations() {
        return intermediateOperations;
    }

    @Override
    public TerminatingOperation<?, ?> terminatingOperation() {
        return terminatingOperation;
    }

    @Override
    public void terminatingOperation(TerminatingOperation<?, ?> terminatingOperation) {
        this.terminatingOperation = requireNonNull(terminatingOperation);
    }

    @Override
    public boolean isParallel() {
        return parallel;
    }

    @Override
    public void parallel() {
        parallel = true;
    }

    @Override
    public void sequential() {
        parallel = false;
    }

    @Override
    public boolean isUnordered() {
        return unordered;
    }

    @Override
    public void ordered(boolean flag) {
        this.unordered = flag;
    }

    @Override
    public List<Runnable> closeHandlers() {
        return closeHandlers;
    }
}
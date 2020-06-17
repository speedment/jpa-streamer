package com.speedment.jpastreamer.pipeline.standard.internal.pipeline;

import com.speedment.jpastreamer.pipeline.Pipeline;
import com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperation;
import com.speedment.jpastreamer.pipeline.terminal.TerminalOperation;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

final class StandardPipeline<T> implements Pipeline<T> {

    private final List<Runnable> closeHandlers;
    private final LinkedList<IntermediateOperation<?, ?>> intermediateOperations;

    private final Class<T> root;
    private TerminalOperation<?, ?> terminalOperation;
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
    public LinkedList<IntermediateOperation<?, ?>> intermediateOperations() {
        return intermediateOperations;
    }

    @Override
    public TerminalOperation<?, ?> terminatingOperation() {
        return terminalOperation;
    }

    @Override
    public void terminatingOperation(TerminalOperation<?, ?> terminalOperation) {
        this.terminalOperation = requireNonNull(terminalOperation);
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

    @Override
    public String toString() {
        return String.format("%s {%n" +
                "%s" +
                "%s" +
                ", parallel= %s" +
                ", unordered= %s" +
                '}', root.getSimpleName(), renderIntermediate(), renderTerminal(), parallel, unordered);
    }

    private String renderIntermediate() {
        return intermediateOperations.stream()
                .map(io -> String.format("  .%s%n", io.toString()))
                .collect(Collectors.joining());
    }

    private String renderTerminal() {
        return String.format("  .%s%n", terminalOperation == null ? "" : terminalOperation.toString());
    }

}

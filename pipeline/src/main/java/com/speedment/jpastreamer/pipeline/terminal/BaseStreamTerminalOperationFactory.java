package com.speedment.jpastreamer.pipeline.terminal;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.stream.BaseStream;

public interface BaseStreamTerminalOperationFactory {

    <T, S extends BaseStream<T, S>> TerminalOperation<S, Iterator<T>> createIterator();

    <T, S extends BaseStream<T, S>> TerminalOperation<S, Spliterator<T>> createSpliterator();

}

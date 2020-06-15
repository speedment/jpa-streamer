package com.speedment.jpastreamer.pipeline;

import com.speedment.jpastreamer.pipeline.terminating.TerminatingOperation;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.stream.BaseStream;

public interface BaseStreamTerminatingOperationsFactory {

    <T, S extends BaseStream<T, S>> TerminatingOperation<S, Iterator<T>> createIterator();

    <T, S extends BaseStream<T, S>> TerminatingOperation<S, Spliterator<T>> createSpliterator();

}

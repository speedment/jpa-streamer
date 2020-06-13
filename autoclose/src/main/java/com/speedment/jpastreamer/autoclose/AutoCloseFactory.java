package com.speedment.jpastreamer.autoclose;

import java.util.function.Supplier;
import java.util.stream.Stream;

public interface AutoCloseFactory {

     <T> Stream<T> createAutoCloseStream(Supplier<Stream<T>> stream);

}

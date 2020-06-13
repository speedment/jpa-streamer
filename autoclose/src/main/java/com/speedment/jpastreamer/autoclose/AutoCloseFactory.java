package com.speedment.jpastreamer.autoclose;

import java.util.stream.Stream;

public interface AutoCloseFactory {

     /**
      * creates and returns a new wrapped Stream that will call its {@link Stream#close()} method
      * automatically after a terminating operation has been called.
      * <p>
      * N.B. The {@link Stream#iterator()} {@link Stream#spliterator()} methods will throw
      * an {@link UnsupportedOperationException} because otherwise the AutoClose
      * property cannot be guaranteed. This can be unlocked by setting the
      * system property "jpastreamer.allowiteratorandspliterator" to {@code true}.
      *
      * @param <T>  Stream type
      * @author     Per Minborg
      */
     <T> Stream<T> createAutoCloseStream(Stream<T> stream);

}
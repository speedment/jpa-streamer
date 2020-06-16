package com.speedment.jpastreamer.builder;

import com.speedment.jpastreamer.renderer.Renderer;

import java.util.stream.Stream;

public interface BuilderFactory {

     /**
      * Creates and returns a new Stream that is parsable and
      * can optimize its pipeline and merge internal operations
      * into the stream source (e.g. SQL or a JPA Query).
      *
      * @param <T>  Stream type
      * @param root class to use as stream source
      * @param renderer to use
      * @author     Per Minborg
      */
     <T> Stream<T> createBuilder(Class<T> root, Renderer renderer);

}
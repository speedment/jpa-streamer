/*
 * JPAstreamer - Express JPA queries with Java Streams
 * Copyright (c) 2020-2022, Speedment, Inc. All Rights Reserved.
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * See: https://github.com/speedment/jpa-streamer/blob/master/LICENSE
 */
package com.speedment.jpastreamer.javasixteen;

import java.util.List;
import java.util.function.*;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 *
 * @author Per Minborg, Julia Gustafsson 
 */
public interface Java16StreamAdditions<T, R> {
    
    List<T> toList(Stream<T> stream); 
    
    Stream<R> mapMulti(Stream<T> stream, BiConsumer<? super T,? super Consumer<R>> mapper); 
    
    IntStream mapMultiToInt(Stream<T> stream, BiConsumer<? super T,? super IntConsumer> mapper); 
    
    DoubleStream mapMultiToDouble(Stream<T> stream, BiConsumer<? super T,? super DoubleConsumer> mapper); 
    
    LongStream mapMultiToLong(Stream<T> stream, BiConsumer<? super T,? super LongConsumer> mapper); 
    
}

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
package com.speedment.jpastreamer.integration.test.inheritance2;

import com.speedment.jpastreamer.application.JPAStreamer;
import com.speedment.jpastreamer.integration.test.inheritance.model.Publication;
import com.speedment.jpastreamer.integration.test.inheritance2.model.*;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InheritanceTest2 {
    
    final static protected JPAStreamer jpaStreamer = JPAStreamer.of("testdb2");

    @Test
    void countTest() {

        final List<Book2> collect = jpaStreamer.stream(Book2.class).collect(Collectors.toList());

        final long expected = collect.stream()
                .filter(b -> b.getPages() > 300)
                .count();

        final long actual = jpaStreamer.stream(Book2.class)
                .filter(Book2$.pages.greaterThan(300))
                .count();

        assertEquals(expected, actual);
    }

    @Test
    void inheritanceTest() {

        final List<Book2> collect = jpaStreamer.stream(Book2.class).collect(Collectors.toList());

        final long expected = collect.stream()
                .filter(b -> b.getTitle().contains("1"))
                .count();

        final long actual = jpaStreamer.stream(Book2.class)
                .filter(Book2$.title.contains("1"))
                .count();

        assertEquals(expected, actual);
    }

    @Test
    void inheritanceTest2() {

        final List<BlogPost2> collect = jpaStreamer.stream(BlogPost2.class).collect(Collectors.toList());

        final long expected = collect.stream()
                .filter(b -> b.getTitle().contains("1"))
                .count();

        final long actual = jpaStreamer.stream(BlogPost2.class)
                .filter(BlogPost2$.title.contains("1"))
                .count();

        assertEquals(expected, actual);
    }


    @Test
    void inheritanceTest3() {

        final List<BlogPost2> collect = jpaStreamer.stream(BlogPost2.class).collect(Collectors.toList());

        final long expected = collect.stream()
                .filter(b -> b.getUrl().contains("www"))
                .count();

        final long actual = jpaStreamer.stream(BlogPost2.class)
                .filter(BlogPost2$.url.contains("www"))
                .count();

        assertEquals(expected, actual);
    }

    @Test
    void inheritanceTest4() {

        final List<Publication2> collect = jpaStreamer.stream(Publication2.class)
                .collect(Collectors.toList());

        final long expected = collect.stream()
                .filter(p -> p.getTitle().contains("1"))
                .count();

        final long actual = jpaStreamer.stream(Publication2.class)
                .filter(Publication2$.title.contains("1"))
                .count();

        assertEquals(expected, actual);
    }
    
}

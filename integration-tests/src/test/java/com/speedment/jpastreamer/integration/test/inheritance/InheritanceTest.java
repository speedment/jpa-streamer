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
package com.speedment.jpastreamer.integration.test.inheritance;

import com.speedment.jpastreamer.application.JPAStreamer;
import com.speedment.jpastreamer.integration.test.inheritance.model.BlogPost;
import com.speedment.jpastreamer.integration.test.inheritance.model.BlogPost$;
import com.speedment.jpastreamer.integration.test.inheritance.model.Book;
import com.speedment.jpastreamer.integration.test.inheritance.model.Book$;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InheritanceTest {
    
    final static protected JPAStreamer jpaStreamer = JPAStreamer.of("testdb");

    @Test
    void countTest() {

        final List<Book> collect = jpaStreamer.stream(Book.class).collect(Collectors.toList());

        final long expected = collect.stream()
                .filter(b -> b.getPages() > 300)
                .count();

        final long actual = jpaStreamer.stream(Book.class)
                .filter(Book$.pages.greaterThan(300))
                .count();

        assertEquals(expected, actual);
    }

    @Test
    void inheritanceTest() {

        final List<Book> collect = jpaStreamer.stream(Book.class).collect(Collectors.toList());

        final long expected = collect.stream()
                .filter(b -> b.getTitle().contains("1"))
                .count();

       final long actual = jpaStreamer.stream(Book.class)
                .filter(Book$.title.contains("1"))
                .count();

        assertEquals(expected, actual);
    }

    @Test
    void inheritanceTest2() {

        final List<BlogPost> collect = jpaStreamer.stream(BlogPost.class).collect(Collectors.toList());

        final long expected = collect.stream()
                .filter(b -> b.getTitle().contains("1"))
                .count();

        final long actual = jpaStreamer.stream(BlogPost.class)
                .filter(BlogPost$.title.contains("1"))
                .count();

        assertEquals(expected, actual);
    }

    @Test
    void inheritanceTest3() {

        final List<BlogPost> collect = jpaStreamer.stream(BlogPost.class)
                .collect(Collectors.toList());

        final long expected = collect.stream()
                .filter(b -> b.getTitle().contains("1"))
                .count();

        final long actual = jpaStreamer.stream(BlogPost.class)
                .filter(BlogPost$.title.contains("1"))
                .count();

        assertEquals(expected, actual);
    }

    
}

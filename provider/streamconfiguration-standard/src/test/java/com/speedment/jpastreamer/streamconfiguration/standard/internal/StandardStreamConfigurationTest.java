/*
 * JPAstreamer - Express JPA queries with Java Streams
 * Copyright (c) 2020-2021, Speedment, Inc. All Rights Reserved.
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * See: https://github.com/speedment/jpa-streamer/blob/master/LICENSE
 *
 */
package com.speedment.jpastreamer.streamconfiguration.standard.internal;

import com.speedment.jpastreamer.field.Field;
import com.speedment.jpastreamer.streamconfiguration.StreamConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.criteria.JoinType;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

final class StandardStreamConfigurationTest {

    private StreamConfiguration<Film> initial;

    @BeforeEach
    public void setup() {
        initial = new StandardStreamConfiguration<>(Film.class);
    }

    @Test
    void entityClass() {
        assertEquals(Film.class, initial.entityClass());
    }

    @Test
    void joins() {
        final StreamConfiguration<Film> j1 = initial.joining(Film$.title);
        assertEquals(Collections.singleton(new StandardJoinConfiguration<>(Film$.title, JoinType.LEFT)), j1.joins());
        final StreamConfiguration<Film> j2 = j1.joining(Film$.length);
        final Set<StreamConfiguration.JoinConfiguration<Film>> excpected =  Stream.<Field<Film>>of(Film$.title, Film$.length)
                .map(f -> new StandardJoinConfiguration<>(f, JoinType.LEFT))
                .collect(Collectors.toSet());

        assertEquals(excpected, j2.joins());
        assertNotSame(j1, j2);
    }

    @Test
    void testEquals() {
        final StreamConfiguration<Film> first = initial.joining(Film$.title).joining(Film$.length);
        final StreamConfiguration<Film> second = initial.joining(Film$.title).joining(Film$.length);
        assertEquals(second, first);
    }

    @Test
    void testHashCode() {
        final StreamConfiguration<Film> first = initial.joining(Film$.title).joining(Film$.length);
        final StreamConfiguration<Film> second = initial.joining(Film$.title).joining(Film$.length);
        assertEquals(second.hashCode(), first.hashCode());
    }

    @Test
    void testToString() {
        final StreamConfiguration<Film> instance = initial.joining(Film$.title).joining(Film$.length);
        final String toString = instance.toString();
        assertTrue(toString.contains(Film.class.getSimpleName()));
        assertTrue(toString.contains(Film$.title.columnName()));
        assertTrue(toString.contains(Film$.length.columnName()));
        System.out.println(toString);
    }
}

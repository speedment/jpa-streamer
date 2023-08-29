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
package com.speedment.jpastreamer.integration.test.standard;

import com.speedment.jpastreamer.integration.test.standard.model.Film;
import com.speedment.jpastreamer.integration.test.standard.model.Film$;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TermOpTest extends JPAStreamerTest {
    
    @Test
    void findAnyTest() {
        Optional<String> anyFilm = jpaStreamer.stream(Film.class)
                .filter(Film$.length.greaterThan(100).and(Film$.title.startsWith("C")))
                .map(Film$.title)
                .findAny();
        
        assertTrue(anyFilm.isPresent());
    }

    @Test
    void findFirstTest() {
        Optional<String> firstFilm = jpaStreamer.stream(Film.class)
                .filter(Film$.length.greaterThan(100).and(Film$.title.startsWith("C")))
                .map(Film$.title)
                .findFirst();

        assertTrue(firstFilm.isPresent());
    }

    @Test
    void anyMatchTest() {
        boolean anyLongFilm = jpaStreamer.stream(Film.class)
                .anyMatch(Film$.length.greaterThan(180));

        assertTrue(anyLongFilm);

        boolean anyVeryLongFilm = jpaStreamer.stream(Film.class)
                .anyMatch(Film$.length.greaterThan(2000));

        assertFalse(anyVeryLongFilm);
    }    
    
    @Test
    void noneMatchTest() {
        boolean noneMatch = jpaStreamer.stream(Film.class)
                .noneMatch(Film$.length.greaterThan(180));

        assertFalse(noneMatch);

        boolean noneMatchVeryLong = jpaStreamer.stream(Film.class)
                .noneMatch(Film$.length.greaterThan(2000));

        assertTrue(noneMatchVeryLong);
    }
    
}

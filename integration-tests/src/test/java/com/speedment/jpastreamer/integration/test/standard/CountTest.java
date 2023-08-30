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

import com.speedment.jpastreamer.application.StreamSupplier;
import com.speedment.jpastreamer.integration.test.standard.model.Film;
import com.speedment.jpastreamer.integration.test.standard.model.Film$;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CountTest extends JPAStreamerTest {
    
    @Test
    void countTest() {
        
        final List<Film> collect = jpaStreamer.stream(Film.class).collect(Collectors.toList());
        
        final long expected = collect.stream()
                .filter(f -> f.getTitle().startsWith("A"))
                .count(); 
        
        final long actual = jpaStreamer.stream(Film.class)
                .filter(Film$.title.startsWith("A"))
                .count();
        
        assertEquals(expected, actual);
    }
    
    @Test 
    void optimizedCountTest() {
        
        try (final StreamSupplier<Film> supplier = jpaStreamer.createStreamSupplier(Film.class)) {

            final long actual = supplier.stream()
                    .filter(Film$.title.startsWith("A"))
                    .sorted(Film$.filmId) // optimized away
                    .map(Film$.title) // optimized away
                    .count();

            final long expected = supplier.stream()
                    .filter(f -> f.getTitle().startsWith("A"))
                    .count();

            assertEquals(expected, actual);
        }
        
    }
    
}

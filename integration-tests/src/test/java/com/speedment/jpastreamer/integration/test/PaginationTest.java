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
package com.speedment.jpastreamer.integration.test;

import com.speedment.jpastreamer.application.StreamSupplier;
import com.speedment.jpastreamer.integration.test.model.Film;
import com.speedment.jpastreamer.integration.test.model.Film$;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PaginationTest extends JPAStreamerTest {
    
    @Test 
    void pagination() {
        
        try (final StreamSupplier<Film> filmSupplier = jpaStreamer.createStreamSupplier(Film.class)) {
            final List<Film> films = filmSupplier.stream().collect(Collectors.toList());
            final List<Film> expectedPage = films.stream()
                    .sorted(Comparator.comparing(Film::getTitle))
                    .skip(25)
                    .limit(10)
                    .collect(Collectors.toList());

            final List<Film> actualPage = jpaStreamer.stream(Film.class)
                    .sorted(Film$.title)
                    .skip(25)
                    .limit(10)
                    .collect(Collectors.toList());
            
            assertEquals(expectedPage, actualPage); 
        }
        
    }
}

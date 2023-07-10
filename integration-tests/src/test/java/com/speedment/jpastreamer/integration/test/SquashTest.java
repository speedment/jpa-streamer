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

public class SquashTest extends JPAStreamerTest {
    
    @Test
    void squashTest() {
        
        try(final StreamSupplier<Film> supplier = jpaStreamer.createStreamSupplier(Film.class)) {
            
            final List<String> actual = supplier.stream()
                    .filter(f -> f.getLength() > 120)
                    .filter(Film$.title.startsWith("A"))
                    .limit(10)
                    .map(Film$.title)
                    .collect(Collectors.toList());

            final List<String> expected = supplier.stream()
                    .filter(f -> f.getLength() > 120)
                    .filter(Film$.title.startsWith("A"))
                    .limit(10)
                    .map(Film$.title)
                    .collect(Collectors.toList());
            
            assertEquals(expected, actual);

        }
    }

    @Test
    void squashTest2() {

        try(final StreamSupplier<Film> supplier = jpaStreamer.createStreamSupplier(Film.class)) {

            final List<String> actual = supplier.stream()
                    .filter(f -> f.getLength() > 120)
                    .filter(Film$.title.startsWith("A"))
                    .sorted(Film$.rating)
                    .sorted(Comparator.comparing(Film::getTitle))
                    .sorted(Film$.length)
                    .limit(50)
                    .map(Film$.title)
                    .collect(Collectors.toList());

            final List<String> expected = supplier.stream()
                    .filter(f -> f.getLength() > 120)
                    .filter(f -> f.getTitle().startsWith("A"))
                    .sorted(Comparator.comparing(Film::getRating))
                    .sorted(Comparator.comparing(Film::getTitle))
                    .sorted(Comparator.comparing(Film::getLength))
                    .limit(50)
                    .map(Film$.title)
                    .collect(Collectors.toList());

            assertEquals(expected, actual);

        }
    }
}

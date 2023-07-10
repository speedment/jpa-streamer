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
import com.speedment.jpastreamer.projection.Projection;
import com.speedment.jpastreamer.streamconfiguration.StreamConfiguration;
import jakarta.persistence.Tuple;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Comparator.*;
import static org.junit.jupiter.api.Assertions.*;

public class ProjectionTest extends JPAStreamerTest {
    
    @Test
    void projection() {
        StreamConfiguration<Film> sc = StreamConfiguration.of(Film.class).selecting(Projection.select(Film$.filmId, Film$.title));
        try(final StreamSupplier<Film> filmSupplier = jpaStreamer.createStreamSupplier(sc))  {
            
            // Check that the rating column is not available
            assertThrows(NullPointerException.class, () -> {
                final List<Film> films = filmSupplier.stream().filter(f -> f.getRating().equals("PG-13")).collect(Collectors.toList());
            });

            // Confirm that the id and title columns are available
            assertDoesNotThrow(() -> {
                final List<Film> first10 = filmSupplier.stream().filter(f -> f.getFilmId() < 10).collect(Collectors.toList());
                final List<Film> startsWithA = filmSupplier.stream().filter(f -> f.getTitle().startsWith("A")).collect(Collectors.toList());
            });
            
        }
        
       /* List<Tuple> actualFilms = jpaStreamer.stream(Film.class)
                .sorted(Film$.length)
                .limit(3)
                .map(Projection.select(Film$.filmId, Film$.title))
                .collect(Collectors.toList());

         final List<Film> expectedFilms = jpaStreamer.stream(Film.class)
                .sorted(comparing(Film::getLength))
                .limit(3)
                .map(f -> new Film(f.getFilmId(), f.getTitle()))
                .collect(Collectors.toList());

        for (int i = 0; i < 3; i++) {
            assertEquals(expectedFilms.get(i).getFilmId(), actualFilms.get(i).get(0, Integer.class));
            assertEquals(expectedFilms.get(i).getTitle(), actualFilms.get(i).get(1, String.class));
        }*/
        
    }
    
}

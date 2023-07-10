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

import com.speedment.jpastreamer.integration.test.model.Film;
import com.speedment.jpastreamer.integration.test.model.Film$;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MapToIntTest extends JPAStreamerTest {
    
    @Test
    void mapToInt() {

        final List<Film> films = jpaStreamer.stream(Film.class).collect(Collectors.toList());
        int expectedMax = films.stream()
                .sorted(Comparator.comparing(Film::getLength))
                .limit(15)
                .mapToInt(Film::getLength)
                .max().orElse(0); 

        int actualMax = jpaStreamer.stream(Film.class)
                .sorted(Film$.length)
                .limit(15)
                .mapToInt(Film$.length.asInt().orElse(0))
                .max().orElse(0);
        
        assertEquals(expectedMax, actualMax);
        
    }
}

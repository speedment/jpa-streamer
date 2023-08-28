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

import com.speedment.jpastreamer.integration.test.standard.model.Actor;
import com.speedment.jpastreamer.integration.test.standard.model.Actor$;
import com.speedment.jpastreamer.integration.test.standard.model.Film;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.function.Function;

import static com.speedment.jpastreamer.streamconfiguration.StreamConfiguration.of;
import static java.util.stream.Collectors.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class PivotTest extends JPAStreamerTest {
    
    @Test
    void pivotTest() {
        assertDoesNotThrow(() -> {
            Map<Actor, Map<String, Long>> pivot = jpaStreamer.stream(of(Actor.class).joining(Actor$.films))
                    .collect(
                            groupingBy(
                                    Function.identity(),
                                    flatMapping(
                                            a -> a.getFilms().stream(),
                                            groupingBy(Film::getRating, counting())
                                    )
                            )
                    );
        }); 
    }
}

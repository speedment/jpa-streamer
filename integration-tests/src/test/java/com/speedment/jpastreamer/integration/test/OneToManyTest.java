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
import com.speedment.jpastreamer.integration.test.model.Language;
import com.speedment.jpastreamer.integration.test.model.Language$;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.speedment.jpastreamer.streamconfiguration.StreamConfiguration.of;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class OneToManyTest extends JPAStreamerTest {
    
    @Test
    void oneToMany() {
        
        final List<Language> languages = jpaStreamer.stream(of(Language.class).joining(Language$.films)).collect(Collectors.toList());

        List<String> expectedFilms = languages.stream()
                .sorted(Comparator.comparing(Language::getName))
                .flatMap(l -> l.getFilms().stream())
                .sorted(Comparator.comparing(Film::getLength))
                .map(Film::getTitle)
                .filter(t -> t.startsWith("A"))
                .limit(10)
                .collect(Collectors.toList());

       List<String> actualFilms = jpaStreamer.stream(of(Language.class).joining(Language$.films))
                .sorted(Language$.name)
                .flatMap(l -> l.getFilms().stream())
                .sorted(Film$.length)
                .map(Film$.title)
                .filter(t -> t.startsWith("A"))
                .limit(10)
                .collect(Collectors.toList());
        
        assertEquals(expectedFilms, actualFilms); 
    }
    
}

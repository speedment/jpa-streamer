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
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MapCustomClassTest extends JPAStreamerTest {
    
    @Test
    void mapCustomClass() {
        // Verifies that the custom mapping does not cause failures
        Optional<TitleLength> first = jpaStreamer.stream(Film.class)
                .map(TitleLength::new)
                .findFirst();

        assertTrue(first.isPresent());
    }


    public static final class TitleLength {

        private final String title;
        private final int length;

        public TitleLength(Film film) {
            this.title = film.getTitle();
            this.length= film.getLength();
        }

        public String title() {
            return title;
        }

        public int length() {
            return length;
        }

        @Override
        public String toString() {
            return "TitleLength{" +
                    "title='" + title + '\'' +
                    ", length=" + length +
                    '}';
        }
    }
    
}

package com.speedment.jpastreamer.integration.test;

import com.speedment.jpastreamer.integration.test.model.Film;
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

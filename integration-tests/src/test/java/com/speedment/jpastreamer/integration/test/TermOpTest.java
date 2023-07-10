package com.speedment.jpastreamer.integration.test;

import com.speedment.jpastreamer.integration.test.model.Film;
import com.speedment.jpastreamer.integration.test.model.Film$;
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

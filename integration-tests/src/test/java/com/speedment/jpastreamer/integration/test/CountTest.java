package com.speedment.jpastreamer.integration.test;

import com.speedment.jpastreamer.application.JPAStreamer;
import com.speedment.jpastreamer.integration.test.model.Film;
import com.speedment.jpastreamer.integration.test.model.Film$;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CountTest {
    
    final private JPAStreamer jpaStreamer = JPAStreamer.of("sakila"); 
    
    @Test
    void countTest() {
        final List<Film> collect = jpaStreamer.stream(Film.class).collect(Collectors.toList());
        final long expected = collect.stream().filter(f -> f.getTitle().startsWith("A")).count(); 
        final long actual = jpaStreamer.stream(Film.class).filter(Film$.title.startsWith("A")).count(); 
        assertEquals(expected, actual);
    }
    
}

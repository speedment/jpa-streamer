package com.speedment.jpastreamer.integration.test;

import com.speedment.jpastreamer.integration.test.model.Film;
import com.speedment.jpastreamer.integration.test.model.Film$;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.speedment.jpastreamer.streamconfiguration.StreamConfiguration.of;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ManyToOneTest extends JPAStreamerTest {

    @Test
    void manyToOneTest() {
        List<Film> expected = jpaStreamer.stream(of(Film.class).joining(Film$.language)).collect(Collectors.toList());

        expected = expected.stream()
                .filter(f -> f.getRating().equals("PG-13"))
                .sorted(Comparator.comparing(Film::getTitle))
                .collect(Collectors.toList());
        
        List<Film> actual = jpaStreamer.stream(of(Film.class).joining(Film$.language))
                .filter(Film$.rating.equal("PG-13"))
                .sorted(Film$.title)
                .collect(Collectors.toList());
        
        assertEquals(expected, actual);
    }
    
}

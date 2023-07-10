package com.speedment.jpastreamer.integration.test;

import com.speedment.jpastreamer.application.StreamSupplier;
import com.speedment.jpastreamer.integration.test.model.Film;
import com.speedment.jpastreamer.integration.test.model.Film$;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

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

            final List<String> ex = supplier.stream()
                    .filter(f -> f.getLength() > 120)
                    .filter(Film$.title.startsWith("A"))
                    .limit(10)
                    .map(Film$.title)
                    .collect(Collectors.toList());

        }
    }
}

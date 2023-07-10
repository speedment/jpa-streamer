package com.speedment.jpastreamer.integration.test;

import com.speedment.jpastreamer.application.StreamSupplier;
import com.speedment.jpastreamer.integration.test.model.Film;
import com.speedment.jpastreamer.integration.test.model.Film$;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.partitioningBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PartitionTest extends JPAStreamerTest {
    
    @Test
    void partitioning() {
        try(final StreamSupplier<Film> supplier = jpaStreamer.createStreamSupplier(Film.class)) {
            
            final List<Film> films = supplier.stream().collect(Collectors.toList()); 
            final Map<Boolean, List<Film>> expectedMap = films.stream()
                    .collect(partitioningBy(f -> f.getLength() > 120)); 
        
            final Map<Boolean, List<Film>> actualMap = supplier.stream()
                    .collect(partitioningBy(Film$.length.greaterThan(120)));
            
            assertEquals(expectedMap, actualMap);
            
        }
    }
    
}
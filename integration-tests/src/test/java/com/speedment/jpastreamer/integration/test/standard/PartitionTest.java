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

import com.speedment.jpastreamer.application.StreamSupplier;
import com.speedment.jpastreamer.integration.test.standard.model.Film;
import com.speedment.jpastreamer.integration.test.standard.model.Film$;
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

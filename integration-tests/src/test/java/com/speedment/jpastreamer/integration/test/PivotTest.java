package com.speedment.jpastreamer.integration.test;

import com.speedment.jpastreamer.integration.test.model.Actor;
import com.speedment.jpastreamer.integration.test.model.Actor$;
import com.speedment.jpastreamer.integration.test.model.Film;
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

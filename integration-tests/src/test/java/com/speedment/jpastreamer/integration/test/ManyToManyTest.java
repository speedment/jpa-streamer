package com.speedment.jpastreamer.integration.test;

import com.speedment.jpastreamer.integration.test.model.Actor;
import com.speedment.jpastreamer.integration.test.model.Actor$;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.speedment.jpastreamer.streamconfiguration.StreamConfiguration.of;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ManyToManyTest extends JPAStreamerTest {
    
    @Test
    void manyToManyTest() {
        List<Actor> expected = jpaStreamer.stream(of(Actor.class).joining(Actor$.films)).collect(Collectors.toList());

        expected = expected.stream()
                .filter(a -> a.getFirstName().startsWith("A"))
                .sorted(Comparator.comparing(Actor::getLastName))
                .collect(Collectors.toList()); 
        
        List<Actor> actual = jpaStreamer.stream(of(Actor.class).joining(Actor$.films))
                .filter(Actor$.firstName.startsWith("A"))
                .sorted(Actor$.lastName)
                .collect(Collectors.toList());
        
        assertEquals(expected, actual);
    }
    
}

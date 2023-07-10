package com.speedment.jpastreamer.integration.test;

import com.speedment.jpastreamer.application.StreamSupplier;
import com.speedment.jpastreamer.integration.test.model.Film;
import com.speedment.jpastreamer.integration.test.model.Film$;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReorderTest extends JPAStreamerTest {

    @Test
    void reorderTest1() {
        
        try (StreamSupplier<Film> supplier = jpaStreamer.createStreamSupplier(Film.class)) {
            final List<Film> actual = supplier.stream()
                    .filter(f -> f.getLength() > 120)
                    .sorted(Film$.length)
                    .filter(Film$.title.startsWith("A"))
                    .distinct()
                    .collect(Collectors.toList());
            
            final List<Film> expected = supplier.stream()
                    .filter(f -> f.getLength() > 120)
                    .sorted(Comparator.comparing(Film::getLength))
                    .filter(f -> f.getTitle().startsWith("A"))
                    .distinct()
                    .collect(Collectors.toList());
            
            assertEquals(expected, actual); 
        }
        
    }
    
    @Test 
    void reorderTest2() {

        try (StreamSupplier<Film> supplier = jpaStreamer.createStreamSupplier(Film.class)) {
            final List<String> actual = supplier.stream()
                    .filter(f -> f.getLength() > 120)
                    .sorted(Film$.length)
                    .filter(Film$.title.startsWith("A"))
                    .map(Film$.title)
                    .limit(10)
                    .collect(Collectors.toList());

            final List<String> expected = supplier.stream()
                    .filter(f -> f.getLength() > 120)
                    .sorted(Comparator.comparing(Film::getLength))
                    .map(Film::getTitle)
                    .filter(title -> title.startsWith("A"))
                    .limit(10)
                    .collect(Collectors.toList());

            assertEquals(expected, actual);
        }
        
    }

    @Test
    void reorderTest3() {

        try (StreamSupplier<Film> supplier = jpaStreamer.createStreamSupplier(Film.class)) {
            final List<String> actual = supplier.stream()
                    .filter(f -> f.getLength() > 120)
                    .limit(10)
                    .sorted(Film$.length)
                    .filter(Film$.title.startsWith("A"))
                    .map(Film$.title)
                    .collect(Collectors.toList());

            final List<String> expected = supplier.stream()
                    .filter(f -> f.getLength() > 120)
                    .limit(10)
                    .sorted(Comparator.comparing(Film::getLength))
                    .filter(f -> f.getTitle().startsWith("A"))
                    .map(Film::getTitle)
                    .collect(Collectors.toList());

            assertEquals(expected, actual);
        }

    }

    @Test
    void reorderTest4() {

        try (StreamSupplier<Film> supplier = jpaStreamer.createStreamSupplier(Film.class)) {
            final List<String> actual = supplier.stream()
                    .map(Film$.title)
                    .limit(10)
                    .sorted(Comparator.comparing(String::valueOf))
                    .filter(t -> t.contains("E"))
                    .collect(Collectors.toList());

            final List<String> expected = supplier.stream()
                    .map(Film::getTitle)
                    .limit(10)
                    .sorted(Comparator.comparing(String::valueOf))
                    .filter(t -> t.contains("E"))
                    .collect(Collectors.toList());

            assertEquals(expected, actual);
        }

    }

    @Test
    void reorderTest5() {

        try (StreamSupplier<Film> supplier = jpaStreamer.createStreamSupplier(Film.class)) {
            final List<String> actual = supplier.stream()
                    .filter(Film$.title.startsWith("A"))
                    .skip(10)
                    .filter(f -> f.getLength() > 120)
                    .sorted(Film$.length)
                    .limit(10)
                    .map(Film$.title)
                    .collect(Collectors.toList());

            final List<String> expected = supplier.stream()
                    .filter(f -> f.getTitle().startsWith("A"))
                    .skip(10)
                    .filter(f -> f.getLength() > 120)
                    .sorted(Comparator.comparing(Film::getLength))
                    .limit(10)
                    .map(Film::getTitle)
                    .collect(Collectors.toList());

            assertEquals(expected, actual);
        }

    }

    @Test
    void reorderTest6() {

        try (StreamSupplier<Film> supplier = jpaStreamer.createStreamSupplier(Film.class)) {
            final List<String> actual = supplier.stream()
                    .sorted(Film$.length)
                    .limit(10)
                    .filter(Film$.title.startsWith("A"))
                    .map(Film$.title)
                    .collect(Collectors.toList());

            final List<String> expected = supplier.stream()
                    .sorted(Comparator.comparing(Film::getLength))
                    .limit(10)
                    .map(Film::getTitle)
                    .filter(title -> title.startsWith("A"))
                    .collect(Collectors.toList());

            assertEquals(expected, actual);
        }

    }

    @Test
    void reorderTest7() {

        try (StreamSupplier<Film> supplier = jpaStreamer.createStreamSupplier(Film.class)) {
            final List<String> actual = supplier.stream()
                    .sorted(Comparator.comparing(Film::getLength))
                    .distinct()
                    .filter(Film$.title.startsWith("A"))
                    .map(Film$.title)
                    .limit(10)
                    .collect(Collectors.toList());

            final List<String> expected = supplier.stream()
                    .sorted(Comparator.comparing(Film::getLength))
                    .distinct()
                    .map(Film::getTitle)
                    .filter(title -> title.startsWith("A"))
                    .limit(10)
                    .collect(Collectors.toList());

            assertEquals(expected, actual);
        }

    }

    @Test
    void reorderTest8() {

        try (StreamSupplier<Film> supplier = jpaStreamer.createStreamSupplier(Film.class)) {
            final List<String> actual = supplier.stream()
                    .filter(f -> f.getTitle().endsWith("A"))
                    .filter(Film$.length.greaterThan(120))
                    .distinct()
                    .sorted(Comparator.comparing(Film::getTitle))
                    .sorted(Film$.length)
                    .map(Film$.title)
                    .limit(10)
                    .limit(10)
                    .collect(Collectors.toList());

            final List<String> expected = supplier.stream()
                    .filter(f -> f.getTitle().endsWith("A"))
                    .filter(f -> f.getLength() > 120)
                    .distinct()
                    .sorted(Comparator.comparing(Film::getTitle))
                    .sorted(Comparator.comparing(Film::getLength))
                    .map(Film::getTitle)
                    .limit(10)
                    .limit(10)
                    .collect(Collectors.toList());

            assertEquals(expected, actual);
        }

    }

    @Test
    void reorderTest9() {

        try (StreamSupplier<Film> supplier = jpaStreamer.createStreamSupplier(Film.class)) {
            final List<String> actual = supplier.stream()
                    .filter(f -> f.getTitle().endsWith("A"))
                    .filter(Film$.length.greaterThan(120))
                    .distinct()
                    .sorted(Film$.length)
                    .sorted(Comparator.comparing(Film::getTitle))
                    .map(Film$.title)
                    .limit(10)
                    .collect(Collectors.toList());

            final List<String> expected = supplier.stream()
                    .filter(f -> f.getTitle().endsWith("A"))
                    .filter(f -> f.getLength() > 120)
                    .distinct()
                    .sorted(Comparator.comparing(Film::getLength))
                    .sorted(Comparator.comparing(Film::getTitle))
                    .map(Film::getTitle)
                    .limit(10)
                    .collect(Collectors.toList());

            assertEquals(expected, actual);
        }

    }
}

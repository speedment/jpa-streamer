package com.speedment.jpastreamer.demo;

import com.speedment.jpastreamer.application.JpaStreamer;
import com.speedment.jpastreamer.application.Streamer;
import com.speedment.jpastreamer.demo.model.Film;
import com.speedment.jpastreamer.demo.model._Film;

public class Main {

    public static void main(String[] args) {

        JpaStreamer jpaStreamer = JpaStreamer.builder()
                .withPersistenceUnitName("sakila")
                .build();


        // Consider jpaStreamer.stream(Film.class);
        // Consider jpaStreamer.of(Film.class);

        Streamer<Film> streamer = jpaStreamer.streamer(Film.class);

        streamer.stream()
                .filter(_Film.length.between(100, 120))
                .forEach(System.out::println);


    }
}

package com.speedment.jpastreamer.demo;

import com.speedment.jpastreamer.application.JpaStreamer;
import com.speedment.jpastreamer.demo.model.Film;
import com.speedment.jpastreamer.demo.model._Film;

public class Main {

    public static void main(String[] args) {

        // Rework to JpaStreamer.create...

        //JpaStreamer.create(String persistenceName)
        //JpaStreamer.create()

        JpaStreamer jpaStreamer = JpaStreamer.createJpaStreamerBuilder("sakila")
                .build();

        jpaStreamer.stream(Film.class)
                .filter(_Film.length.between(100, 120))
                .forEach(System.out::println);

        jpaStreamer.stop();


    }
}

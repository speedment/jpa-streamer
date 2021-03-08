/*
 * JPAstreamer - Express JPA queries with Java Streams
 * Copyright (c) 2020-2021, Speedment, Inc. All Rights Reserved.
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * See: https://github.com/speedment/jpa-streamer/blob/master/LICENSE
 *
 */
package com.speedment.jpastreamer.fieldgenerator.test;

import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {
        final Film olle = new Film();
        olle.setFilmId(1);
        olle.setTitle("Moderns Dimunitiva Son Vid Namn Olle");
        olle.setDescription("Olle går i skogen och träffar en björn som äter upp alla hans surt förvärvade blåbär. Olle kräver sedan oväntat av sin mamma att få återse den fyrbenta tjuven.");
        olle.setLength(120);

        final Film spindel = new Film();
        spindel.setFilmId(2);
        spindel.setTitle("Den Disorienterade Spindeln");
        spindel.setDescription("Spindeln klättrar upp för sin tråd men allt går åt helvete och spindeln faller ned. Han ger dock ej upp utan försöker ånyo ad infinitum!");
        spindel.setLength(110);

        // Make sure generation is actually done
        Stream.of(olle, spindel)
                .filter(Film$.length.between(100, 150))
                .filter(Film$.rating.in("G", "PG"))
                .sorted(Film$.title)
                .forEachOrdered(System.out::println);

        // Make sure Lombok methods gets generated
        Lombok3Bean lombok3Bean = new Lombok3Bean();
        lombok3Bean.getName();

    }
}

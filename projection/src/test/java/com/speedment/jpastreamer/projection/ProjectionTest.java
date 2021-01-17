/*
 * JPAstreamer - Express JPA queries with Java Streams
 * Copyright (c) 2020-2020, Speedment, Inc. All Rights Reserved.
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * See: https://github.com/speedment/jpa-streamer/blob/master/LICENSE
 */
package com.speedment.jpastreamer.projection;

import com.speedment.jpastreamer.field.ComparableField;
import com.speedment.jpastreamer.field.StringField;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;

import static java.util.Collections.singleton;
import static org.junit.jupiter.api.Assertions.*;

final class ProjectionTest {

    @Test
    void select1() {
        final Projection<Person> projection = Projection.select(Person$.personId);
        assertEquals(Person.class, projection.entityClass());
        assertEquals(singleton(Person$.personId), projection.fields());
    }

    @Test
    void select2() {
        final Projection<Person> projection = Projection.select(Person$.personId, Person$.name);
        assertEquals(Person.class, projection.entityClass());
        assertEquals(new HashSet<>(Arrays.asList(Person$.personId, Person$.name)), projection.fields());
    }

    private static final class Person {
        int personId;
        String name;
        int born;

        public int getPersonId() {
            return personId;
        }

        public String getName() {
            return name;
        }

        public int getBorn() {
            return born;
        }
    }

    private static final class Person$ {

        public static final ComparableField<Person, Integer> personId = ComparableField.create(
                Person.class,
                "personId",
                Person::getPersonId,
                true
        );

        public static final StringField<Person> name = StringField.create(
                Person.class,
                "name",
                Person::getName,
                false
        );

        public static final ComparableField<Person, Integer> born = ComparableField.create(
                Person.class,
                "born",
                Person::getBorn,
                false
        );

    }

}
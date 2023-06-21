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
package com.speedment.jpastreamer.fieldgenerator.test;

import jakarta.persistence.*;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false, unique = true, updatable = false, columnDefinition = "smallint(5)")
    private int userId;

    @Column(name = "name", nullable = false, columnDefinition = "varchar(255)")
    private String name;

    private boolean member;

    private boolean large;

    public int getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    // Use "get" rather than "is"
    public boolean getMember() {
        return member;
    }

    // Use "is" rather than "get"
    public boolean isLarge() {
        return large;
    }

}

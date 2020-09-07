package com.speedment.jpastreamer.fieldgenerator.test;

import javax.persistence.*;

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
        return member;
    }

}

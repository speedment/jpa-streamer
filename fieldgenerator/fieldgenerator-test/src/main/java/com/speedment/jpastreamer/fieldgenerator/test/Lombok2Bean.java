package com.speedment.jpastreamer.fieldgenerator.test;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;

import javax.persistence.Access;
import javax.persistence.Entity;

@Entity
public class Lombok2Bean {

    @Getter(value = AccessLevel.PACKAGE)
    private int id;

    @Getter(value = AccessLevel.PRIVATE)
    String name;

    @Getter(value = AccessLevel.NONE)
    String petName;

}

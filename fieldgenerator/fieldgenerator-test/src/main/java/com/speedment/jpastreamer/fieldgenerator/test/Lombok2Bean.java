package com.speedment.jpastreamer.fieldgenerator.test;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;

import javax.persistence.Entity;

@Entity
public class Lombok2Bean {

    @Getter
    private int id;
    @Getter
    String name;
    @Getter(AccessLevel.NONE)
    String petName;

}

package com.speedment.jpastreamer.fieldgenerator.test;

import lombok.AccessLevel;
import lombok.Getter;

import javax.persistence.Entity;

@Getter
@Entity
public class Lombok3Bean {

    @Getter(value = AccessLevel.PRIVATE)
    private int id;
    String name;
    String petName;

}

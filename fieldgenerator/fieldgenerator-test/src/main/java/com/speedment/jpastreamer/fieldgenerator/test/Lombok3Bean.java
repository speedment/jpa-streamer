package com.speedment.jpastreamer.fieldgenerator.test;

import lombok.Getter;

import javax.persistence.Entity;

@Getter
@Entity
public class Lombok3Bean {

    private int id;
    String name;
    String petName;

}
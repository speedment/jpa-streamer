package com.speedment.jpastreamer.fieldgenerator.test;

import lombok.Data;

import javax.persistence.Entity;

@Data
@Entity
public class LombokBean {

    private int id;
    String name;
    String petName;

}

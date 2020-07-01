package com.speedment.jpastreamer.fieldgenerator.test;

import com.speedment.common.codegen.Generator;
import com.speedment.common.codegen.constant.SimpleParameterizedType;
import com.speedment.common.codegen.controller.AlignTabs;
import com.speedment.common.codegen.controller.AutoImports;
import com.speedment.common.codegen.internal.java.JavaGenerator;
import com.speedment.common.codegen.model.*;
import com.speedment.common.codegen.model.Class;
import com.speedment.jpastreamer.field.ComparableField;
import com.speedment.jpastreamer.field.ReferenceField;

import java.util.ArrayList;
import java.util.List;

public class GeneratorTest {

    public static void main(String[] args) {

        File file = File.of("com/example/DateMapper.java");
        Class clazz = Class.of("DateMapper").public_();

        Generator generator = Generator.forJava();

        // Begin building the field value parameters
        final List<Value<?>> utilDateParams = new ArrayList<>();

        utilDateParams.add(Value.ofReference("Film.class"));
        utilDateParams.add(Value.ofText("rental_date"));
        utilDateParams.add(Value.ofReference("Film::getRentalDate"));
        utilDateParams.add(Value.ofBoolean(true));

        clazz.add(Field.of("rental_date", SimpleParameterizedType.create(ComparableField.class, Film.class, java.util.Date.class))
                .public_().static_().final_()
                .set(Value.ofInvocation(
                        ComparableField.class,
                        "create",
                        utilDateParams.toArray(new Value<?>[0])
                )));

        // Begin building the field value parameters
        final List<Value<?>> sqlDateParams = new ArrayList<>();

        sqlDateParams.add(Value.ofReference("Film.class"));
        sqlDateParams.add(Value.ofText("rental_time"));
        sqlDateParams.add(Value.ofReference("Film::getRentalTime"));
        sqlDateParams.add(Value.ofBoolean(true));

        clazz.add(Field.of("rental_time", SimpleParameterizedType.create(ReferenceField.class, Film.class, java.sql.Date.class))
                .public_().static_().final_()
                .set(Value.ofInvocation(
                        ComparableField.class,
                        "create",
                        sqlDateParams.toArray(new Value<?>[0])
                )));

        clazz.add(Field.of("rental_time2", java.sql.Date.class)
                .public_().static_().final_()
                .set(Value.ofReference("null")));

        clazz.add(Field.of("rental_time3", java.util.Date.class)
                .public_().static_().final_()
                .set(Value.ofReference("null")));


        file.add(clazz);
        file.call(new AutoImports(generator.getDependencyMgr())).call(new AlignTabs<>());

        System.out.println(generator.on(file).get());
        System.out.println(new JavaGenerator().on(file).get());
    }
}

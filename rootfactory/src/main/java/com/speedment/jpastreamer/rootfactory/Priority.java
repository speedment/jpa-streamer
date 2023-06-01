package com.speedment.jpastreamer.rootfactory;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target( { TYPE } )
@Retention(RUNTIME)
public @interface Priority {

    /**
     * The value indicates the priority of the service provider. 
     * A lower value gives higher priority, i.e. priority 1 is highest. 
     *
     * @return the provider's priority 
     */
    int value() default 20;
    
}

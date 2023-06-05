package com.speedment.jpastreamer.field;

import com.speedment.jpastreamer.field.method.IntGetter;
import com.speedment.jpastreamer.field.method.ReferenceGetter;

import java.io.Serializable;
import java.util.function.Function;
import java.util.function.ToIntFunction;
import java.util.stream.Stream;

public final class Fields {

    private Fields() {
    }

    public static <ENTITY> StringField<ENTITY> of(StringMethodReference<ENTITY> mr) {
        ReferenceMetadata<ENTITY, ReferenceGetter<ENTITY, String>> metadata = metadata(mr);
        return StringField.create(metadata.type(), metadata.name(), metadata.getter(), metadata.isUnique());
    }

    public static <ENTITY> IntField<ENTITY> of(IntReference<ENTITY> ig) {
        ReferenceMetadata<ENTITY, IntGetter<ENTITY>> metadata = metadata(ig);
        return IntField.create(metadata.type(), metadata.name(), metadata.getter(), metadata.isUnique());
    }

    interface StringMethodReference<T> extends ReferenceGetter<T, String>, Serializable {}
    interface ComparableMethodReference<T, C extends Comparable<C>> extends Function<T, C>, Serializable {}
    interface MethodReference<T, C extends Comparable<C>> extends Function<T, C>, Serializable {}
    interface IntReference<T> extends ToIntFunction<T>, Serializable {}

    interface ReferenceMetadata<T, G> {
        Class<T> type();
        String name();
        G getter();
        boolean isUnique();
    }

    static <T, G> ReferenceMetadata<T, G> metadata(Serializable target) {
        // Serialization magic goes here
        return null;
    }

    // Demo

    interface Film {

        int id();

        String name();

    }


    public static final StringField<Film> FILM_ID = Fields.of(Film::name);
    public static final IntField<Film> FILM_NAME = Fields.of(Film::id);

    public static void main(String[] args) {

        Stream.<Film>of()
                .filter(FILM_ID.between("A", "B"))
                .mapToInt(FILM_ID.asInt())
                .toArray();

        Stream.<Film>of()
                .filter(of(Film::name).between("A", "B"))
                .mapToInt(of(Film::id).asInt())
                .toArray();


    }


}

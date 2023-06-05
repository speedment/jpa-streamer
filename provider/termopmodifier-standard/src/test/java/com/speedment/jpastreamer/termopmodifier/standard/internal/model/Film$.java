package com.speedment.jpastreamer.termopmodifier.standard.internal.model;

import com.speedment.jpastreamer.field.ComparableField;
import com.speedment.jpastreamer.field.ShortField;
import com.speedment.jpastreamer.field.StringField;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Film$ {

    /**
     * This Field corresponds to the {@link Film} field "length".
     */
    public static final ComparableField<Film, Short> length = ComparableField.create(
            Film.class,
            "length",
            Film::getLength,
            false
    );
    /**
     * This Field corresponds to the {@link Film} field "filmId".
     */
    public static final ShortField<Film> filmId = ShortField.create(
            Film.class,
            "filmId",
            Film::getFilmId,
            false
    );
    /**
     * This Field corresponds to the {@link Film} field "replacementCost".
     */
    public static final ComparableField<Film, BigDecimal> replacementCost = ComparableField.create(
            Film.class,
            "replacementCost",
            Film::getReplacementCost,
            false
    );

    /**
     * This Field corresponds to the {@link Film} field "rating".
     */
    public static final StringField<Film> rating = StringField.create(
            Film.class,
            "rating",
            Film::getRating,
            false
    );
    /**
     * This Field corresponds to the {@link Film} field "title".
     */
    public static final StringField<Film> title = StringField.create(
            Film.class,
            "title",
            Film::getTitle,
            false
    );
    /**
     * This Field corresponds to the {@link Film} field "languageId".
     */
    public static final ShortField<Film> languageId = ShortField.create(
            Film.class,
            "languageId",
            Film::getLanguageId,
            false
    );
    /**
     * This Field corresponds to the {@link Film} field "description".
     */
    public static final StringField<Film> description = StringField.create(
            Film.class,
            "description",
            Film::getDescription,
            false
    );
    /**
     * This Field corresponds to the {@link Film} field "rentalRate".
     */
    public static final ComparableField<Film, BigDecimal> rentalRate = ComparableField.create(
            Film.class,
            "rentalRate",
            Film::getRentalRate,
            false
    );
    /**
     * This Field corresponds to the {@link Film} field "rentalDuration".
     */
    public static final ShortField<Film> rentalDuration = ShortField.create(
            Film.class,
            "rentalDuration",
            Film::getRentalDuration,
            false
    );
    /**
     * This Field corresponds to the {@link Film} field "lastUpdate".
     */
    public static final ComparableField<Film, Timestamp> lastUpdate = ComparableField.create(
            Film.class,
            "lastUpdate",
            Film::getLastUpdate,
            false
    );
}

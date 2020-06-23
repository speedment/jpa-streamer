package com.speedment.jpastreamer.demo.model;

import com.speedment.jpastreamer.field.ComparableField;
import com.speedment.jpastreamer.field.IntField;
import com.speedment.jpastreamer.field.StringField;

public final class _Film {

    public static final IntField<Film, Integer> filmId = IntField.create(Film.class, "filmId", Film::getFilmId, null, true);

    public static final StringField<Film, String> title = StringField.create(Film.class, "title", Film::getTitle, null, true);

    public static final StringField<Film, String> description = StringField.create(Film.class, "description", Film::getDescription, null, true);;

    //public static final ComparableField<Film, Year, Year> year = ComparableField.create(null, Film::getReleaseYear, Film::setReleaseYear, TypeMapper.identity(), false);

/*    public static final Function<Film, Language> language = null; // Todo

    public static final Function<Film, Language> originalLanguage = null; // Todo*/

    public static final ComparableField<Film, Integer, Integer> rentalDuration =  ComparableField.create(Film.class, "rentalDuration", Film::getRentalDuration, null, false);

    public static final ComparableField<Film, Float, Float> rentalRate =  ComparableField.create(Film.class, "rentalRate", Film::getRentalRate, null, false);

    public static final ComparableField<Film, Integer, Integer> length =  ComparableField.create(Film.class, "length", Film::getLength, null, false);

/*    public static final ReferenceField<Film, FilmRating, FilmRating> rating = null; // ReferenceField.create(null, Film::getRating, Film::setRentalRate, TypeMapper.identity(), false);*/

}

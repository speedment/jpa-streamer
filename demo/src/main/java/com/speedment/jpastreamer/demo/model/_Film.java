package com.speedment.jpastreamer.demo.model;

import com.speedment.jpastreamer.field.ColumnIdentifierUtil;
import com.speedment.jpastreamer.field.ComparableField;
import com.speedment.jpastreamer.field.IntField;
import com.speedment.jpastreamer.field.StringField;
import com.speedment.runtime.config.identifier.ColumnIdentifier;
import com.speedment.runtime.typemapper.TypeMapper;

import java.util.function.Function;

public final class _Film {

    public static final IntField<Film, Integer> filmId = IntField.create(ColumnIdentifierUtil.of(), Film::getFilmId, Film::setFilmId, TypeMapper.identity(), true);

    public static final StringField<Film, String> title = StringField.create(ColumnIdentifierUtil.of(), Film::getTitle, Film::setTitle, TypeMapper.identity(), true);

    public static final StringField<Film, String> description = StringField.create(ColumnIdentifierUtil.of(), Film::getDescription, Film::setDescription, TypeMapper.identity(), true);;

    //public static final ComparableField<Film, Year, Year> year = ComparableField.create(null, Film::getReleaseYear, Film::setReleaseYear, TypeMapper.identity(), false);

/*    public static final Function<Film, Language> language = null; // Todo

    public static final Function<Film, Language> originalLanguage = null; // Todo*/

    public static final ComparableField<Film, Integer, Integer> rentalDuration =  ComparableField.create(ColumnIdentifierUtil.of(), Film::getRentalDuration, Film::setRentalDuration, TypeMapper.identity(), false);

    public static final ComparableField<Film, Float, Float> rentalRate =  ComparableField.create(ColumnIdentifierUtil.of(), Film::getRentalRate, Film::setRentalRate, TypeMapper.identity(), false);

    public static final ComparableField<Film, Integer, Integer> length =  ComparableField.create(ColumnIdentifierUtil.of(), Film::getLength, Film::setLength, TypeMapper.identity(), false);

/*    public static final ReferenceField<Film, FilmRating, FilmRating> rating = null; // ReferenceField.create(null, Film::getRating, Film::setRentalRate, TypeMapper.identity(), false);*/

    public enum  DummyColumnIdentifier implements ColumnIdentifier<Film> {
        INSTANCE;

        @Override
        public String getColumnId() {
            return null;
        }

        @Override
        public String getDbmsId() {
            return null;
        }

        @Override
        public String getSchemaId() {
            return null;
        }

        @Override
        public String getTableId() {
            return null;
        }
    }

}
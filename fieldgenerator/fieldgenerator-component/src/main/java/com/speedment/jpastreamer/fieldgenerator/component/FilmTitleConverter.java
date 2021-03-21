/*
 * JPAstreamer - Express JPA queries with Java Streams
 * Copyright (c) 2020-2020, Speedment, Inc. All Rights Reserved.
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * See: https://github.com/speedment/jpa-streamer/blob/master/LICENSE
 */
package com.speedment.jpastreamer.fieldgenerator.component;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class FilmTitleConverter implements
        AttributeConverter<FilmTitle, String> {

    private static final String SEPARATOR = ": ";

    @Override
    public String convertToDatabaseColumn(FilmTitle filmTitle) {
        if (filmTitle == null) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        if (filmTitle.getMainTitle() != null && !filmTitle.getSubTitle()
                .isEmpty()) {
            sb.append(filmTitle.getMainTitle());
            sb.append(SEPARATOR);
        }

        if (filmTitle.getMainTitle() != null
                && !filmTitle.getMainTitle().isEmpty()) {
            sb.append(filmTitle.getSubTitle());
        }

        return sb.toString();
    }

    @Override
    public FilmTitle convertToEntityAttribute(String dbPersonName) {
        if (dbPersonName == null || dbPersonName.isEmpty()) {
            return null;
        }

        String[] pieces = dbPersonName.split(SEPARATOR);

        if (pieces == null || pieces.length == 0) {
            return null;
        }

        FilmTitle filmTitle = new FilmTitle();
        String firstPiece = !pieces[0].isEmpty() ? pieces[0] : null;
        if (dbPersonName.contains(SEPARATOR)) {
            filmTitle.setMainTitle(firstPiece);

            if (pieces.length >= 2 && pieces[1] != null
                    && !pieces[1].isEmpty()) {
                filmTitle.setSubTitle(pieces[1]);
            }
        } else {
            filmTitle.setMainTitle(firstPiece);
        }

        return filmTitle;
    }
}

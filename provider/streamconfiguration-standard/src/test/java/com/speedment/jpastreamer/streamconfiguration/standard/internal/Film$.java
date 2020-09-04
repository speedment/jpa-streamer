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
package com.speedment.jpastreamer.streamconfiguration.standard.internal;

import com.speedment.jpastreamer.field.ComparableField;
import com.speedment.jpastreamer.field.StringField;

final class Film$ {

    public static final StringField<Film> title = StringField.create(
            Film.class,
            "title",
            Film::getTitle,
            false
    );

    public static final ComparableField<Film, Integer> length = ComparableField.create(
            Film.class,
            "length",
            Film::getLength,
            false
    );
}

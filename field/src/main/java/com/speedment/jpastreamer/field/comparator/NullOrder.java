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
package com.speedment.jpastreamer.field.comparator;

import java.util.EnumMap;
import java.util.Map;

/**
 * Determines in if null values should be located before or after other values
 * in a list of results.
 *
 * @author Per Minborg
 * @since 2.2.0
 */
public enum NullOrder {

    NONE, FIRST, LAST;

    private static final Map<NullOrder, NullOrder> REVERSED_MAP = new EnumMap<>(NullOrder.class);

    static {
        REVERSED_MAP.put(NONE, NONE);
        REVERSED_MAP.put(FIRST, LAST);
        REVERSED_MAP.put(LAST, FIRST);
        if (REVERSED_MAP.size() != NullOrder.values().length) {
            throw new IllegalStateException("Missing mapping for NullOrder");
        }
    }

    public NullOrder reversed() {
        return REVERSED_MAP.get(this);
    }

}

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
package com.speedment.jpastreamer.field.internal.util;

import java.util.*;

import static java.util.Collections.*;

/**
 * Utility class for constructing temporary collections.
 *
 * @author Emil Forslund
 * @since  3.0.11
 */
public final class CollectionUtil {

    /**
     * Returns a {@link Set} with the values in the specified collection. If the
     * specified collection is already a {@link Set}, it might be returned. This
     * method makes no guarantees regarding mutability or iteration order of the
     * returned set.
     *
     * @param <T>         the element type
     * @param collection  the collection
     * @return            a set with the same elements as the collection
     */
    public static <T> Set<T> collectionToSet(Collection<T> collection) {
        if (collection instanceof Set) {
            return (Set<T>) collection;
        } else {
            switch (collection.size()) {
                case 0  : return emptySet();
                case 1  : return singleton(collection.iterator().next());
                default : return new HashSet<>(collection);
            }
        }
    }

    /**
     * Creates a copy of the specified list and adds the specified elements to
     * it before returning it as an immutable list.
     *
     * @param <T>       the element type
     * @param original  the original list
     * @param element   elements to add
     * @return          the new immutable list
     */
    @SuppressWarnings("varargs")
    @SafeVarargs
    public static <T> List<T> copyAndAdd(List<T> original, T... element) {
        switch (original.size() + element.length) {
            case 0 : return emptyList();
            case 1 : return singletonList(
                original.isEmpty() ? element[0] : original.get(0)
            );
            default :
                return asUnmodifiableList(original, element);
        }
    }

    private static <T> List<T> asUnmodifiableList(List<T> original, T[] element) {
        final List<T> copy = new ArrayList<>(original);
        addAll(copy, element);
        return unmodifiableList(copy);
    }

    /**
     * Should not be instantiated.
     */
    private CollectionUtil() {}
}

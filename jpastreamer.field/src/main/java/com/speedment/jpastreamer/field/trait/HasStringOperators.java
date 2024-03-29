/*
 * JPAstreamer - Express JPA queries with Java Streams
 * Copyright (c) 2020-2022, Speedment, Inc. All Rights Reserved.
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * See: https://github.com/speedment/jpa-streamer/blob/master/LICENSE
 */
package com.speedment.jpastreamer.field.trait;

import com.speedment.jpastreamer.field.predicate.SpeedmentPredicate;

/**
 * A representation of an Entity field that is a {@code String} type. String
 * fields have additional methods that makes it easier to create string-related
 * predicates.
 *
 * @param <ENTITY> the entity type
 *
 * @author Per Minborg
 * @author Emil Forslund
 * @since 2.2.0
 */
public interface HasStringOperators<ENTITY> {

    /**
     * Returns a {@link java.util.function.Predicate} that will evaluate to
     * {@code true}, if and only if this Field is <em>equal</em> to the given
     * value while ignoring the case of the Strings that are compared.
     *
     * @param value to compare
     * @return a Predicate that will evaluate to {@code true}, if and only if
     * this Field is <em>equal</em> to the given value while ignoring the case
     * of the Strings that are compared
     *
     * @see String#compareToIgnoreCase(String)
     */
    SpeedmentPredicate<ENTITY> equalIgnoreCase(String value);

    /**
     * Returns a {@link java.util.function.Predicate} that will evaluate to
     * {@code true}, if and only if this Field is <em>not equal</em> to the
     * given value while ignoring the case of the Strings that are compared.
     *
     * @param value to compare
     * @return a Predicate that will evaluate to {@code true}, if and only if
     * this Field is <em>not equal</em> to the given value while ignoring the
     * case of the Strings that are compared
     *
     * @see String#compareToIgnoreCase(String)
     */
    default SpeedmentPredicate<ENTITY> notEqualIgnoreCase(String value) {
        return equalIgnoreCase(value).negate();
    }

    /**
     * Returns a {@link java.util.function.Predicate} that will evaluate to
     * {@code true}, if and only if this Field <em>starts with</em> the given
     * value.
     *
     * @param value to compare
     * @return a Predicate that will evaluate to {@code true}, if and only if
     * this Field <em>starts with</em> the given value
     *
     * @see String#startsWith(String)
     */
    SpeedmentPredicate<ENTITY> startsWith(String value);

    /**
     * Returns a {@link java.util.function.Predicate} that will evaluate to
     * {@code true}, if and only if this Field <em>not starts with</em> the
     * given value.
     *
     * @param value to compare
     * @return a Predicate that will evaluate to {@code true}, if and only if
     * this Field <em>not starts with</em> the given value
     *
     * @see String#startsWith(String)
     */
    default SpeedmentPredicate<ENTITY> notStartsWith(String value) {
        return startsWith(value).negate();
    }

    /**
     * Returns a {@link java.util.function.Predicate} that will evaluate to
     * {@code true}, if and only if this Field <em>ends with</em> the given
     * value.
     *
     * @param value to compare
     * @return a Predicate that will evaluate to {@code true}, if and only if
     * this Field <em>ends with</em> the given value
     *
     * @see String#endsWith(String)
     */
    SpeedmentPredicate<ENTITY> endsWith(String value);

    /**
     * Returns a {@link java.util.function.Predicate} that will evaluate to
     * {@code true}, if and only if this Field <em>not ends with</em> the given
     * value.
     *
     * @param value to compare
     * @return a Predicate that will evaluate to {@code true}, if and only if
     * this Field <em>not ends with</em> the given value
     *
     * @see String#endsWith(String)
     */
    default SpeedmentPredicate<ENTITY> notEndsWith(String value) {
        return endsWith(value).negate();
    }

    /**
     * Returns a {@link java.util.function.Predicate} that will evaluate to
     * {@code true}, if and only if this Field <em>contains</em> the given
     * value.
     *
     * @param value to compare
     * @return a Predicate that will evaluate to {@code true}, if and only if
     * this Field <em>contains</em> the given value
     *
     * @see String#contains(CharSequence)
     */
    SpeedmentPredicate<ENTITY> contains(String value);

    /**
     * Returns a {@link java.util.function.Predicate} that will evaluate to
     * {@code true}, if and only if this Field <em>not contains</em> the given
     * value.
     *
     * @param value to compare
     * @return a Predicate that will evaluate to {@code true}, if and only if
     * this Field <em>not contains</em> the given value
     *
     * @see String#contains(CharSequence)
     */
    default SpeedmentPredicate<ENTITY> notContains(String value) {
        return contains(value).negate();
    }

    /**
     * Returns a {@link java.util.function.Predicate} that will evaluate to
     * {@code true}, if and only if this Field <em>is empty</em>. An empty Field
     * contains a String with length zero.
     *
     * @return a Predicate that will evaluate to {@code true}, if and only if
     * this Field <em>is empty</em>
     *
     * @see String#isEmpty()
     */
    SpeedmentPredicate<ENTITY> isEmpty();

    /**
     * Returns a {@link java.util.function.Predicate} that will evaluate to
     * {@code true}, if and only if this Field <em>is not empty</em>. An empty
     * Field contains a String with length zero.
     *
     * @return a Predicate that will evaluate to {@code true}, if and only if
     * this Field <em>is not empty</em>
     *
     * @see String#isEmpty()
     */
    default SpeedmentPredicate<ENTITY> isNotEmpty() {
        return isEmpty().negate();
    }

    /**
     * Returns a {@link java.util.function.Predicate} that will evaluate to
     * {@code true}, if and only if this Field <em>starts with</em> the given
     * value while ignoring the case of the Strings that are compared.
     *
     * @param value to compare
     * @return a Predicate that will evaluate to {@code true}, if and only if
     * this Field <em>starts with</em> the given value while ignoring the case
     * of the Strings that are compared
     *
     * @see String#startsWith(String)
     */
    SpeedmentPredicate<ENTITY> startsWithIgnoreCase(String value);

    /**
     * Returns a {@link java.util.function.Predicate} that will evaluate to
     * {@code true}, if and only if this Field <em>not starts with</em> the
     * given value while ignoring the case of the Strings that are compared.
     *
     * @param value to compare
     * @return a Predicate that will evaluate to {@code true}, if and only if
     * this Field <em>not starts with</em> the given value while ignoring the
     * case of the Strings that are compared
     *
     * @see String#startsWith(String)
     */
    default SpeedmentPredicate<ENTITY> notStartsWithIgnoreCase(String value) {
        return startsWithIgnoreCase(value).negate();
    }

    /**
     * Returns a {@link java.util.function.Predicate} that will evaluate to
     * {@code true}, if and only if this Field <em>ends with</em> the given
     * value while ignoring the case of the Strings that are compared.
     *
     * @param value to compare
     * @return a Predicate that will evaluate to {@code true}, if and only if
     * this Field <em>ends with</em> the given value while ignoring the case of
     * the Strings that are compared
     *
     * @see String#endsWith(String)
     */
    SpeedmentPredicate<ENTITY> endsWithIgnoreCase(String value);

    /**
     * Returns a {@link java.util.function.Predicate} that will evaluate to
     * {@code true}, if and only if this Field <em>not ends with</em> the given
     * value while ignoring the case of the Strings that are compared.
     *
     * @param value to compare
     * @return a Predicate that will evaluate to {@code true}, if and only if
     * this Field <em>not ends with</em> the given value while ignoring the case
     * of the Strings that are compared
     *
     * @see String#startsWith(String)
     */
    default SpeedmentPredicate<ENTITY> notEndsWithIgnoreCase(String value) {
        return endsWithIgnoreCase(value).negate();
    }

    /**
     * Returns a {@link java.util.function.Predicate} that will evaluate to
     * {@code true}, if and only if this Field <em>contains</em> the given value
     * while ignoring the case of the Strings that are compared.
     *
     * @param value to compare
     * @return a Predicate that will evaluate to {@code true}, if and only if
     * this Field <em>contains</em> the given value while ignoring the case of
     * the Strings that are compared
     *
     * @see String#contains(CharSequence)
     */
    SpeedmentPredicate<ENTITY> containsIgnoreCase(String value);

    /**
     * Returns a {@link java.util.function.Predicate} that will evaluate to
     * {@code true}, if and only if this Field <em>does not contain</em> the
     * given value while ignoring the case of the Strings that are compared.
     *
     * @param value to compare
     * @return a Predicate that will evaluate to {@code true}, if and only if
     * this Field <em>does not contain</em> the given value while ignoring the
     * case of the Strings that are compared
     *
     * @see String#contains(CharSequence)
     */
    default SpeedmentPredicate<ENTITY> notContainsIgnoreCase(String value) {
        return containsIgnoreCase(value).negate();
    }

}

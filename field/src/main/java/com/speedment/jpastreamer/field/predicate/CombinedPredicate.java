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
package com.speedment.jpastreamer.field.predicate;

import com.speedment.jpastreamer.field.internal.predicate.AbstractCombinedPredicate;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Aggregation of a number of {@link Predicate Predicates} of the same type
 * (e.g. AND or OR) that can be applied in combination.
 *
 * @param <ENTITY> the entity type
 *
 * @author Per Minborg
 * @author Emil Forslund
 */
public interface CombinedPredicate<ENTITY> extends SpeedmentPredicate<ENTITY> {

    /**
     * This enum list all the different types of combinations
     */
    enum Type {
        AND, OR
    }

    /**
     * Creates and returns a {link Stream} of all predicates that this
     * CombinedPredicate holds.
     *
     * @return a {link Stream} of all predicates that this CombinedPredicate
     * holds
     */
    Stream<Predicate<? super ENTITY>> stream();

    /**
     * Returns the number of predicates that this CombinedBasePredicate holds
     *
     * @return the number of predicates that this CombinedBasePredicate holds
     */
    int size();

    /**
     * Returns the {@link Type} of this CombinedBasePredicate
     *
     * @return the {@link Type} of this CombinedBasePredicate
     */
    Type getType();

    @Override
    CombinedPredicate<ENTITY> and(Predicate<? super ENTITY> other);

    @Override
    CombinedPredicate<ENTITY> or(Predicate<? super ENTITY> other);
    
    @Override
    CombinedPredicate<ENTITY> negate();

    /**
     * Creates and returns a new CombinedPredicate that is the logical AND
     * combination of the given predicates.
     *
     * @param <ENTITY> entity type
     * @param first the first predicate used in the AND operation
     * @param second the first predicate used in the AND operation
     * @return a new CombinedPredicate that is the logical AND combination of
     * the given predicates
     */
    static <ENTITY> CombinedPredicate<ENTITY> and(Predicate<ENTITY> first, Predicate<? super ENTITY> second) {
        @SuppressWarnings("unchecked")
        final Predicate<ENTITY> secondCasted = (Predicate<ENTITY>) second;
        return new AbstractCombinedPredicate.AndCombinedBasePredicateImpl<>(
            Arrays.asList(first, secondCasted)
        );
    }

    /**
     * Creates and returns a new CombinedPredicate that is the logical AND
     * combination of the given predicates.
     *
     * @param <ENTITY> entity type
     * @param predicates the predicates that make up the AND operation
     * @return a new CombinedPredicate that is the logical AND combination of
     * the given predicates
     */
    static <ENTITY> CombinedPredicate<ENTITY> and(List<Predicate<? super ENTITY>> predicates) {
        return new AbstractCombinedPredicate.AndCombinedBasePredicateImpl<>(predicates);
    }

    /**
     * Creates and returns a new CombinedPredicate that is the logical OR
     * combination of the given predicates.
     *
     * @param <ENTITY> entity type
     * @param first the first predicate used in the OR operation
     * @param second the first predicate used in the OR operation
     * @return a new CombinedPredicate that is the logical OR combination of the
     * given predicates
     */
    static <ENTITY> CombinedPredicate<ENTITY> or(Predicate<ENTITY> first, Predicate<? super ENTITY> second) {
        @SuppressWarnings("unchecked")
        final Predicate<ENTITY> secondCasted = (Predicate<ENTITY>) second;
        return new AbstractCombinedPredicate.OrCombinedBasePredicateImpl<>(
            Arrays.asList(first, secondCasted)
        );
    }

    /**
     * Creates or returns a new CombinedPredicate that is the logical OR
     * combination of the given predicates.
     *
     * @param <ENTITY> entity type
     * @param predicates the predicates that make up the OR operation
     * @return a new CombinedPredicate that is the logical OR combination of
     * the given predicates
     */
    static <ENTITY> CombinedPredicate<ENTITY> or(List<Predicate<? super ENTITY>> predicates) {
        return new AbstractCombinedPredicate.OrCombinedBasePredicateImpl<>(predicates);
    }
}

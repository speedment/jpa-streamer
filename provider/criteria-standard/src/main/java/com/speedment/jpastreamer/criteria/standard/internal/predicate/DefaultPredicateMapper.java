/*
 * Copyright (c) 2006-2020, Speedment, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); You may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.speedment.jpastreamer.criteria.standard.internal.predicate;

import static java.util.Objects.requireNonNull;

import com.speedment.common.tuple.Tuple;
import com.speedment.common.tuple.Tuple3;
import com.speedment.common.tuple.Tuples;
import com.speedment.jpastreamer.criteria.Criteria;
import com.speedment.jpastreamer.criteria.standard.internal.util.Cast;
import com.speedment.jpastreamer.exception.JpaStreamerException;
import com.speedment.jpastreamer.field.predicate.FieldPredicate;
import com.speedment.jpastreamer.field.predicate.Inclusion;
import com.speedment.jpastreamer.field.predicate.trait.HasInclusion;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import java.util.function.BiFunction;
import java.util.function.Function;

public final class DefaultPredicateMapper implements PredicateMapper {
    
    @Override
    public <T> Predicate mapPredicate(
        final Criteria<T> criteria,
        final FieldPredicate<T> fieldPredicate
    ) {
        requireNonNull(criteria);
        requireNonNull(fieldPredicate);

        return mapPredicate0(criteria, fieldPredicate);
    }

    private <T> Predicate alwaysTrue(
        final Criteria<T> criteria,
        final FieldPredicate<T> fieldPredicate
    ) {
        return noValueMapping(
            fieldPredicate,
            column -> criteria.getBuilder().isTrue(criteria.getBuilder().literal(true))
        );
    }

    private <T> Predicate alwaysFalse(
        final Criteria<T> criteria,
        final FieldPredicate<T> fieldPredicate
    ) {
        return noValueMapping(
            fieldPredicate,
            column -> criteria.getBuilder().isFalse(criteria.getBuilder().literal(true))
        );
    }

    private <T> Predicate isNull(
        final Criteria<T> criteria,
        final FieldPredicate<T> fieldPredicate
    ) {
        return noValueMapping(
            fieldPredicate,
            column -> criteria.getBuilder().isNull(criteria.getRoot().get(column))
        );
    }

    private <T> Predicate isNotNull(
        final Criteria<T> criteria,
        final FieldPredicate<T> fieldPredicate
    ) {
        return noValueMapping(
            fieldPredicate,
            column -> criteria.getBuilder().isNotNull(criteria.getRoot().get(column))
        );
    }

    private <T> Predicate equal(
        final Criteria<T> criteria,
        final FieldPredicate<T> fieldPredicate
    ) {
        return typeMapping(
            fieldPredicate,
            (column, value) -> criteria.getBuilder().equal(criteria.getRoot().get(column), value),
            Object.class
        );
    }

    private <T> Predicate notEqual(
        final Criteria<T> criteria,
        final FieldPredicate<T> fieldPredicate
    ) {
        return typeMapping(
            fieldPredicate,
            (column, value) -> criteria.getBuilder().notEqual(criteria.getRoot().get(column), value),
            Object.class
        );
    }

    @SuppressWarnings("unchecked")
    private <T> Predicate lessThan(
        final Criteria<T> criteria,
        final FieldPredicate<T> fieldPredicate
    ) {
        return singleBoundRangeComparisonMapping(
            fieldPredicate,
            (column, value) -> criteria.getBuilder().lt(criteria.getRoot().get(column), value),
            (column, value) -> criteria.getBuilder().lessThan(criteria.getRoot().get(column), value)
        );
    }

    @SuppressWarnings("unchecked")
    private <T> Predicate lessOrEqual(
        final Criteria<T> criteria,
        final FieldPredicate<T> fieldPredicate
    ) {
        return singleBoundRangeComparisonMapping(
            fieldPredicate,
            (column, value) -> criteria.getBuilder().le(criteria.getRoot().get(column), value),
            (column, value) -> criteria.getBuilder().lessThanOrEqualTo(criteria.getRoot().get(column), value)
        );
    }

    private <T, S extends Comparable<? super S>> Predicate between(
        final Criteria<T> criteria,
        final FieldPredicate<T> fieldPredicate
    ) {
        return doubleBoundRangeComparisonMapping(
            fieldPredicate,
            (column, value) -> {
                final CriteriaBuilder builder = criteria.getBuilder();
                final Path<S> columnPath = criteria.getRoot().get(column);

                switch (value.get2()) {
                    case START_INCLUSIVE_END_INCLUSIVE:
                        return builder.between(
                            columnPath,
                            value.get0(),
                            value.get1()
                        );
                    case START_INCLUSIVE_END_EXCLUSIVE:
                        return builder.and(
                            builder.greaterThanOrEqualTo(
                                columnPath,
                                value.get0()
                            ),
                            builder.lessThan(
                                columnPath,
                                value.get1()
                            )
                        );
                    case START_EXCLUSIVE_END_INCLUSIVE:
                        return builder.and(
                            builder.greaterThan(
                                columnPath,
                                value.get0()
                            ),
                            builder.lessThanOrEqualTo(
                                columnPath,
                                value.get1())
                        );
                    case START_EXCLUSIVE_END_EXCLUSIVE:
                        return builder.and(
                            builder.greaterThan(
                                columnPath,
                                value.get0()
                            ),
                            builder.lessThan(
                                columnPath,
                                value.get1()
                            )
                        );
                    default:
                        throw new JpaStreamerException();
                }
            }
        );
    }

    private <T, S extends Comparable<? super S>> Predicate notBetween(
        final Criteria<T> criteria,
        final FieldPredicate<T> fieldPredicate
    ) {
        return doubleBoundRangeComparisonMapping(
            fieldPredicate,
            (column, value) -> {
                final CriteriaBuilder builder = criteria.getBuilder();
                final Path<S> columnPath = criteria.getRoot().get(column);

                final Inclusion inclusion = value.get2();

                switch (inclusion) {
                    case START_INCLUSIVE_END_INCLUSIVE:
                        return builder.or(
                            builder.lessThan(
                                columnPath,
                                value.get0()
                            ),
                            builder.greaterThan(
                                columnPath,
                                value.get1()
                            )
                        );
                    case START_INCLUSIVE_END_EXCLUSIVE:
                        return builder.or(
                            builder.lessThan(
                                columnPath,
                                value.get0()
                            ),
                            builder.greaterThanOrEqualTo(
                                columnPath,
                                value.get1()
                            )
                        );
                    case START_EXCLUSIVE_END_INCLUSIVE:
                        return builder.or(
                            builder.lessThanOrEqualTo(
                                columnPath,
                                value.get0()
                            ),
                            builder.greaterThan(
                                columnPath,
                                value.get1()
                            )
                        );
                    case START_EXCLUSIVE_END_EXCLUSIVE:
                        return builder.or(
                            builder.lessThanOrEqualTo(
                                columnPath,
                                value.get0()
                            ),
                            builder.greaterThanOrEqualTo(
                                columnPath,
                                value.get1()
                            )
                        );
                    default:
                        throw new JpaStreamerException("Inclusion type [" + inclusion + "] is not supported");
                    }
                }
        );
    }

    private <T> Predicate in(
        final Criteria<T> criteria,
        final FieldPredicate<T> fieldPredicate
    ) {
        throw new UnsupportedOperationException("todo");
    }

    private <T> Predicate notIn(
        final Criteria<T> criteria,
        final FieldPredicate<T> fieldPredicate
    ) {
        throw new UnsupportedOperationException("todo");
    }

    @SuppressWarnings("unchecked")
    private <T> Predicate greaterThan(
        final Criteria<T> criteria,
        final FieldPredicate<T> fieldPredicate
    ) {
        return singleBoundRangeComparisonMapping(
            fieldPredicate,
            (column, value) -> criteria.getBuilder().gt(criteria.getRoot().get(column), value),
            (column, value) -> criteria.getBuilder().greaterThan(criteria.getRoot().get(column), value)
        );
    }

    @SuppressWarnings("unchecked")
    private <T> Predicate greaterOrEqual(
        final Criteria<T> criteria,
        final FieldPredicate<T> fieldPredicate
    ) {
        return singleBoundRangeComparisonMapping(
            fieldPredicate,
            (column, value) -> criteria.getBuilder().ge(criteria.getRoot().get(column), value),
            (column, value) -> criteria.getBuilder().greaterThanOrEqualTo(criteria.getRoot().get(column), value)
        );
    }

    private <T> Predicate equalIgnoreCase(
        final Criteria<T> criteria,
        final FieldPredicate<T> fieldPredicate
    ) {
        return typeMapping(
            fieldPredicate,
            (column, value) -> criteria.getBuilder().equal(
                criteria.getBuilder().lower(criteria.getRoot().get(column)), value.toLowerCase()),
            String.class
        );
    }

    private <T> Predicate notEqualIgnoreCase(
        final Criteria<T> criteria,
        final FieldPredicate<T> fieldPredicate
    ) {
        return typeMapping(
            fieldPredicate,
            (column, value) -> criteria.getBuilder().notEqual(
                criteria.getBuilder().lower(criteria.getRoot().get(column)), value.toLowerCase()),
            String.class
        );
    }

    private <T> Predicate startsWith(
        final Criteria<T> criteria,
        final FieldPredicate<T> fieldPredicate
    ) {
        return typeMapping(
            fieldPredicate,
            (column, value) -> criteria.getBuilder().like(criteria.getRoot().get(column), value + "%"),
            String.class
        );
    }

    private <T> Predicate notStartsWith(
        final Criteria<T> criteria,
        final FieldPredicate<T> fieldPredicate
    ) {
        return typeMapping(
            fieldPredicate,
            (column, value) -> criteria.getBuilder().notLike(criteria.getRoot().get(column), value + "%"),
            String.class
        );
    }

    private <T> Predicate startsWithIgnoreCase(
        final Criteria<T> criteria,
        final FieldPredicate<T> fieldPredicate
    ) {
        return typeMapping(
            fieldPredicate,
            (column, value) -> criteria.getBuilder().like(
                criteria.getBuilder().lower(criteria.getRoot().get(column)), value.toLowerCase() + "%"),
            String.class
        );
    }

    private <T> Predicate notStartsWithIgnoreCase(
        final Criteria<T> criteria,
        final FieldPredicate<T> fieldPredicate
    ) {
        return typeMapping(
            fieldPredicate,
            (column, value) -> criteria.getBuilder().notLike(
                criteria.getBuilder().lower(criteria.getRoot().get(column)), value.toLowerCase() + "%"),
            String.class
        );
    }

    private <T> Predicate endsWith(
        final Criteria<T> criteria,
        final FieldPredicate<T> fieldPredicate
    ) {
        return typeMapping(
            fieldPredicate,
            (column, value) -> criteria.getBuilder().like(criteria.getRoot().get(column), "%" + value),
            String.class
        );
    }

    private <T> Predicate notEndsWith(
        final Criteria<T> criteria,
        final FieldPredicate<T> fieldPredicate
    ) {
        return typeMapping(
            fieldPredicate,
            (column, value) -> criteria.getBuilder().notLike(criteria.getRoot().get(column), "%" + value),
            String.class
        );
    }

    private <T> Predicate endsWithIgnoreCase(
        final Criteria<T> criteria,
        final FieldPredicate<T> fieldPredicate
    ) {
        return typeMapping(
            fieldPredicate,
            (column, value) -> criteria.getBuilder().like(
                criteria.getBuilder().lower(criteria.getRoot().get(column)), "%" + value.toLowerCase()),
            String.class
        );
    }

    private <T> Predicate notEndsWithIgnoreCase(
        final Criteria<T> criteria,
        final FieldPredicate<T> fieldPredicate
    ) {
        return typeMapping(
            fieldPredicate,
            (column, value) -> criteria.getBuilder().notLike(
                criteria.getBuilder().lower(criteria.getRoot().get(column)), "%" + value.toLowerCase()),
            String.class
        );
    }

    private <T> Predicate contains(
        final Criteria<T> criteria,
        final FieldPredicate<T> fieldPredicate
    ) {
        return typeMapping(
            fieldPredicate,
            (column, value) -> criteria.getBuilder().like(criteria.getRoot().get(column), "%" + value + "%"),
            String.class
        );
    }

    private <T> Predicate notContains(
        final Criteria<T> criteria,
        final FieldPredicate<T> fieldPredicate
    ) {
        return typeMapping(
            fieldPredicate,
            (column, value) -> criteria.getBuilder().notLike(criteria.getRoot().get(column), "%" + value + "%"),
            String.class
        );
    }

    private <T> Predicate containsIgnoreCase(
        final Criteria<T> criteria,
        final FieldPredicate<T> fieldPredicate
    ) {
        return typeMapping(
            fieldPredicate,
            (column, value) -> criteria.getBuilder().like(
                criteria.getBuilder().lower(criteria.getRoot().get(column)), "%" + value.toLowerCase() + "%"),
            String.class
        );
    }

    private <T> Predicate notContainsIgnoreCase(
        final Criteria<T> criteria,
        final FieldPredicate<T> fieldPredicate
    ) {
        return typeMapping(
            fieldPredicate,
            (column, value) -> criteria.getBuilder().notLike(
                criteria.getBuilder().lower(criteria.getRoot().get(column)), "%" + value.toLowerCase() + "%"),
            String.class
        );
    }

    private <T> Predicate isEmpty(
        final Criteria<T> criteria,
        final FieldPredicate<T> fieldPredicate
    ) {
        return noValueMapping(
            fieldPredicate,
            column -> criteria.getBuilder().equal(criteria.getRoot().get(column), "")
        );
    }

    private <T> Predicate isNotEmpty(
        final Criteria<T> criteria,
        final FieldPredicate<T> fieldPredicate
    ) {
        return noValueMapping(
            fieldPredicate,
            column -> criteria.getBuilder().notEqual(criteria.getRoot().get(column), "")
        );
    }

    /*
     * Mapping Helpers - Start
     */

    private <T> Predicate noValueMapping(
        final FieldPredicate<T> fieldPredicate,
        final Function<String, Predicate> callback
    ) {
        final String column = fieldPredicate.getField().columnName();

        return callback.apply(column);
    }

    @SuppressWarnings("unchecked")
    private <T, S> Predicate typeMapping(
        final FieldPredicate<T> fieldPredicate,
        final BiFunction<String, S, Predicate> callback,
        final Class<S> clazz
    ) {
        final String column = fieldPredicate.getField().columnName();
        final Object value = Cast.castOrFail(fieldPredicate, Tuple.class).get(0);

        if (clazz.isInstance(value)) {
            return callback.apply(column, (S) value);
        }

        throw new JpaStreamerException();
    }

    @SuppressWarnings("rawtypes")
    private <T> Predicate singleBoundRangeComparisonMapping(
        final FieldPredicate<T> fieldPredicate,
        final BiFunction<String, Number, Predicate> callback,
        final BiFunction<String, Comparable, Predicate> comparableCallback
    ) {
        final String column = fieldPredicate.getField().columnName();
        final Tuple tuple = Cast.castOrFail(fieldPredicate, Tuple.class);

        if (tuple.degree() != 1) {
            throw new JpaStreamerException("Invalid number of arguments - expected 1, found " + tuple.degree());
        }

        final Object value = tuple.get(0);

        if (value instanceof Number) {
            return callback.apply(column, (Number) value);
        }

        if (value instanceof Character) {
            return callback.apply(column, (int) (char) value);
        }

        if (value instanceof Comparable) {
            return comparableCallback.apply(column, (Comparable) value);
        }

        throw new JpaStreamerException("Illegal comparison value [" + value + "]");
    }

    @SuppressWarnings({"rawtypes"})
    private <T> Predicate doubleBoundRangeComparisonMapping(
            final FieldPredicate<T> fieldPredicate,
            final BiFunction<String, Tuple3<Comparable, Comparable, Inclusion>, Predicate> callback
    ) {
        final String column = fieldPredicate.getField().columnName();
        final Tuple value = Cast.castOrFail(fieldPredicate, Tuple.class);

        if (value.degree() != 2) {
            throw new JpaStreamerException("Invalid number of arguments - expected 2, found " + value.degree());
        }

        final Inclusion inclusion = Cast.cast(fieldPredicate, HasInclusion.class)
            .map(HasInclusion::getInclusion)
            .orElse(Inclusion.START_INCLUSIVE_END_INCLUSIVE);

        if (value.get(0) instanceof Comparable && value.get(1) instanceof Comparable) {
            final Tuple3<Comparable, Comparable, Inclusion> tuple3 = Tuples.of(
                (Comparable) value.get(0),
                (Comparable) value.get(1),
                inclusion
            );

            return callback.apply(column, tuple3);
        }

        throw new JpaStreamerException("Illegal comparison value [" + value + "]");
    }

    /*
     * Mapping Helpers - End
     */

    private <T> Predicate mapPredicate0(
        final Criteria<T> criteria,
        final FieldPredicate<T> fieldPredicate
    ) {
        switch (fieldPredicate.getPredicateType()) {
            case ALWAYS_TRUE:
                return alwaysTrue(criteria, fieldPredicate);
            case ALWAYS_FALSE:
                return alwaysFalse(criteria, fieldPredicate);
            case IS_NULL:
                return isNull(criteria, fieldPredicate);
            case IS_NOT_NULL:
                return isNotNull(criteria, fieldPredicate);
            case EQUAL:
                return equal(criteria, fieldPredicate);
            case NOT_EQUAL:
                return notEqual(criteria, fieldPredicate);
            case GREATER_THAN:
                return greaterThan(criteria, fieldPredicate);
            case GREATER_OR_EQUAL:
                return greaterOrEqual(criteria, fieldPredicate);
            case LESS_THAN:
                return lessThan(criteria, fieldPredicate);
            case LESS_OR_EQUAL:
                return lessOrEqual(criteria, fieldPredicate);
            case BETWEEN:
                return between(criteria, fieldPredicate);
            case NOT_BETWEEN:
                return notBetween(criteria, fieldPredicate);
            case IN:
                return in(criteria, fieldPredicate);
            case NOT_IN:
                return notIn(criteria, fieldPredicate);
            case EQUAL_IGNORE_CASE:
                return equalIgnoreCase(criteria, fieldPredicate);
            case NOT_EQUAL_IGNORE_CASE:
                return notEqualIgnoreCase(criteria, fieldPredicate);
            case STARTS_WITH:
                return startsWith(criteria, fieldPredicate);
            case NOT_STARTS_WITH:
                return notStartsWith(criteria, fieldPredicate);
            case STARTS_WITH_IGNORE_CASE:
                return startsWithIgnoreCase(criteria, fieldPredicate);
            case NOT_STARTS_WITH_IGNORE_CASE:
                return notStartsWithIgnoreCase(criteria, fieldPredicate);
            case ENDS_WITH:
                return endsWith(criteria, fieldPredicate);
            case NOT_ENDS_WITH:
                return notEndsWith(criteria, fieldPredicate);
            case ENDS_WITH_IGNORE_CASE:
                return endsWithIgnoreCase(criteria, fieldPredicate);
            case NOT_ENDS_WITH_IGNORE_CASE:
                return notEndsWithIgnoreCase(criteria, fieldPredicate);
            case CONTAINS:
                return contains(criteria, fieldPredicate);
            case NOT_CONTAINS:
                return notContains(criteria, fieldPredicate);
            case CONTAINS_IGNORE_CASE:
                return containsIgnoreCase(criteria, fieldPredicate);
            case NOT_CONTAINS_IGNORE_CASE:
                return notContainsIgnoreCase(criteria, fieldPredicate);
            case IS_EMPTY:
                return isEmpty(criteria, fieldPredicate);
            case IS_NOT_EMPTY:
                return isNotEmpty(criteria, fieldPredicate);
            default:
                throw new JpaStreamerException(
                    "Predicate type [" + fieldPredicate.getPredicateType()
                        + "] is not supported"
                );
        }
    }
}

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
package com.speedment.jpastreamer.criteria.standard.internal.predicate;

import static java.util.Objects.requireNonNull;

import com.speedment.jpastreamer.criteria.Criteria;
import com.speedment.jpastreamer.criteria.standard.internal.util.Cast;
import com.speedment.jpastreamer.exception.JPAStreamerException;
import com.speedment.jpastreamer.field.Field;
import com.speedment.jpastreamer.field.predicate.FieldPredicate;
import com.speedment.jpastreamer.field.predicate.Inclusion;
import com.speedment.jpastreamer.field.predicate.trait.HasInclusion;
import com.speedment.jpastreamer.field.trait.HasArg0;
import com.speedment.jpastreamer.field.trait.HasArg1;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;

public final class DefaultPredicateMapper implements PredicateMapper {
    
    @Override
    public <ENTITY> Predicate mapPredicate(
        final Criteria<ENTITY, ?> criteria,
        final FieldPredicate<ENTITY> fieldPredicate
    ) {
        requireNonNull(criteria);
        requireNonNull(fieldPredicate);

        return mapPredicate0(criteria, fieldPredicate);
    }

    private <ENTITY> Predicate alwaysTrue(
        final Criteria<ENTITY, ?> criteria,
        final FieldPredicate<ENTITY> fieldPredicate
    ) {
        return noValueMapping(
            fieldPredicate,
            column -> criteria.getBuilder().isTrue(criteria.getBuilder().literal(true))
        );
    }

    private <ENTITY> Predicate alwaysFalse(
        final Criteria<ENTITY, ?> criteria,
        final FieldPredicate<ENTITY> fieldPredicate
    ) {
        return noValueMapping(
            fieldPredicate,
            column -> criteria.getBuilder().isFalse(criteria.getBuilder().literal(true))
        );
    }

    private <ENTITY> Predicate isNull(
        final Criteria<ENTITY, ?> criteria,
        final FieldPredicate<ENTITY> fieldPredicate
    ) {
        return noValueMapping(
            fieldPredicate,
            column -> criteria.getBuilder().isNull(criteria.getRoot().get(column))
        );
    }

    private <ENTITY> Predicate isNotNull(
        final Criteria<ENTITY, ?> criteria,
        final FieldPredicate<ENTITY> fieldPredicate
    ) {
        return noValueMapping(
            fieldPredicate,
            column -> criteria.getBuilder().isNotNull(criteria.getRoot().get(column))
        );
    }

    private <ENTITY> Predicate equal(
        final Criteria<ENTITY, ?> criteria,
        final FieldPredicate<ENTITY> fieldPredicate
    ) {
        return typeMapping(
            fieldPredicate,
            (column, value) -> criteria.getBuilder().equal(criteria.getRoot().get(column), value),
            Object.class
        );
    }

    private <ENTITY> Predicate notEqual(
        final Criteria<ENTITY, ?> criteria,
        final FieldPredicate<ENTITY> fieldPredicate
    ) {
        return typeMapping(
            fieldPredicate,
            (column, value) -> criteria.getBuilder().notEqual(criteria.getRoot().get(column), value),
            Object.class
        );
    }

    @SuppressWarnings("unchecked")
    private <ENTITY> Predicate lessThan(
        final Criteria<ENTITY, ?> criteria,
        final FieldPredicate<ENTITY> fieldPredicate
    ) {
        return singleBoundRangeComparisonMapping(
            fieldPredicate,
            (column, value) -> criteria.getBuilder().lt(criteria.getRoot().get(column), value),
            (column, value) -> criteria.getBuilder().lessThan(criteria.getRoot().get(column), value)
        );
    }

    @SuppressWarnings("unchecked")
    private <ENTITY> Predicate lessOrEqual(
        final Criteria<ENTITY, ?> criteria,
        final FieldPredicate<ENTITY> fieldPredicate
    ) {
        return singleBoundRangeComparisonMapping(
            fieldPredicate,
            (column, value) -> criteria.getBuilder().le(criteria.getRoot().get(column), value),
            (column, value) -> criteria.getBuilder().lessThanOrEqualTo(criteria.getRoot().get(column), value)
        );
    }

    @SuppressWarnings("unchecked")
    private <ENTITY, S extends Comparable<? super S>> Predicate between(
        final Criteria<ENTITY, ?> criteria,
        final FieldPredicate<ENTITY> fieldPredicate
    ) {
        return doubleBoundRangeComparisonMapping(
            fieldPredicate,
            (column, value) -> {
                final CriteriaBuilder builder = criteria.getBuilder();
                final Path<S> columnPath = criteria.getRoot().get(column);

                final S first = (S) value.getLowerBound();
                final S second = (S) value.getUpperBound();

                final Inclusion inclusion = value.getInclusion();

                switch (inclusion) {
                    case START_EXCLUSIVE_END_EXCLUSIVE:
                        return builder.between(
                            columnPath,
                            first,
                            second
                        );
                    case START_INCLUSIVE_END_EXCLUSIVE:
                        return builder.and(
                            builder.greaterThanOrEqualTo(
                                columnPath,
                                first
                            ),
                            builder.lessThan(
                                columnPath,
                                second
                            )
                        );
                    case START_EXCLUSIVE_END_INCLUSIVE:
                        return builder.and(
                            builder.greaterThan(
                                columnPath,
                                first
                            ),
                            builder.lessThanOrEqualTo(
                                columnPath,
                                second
                            )
                        );
                    case START_INCLUSIVE_END_INCLUSIVE:
                        return builder.and(
                            builder.greaterThanOrEqualTo(
                                columnPath,
                                first
                            ),
                            builder.lessThanOrEqualTo(
                                columnPath,
                                second
                            )
                        );
                    default:
                        throw new JPAStreamerException(
                            "Inclusion type [" + inclusion + "] is not supported"
                        );
                }
            }
        );
    }

    @SuppressWarnings("unchecked")
    private <ENTITY, S extends Comparable<? super S>> Predicate notBetween(
        final Criteria<ENTITY, ?> criteria,
        final FieldPredicate<ENTITY> fieldPredicate
    ) {
        return doubleBoundRangeComparisonMapping(
            fieldPredicate,
            (column, value) -> {
                final CriteriaBuilder builder = criteria.getBuilder();
                final Path<S> columnPath = criteria.getRoot().get(column);

                final S first = (S) value.getLowerBound();
                final S second = (S) value.getUpperBound();

                final Inclusion inclusion = value.getInclusion();

                switch (inclusion) {
                    case START_EXCLUSIVE_END_EXCLUSIVE:
                        return builder.or(
                            builder.lessThanOrEqualTo(
                                columnPath,
                                first
                            ),
                            builder.greaterThanOrEqualTo(
                                columnPath,
                                second
                            )
                        );
                    case START_EXCLUSIVE_END_INCLUSIVE:
                        return builder.or(
                            builder.lessThanOrEqualTo(
                                columnPath,
                                first
                            ),
                            builder.greaterThan(
                                columnPath,
                                second
                            )
                        );
                    case START_INCLUSIVE_END_INCLUSIVE:
                        return builder.or(
                            builder.lessThan(
                                columnPath,
                                first
                            ),
                            builder.greaterThan(
                                columnPath,
                                second
                            )
                        );
                    case START_INCLUSIVE_END_EXCLUSIVE:
                        return builder.or(
                            builder.lessThan(
                                columnPath,
                                first
                            ),
                            builder.greaterThanOrEqualTo(
                                columnPath,
                                second
                            )
                        );
                    default:
                        throw new JPAStreamerException(
                            "Inclusion type [" + inclusion + "] is not supported"
                        );
                    }
                }
        );
    }

    private <ENTITY> Predicate in(
        final Criteria<ENTITY, ?> criteria,
        final FieldPredicate<ENTITY> fieldPredicate
    ) {
        final Object value = Cast.castOrFail(fieldPredicate, HasArg0.class).get0();

        if (!(value instanceof Set)) {
            throw new JPAStreamerException();
        }

        final Field<ENTITY> field = fieldPredicate.getField();
        final String column = field.columnName();

        final Set<?> set = (Set<?>) value;

        return criteria.getRoot().get(column).in(set);
    }

    private <ENTITY> Predicate notIn(
        final Criteria<ENTITY, ?> criteria,
        final FieldPredicate<ENTITY> fieldPredicate
    ) {
        return in(criteria, fieldPredicate).not();
    }

    @SuppressWarnings("unchecked")
    private <ENTITY> Predicate greaterThan(
        final Criteria<ENTITY, ?> criteria,
        final FieldPredicate<ENTITY> fieldPredicate
    ) {
        return singleBoundRangeComparisonMapping(
            fieldPredicate,
            (column, value) -> criteria.getBuilder().gt(criteria.getRoot().get(column), value),
            (column, value) -> criteria.getBuilder().greaterThan(criteria.getRoot().get(column), value)
        );
    }

    @SuppressWarnings("unchecked")
    private <ENTITY> Predicate greaterOrEqual(
        final Criteria<ENTITY, ?> criteria,
        final FieldPredicate<ENTITY> fieldPredicate
    ) {
        return singleBoundRangeComparisonMapping(
            fieldPredicate,
            (column, value) -> criteria.getBuilder().ge(criteria.getRoot().get(column), value),
            (column, value) -> criteria.getBuilder().greaterThanOrEqualTo(criteria.getRoot().get(column), value)
        );
    }

    private <ENTITY> Predicate equalIgnoreCase(
        final Criteria<ENTITY, ?> criteria,
        final FieldPredicate<ENTITY> fieldPredicate
    ) {
        return typeMapping(
            fieldPredicate,
            (column, value) -> criteria.getBuilder().equal(
                criteria.getBuilder().lower(criteria.getRoot().get(column)), value.toLowerCase()),
            String.class
        );
    }

    private <ENTITY> Predicate notEqualIgnoreCase(
        final Criteria<ENTITY, ?> criteria,
        final FieldPredicate<ENTITY> fieldPredicate
    ) {
        return typeMapping(
            fieldPredicate,
            (column, value) -> criteria.getBuilder().notEqual(
                criteria.getBuilder().lower(criteria.getRoot().get(column)), value.toLowerCase()),
            String.class
        );
    }

    private <ENTITY> Predicate startsWith(
        final Criteria<ENTITY, ?> criteria,
        final FieldPredicate<ENTITY> fieldPredicate
    ) {
        return typeMapping(
            fieldPredicate,
            (column, value) -> criteria.getBuilder().like(criteria.getRoot().get(column), value + "%"),
            String.class
        );
    }

    private <ENTITY> Predicate notStartsWith(
        final Criteria<ENTITY, ?> criteria,
        final FieldPredicate<ENTITY> fieldPredicate
    ) {
        return typeMapping(
            fieldPredicate,
            (column, value) -> criteria.getBuilder().notLike(criteria.getRoot().get(column), value + "%"),
            String.class
        );
    }

    private <ENTITY> Predicate startsWithIgnoreCase(
        final Criteria<ENTITY, ?> criteria,
        final FieldPredicate<ENTITY> fieldPredicate
    ) {
        return typeMapping(
            fieldPredicate,
            (column, value) -> criteria.getBuilder().like(
                criteria.getBuilder().lower(criteria.getRoot().get(column)), value.toLowerCase() + "%"),
            String.class
        );
    }

    private <ENTITY> Predicate notStartsWithIgnoreCase(
        final Criteria<ENTITY, ?> criteria,
        final FieldPredicate<ENTITY> fieldPredicate
    ) {
        return typeMapping(
            fieldPredicate,
            (column, value) -> criteria.getBuilder().notLike(
                criteria.getBuilder().lower(criteria.getRoot().get(column)), value.toLowerCase() + "%"),
            String.class
        );
    }

    private <ENTITY> Predicate endsWith(
        final Criteria<ENTITY, ?> criteria,
        final FieldPredicate<ENTITY> fieldPredicate
    ) {
        return typeMapping(
            fieldPredicate,
            (column, value) -> criteria.getBuilder().like(criteria.getRoot().get(column), "%" + value),
            String.class
        );
    }

    private <ENTITY> Predicate notEndsWith(
        final Criteria<ENTITY, ?> criteria,
        final FieldPredicate<ENTITY> fieldPredicate
    ) {
        return typeMapping(
            fieldPredicate,
            (column, value) -> criteria.getBuilder().notLike(criteria.getRoot().get(column), "%" + value),
            String.class
        );
    }

    private <ENTITY> Predicate endsWithIgnoreCase(
        final Criteria<ENTITY, ?> criteria,
        final FieldPredicate<ENTITY> fieldPredicate
    ) {
        return typeMapping(
            fieldPredicate,
            (column, value) -> criteria.getBuilder().like(
                criteria.getBuilder().lower(criteria.getRoot().get(column)), "%" + value.toLowerCase()),
            String.class
        );
    }

    private <ENTITY> Predicate notEndsWithIgnoreCase(
        final Criteria<ENTITY, ?> criteria,
        final FieldPredicate<ENTITY> fieldPredicate
    ) {
        return typeMapping(
            fieldPredicate,
            (column, value) -> criteria.getBuilder().notLike(
                criteria.getBuilder().lower(criteria.getRoot().get(column)), "%" + value.toLowerCase()),
            String.class
        );
    }

    private <ENTITY> Predicate contains(
        final Criteria<ENTITY, ?> criteria,
        final FieldPredicate<ENTITY> fieldPredicate
    ) {
        return typeMapping(
            fieldPredicate,
            (column, value) -> criteria.getBuilder().like(criteria.getRoot().get(column), "%" + value + "%"),
            String.class
        );
    }

    private <ENTITY> Predicate notContains(
        final Criteria<ENTITY, ?> criteria,
        final FieldPredicate<ENTITY> fieldPredicate
    ) {
        return typeMapping(
            fieldPredicate,
            (column, value) -> criteria.getBuilder().notLike(criteria.getRoot().get(column), "%" + value + "%"),
            String.class
        );
    }

    private <ENTITY> Predicate containsIgnoreCase(
        final Criteria<ENTITY, ?> criteria,
        final FieldPredicate<ENTITY> fieldPredicate
    ) {
        return typeMapping(
            fieldPredicate,
            (column, value) -> criteria.getBuilder().like(
                criteria.getBuilder().lower(criteria.getRoot().get(column)), "%" + value.toLowerCase() + "%"),
            String.class
        );
    }

    private <ENTITY> Predicate notContainsIgnoreCase(
        final Criteria<ENTITY, ?> criteria,
        final FieldPredicate<ENTITY> fieldPredicate
    ) {
        return typeMapping(
            fieldPredicate,
            (column, value) -> criteria.getBuilder().notLike(
                criteria.getBuilder().lower(criteria.getRoot().get(column)), "%" + value.toLowerCase() + "%"),
            String.class
        );
    }

    private <ENTITY> Predicate isEmpty(
        final Criteria<ENTITY, ?> criteria,
        final FieldPredicate<ENTITY> fieldPredicate
    ) {
        return noValueMapping(
            fieldPredicate,
            column -> criteria.getBuilder().equal(criteria.getRoot().get(column), "")
        );
    }

    private <ENTITY> Predicate isNotEmpty(
        final Criteria<ENTITY, ?> criteria,
        final FieldPredicate<ENTITY> fieldPredicate
    ) {
        return noValueMapping(
            fieldPredicate,
            column -> criteria.getBuilder().notEqual(criteria.getRoot().get(column), "")
        );
    }

    /*
     * Mapping Helpers - Start
     */

    private <ENTITY> Predicate noValueMapping(
        final FieldPredicate<ENTITY> fieldPredicate,
        final Function<String, Predicate> callback
    ) {
        final String column = fieldPredicate.getField().columnName();

        return callback.apply(column);
    }

    @SuppressWarnings("unchecked")
    private <ENTITY, S> Predicate typeMapping(
        final FieldPredicate<ENTITY> fieldPredicate,
        final BiFunction<String, S, Predicate> callback,
        final Class<S> clazz
    ) {
        final String column = fieldPredicate.getField().columnName();
        final Object value = Cast.castOrFail(fieldPredicate, HasArg0.class).get0();

        if (clazz.isInstance(value)) {
            return callback.apply(column, (S) value);
        }

        throw new JPAStreamerException();
    }

    @SuppressWarnings("rawtypes")
    private <ENTITY> Predicate singleBoundRangeComparisonMapping(
        final FieldPredicate<ENTITY> fieldPredicate,
        final BiFunction<String, Number, Predicate> callback,
        final BiFunction<String, Comparable, Predicate> comparableCallback
    ) {
        final String column = fieldPredicate.getField().columnName();
        final Object value = Cast.castOrFail(fieldPredicate, HasArg0.class).get0();

        if (value instanceof Number) {
            return callback.apply(column, (Number) value);
        }

        if (value instanceof Character) {
            return callback.apply(column, (int) (char) value);
        }

        if (value instanceof Comparable) {
            return comparableCallback.apply(column, (Comparable) value);
        }

        throw new JPAStreamerException("Illegal comparison value [" + value + "]");
    }

    @SuppressWarnings("unchecked")
    private <ENTITY, S extends Comparable<? super S>> Predicate doubleBoundRangeComparisonMapping(
            final FieldPredicate<ENTITY> fieldPredicate,
            final BiFunction<String, RangeInformation<S>, Predicate> callback
    ) {
        final String column = fieldPredicate.getField().columnName();
        final Object arg0 = Cast.castOrFail(fieldPredicate, HasArg0.class).get0();
        final Object arg1 = Cast.castOrFail(fieldPredicate, HasArg1.class).get1();

        final Inclusion inclusion = Cast.cast(fieldPredicate, HasInclusion.class)
            .map(HasInclusion::getInclusion)
            .orElse(Inclusion.START_INCLUSIVE_END_INCLUSIVE);

        if (arg0 instanceof Comparable && arg1 instanceof Comparable) {
            final RangeInformation<S> rangeInformation = new RangeInformation<>((S) arg0, (S) arg1,
                    inclusion);

            return callback.apply(column, rangeInformation);
        }

        throw new JPAStreamerException("Illegal comparison values [" + arg0 + "," + arg1 + "]");
    }

    /*
     * Mapping Helpers - End
     */

    private <ENTITY> Predicate mapPredicate0(
        final Criteria<ENTITY, ?> criteria,
        final FieldPredicate<ENTITY> fieldPredicate
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
                throw new JPAStreamerException(
                    "Predicate type [" + fieldPredicate.getPredicateType()
                        + "] is not supported"
                );
        }
    }

    private static final class RangeInformation<S extends Comparable<? super S>> {

        private final S lowerBound;
        private final S upperBound;
        private final Inclusion inclusion;

        private RangeInformation(
            final S lowerBound,
            final S upperBound,
            final Inclusion inclusion
        ) {
            this.lowerBound = requireNonNull(lowerBound);
            this.upperBound = requireNonNull(upperBound);
            this.inclusion = requireNonNull(inclusion);
        }

        public S getLowerBound() {
            return lowerBound;
        }

        public S getUpperBound() {
            return upperBound;
        }

        public Inclusion getInclusion() {
            return inclusion;
        }
    }
}

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

import static com.speedment.jpastreamer.criteria.standard.internal.predicate.ParameterizedPredicate.createParameterizedPredicate;
import static java.util.Objects.requireNonNull;

import com.speedment.common.function.TriFunction;
import com.speedment.jpastreamer.criteria.Criteria;
import com.speedment.jpastreamer.criteria.QueryParameter;
import com.speedment.jpastreamer.criteria.standard.internal.InternalQueryParameter;
import com.speedment.jpastreamer.criteria.standard.internal.util.Cast;
import com.speedment.jpastreamer.exception.JPAStreamerException;
import com.speedment.jpastreamer.field.Field;
import com.speedment.jpastreamer.field.predicate.FieldPredicate;
import com.speedment.jpastreamer.field.predicate.Inclusion;
import com.speedment.jpastreamer.field.predicate.trait.HasInclusion;
import com.speedment.jpastreamer.field.trait.HasArg0;
import com.speedment.jpastreamer.field.trait.HasArg1;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import java.util.Set;
import java.util.function.Function;

public final class DefaultPredicateMapper implements PredicateMapper {
    
    @Override
    public <ENTITY> PredicateMapping mapPredicate(
        final Criteria<ENTITY, ?> criteria,
        final FieldPredicate<ENTITY> fieldPredicate
    ) {
        requireNonNull(criteria);
        requireNonNull(fieldPredicate);

        return mapPredicate0(criteria, fieldPredicate);
    }

    private <ENTITY> PredicateMapping alwaysTrue(
        final Criteria<ENTITY, ?> criteria,
        final FieldPredicate<ENTITY> fieldPredicate
    ) {
        return noValueMapping(
            fieldPredicate,
            column -> criteria.getBuilder().isTrue(criteria.getBuilder().literal(true))
        );
    }

    private <ENTITY> PredicateMapping alwaysFalse(
        final Criteria<ENTITY, ?> criteria,
        final FieldPredicate<ENTITY> fieldPredicate
    ) {
        return noValueMapping(
            fieldPredicate,
            column -> criteria.getBuilder().isFalse(criteria.getBuilder().literal(true))
        );
    }

    private <ENTITY> PredicateMapping isNull(
        final Criteria<ENTITY, ?> criteria,
        final FieldPredicate<ENTITY> fieldPredicate
    ) {
        return noValueMapping(
            fieldPredicate,
            column -> criteria.getBuilder().isNull(criteria.getRoot().get(column))
        );
    }

    private <ENTITY> PredicateMapping isNotNull(
        final Criteria<ENTITY, ?> criteria,
        final FieldPredicate<ENTITY> fieldPredicate
    ) {
        return noValueMapping(
            fieldPredicate,
            column -> criteria.getBuilder().isNotNull(criteria.getRoot().get(column))
        );
    }

    private <ENTITY> PredicateMapping equal(
        final Criteria<ENTITY, ?> criteria,
        final FieldPredicate<ENTITY> fieldPredicate
    ) {
        return typeMapping(
            criteria,
            fieldPredicate,
            createParameterizedPredicate(
                (column, parameter) -> criteria.getBuilder().equal(criteria.getRoot().get(column), parameter)
            ),
            Object.class
        );
    }

    private <ENTITY> PredicateMapping notEqual(
        final Criteria<ENTITY, ?> criteria,
        final FieldPredicate<ENTITY> fieldPredicate
    ) {
        return typeMapping(
            criteria,
            fieldPredicate,
            createParameterizedPredicate(
                (column, parameter) -> criteria.getBuilder().notEqual(criteria.getRoot().get(column), parameter)
            ),
            Object.class
        );
    }

    @SuppressWarnings("unchecked")
    private <ENTITY> PredicateMapping lessThan(
        final Criteria<ENTITY, ?> criteria,
        final FieldPredicate<ENTITY> fieldPredicate
    ) {
        return singleBoundRangeComparisonMapping(
            criteria,
            fieldPredicate,
            createParameterizedPredicate(
                (column, parameter) -> criteria.getBuilder().lt(criteria.getRoot().get(column), parameter)
            ),
            createParameterizedPredicate(
                (column, parameter) -> criteria.getBuilder().lessThan(criteria.getRoot().get(column), parameter)
            )
        );
    }

    @SuppressWarnings("unchecked")
    private <ENTITY> PredicateMapping lessOrEqual(
        final Criteria<ENTITY, ?> criteria,
        final FieldPredicate<ENTITY> fieldPredicate
    ) {
        return singleBoundRangeComparisonMapping(
            criteria,
            fieldPredicate,
            createParameterizedPredicate(
                (column, parameter) -> criteria.getBuilder().le(criteria.getRoot().get(column), parameter)
            ),
            createParameterizedPredicate(
                (column, parameter) -> criteria.getBuilder().lessThanOrEqualTo(criteria.getRoot().get(column), parameter)
            )
        );
    }

    @SuppressWarnings("unchecked")
    private <ENTITY, S extends Comparable<? super S>> PredicateMapping between(
        final Criteria<ENTITY, ?> criteria,
        final FieldPredicate<ENTITY> fieldPredicate
    ) {
        return doubleBoundRangeComparisonMapping(
            criteria,
            fieldPredicate,
            (column, parameterExpressions, inclusion) -> {
                final CriteriaBuilder builder = criteria.getBuilder();
                final Path<S> columnPath = criteria.getRoot().get(column);

                final ParameterExpression<S> first = (ParameterExpression<S>) parameterExpressions.getFirst();
                final ParameterExpression<S> second = (ParameterExpression<S>) parameterExpressions.getSecond();

                switch (inclusion) {
                    case START_EXCLUSIVE_END_EXCLUSIVE:
                        return builder.and(
                            builder.greaterThan(
                                columnPath,
                                first
                            ),
                            builder.lessThan(
                                columnPath,
                                second
                            )
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
                        return builder.between(
                            columnPath,
                            first,
                            second
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
    private <ENTITY, S extends Comparable<? super S>> PredicateMapping notBetween(
        final Criteria<ENTITY, ?> criteria,
        final FieldPredicate<ENTITY> fieldPredicate
    ) {
        return doubleBoundRangeComparisonMapping(
            criteria,
            fieldPredicate,
            (column, parameterExpressions, inclusion) -> {
                final CriteriaBuilder builder = criteria.getBuilder();
                final Path<S> columnPath = criteria.getRoot().get(column);

                final ParameterExpression<S> first = (ParameterExpression<S>) parameterExpressions.getFirst();
                final ParameterExpression<S> second = (ParameterExpression<S>) parameterExpressions.getSecond();

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

    private <ENTITY> PredicateMapping in(
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

        return new PredicateMapping(criteria.getRoot().get(column).in(set));
    }

    private <ENTITY> PredicateMapping notIn(
        final Criteria<ENTITY, ?> criteria,
        final FieldPredicate<ENTITY> fieldPredicate
    ) {
        final Predicate predicate = in(criteria, fieldPredicate).getPredicate().not();

        return new PredicateMapping(predicate);
    }

    @SuppressWarnings("unchecked")
    private <ENTITY> PredicateMapping greaterThan(
        final Criteria<ENTITY, ?> criteria,
        final FieldPredicate<ENTITY> fieldPredicate
    ) {
        return singleBoundRangeComparisonMapping(
            criteria,
            fieldPredicate,
            createParameterizedPredicate(
                (column, parameter) -> criteria.getBuilder().gt(criteria.getRoot().get(column), parameter)
            ),
            createParameterizedPredicate(
                (column, parameter) -> criteria.getBuilder().greaterThan(criteria.getRoot().get(column), parameter)
            )
        );
    }

    @SuppressWarnings("unchecked")
    private <ENTITY> PredicateMapping greaterOrEqual(
        final Criteria<ENTITY, ?> criteria,
        final FieldPredicate<ENTITY> fieldPredicate
    ) {
        return singleBoundRangeComparisonMapping(
            criteria,
            fieldPredicate,
            createParameterizedPredicate(
                (column, parameter) -> criteria.getBuilder().ge(criteria.getRoot().get(column), parameter)
            ),
            createParameterizedPredicate(
                (column, parameter) -> criteria.getBuilder().greaterThanOrEqualTo(criteria.getRoot().get(column), parameter)
            )
        );
    }

    private <ENTITY> PredicateMapping equalIgnoreCase(
        final Criteria<ENTITY, ?> criteria,
        final FieldPredicate<ENTITY> fieldPredicate
    ) {
        return typeMapping(
            criteria,
            fieldPredicate,
            createParameterizedPredicate(
                (column, parameter) -> criteria.getBuilder().equal(criteria.getBuilder().lower(criteria.getRoot().get(column)), parameter),
                String::toLowerCase
            ),
            String.class
        );
    }

    private <ENTITY> PredicateMapping notEqualIgnoreCase(
        final Criteria<ENTITY, ?> criteria,
        final FieldPredicate<ENTITY> fieldPredicate
    ) {
        return typeMapping(
            criteria,
            fieldPredicate,
            createParameterizedPredicate(
                (column, parameter) -> criteria.getBuilder().notEqual(criteria.getBuilder().lower(criteria.getRoot().get(column)), parameter),
                String::toLowerCase
            ),
            String.class
        );
    }

    private <ENTITY> PredicateMapping startsWith(
        final Criteria<ENTITY, ?> criteria,
        final FieldPredicate<ENTITY> fieldPredicate
    ) {
        return typeMapping(
            criteria,
            fieldPredicate,
            createParameterizedPredicate(
                (column, parameter) -> criteria.getBuilder().like(criteria.getRoot().get(column), parameter),
                value -> value + "%"
            ),
            String.class
        );
    }

    private <ENTITY> PredicateMapping notStartsWith(
        final Criteria<ENTITY, ?> criteria,
        final FieldPredicate<ENTITY> fieldPredicate
    ) {
        return typeMapping(
            criteria,
            fieldPredicate,
            createParameterizedPredicate(
                (column, parameter) -> criteria.getBuilder().notLike(criteria.getRoot().get(column), parameter),
                value -> value + "%"
            ),
            String.class
        );
    }

    private <ENTITY> PredicateMapping startsWithIgnoreCase(
        final Criteria<ENTITY, ?> criteria,
        final FieldPredicate<ENTITY> fieldPredicate
    ) {
        return typeMapping(
            criteria,
            fieldPredicate,
            createParameterizedPredicate(
                (column, parameter) -> criteria.getBuilder().like(criteria.getBuilder().lower(criteria.getRoot().get(column)), parameter),
                value -> value.toLowerCase() + "%"
            ),
            String.class
        );
    }

    private <ENTITY> PredicateMapping notStartsWithIgnoreCase(
        final Criteria<ENTITY, ?> criteria,
        final FieldPredicate<ENTITY> fieldPredicate
    ) {
        return typeMapping(
            criteria,
            fieldPredicate,
            createParameterizedPredicate(
                (column, parameter) -> criteria.getBuilder().notLike(criteria.getBuilder().lower(criteria.getRoot().get(column)), parameter),
                value -> value.toLowerCase() + "%"
            ),
            String.class
        );
    }

    private <ENTITY> PredicateMapping endsWith(
        final Criteria<ENTITY, ?> criteria,
        final FieldPredicate<ENTITY> fieldPredicate
    ) {
        return typeMapping(
            criteria,
            fieldPredicate,
            createParameterizedPredicate(
                (column, parameter) -> criteria.getBuilder().like(criteria.getRoot().get(column), parameter),
                value -> "%" + value
            ),
            String.class
        );
    }

    private <ENTITY> PredicateMapping notEndsWith(
        final Criteria<ENTITY, ?> criteria,
        final FieldPredicate<ENTITY> fieldPredicate
    ) {
        return typeMapping(
            criteria,
            fieldPredicate,
            createParameterizedPredicate(
                (column, parameter) -> criteria.getBuilder().notLike(criteria.getRoot().get(column), parameter),
                value -> "%" + value
            ),
            String.class
        );
    }

    private <ENTITY> PredicateMapping endsWithIgnoreCase(
        final Criteria<ENTITY, ?> criteria,
        final FieldPredicate<ENTITY> fieldPredicate
    ) {
        return typeMapping(
            criteria,
            fieldPredicate,
            createParameterizedPredicate(
                (column, parameter) -> criteria.getBuilder().like(criteria.getBuilder().lower(criteria.getRoot().get(column)), parameter),
                value -> "%" + value.toLowerCase()
            ),
            String.class
        );
    }

    private <ENTITY> PredicateMapping notEndsWithIgnoreCase(
        final Criteria<ENTITY, ?> criteria,
        final FieldPredicate<ENTITY> fieldPredicate
    ) {
        return typeMapping(
            criteria,
            fieldPredicate,
            createParameterizedPredicate(
                (column, parameter) -> criteria.getBuilder().notLike(criteria.getBuilder().lower(criteria.getRoot().get(column)), parameter),
                value -> "%" + value.toLowerCase()
            ),
            String.class
        );
    }

    private <ENTITY> PredicateMapping contains(
        final Criteria<ENTITY, ?> criteria,
        final FieldPredicate<ENTITY> fieldPredicate
    ) {
        return typeMapping(
            criteria,
            fieldPredicate,
            createParameterizedPredicate(
                (column, parameter) -> criteria.getBuilder().like(criteria.getRoot().get(column), parameter),
                value -> "%" + value + "%"
            ),
            String.class
        );
    }

    private <ENTITY> PredicateMapping notContains(
        final Criteria<ENTITY, ?> criteria,
        final FieldPredicate<ENTITY> fieldPredicate
    ) {
        return typeMapping(
            criteria,
            fieldPredicate,
            createParameterizedPredicate(
                (column, parameter) -> criteria.getBuilder().notLike(criteria.getRoot().get(column), parameter),
                value -> "%" + value + "%"
            ),
            String.class
        );
    }

    private <ENTITY> PredicateMapping containsIgnoreCase(
        final Criteria<ENTITY, ?> criteria,
        final FieldPredicate<ENTITY> fieldPredicate
    ) {
        return typeMapping(
            criteria,
            fieldPredicate,
            createParameterizedPredicate(
                (column, parameter) -> criteria.getBuilder().like(criteria.getBuilder().lower(criteria.getRoot().get(column)), parameter),
                value -> "%" + value.toLowerCase() + "%"
            ),
            String.class
        );
    }

    private <ENTITY> PredicateMapping notContainsIgnoreCase(
        final Criteria<ENTITY, ?> criteria,
        final FieldPredicate<ENTITY> fieldPredicate
    ) {
        return typeMapping(
            criteria,
            fieldPredicate,
            createParameterizedPredicate(
                (column, parameter) -> criteria.getBuilder().notLike(criteria.getBuilder().lower(criteria.getRoot().get(column)), parameter),
                value -> "%" + value.toLowerCase() + "%"
            ),
            String.class
        );
    }

    private <ENTITY> PredicateMapping isEmpty(
        final Criteria<ENTITY, ?> criteria,
        final FieldPredicate<ENTITY> fieldPredicate
    ) {
        return noValueMapping(
            fieldPredicate,
            column -> criteria.getBuilder().equal(criteria.getRoot().get(column), "")
        );
    }

    private <ENTITY> PredicateMapping isNotEmpty(
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

    private <ENTITY> PredicateMapping noValueMapping(
        final FieldPredicate<ENTITY> fieldPredicate,
        final Function<String, Predicate> callback
    ) {
        final String column = fieldPredicate.getField().columnName();

        return new PredicateMapping(callback.apply(column));
    }

    @SuppressWarnings("unchecked")
    private <ENTITY, S> PredicateMapping typeMapping(
        final Criteria<ENTITY, ?> criteria,
        final FieldPredicate<ENTITY> fieldPredicate,
        final ParameterizedPredicate<String, S> parameterizedPredicate,
        final Class<S> clazz
    ) {
        final String column = fieldPredicate.getField().columnName();
        final Object value = Cast.castOrFail(fieldPredicate, HasArg0.class).get0();

        if (clazz.isInstance(value)) {
            final S val = parameterizedPredicate.getValueMapper().apply((S) value);

            final ParameterExpression<S> parameter = criteria.getBuilder().parameter(clazz);
            final QueryParameter<S> queryParameter = new InternalQueryParameter<>(parameter, val);

            return new PredicateMapping(parameterizedPredicate.getParameterMapper()
                .apply(column, parameter), queryParameter);
        }

        throw new JPAStreamerException();
    }

    @SuppressWarnings("rawtypes")
    private <ENTITY> PredicateMapping singleBoundRangeComparisonMapping(
        final Criteria<ENTITY, ?> criteria,
        final FieldPredicate<ENTITY> fieldPredicate,
        final ParameterizedPredicate<String, Number> parameterizedNumberPredicate,
        final ParameterizedPredicate<String, Comparable> parameterizedComparablePredicate
    ) {
        final String column = fieldPredicate.getField().columnName();
        final Object value = Cast.castOrFail(fieldPredicate, HasArg0.class).get0();

        if (value instanceof Number) {
            final Number val = parameterizedNumberPredicate.getValueMapper().apply((Number) value);

            final ParameterExpression<Number> numberParameter = criteria.getBuilder().parameter(Number.class);
            final QueryParameter<Number> queryParameter = new InternalQueryParameter<>(numberParameter, val);

            return new PredicateMapping(parameterizedNumberPredicate.getParameterMapper()
                .apply(column, numberParameter), queryParameter);
        }

        if (value instanceof Character) {
            final Number val = parameterizedNumberPredicate.getValueMapper().apply((int) (char) value);

            final ParameterExpression<Number> numberParameter = criteria.getBuilder().parameter(Number.class);
            final QueryParameter<Number> queryParameter = new InternalQueryParameter<>(numberParameter, val);

            return new PredicateMapping(parameterizedNumberPredicate.getParameterMapper()
                .apply(column, numberParameter), queryParameter);
        }

        if (value instanceof Comparable) {
            final Comparable val = parameterizedComparablePredicate.getValueMapper().apply((Comparable) value);

            final ParameterExpression<Comparable> comparableParameter = criteria.getBuilder().parameter(Comparable.class);
            final QueryParameter<Comparable> queryParameter = new InternalQueryParameter<>(comparableParameter, val);

            return new PredicateMapping(parameterizedComparablePredicate.getParameterMapper()
                .apply(column, comparableParameter), queryParameter);
        }

        throw new JPAStreamerException("Illegal comparison value [" + value + "]");
    }

    @SuppressWarnings("rawtypes")
    private <ENTITY> PredicateMapping doubleBoundRangeComparisonMapping(
        final Criteria<ENTITY, ?> criteria,
        final FieldPredicate<ENTITY> fieldPredicate,
        final TriFunction<String, Pair<ParameterExpression<?>, ParameterExpression<?>>, Inclusion, Predicate> callback
    ) {
        final String column = fieldPredicate.getField().columnName();
        final Object arg0 = Cast.castOrFail(fieldPredicate, HasArg0.class).get0();
        final Object arg1 = Cast.castOrFail(fieldPredicate, HasArg1.class).get1();

        final Inclusion inclusion = Cast.cast(fieldPredicate, HasInclusion.class)
            .map(HasInclusion::getInclusion)
            .orElse(Inclusion.START_INCLUSIVE_END_INCLUSIVE);

        if (arg0 instanceof Comparable && arg1 instanceof Comparable) {
            final ParameterExpression<Comparable> lowerBoundParameter = criteria.getBuilder().parameter(Comparable.class);
            final ParameterExpression<Comparable> upperBoundParameter = criteria.getBuilder().parameter(Comparable.class);

            final QueryParameter<Comparable> lowerBoundQueryParameter = new InternalQueryParameter<>(lowerBoundParameter, (Comparable) arg0);
            final QueryParameter<Comparable> upperBoundQueryParameter = new InternalQueryParameter<>(upperBoundParameter, (Comparable) arg1);

            return new PredicateMapping(callback.apply(column, new Pair<>(lowerBoundParameter, upperBoundParameter), inclusion), lowerBoundQueryParameter, upperBoundQueryParameter);
        }

        throw new JPAStreamerException("Illegal comparison values [" + arg0 + "," + arg1 + "]");
    }

    /*
     * Mapping Helpers - End
     */

    private <ENTITY> PredicateMapping mapPredicate0(
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

    private static final class Pair<S, T> {
        private final S first;
        private final T second;

        private Pair(S first, T second) {
            this.first = first;
            this.second = second;
        }

        public S getFirst() {
            return first;
        }

        public T getSecond() {
            return second;
        }
    }
}

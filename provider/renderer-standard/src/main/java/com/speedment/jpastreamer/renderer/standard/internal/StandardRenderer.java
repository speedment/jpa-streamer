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
package com.speedment.jpastreamer.renderer.standard.internal;

import static java.util.Objects.requireNonNull;

import com.speedment.jpastreamer.criteria.Criteria;
import com.speedment.jpastreamer.criteria.CriteriaFactory;
import com.speedment.jpastreamer.interopoptimizer.IntermediateOperationOptimizerFactory;
import com.speedment.jpastreamer.merger.CriteriaMerger;
import com.speedment.jpastreamer.merger.MergerFactory;
import com.speedment.jpastreamer.merger.QueryMerger;
import com.speedment.jpastreamer.pipeline.Pipeline;
import com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperation;
import com.speedment.jpastreamer.pipeline.terminal.TerminalOperationType;
import com.speedment.jpastreamer.renderer.RenderResult;
import com.speedment.jpastreamer.renderer.Renderer;
import com.speedment.jpastreamer.rootfactory.RootFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import java.util.ServiceLoader;
import java.util.stream.Stream;

public final class StandardRenderer implements Renderer {

    private final EntityManager entityManager;
    private final CriteriaFactory criteriaFactory;

    private final IntermediateOperationOptimizerFactory intermediateOperationOptimizerFactory;

    private final MergerFactory mergerFactory;

    StandardRenderer(final EntityManagerFactory entityManagerFactory) {
        this.entityManager = requireNonNull(entityManagerFactory).createEntityManager();
        this.criteriaFactory = RootFactory.getOrThrow(CriteriaFactory.class, ServiceLoader::load);
        this.intermediateOperationOptimizerFactory = RootFactory.getOrThrow(IntermediateOperationOptimizerFactory.class, ServiceLoader::load);
        this.mergerFactory = RootFactory.getOrThrow(MergerFactory.class, ServiceLoader::load);
    }

    @Override
    public <T> RenderResult<?> render(final Pipeline<T> pipeline) {
        optimizePipeline(pipeline);

        final Class<T> entityClass = pipeline.root();

        final CriteriaMerger criteriaMerger = mergerFactory.createCriteriaMerger();
        final QueryMerger queryMerger = mergerFactory.createQueryMerger();

        final Criteria<T, T> criteria = criteriaFactory.createCriteria(entityManager, entityClass);
        criteria.getRoot().alias(pipeline.root().getSimpleName());
        criteria.getQuery().select(criteria.getRoot());

        criteriaMerger.merge(pipeline, criteria);

        if (pipeline.terminatingOperation().type() == TerminalOperationType.COUNT && pipeline.intermediateOperations().size() == 0) {
            final Criteria<T, Long> countCriteria = createCountCriteria(criteria);

            final TypedQuery<Long> typedQuery = entityManager.createQuery(countCriteria.getQuery());

            return new StandardRenderResult<>(
                Long.class,
                typedQuery.getResultStream(),
                pipeline.terminatingOperation()
            );
        }

        final TypedQuery<T> typedQuery = entityManager.createQuery(criteria.getQuery());

        queryMerger.merge(pipeline, typedQuery);

        final Stream<T> baseStream = typedQuery.getResultStream();
        final Stream<T> replayed = replay(baseStream, pipeline);

        return new StandardRenderResult<>(
            entityClass,
            replayed,
            pipeline.terminatingOperation()
        );
    }

    private <T> Criteria<T, Long> createCountCriteria(final Criteria<T, T> criteria) {
        final CriteriaQuery<T> criteriaQuery = criteria.getQuery();

        final Criteria<T, Long> countCriteria = criteriaFactory.createCriteria(
            entityManager,
            criteriaQuery.getResultType(),
            Long.class
        );
        countCriteria.getRoot().alias(criteria.getRoot().getAlias());

        final CriteriaQuery<Long> countQuery = countCriteria.getQuery();

        countQuery.select(countCriteria.getBuilder().count(countCriteria.getRoot()));

        if (criteriaQuery.getRestriction() != null) {
            countQuery.where(criteriaQuery.getRestriction());
        }

        countQuery.distinct(criteriaQuery.isDistinct());
        countQuery.orderBy(criteria.getQuery().getOrderList());

        return countCriteria;
    }

     @SuppressWarnings({"rawtypes", "unchecked"})
    private <T> Stream<T> replay(final Stream<T> stream, final Pipeline<T> pipeline) {
        Stream<T> decorated = stream;

        for (IntermediateOperation intermediateOperation : pipeline.intermediateOperations()) {
            decorated = (Stream<T>) intermediateOperation.function().apply(decorated);
        }

        return decorated;
    }

    private <T> void optimizePipeline(final Pipeline<T> pipeline) {
        intermediateOperationOptimizerFactory.stream().forEach(intermediateOperationOptimizer -> intermediateOperationOptimizer.optimize(pipeline));
    }

    @Override
    public void close() {
        entityManager.close();
    }
}

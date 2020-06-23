package com.speedment.jpastreamer.renderer.standard.internal;

import static java.util.Objects.requireNonNull;

import com.speedment.jpastreamer.criteria.Criteria;
import com.speedment.jpastreamer.criteria.CriteriaFactory;
import com.speedment.jpastreamer.merger.CriteriaMerger;
import com.speedment.jpastreamer.merger.QueryMerger;
import com.speedment.jpastreamer.pipeline.Pipeline;
import com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperation;
import com.speedment.jpastreamer.renderer.RenderResult;
import com.speedment.jpastreamer.renderer.Renderer;
import com.speedment.jpastreamer.rootfactory.RootFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.stream.Stream;

public final class StandardRenderer implements Renderer {

    private final EntityManager entityManager;
    private final CriteriaFactory criteriaFactory;

    private final CriteriaMerger criteriaMerger;
    private final QueryMerger queryMerger;

    StandardRenderer(final EntityManagerFactory entityManagerFactory) {
        this.entityManager = requireNonNull(entityManagerFactory).createEntityManager();
        this.criteriaFactory = RootFactory.getOrThrow(CriteriaFactory.class);
        this.criteriaMerger = RootFactory.getOrThrow(CriteriaMerger.class);
        this.queryMerger = RootFactory.getOrThrow(QueryMerger.class);
    }

    @Override
    public <T> RenderResult<T> render(final Pipeline<T> pipeline) {
        final Class<T> entityClass = pipeline.root();

        final Stream<T> baseStream = baseStream(pipeline, entityManager, entityClass);
        final Stream<T> replayed = replay(baseStream, pipeline);

        return new StandardRenderResult<>(
            replayed,
            pipeline.terminatingOperation()
        );
    }

     private <T> Stream<T> baseStream(
         final Pipeline<T> pipeline,
         final EntityManager entityManager,
         final Class<T> entityClass
     ) {
         final Criteria<T> criteria = criteriaFactory.createCriteria(entityManager, entityClass);
         criteria.getQuery().select(criteria.getRoot());

         criteriaMerger.merge(pipeline, criteria);

         final TypedQuery<T> typedQuery = entityManager.createQuery(criteria.getQuery());

         queryMerger.merge(pipeline, typedQuery);

         return typedQuery.getResultStream();
     }

     @SuppressWarnings({"rawtypes", "unchecked"})
    private <T> Stream<T> replay(final Stream<T> stream, final Pipeline<T> pipeline) {
        Stream<T> decorated = stream;

        for (IntermediateOperation intermediateOperation : pipeline.intermediateOperations()) {
            decorated = (Stream<T>) intermediateOperation.function().apply(decorated);
        }

        return decorated;
    }

    @Override
    public void close() {
        entityManager.close();
    }
}

package com.speedment.jpastreamer.renderer.standard.internal;

import static java.util.Objects.requireNonNull;

import com.speedment.jpastreamer.pipeline.Pipeline;
import com.speedment.jpastreamer.renderer.RenderResult;
import com.speedment.jpastreamer.renderer.Renderer;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.stream.Stream;

public class StandardRenderer implements Renderer {

    private final EntityManager entityManager;

    StandardRenderer(EntityManagerFactory entityManagerFactory) {
        this.entityManager = requireNonNull(entityManagerFactory).createEntityManager();
    }

    @Override
    public <T> RenderResult<T> render(Pipeline<T> pipeline) {
        final Class<T> entityClass = pipeline.root();

        // TODO: decorate stream

        return new StandardRenderResult<>(
            baseStream(entityManager, entityClass),
            pipeline.terminatingOperation()
        );
    }

     private <T> Stream<T> baseStream(EntityManager entityManager, Class<T> entityClass) {
        final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        final CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityClass);

        final Root<T> root = criteriaQuery.from(entityClass);
        criteriaQuery.select(root);

        final TypedQuery<T> typedQuery = entityManager.createQuery(criteriaQuery);

        return typedQuery.getResultStream();
    }

    @Override
    public void close() {
        entityManager.close();
    }
}

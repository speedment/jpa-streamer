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
package com.speedment.jpastreamer.termopmodifier.standard.internal;

import com.speedment.jpastreamer.pipeline.Pipeline;
import com.speedment.jpastreamer.termopmodifier.standard.internal.model.Film;
import com.speedment.jpastreamer.termopmodifier.standard.internal.model.Film$;

import java.util.function.Predicate;
import java.util.stream.Stream;

public class FindFirstTest extends StandardTerminalOperationModifierTest<Film> {

    @Override
    Class<Film> getEntityClass() {
        return Film.class;
    }

    @Override
    protected Stream<PipelineTestCase<Film>> pipelines() {
        return Stream.of(
                noFindFirst(),
                findFirst()
        );
    }

    private PipelineTestCase<Film> noFindFirst() {
        final Pipeline<Film> noFindFirst = createPipeline(
                tof.createAllMatch(Film$.title.startsWith("A")),
                iof.createLimit(100)
        );

        final Pipeline<Film> noFindFirstExpected = createPipeline(
                tof.createAllMatch(Film$.title.startsWith("A")),
                iof.createLimit(100)
        );

        return new PipelineTestCase<>("No Find First", noFindFirst, noFindFirstExpected);
    }

    private PipelineTestCase<Film> findFirst() {
        final Predicate<Film> p = f -> f.getTitle().startsWith("A");

        final Pipeline<Film> findFirst = createPipeline(
                tof.acquireFindFirst(),
                iof.createFilter(p),
                iof.createLimit(100)
        );

        final Pipeline<Film> findFirstExpected = createPipeline(
                tof.acquireFindFirst(),
                iof.createFilter(p),
                iof.createLimit(100),
                iof.createLimit(1)
        );

        return new PipelineTestCase<>("Find First", findFirst, findFirstExpected);
    }

}

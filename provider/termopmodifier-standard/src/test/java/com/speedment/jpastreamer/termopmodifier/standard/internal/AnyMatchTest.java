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

import com.speedment.jpastreamer.field.predicate.SpeedmentPredicate;
import com.speedment.jpastreamer.pipeline.Pipeline;
import com.speedment.jpastreamer.termopmodifier.standard.internal.model.Film;
import com.speedment.jpastreamer.termopmodifier.standard.internal.model.Film$;

import java.util.function.Predicate;
import java.util.stream.Stream;

public class AnyMatchTest extends StandardTerminalOperationModifierTest<Film> {

    @Override
    Class<Film> getEntityClass() {
        return Film.class;
    }

    @Override
    protected Stream<PipelineTestCase<Film>> pipelines() {
        return Stream.of(
                noAnyMatch(),
                anyMatchLambda(),
                anyMatchSpeedmentPredicate()
        );
    }

    private PipelineTestCase<Film> noAnyMatch() {
        final Pipeline<Film> noAnyMatch = createPipeline(
                tof.createAllMatch(s -> s.equals("test")),
                iof.createLimit(100)
        );

        final Pipeline<Film> noAnyMatchExpected = createPipeline(
                tof.createAllMatch(s -> s.equals("test")),
                iof.createLimit(100)
        );

        return new PipelineTestCase<>("No Any Match", noAnyMatch, noAnyMatchExpected);
    }

    private PipelineTestCase<Film> anyMatchLambda() {
        final Predicate<Film> p = f -> f.getTitle().startsWith("A");

        final Pipeline<Film> anyMatch = createPipeline(
                tof.createAnyMatch(p),
                iof.createLimit(100)
        );

        final Pipeline<Film> anyMatchExpected = createPipeline(
                tof.createAnyMatch(s -> true),
                iof.createLimit(100)
        );

        return new PipelineTestCase<>("Any Match Lambda", anyMatch, anyMatchExpected);
    }

    private PipelineTestCase<Film> anyMatchSpeedmentPredicate() {
        SpeedmentPredicate<Film> predicate = Film$.title.startsWith("A");

        final Pipeline<Film> anyMatch = createPipeline(
                tof.createAnyMatch(predicate),
                iof.createLimit(100)
        );

        final Pipeline<Film> anyMatchExpected = createPipeline(
                tof.createAnyMatch(s -> true),
                iof.createLimit(100),
                iof.createFilter(predicate),
                iof.createLimit(1)
        );

        return new PipelineTestCase<>("Any Match Speedment Predicate", anyMatch, anyMatchExpected);
    }
    
}

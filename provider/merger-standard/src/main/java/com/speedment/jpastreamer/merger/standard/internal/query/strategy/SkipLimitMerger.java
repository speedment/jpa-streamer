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

package com.speedment.jpastreamer.merger.standard.internal.query.strategy;

import static com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperationType.LIMIT;
import static com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperationType.SKIP;
import static java.util.Objects.requireNonNull;

import com.speedment.jpastreamer.merger.QueryMerger;
import com.speedment.jpastreamer.merger.result.QueryMergeResult;
import com.speedment.jpastreamer.merger.standard.internal.query.result.StandardQueryMergeResult;
import com.speedment.jpastreamer.pipeline.Pipeline;
import com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperation;
import com.speedment.jpastreamer.pipeline.intermediate.IntermediateOperationType;

import javax.persistence.Query;
import java.util.List;

public enum SkipLimitMerger implements QueryMerger {

    INSTANCE;

    @Override
    public <T> QueryMergeResult<T> merge(Pipeline<T> pipeline, Query query) {
        requireNonNull(pipeline);
        requireNonNull(query);

        final List<IntermediateOperation<?, ?>> operations = pipeline.intermediateOperations();

        Integer skipIndex = null;
        Integer limitIndex = null;

        for (int i = 0; i < operations.size(); i++) {
            final IntermediateOperation<?, ?> intermediateOperation = operations.get(i);

            final IntermediateOperationType type = intermediateOperation.type();

            if (type != SKIP && type != LIMIT) {
                continue;
            }

            if (type == SKIP) {
                query.setFirstResult((int) getArgument(intermediateOperation));
                skipIndex = i;

                if (i + 1 < operations.size()) {
                    final IntermediateOperation<?, ?> next = operations.get(i + 1);

                    if (next.type() == LIMIT) {
                        query.setMaxResults((int) getArgument(next));
                        limitIndex = i + 1;
                    }
                }
            } else {
                query.setMaxResults((int) getArgument(intermediateOperation));
                limitIndex = i;
            }

            break;
        }

        if (skipIndex != null) {
            operations.remove((int) skipIndex);
        }

        if (limitIndex != null) {
            operations.remove((int) limitIndex);
        }

        return new StandardQueryMergeResult<>(pipeline, query);
    }

    private long getArgument(IntermediateOperation<?, ?> intermediateOperation) {
        final Object[] arguments = intermediateOperation.arguments();

        if (arguments.length != 1) {
            return 0;
        }

        if (arguments[0] instanceof Long) {
            return (long) arguments[0];
        }

        return 0;
    }
}

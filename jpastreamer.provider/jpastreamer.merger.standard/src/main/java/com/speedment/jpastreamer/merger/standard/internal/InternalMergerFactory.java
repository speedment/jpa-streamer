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
package com.speedment.jpastreamer.merger.standard.internal;

import com.speedment.jpastreamer.merger.CriteriaMerger;
import com.speedment.jpastreamer.merger.MergerFactory;
import com.speedment.jpastreamer.merger.QueryMerger;
import com.speedment.jpastreamer.merger.standard.internal.criteria.InternalCriteriaMerger;
import com.speedment.jpastreamer.merger.standard.internal.query.InternalQueryMerger;

public final class InternalMergerFactory implements MergerFactory {

    @Override
    public CriteriaMerger createCriteriaMerger() {
        return new InternalCriteriaMerger();
    }

    @Override
    public QueryMerger createQueryMerger() {
        return new InternalQueryMerger();
    }
}

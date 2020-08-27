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
package com.speedment.jpastreamer.merger.standard;

import com.speedment.jpastreamer.merger.CriteriaMerger;
import com.speedment.jpastreamer.merger.MergerFactory;
import com.speedment.jpastreamer.merger.QueryMerger;
import com.speedment.jpastreamer.merger.standard.internal.InternalMergerFactory;

public final class StandardMergerFactory implements MergerFactory {

    private final MergerFactory mergerFactory = new InternalMergerFactory();

    @Override
    public CriteriaMerger createCriteriaMerger() {
        return mergerFactory.createCriteriaMerger();
    }

    @Override
    public QueryMerger createQueryMerger() {
        return mergerFactory.createQueryMerger();
    }
}

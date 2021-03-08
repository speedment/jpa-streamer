/*
 * JPAstreamer - Express JPA queries with Java Streams
 * Copyright (c) 2020-2021, Speedment, Inc. All Rights Reserved.
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * See: https://github.com/speedment/jpa-streamer/blob/master/LICENSE
 *
 */
package com.speedment.jpastreamer.pipeline.trait;

import com.speedment.jpastreamer.pipeline.terminal.TerminalOperationType;

public interface HasType<E extends Enum<E>> {

    /**
     * Returns the type of the operation.
     *
     * @return the type of the operation
     * @see TerminalOperationType
     */
    E type();

}

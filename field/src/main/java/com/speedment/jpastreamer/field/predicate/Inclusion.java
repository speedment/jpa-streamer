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
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.speedment.jpastreamer.field.predicate;



/**
 * Determines if a range of results should be start and/or end-inclusive.
 * <p>
 * For an example, take the series {@code [1 2 3 4 5]}. If we select the range 
 * {@code (2, 4)} from this series, we will get the following results:
 * <table>
 *     <caption>Inclusion</caption>
 *      <thead>
 *          <tr>
 *              <th>Enum Constant</th>
 *              <th>Included Elements</th>
 *          </tr>
 *      </thead>
 *      <tbody>
 *          <tr>
 *              <td>{@link #START_INCLUSIVE_END_INCLUSIVE}</td>
 *              <td>{@code [2, 3, 4]}</td>
 *          </tr>
 *          <tr>
 *              <td>{@link #START_INCLUSIVE_END_EXCLUSIVE}</td>
 *              <td>{@code [2, 3]}</td>
 *          </tr>
 *          <tr>
 *              <td>{@link #START_EXCLUSIVE_END_INCLUSIVE}</td>
 *              <td>{@code [3, 4]}</td>
 *          </tr>
 *          <tr>
 *              <td>{@link #START_EXCLUSIVE_END_EXCLUSIVE}</td>
 *              <td>{@code [3]}</td>
 *          </tr>
 *      </tbody>
 * </table>
 * 
 * @author  Per Minborg
 * @author  Emil Forslund
 * @since   2.2.0
 */

public enum Inclusion {

    START_INCLUSIVE_END_INCLUSIVE(true, true),
    START_INCLUSIVE_END_EXCLUSIVE(true, false),
    START_EXCLUSIVE_END_INCLUSIVE(false, true),
    START_EXCLUSIVE_END_EXCLUSIVE(false, false);

    private final boolean startInclusive;
    private final boolean endInclusive;

    Inclusion(boolean startInclusive, boolean endInclusive) {
        this.startInclusive = startInclusive;
        this.endInclusive = endInclusive;
    }

    /**
     * Returns {@code true} if the first element in the range should be 
     * included, else {@code false}.
     * 
     * @return  {@code true} if start is included, else {@code false}
     */
    public boolean isStartInclusive() {
        return startInclusive;
    }

    /**
     * Returns {@code true} if the last element in the range should be 
     * included, else {@code false}.
     * 
     * @return  {@code true} if end is included, else {@code false}
     */
    public boolean isEndInclusive() {
        return endInclusive;
    }
}
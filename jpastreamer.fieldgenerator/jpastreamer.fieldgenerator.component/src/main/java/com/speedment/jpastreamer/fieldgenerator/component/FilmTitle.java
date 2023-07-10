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
package com.speedment.jpastreamer.fieldgenerator.component;

import java.io.Serializable;

public class FilmTitle implements Serializable {

    private String mainTitle;
    private String subTitle;

    public FilmTitle() {
        this.mainTitle = "";
        this.subTitle = "";
    }

    public FilmTitle(String mainTitle) {
        this.mainTitle = mainTitle;
        this.subTitle = "";
    }

    public FilmTitle(String mainTitle, String subTitle) {
        this.mainTitle = mainTitle;
        this.subTitle = subTitle;
    }

    public String getMainTitle() {
        return mainTitle;
    }

    public void setMainTitle(String mainTitle) {
        this.mainTitle = mainTitle;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    @Override
    public String toString() {
        return "FilmTitle{" +
                "mainTitle='" + mainTitle + '\'' +
                ", subTitle='" + subTitle + '\'' +
                '}';
    }
}

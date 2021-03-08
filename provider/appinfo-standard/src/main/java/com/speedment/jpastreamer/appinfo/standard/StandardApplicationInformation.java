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
package com.speedment.jpastreamer.appinfo.standard;


import com.speedment.jpastreamer.appinfo.ApplicationInformation;
import com.speedment.jpastreamer.appinfo.standard.internal.InternalStandardApplicationInformation;

public final class StandardApplicationInformation implements ApplicationInformation {

    private final ApplicationInformation delegate = new InternalStandardApplicationInformation();

    @Override
    public String vendor() {
        return delegate.vendor();
    }

    @Override
    public String title() {
        return delegate.title();
    }

    @Override
    public String subtitle() {
        return delegate.subtitle();
    }

    @Override
    public String repository() {
        return delegate.repository();
    }

    @Override
    public String implementationVersion() {
        return delegate.implementationVersion();
    }

    @Override
    public String specificationVersion() {
        return delegate.specificationVersion();
    }

    @Override
    public boolean isProductionMode() {
        return delegate.isProductionMode();
    }

    @Override
    public String licenseName() {
        return delegate.licenseName();
    }

    @Override
    public String banner() {
        return delegate.banner();
    }
}

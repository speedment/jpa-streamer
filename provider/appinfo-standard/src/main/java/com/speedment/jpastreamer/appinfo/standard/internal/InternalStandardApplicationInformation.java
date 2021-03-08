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
package com.speedment.jpastreamer.appinfo.standard.internal;

import com.speedment.jpastreamer.appinfo.ApplicationInformation;

import java.util.Optional;

public final class InternalStandardApplicationInformation implements ApplicationInformation {

    @Override
    public String vendor() {
        return "Speedment, Inc.";
    }

    @Override
    public String title() {
        return "JPAstreamer";
    }

    @Override
    public String subtitle() {
        return "Open Source";
    }

    @Override
    public String repository() {
        return "jpa-streamer";
    }

    @Override
    public String implementationVersion() {
        return Optional.ofNullable(InternalStandardApplicationInformation.class.getPackage().getImplementationVersion())
                .orElse("unknown version");
    }

    @Override
    public String specificationVersion() {
        return Optional.ofNullable(InternalStandardApplicationInformation.class.getPackage().getSpecificationVersion())
                .orElse("unknown version");
    }

    @Override
    public String licenseName() {
        return "LGPL 2.1";
    }

    @Override
    public String banner() {

        // http://patorjk.com/software/taag/#p=display&f=Doom&t=JPAstreamer

        return String.format(
                "   _________  ___      _                                      %n" +
                "  |_  | ___ \\/ _ \\    | |                                     %n" +
                "    | | |_/ / /_\\ \\___| |_ _ __ ___  __ _ _ __ ___   ___ _ __ %n" +
                "    | |  __/|  _  / __| __| '__/ _ \\/ _` | '_ ` _ \\ / _ \\ '__|%n" +
                "/\\__/ / |   | | | \\__ \\ |_| | |  __/ (_| | | | | | |  __/ |   %n" +
                "\\____/\\_|   \\_| |_/___/\\__|_|  \\___|\\__,_|_| |_| |_|\\___|_|");


    }
}

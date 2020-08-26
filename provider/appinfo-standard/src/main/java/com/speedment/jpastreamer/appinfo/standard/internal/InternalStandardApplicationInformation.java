package com.speedment.jpastreamer.appinfo.standard.internal;

import com.speedment.jpastreamer.appinfo.ApplicationInformation;

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
        return "0.1.0";
    }

    @Override
    public String specificationVersion() {
        return "0.1";
    }

    @Override
    public String licenseName() {
        return "LGPL 2.1";
    }

    @Override
    public String banner() {
        return String.format("   _________  ___      _                                      %n" +
                "  |_  | ___ \\/ _ \\    | |                                     %n" +
                "    | | |_/ / /_\\ \\___| |_ _ __ ___  __ _ _ __ ___   ___ _ __ %n" +
                "    | |  __/|  _  / __| __| '__/ _ \\/ _` | '_ ` _ \\ / _ \\ '__|%n" +
                "/\\__/ / |   | | | \\__ \\ |_| | |  __/ (_| | | | | | |  __/ |   %n" +
                "\\____/\\_|   \\_| |_/___/\\__|_|  \\___|\\__,_|_| |_| |_|\\___|_|   %n");
    }
}
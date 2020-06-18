package com.speedment.jpastreamer.application.standard.internal;

import com.speedment.jpastreamer.application.JpaStreamer;
import com.speedment.jpastreamer.application.JpaStreamerBuilder;

import javax.persistence.EntityManagerFactory;

import static java.util.Objects.requireNonNull;

public final class StandardJpaStreamerBuilder implements JpaStreamerBuilder {

    private final EntityManagerFactory entityManagerFactory;

    public StandardJpaStreamerBuilder(final EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = requireNonNull(entityManagerFactory);
    }

    @Override
    public JpaStreamer build() {

        // http://patorjk.com/software/taag/#p=display&f=Doom&t=JpaStreamer

        final String welcome =
                "   ___             _____ _                                      \n" +
                "  |_  |           /  ___| |                                     \n" +
                "    | |_ __   __ _\\ `--.| |_ _ __ ___  __ _ _ __ ___   ___ _ __ \n" +
                "    | | '_ \\ / _` |`--. \\ __| '__/ _ \\/ _` | '_ ` _ \\ / _ \\ '__|\n" +
                "/\\__/ / |_) | (_| /\\__/ / |_| | |  __/ (_| | | | | | |  __/ |   \n" +
                "\\____/| .__/ \\__,_\\____/ \\__|_|  \\___|\\__,_|_| |_| |_|\\___|_|   \n" +
                "      | |                                                       \n" +
                "      |_|   ";

        System.out.println(welcome);

        return new StandardJpaStreamer(entityManagerFactory);
    }

}
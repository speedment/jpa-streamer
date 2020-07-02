package com.speedment.jpastreamer.application.standard.internal;

import static java.util.Objects.requireNonNull;

import com.speedment.jpastreamer.application.JPAStreamer;
import com.speedment.jpastreamer.application.JPAStreamerBuilder;

import javax.persistence.EntityManagerFactory;
import java.util.Optional;

public final class StandardJPAStreamerBuilder implements JPAStreamerBuilder {

    private final EntityManagerFactory entityManagerFactory;

    public StandardJPAStreamerBuilder(final EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = requireNonNull(entityManagerFactory);
    }

    @Override
    public JPAStreamer build() {

        // http://patorjk.com/software/taag/#p=display&f=Doom&t=JPAStreamer

        final String welcome = String.format(
                "   _________  ___    _____ _                                      \n" +
                "  |_  | ___ \\/ _ \\  /  ___| |                                     \n" +
                "    | | |_/ / /_\\ \\ \\ `--.| |_ _ __ ___  __ _ _ __ ___   ___ _ __ \n" +
                "    | |  __/|  _  |  `--. \\ __| '__/ _ \\/ _` | '_ ` _ \\ / _ \\ '__|\n" +
                "/\\__/ / |   | | | | /\\__/ / |_| | |  __/ (_| | | | | | |  __/ |   \n" +
                "\\____/\\_|   \\_| |_/ \\____/ \\__|_|  \\___|\\__,_|_| |_| |_|\\___|_|   \n" +
                ":: JPA Streamer :: %22s                                   \n" +
                "%s                              "
                ,implementationVersion(), javaImplementationInfo());

        System.out.println(welcome);

        return new StandardJPAStreamer(entityManagerFactory);
    }


    public static String implementationVersion() {
        return Optional.ofNullable(StandardJPAStreamerBuilder.class.getPackage().getImplementationVersion())
                .orElse("unknown version");
    }

    public static String javaImplementationInfo() {
        return String.format("Running under %s %s",
                System.getProperty("java.runtime.name"),
                System.getProperty("java.runtime.version")
        );
    }

}

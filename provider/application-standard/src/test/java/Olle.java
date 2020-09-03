import com.speedment.jpastreamer.application.JPAStreamer;
import com.speedment.jpastreamer.application.StreamConfiguration;
import com.speedment.jpastreamer.field.Field;

import java.util.Set;

public class Olle {

    public static void main(String[] args) {
        JPAStreamer jpaStreamer = JPAStreamer.createJPAStreamerBuilder("olle").build();

        jpaStreamer.stream(StreamConfiguration.builder(Film.class).joining(Film$.actor).build());


    }

    private final static class Film {}

    private final static class Film$ {

        private static final Field<Film> actor= null;

    }

}

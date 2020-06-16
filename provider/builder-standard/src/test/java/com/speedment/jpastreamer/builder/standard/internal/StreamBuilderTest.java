package com.speedment.jpastreamer.builder.standard.internal;

import com.speedment.jpastreamer.pipeline.Pipeline;
import com.speedment.jpastreamer.pipeline.terminal.TerminalOperation;
import com.speedment.jpastreamer.renderer.RenderResult;
import com.speedment.jpastreamer.renderer.Renderer;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

class StreamBuilderTest {

    private static final Factories FACTORIES = InjectedFactories.INSTANCE;

    @Test
    @Disabled
    void filter() {
        final Renderer renderer = new MockRenderer();
        Stream<String> builder = new StreamBuilder<>(FACTORIES, String.class, renderer);

        builder
                .skip(1)
                .limit(1)
                .count();

    }

    private static final class MockRenderer implements Renderer {

        @Override
        public <T> RenderResult<T> render(Pipeline<T> pipeline) {

            System.out.println(pipeline);

            return new RenderResult<T>() {
                @Override
                public Stream<T> stream() {
                    return (Stream<T>) Stream.of("A", "B", "C");
                }

                @Override
                public TerminalOperation<?, ?> terminalOperation() {
                    return FACTORIES.terminal().acquireCount();
                }
            };
        }

        @Override
        public void close() {}
    }

}